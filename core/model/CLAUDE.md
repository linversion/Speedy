# :core:model 模块设计规范

## 模块定位
`:core:model` 是项目的**纯数据模型层**。它不包含任何业务逻辑，仅定义应用中使用的公共数据结构。它是整个项目依赖图的叶子节点之一。

## 核心设计逻辑
1. **纯粹性**：仅包含 Kotlin Data Class、Enum 和 Interface。严禁包含 Android Framework 依赖或 UI 相关逻辑。
2. **全局共享**：作为底层模块，被 `data`, `network`, `database`, `ui`, `feature` 等几乎所有模块引用。
3. **不可变性**：优先使用 `val` 定义不可变模型，确保数据流的安全。

## 准入规则
- **领域模型**：如 `NewsResource`, `Topic`。
- **用户状态模型**：如 `UserData`, `UserNewsResource`（通常是业务模型与用户偏好的结合体）。
- **配置枚举**：如 `ThemeBrand`, `DarkThemeConfig`。

---
*此文档用于指导 :core:model 模块的迭代与维护。*
