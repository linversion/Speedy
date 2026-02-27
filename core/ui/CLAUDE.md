# :core:ui 模块设计规范

## 模块定位
`:core:ui` 是 Now in Android 项目的**业务相关公共 UI 组件库**。它位于层级结构的中层：
- **上层**：`:feature:*` 模块（具体页面逻辑）
- **中层**：`:core:ui`（业务通用 UI）
- **下层**：`:core:designsystem`（原子级设计系统，不识业务模型）

## 核心设计逻辑
1. **业务模型感知**：与 `designsystem` 不同，本模块允许依赖 `:core:model`。组件可以直接接收 `NewsResource` 或 `Topic` 等业务对象。
2. **跨模块复用**：如果一个 UI 组件或逻辑在两个及以上 `feature` 模块中使用，应下沉至此。
3. **UI 基础设施**：存放与 UI 渲染紧密相关的全局工具（如时区处理、性能监控 JankStats、分析统计埋点）。

## 准入规则
- **共享业务组件**：例如在不同屏幕出现的 `NewsResourceCard`。
- **UI 扩展函数**：通用的 Compose 修饰符或辅助函数（如 `TrackScrollJank`）。
- **预览辅助**：跨模块使用的 `PreviewParameterProvider` 或自定义预览注解。
- **全局 UI 状态**：如 CompositionLocal 定义（例如 `LocalTimeZone`）。

## 测试规范
- **测试类型**：**Screenshot Tests (androidTest)** 和 **Compose UI Tests**。
- **测试重点**：
    - **视觉回归测试**：确保组件在不同主题（亮暗色）、字体大小、屏幕尺寸下渲染正常。
    - **交互逻辑**：验证组件的基本点击、滑动交互。
- **要求**：
    - 关键业务组件（如 `NewsResourceCard`）必须有截图测试。
    - 配合 `PreviewParameterProvider` 生成多种状态下的测试用例。

---
*此文档用于指导 :core:ui 模块的迭代与维护。*
