# Now in Android (NiA) 项目全局设计规范

## 1. 项目概览与技术栈
一个遵循现代 Android 开发最佳实践的开源项目，从 Nowinandroid项目中抽离出来的一个项目基底，方便新项目快速启动。

- **语言**: Kotlin + [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
- **UI 框架**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3 + Adaptive Layout)
- **依赖注入**: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- **架构**: 响应式分层架构 (feature UI -> Domain -> Data -> :core:network / :core:datastore)
- **数据库**: [Room](https://developer.android.com/training/data-storage/room)
- **网络**: [Retrofit](https://square.github.io/retrofit/) + Kotlinx Serialization
- **图片加载**: [Coil](https://coil-kt.github.io/coil/)
- **性能监控**: JankStats + Firebase Performance

## 2. 依赖管理与构建逻辑 (Dependency Management)
项目使用 Gradle 现代化的构建系统配置：

- **Version Catalog**: 所有依赖版本和库定义统一管理在 `gradle/libs.versions.toml` 中。
- **Convention Plugins**: 复杂的构建逻辑（如 Compose 配置、Room 设置、Hilt 绑定、代码质量检查）封装在 `:build-logic` 模块中，通过自定义插件（如 `nowinandroid.android.library.compose`）在各模块间共享。
- **添加依赖原则**:
    1. 检查 `libs.versions.toml` 是否已存在该依赖。
    2. 如果是通用的构建配置，修改 `:build-logic`。
    3. 在各模块的 `build.gradle.kts` 中通过别名引入：`implementation(libs.androidx.compose.ui)`。
    4. 未经用户同意不得修改已有依赖的版本

## 2.1. 导航系统 (Navigation)
项目采用 **Compose Navigation (类型安全版)**：

- **类型安全路由**: 使用 Kotlinx Serialization 定义路由对象（如 `@Serializable data object ForYouRoute`），取代传统的字符串常量。
- **NavHost 配置**: 全局导航图定义在 `:app` 模块的 `AppNavHost.kt` 中。
- **模块化导航**: 每个 Feature 模块通过 `NavGraphBuilder` 扩展函数（如 `forYouSection`）暴露其内部路由。

### 弹窗与对话框管理 (Popups & Dialogs)
为了避免在 Compose 页面中编写大量的 `Boolean` 状态来控制弹窗显隐（即避免“状态驱动弹窗”导致的 UI 逻辑臃肿），项目推荐以下实践：
1. **Dialog 作为目的地**: 优先将复杂的弹窗定义为导航图中的一个 `dialog(...)` 目的地。
    - 就像 `composable(...)` 一样，在 `NavGraphBuilder` 中注册路由。
    - 调用 `navController.navigate(MyDialogRoute)` 即可弹出，无需在页面中声明 `showDialog` 变量。
    - 弹窗拥有独立的 `NavBackStackEntry`，可以拥有自己独立的 `ViewModel`。
2. **适用场景**:
    - **导航目的地**: 涉及复杂业务逻辑、多步骤流程或需要独立 ViewModel 的弹窗。
    - **状态驱动 (if-show)**: 仅用于极简单的二次确认框（如“确定删除吗？”）或全局单例级的简单对话框（如 `SettingsDialog`）。

### 多 Activity 模式处理
虽然项目推崇 **单 Activity 模式**，但在混合开发或特殊业务场景下，Navigation 也支持将 Activity 作为导航目标：
1. **Activity 作为目的地 (Activity Destination)**:
    - 在 `NavGraphBuilder` 中使用 `activity<Route> { activityClass = MyActivity::class }` 注册。
    - 这样可以像普通 Composable 一样使用 `navController.navigate(MyActivityRoute)` 跳转，并支持类型安全的参数传递。
    - 适用于需要完全独立进程空间、特定 Window 属性或复用旧版 Activity 的场景。
2. **标准 Intent 跳转**:
    - 对于不受 `NavController` 管理的外部 Activity，使用标准的 `context.startActivity(Intent(...))`。
3. **独立导航栈**:
    - 新 Activity 启动后将拥有自己独立的 `NavHost`，无法直接通过 `popBackStack` 回到前一个 Activity 的 Compose 页面（除非结束当前 Activity）。


## 3. 项目结构 (Module Hierarchy)

### App 模块
- **`:app`**: 壳工程，负责所有模块的组装、全局导航配置及应用初始化。

### Feature 模块 (`:feature:*`)
- **原则**: 按功能拆分（如 `foryou`, `bookmarks`, `search`, `interests`）。
- **内容**: 包含该功能的 ViewModel、UI 界面（Composables）和导航逻辑。
- **依赖**: 仅依赖 `:core:*` 模块，**禁止** Feature 模块间互相依赖。

### Core 模块 (`:core:*`)
- **`:core:model`**: 纯数据模型，全局共享。
- **`:core:data`**: 数据仓库层（Repository），负责本地/远程数据协调。
- **`:core:domain`**: 业务逻辑层（UseCase）。
- **`:core:ui`**: 业务相关公共 UI 组件（感知业务模型）。
- **`:core:designsystem`**: 原子级设计系统（纯样式，模型无关）。
- **`:core:network` / `:core:database` / `:core:datastore`**: 数据源实现。
- **`:core:common`**: 全局工具类（协程调度、通用 Result）。

### UseCase 使用原则
**满足以下任一条件时，才需要创建 UseCase：**
1. 需要协调**多个 Repository** 完成一个业务操作。
2. 包含真实的**业务规则**（不只是空值检查，而是领域逻辑判断）。
3. 需要**副作用编排**，例如登录成功后同时清除缓存、触发埋点上报。
4. 同一段逻辑被**多个 ViewModel 复用**。
5. 需要将底层异常（网络/数据库）**转换为业务语义的异常**。

**以下情况可以跳过 UseCase，ViewModel 直接注入 Repository：**
- 纯粹的数据读取，没有任何业务判断（如 `repository.getUser()`）。
- UseCase 的实现只是 `return repository.doX(params)` 的透传转发。

> 架构规范是指导，不是教条。避免为了"有 UseCase"而写空洞的转发层。

### 测试支持模块
- **`:core:testing`**: 基础测试运行器与 JUnit Rules。
- **`:core:data-test`**: 提供 Repository 的 Fake 实现，供 Feature 模块单元测试使用。

## 4. 测试策略
- **单元测试 (test)**: 针对 ViewModel, UseCase, Repository。优先使用 `:core:data-test` 提供的 Fakes。
- **屏幕截图测试 (Screenshot Tests)**: 针对 `:core:ui` 和 `:feature:*`。使用 Roborazzi 验证视觉回归。
- **仪器测试 (androidTest)**: 针对数据库 DAO 及需要真实 Android 环境的集成测试。
- **基准测试 (benchmarks)**: 监控启动速度和滑动流畅度。

## 5. 开发新功能 (New Feature) 指南

如果你要开发一个新的功能模块（例如 `feature:chat`）：

1.  **创建模块**: 在 `feature` 目录下创建新子模块，并在 `settings.gradle.kts` 中注册。
2.  **定义模型**: 如果有新业务模型，在 `:core:model` 中定义。
3.  **数据层实现**:
    - 如果涉及新接口，在 `:core:network` 增加 API 定义。
    - 如果涉及持久化，在 `:core:database` 增加 Entity 和 DAO。
    - 在 `:core:data` 中实现 Repository 接口，并在 `:core:data-test` 提供对应的 Fake 实现。
4.  **UI 表现**:
    - 在新 feature module 中编写 Composables。
    - 优先使用 `:core:designsystem` 的原子组件和 `:core:ui` 的业务组件。
    - 在 `:core:ui` 中定义该功能所需的预览辅助类（PreviewParameterProvider）。
5.  **逻辑注入**: 编写 ViewModel，通过 Hilt 注入所需的 UseCase 或 Repository。
6.  **配置导航**: 在 `:app` 模块的导航图中关联新 Feature 的 Entry point。
在 feature的内部 navigation目录中配置示例：
```agsl
@Serializable data object GreetingRoute

fun NavController.navigateToGreeting(navOptions: NavOptions) = navigate(route = GreetingRoute, navOptions)

fun NavGraphBuilder.greetingScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable<GreetingRoute> {
        Greeting(
            name = "Hello World!",
            onShowSnackbar = onShowSnackbar
        )
    }
}
```

7. **编写测试**:
    - 使用 `:core:data-test` 编写 ViewModel 的单元测试。
    - 为关键界面编写截图测试。

---
*本规范旨在保持项目的高内聚、低耦合以及可测试性。*
