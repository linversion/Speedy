# :core:designsystem 模块设计规范

## 模块定位
`:core:designsystem` 是 Now in Android 项目的**原子级设计系统**。它位于项目依赖层级的底层：
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

---
*此文档用于指导 :core:designsystem 模块的迭代与维护。*
