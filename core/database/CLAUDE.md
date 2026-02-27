# :core:database 模块设计规范

## 模块定位
`:core:database` 是项目的**本地持久化层**。它基于 Room 数据库，负责定义表结构、索引和本地查询逻辑。

## 核心设计逻辑
1. **Entity 模型**：定义本地数据库表（Database Entity）。
2. **DAO 模式**：通过 DAO 提供细粒度的增删改查操作。
3. **迁移逻辑**：负责数据库版本的升降级（Migrations）。

## 准入规则
- **Room Entity 类**：如 `NewsResourceEntity`。
- **DAO 接口**：如 `NewsResourceDao`。
- **数据库实例配置**：`NiaDatabase` 类。

## 测试规范
- **测试类型**：主要使用 **Instrumented Tests (androidTest)**，因为 Room 需要在 Android 环境下验证 SQL。
- **测试重点**：
    - **DAO 查询**：验证增删改查逻辑是否符合预期，特别是复杂的关联查询。
    - **数据库迁移**：验证 `DatabaseMigrations` 是否能正确处理版本升级而不导致数据丢失。
- **要求**：
    - 每个新定义的 DAO 方法都应有对应的集成测试。
    - 测试时应使用 `inMemoryDatabaseBuilder` 以确保测试间的隔离。

---
*此文档用于指导 :core:database 模块的迭代与维护。*
