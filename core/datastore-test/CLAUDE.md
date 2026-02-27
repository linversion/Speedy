# :core:datastore-test 模块设计规范

## 模块定位
`:core:datastore-test` 是项目的**DataStore 测试工具层**。它为其他模块的集成测试和单元测试提供内存中的 DataStore 实现，避免测试对磁盘 I/O 的依赖。

## 核心设计逻辑
1. **测试隔离**：提供 `InMemoryDataStore`，确保每个测试用例运行时都有一个干净、独立的存储环境，且运行速度极快。
2. **DI 集成**：通过 `TestDataStoreModule` 提供 Hilt 绑定，方便在测试中一键替换真实的 `DataStore`。

## 准入规则
- **DataStore Fakes**：内存实现的 DataStore 工具类。
- **测试专用 Module**：用于 Hilt 的测试 Module。

## 测试规范
- **使用建议**：
    - 在任何需要验证持久化偏好逻辑的单元测试或集成测试中，应通过 Hilt 注入此模块提供的 `TestDataStoreModule`。
    - 避免在测试中手动创建文件系统上的 DataStore，优先使用 `InMemoryDataStore`。

---
*此文档用于指导 :core:datastore-test 模块的迭代与维护。*
