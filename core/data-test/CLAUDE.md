# :core:data-test 模块设计规范

## 模块定位
`:core:data-test` 是项目的**测试基础设施层**。它专门为其他模块（如 `:core:domain`, `:feature:*`）提供数据层的虚假实现（Fakes）和测试辅助工具。

## 核心设计逻辑
1. **测试双倍 (Test Doubles)**：提供 Repository 接口的 Fake 实现（如 `FakeNewsRepository`），这些实现通常在内存中管理数据，避免了对数据库或网络的依赖。
2. **环境模拟**：提供网络状态、时区等环境因素的受控模拟实现（如 `AlwaysOnlineNetworkMonitor`）。
3. **DI 集成**：通过 Hilt 的 `TestDataModule`，方便在测试中一键替换真实的数据层实现。

## 准入规则
- **Fake Repository**：对应 `:core:data` 中接口的 Fake 实现。
- **测试专用 Module**：用于 Hilt 测试的 Hilt Module。
- **环境模拟器**：用于测试环境的 Monitor 模拟类。

## 测试规范
- **职责**：本模块的主要目的是**服务于其他模块的测试**。
- **自测要求**：
    - 复杂的 Fake 实现（如涉及复杂内存状态管理的 Fake）应编写单元测试，确保其行为逻辑（如增删改查后的内存状态）与真实 Repository 的预期行为一致。
- **使用建议**：
    - 在编写功能模块的单元测试时，应优先依赖此模块提供的 Fakes，而不是使用 Mock 框架（如 Mockito/MockK）手动打桩，以提高测试的稳定性和可维护性。

---
*此文档用于指导 :core:data-test 模块的迭代与维护。*
