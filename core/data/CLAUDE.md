core:data 模块设计规范

## 模块定位
`:core:data` 是项目的**数据仓库层**。它负责协调本地数据库 (`:core:database`)、远程网络 (`:core:network`) 和用户偏好 (`:core:datastore`)，为上层提供统一的数据访问接口。

## 核心设计逻辑
1. **Repository 模式**：通过 Repository 封装数据来源（Local vs Remote）。上层模块不应知道数据是从缓存还是网络获取的。
2. **数据同步**：包含 `SyncUtilities`，处理后台同步、增量更新（Offline-first 策略）的逻辑。
3. **模型转换**：负责将 `database` 的 Entity 模型或 `network` 的 DTO 模型转换为 `core:model` 中的领域模型。

## 准入规则
- **Repository 接口与实现**：如 `NewsRepository`, `TopicsRepository`。
- **数据同步逻辑**：Worker 的配置、Sync 状态管理。
- **业务级数据工具**：处理数据转换的 Mapper 类。

## 测试规范
- **测试类型**：主要使用 **Unit Tests (test)**。
- **测试重点**：
    - **Repository 行为**：使用 Fake 数据源（FakeDao, FakeNetworkDataSource）验证数据获取流程、异常处理及离线优先策略。
    - **数据映射 (Mapping)**：验证 Entity/DTO 到 Model 的转换逻辑是否正确。
- **要求**：
    - 所有 Repository 的公共方法必须有单元测试覆盖。
    - 涉及同步逻辑时，需测试成功、失败及部分成功的边界情况。

---
*此文档用于指导 :core:data 模块的迭代与维护。*
