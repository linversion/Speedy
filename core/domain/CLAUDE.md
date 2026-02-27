# :core:domain 模块设计规范

## 模块定位
`:core:domain` 是项目的**业务逻辑层（领域层）**。它位于 `data` 层之上，负责组合多个 Repository 的数据或处理复杂的业务计算逻辑。

## 核心设计逻辑
1. **UseCase 模式**：每个业务逻辑封装为一个 UseCase 类（如 `GetFollowableTopicsUseCase`）。
2. **逻辑组合**：当一个功能需要同时调用多个 Repository 时，应在 UseCase 中组合，而不是在 ViewModel 中组合。
3. **简化上层**：UseCase 应该返回 UI 层最易用的数据格式，减轻 ViewModel 的负担。

## 准入规则
- **复杂业务查询**：如“获取用户关注的话题并按某种逻辑排序”。
- **多仓库联动**：涉及跨 Repository 的数据聚合逻辑。

## 测试规范
- **测试类型**：**Unit Tests (test)**。
- **测试重点**：
    - **业务规则验证**：验证 UseCase 中的计算、过滤、排序逻辑。
- **要求**：
    - 每个 UseCase 必须配有单元测试。
    - 使用 FakeRepository 来模拟数据层，确保测试运行速度和稳定性。

---
*此文档用于指导 :core:domain 模块的迭代与维护。*
