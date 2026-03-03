# :core:designsystem 模块设计规范

## 模块定位
`:core:designsystem` 是项目的**原子级设计系统**。它位于项目依赖层级的底层：
- **下层**：无（仅依赖 Compose/Android 基础库）
- **本层**：提供基础风格与原子组件
- **上层**：`:core:ui`（业务通用 UI）和 `:feature:*` 模块

## 核心设计逻辑
1. **模型无关性**：**禁止**依赖 `:core:model`。组件应仅接收基础数据类型（如 `String`, `Boolean`, `Color`）或 Compose Lambdas。
2. **原子性**：只包含最小单元的 UI 元素（按钮、标签、开关、背景）和样式系统。
3. **视觉一致性**：通过统一的 `NiaTheme` 管理颜色、字体、渐变和形状，确保全应用视觉风格高度统一。

## 准入规则

- **原子组件**：如 `NiaButton`, `NiaChip`, `NiaTab` 等不含业务逻辑的基础组件。
- **主题系统**：颜色 (Color)、排版 (Type)、渐变 (Gradient)、全局背景 (Background) 的定义。
- **图标资产**：`NiaIcons` 枚举和图标矢量资源。
- **底层 UI 逻辑**：如通用的滚动条行为 (`scrollbar`)、异步图片加载基础封装 (`DynamicAsyncImage`)。

## 测试规范
- **测试类型**：**Screenshot Tests**。
- **测试重点**：
    - **样式覆盖面**：验证组件在所有主题变体、状态（Enabled/Disabled, Selected/Unselected）下的显示。
    - **主题一致性**：验证 `NiaTheme` 切换时颜色和字体的应用。
- **要求**：
    - 每个原子组件必须配有对应的 Compose Preview 且覆盖主要状态，并作为截图测试的基准。

## Composable Preview 编写规范

Preview 用于 IDE 实时查看，截图测试用于 CI 自动回归检测，两者互补。

### 覆盖要求

| 模块层级 | 必须写 Preview 的对象 | 覆盖维度 |
|---------|---------------------|---------|
| designsystem（原子组件层） | 每个公开的原子组件 | 所有状态组合 × 亮/暗主题 |
| core:ui（业务通用 UI 层） | 每个共享业务组件 | 典型状态 + 边界情况（超长文本、空值、缺图等） |
| feature（页面层） | 每个 Screen 级 Composable | 每个 UiState 分支（Loading / Success / Empty / Error）× 多设备尺寸 |

### 多重预览注解（Multipreview Annotations）

定义自定义注解消除重复的 `@Preview` 堆叠，两者可叠加使用：

```kotlin
// 定义在 designsystem 模块，适用于原子组件
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme")
annotation class ThemePreviews

// 定义在 core:ui 模块，适用于全屏页面和自适应布局组件
@Preview(name = "phone", device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480")
@Preview(name = "landscape", device = "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480")
@Preview(name = "foldable", device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480")
@Preview(name = "tablet", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480")
annotation class DevicePreviews
```

### 编写模板

**designsystem 层** — `@ThemePreviews` + 硬编码数据 + 主题→背景→组件包裹：
```kotlin
@ThemePreviews
@Composable
private fun SomeButtonPreview() {
    AppTheme { AppBackground { SomeButton(onClick = {}, text = { Text("Label") }) } }
}
```

**core:ui 层** — 在上述基础上改用 `@Preview` + `@PreviewParameter(Provider::class)` 注入测试数据。涉及图片加载时设置 `LocalInspectionMode provides true`。为边界情况编写专门的 Preview。

**feature 层** — 改用 `@DevicePreviews` + `@PreviewParameter`。为每个 UiState 分支写独立 Preview 函数。页面自带 Background，外层只需包裹主题。

### 组织约定
- Preview 放在同文件末尾，`private fun`，`{组件名}{状态}Preview` 命名
- 每个 Preview 只展示一种状态，便于截图测试独立捕获
- 跨模块共享的 `PreviewParameterProvider` 放 `:core:ui`，feature 专用的放各自模块
- 建立共享 `PreviewParameterData` object 集中管理样本数据
- **designsystem 层禁止依赖 model 层**，不使用 Provider，一律硬编码

## 截图测试（Screenshot Tests）编写规范

基于 Roborazzi，辅助函数定义在 `:core:screenshot-testing` 模块中。

### 测试类结构

```kotlin
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)           // 原生图形渲染
@Config(application = HiltTestApplication::class, qualifiers = "480dpi")
@LooperMode(LooperMode.Mode.PAUSED)               // 确保渲染完成后截图
class SomeComponentScreenshotTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun component_multipleThemes() {
        composeTestRule.captureMultiTheme("SomeComponent") { description ->
            Surface { SomeComponent(label = "$description Label") }
        }
    }

    @Test
    fun component_selectedState() {
        composeTestRule.captureMultiTheme("SomeComponent", "SomeComponentSelected") { desc ->
            Surface { SomeComponent(selected = true, label = "$desc Label") }
        }
    }
}
```

需要 Hilt 注入时（app 层集成测试），额外添加 `@HiltAndroidTest`、`HiltAndroidRule(order=0)` 和 `createAndroidComposeRule<HiltComponentActivity>(order=1)`。

### 核心辅助函数

**`captureMultiTheme(name, overrideFileName?, shouldCompare*)`** — 原子组件用。自动排列亮/暗 × 品牌主题 × 动态颜色组合。通过 `shouldCompareDarkMode`、`shouldCompareDynamicColor`、`shouldCompareAndroidTheme`（均默认 true）控制维度。截图路径：`src/test/screenshots/{name}/{file}_{light|dark}_{theme}_{dynamic}.png`

**`captureMultiDevice(screenshotName)`** — 全屏页面用。一次调用自动在 Phone/Foldable/Tablet 三种尺寸截图并集成 ATF 无障碍检查，但默认只跑 lightMode。为每个 UiState 分支各调用一次。截图路径：`src/test/screenshots/{name}_{phone|foldable|tablet}.png`

**`captureForDevice(deviceName, deviceSpec, screenshotName, darkMode)`** — 单设备精确控制。`captureMultiDevice` 内部就是循环调用此函数。最常见的独立使用场景是补充暗色模式变体（`darkMode = true`），也适用于自定义设备 spec 或自定义 `RoborazziOptions` 的场合。截图路径：`src/test/screenshots/{name}_{deviceName}.png`

### 高级技巧

- **动画帧捕获**：`mainClock.autoAdvance = false` → `advanceTimeBy(ms)` → `captureRoboImage()`
- **大字体测试**：`DeviceConfigurationOverride(FontScale(2f) then ForcedSize(DpSize(...)))` 包裹组件
- **Window Insets**：`DeviceConfigurationOverride.WindowInsets(WindowInsetsCompat.Builder()...build())` 模拟系统栏
- **无障碍抑制**：`accessibilitySuppressions = Matchers.allOf(matchesCheck(...), matchesElements(...))` 豁免已知误报

### 运行命令

```bash
无 Flavor情况下
./gradlew updateRoborazziDebug                          # 录制基准截图
./gradlew verifyRoborazziDebug                          # CI 验证（与基准对比）
./gradlew :core:designsystem:updateRoborazziDebug       # 单模块运行
```

截图基准 PNG 应提交到版本控制中。

---
*此文档用于指导 :core:designsystem 模块的迭代与维护。*
