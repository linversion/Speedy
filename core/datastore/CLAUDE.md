# :core:datastore 模块设计规范

## 模块定位
`:core:datastore` 是项目的**轻量级偏好存储层**。它基于 Jetpack DataStore，用于存储用户的个性化设置和简单的状态。

## 核心设计逻辑
1. **Proto DataStore**：优先使用 Proto DataStore 来保证类型安全。
2. **单一职责**：只处理用户偏好（如主题、动态色彩开关、已读状态版本号）。

## 准入规则
- **DataStore 序列化器**：`UserPreferencesSerializer`。
- **配置访问接口**：`NiaPreferencesDataSource`。
- **Proto 文件定义**：用户的偏好模型定义。

## 测试规范
- **测试类型**：**Unit Tests (test)**。
- **测试重点**：
    - **读写一致性**：验证写入的数据能被正确读取，并符合 proto 定义。
    - **迁移逻辑**：如果存在旧数据的迁移逻辑，必须通过测试验证。
- **要求**：
    - 测试应使用临时的 DataStore 文件，并在测试结束后清理，确保环境独立。

---
*此文档用于指导 :core:datastore 模块的迭代与维护。*
