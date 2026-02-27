# :core:datastore-proto 模块设计规范

## 模块定位
`:core:datastore-proto` 是项目的**数据序列化协议层**。它专门用于定义 Jetpack DataStore 使用的 Protobuf 结构。

## 核心设计逻辑
1. **类型安全**：通过 Protobuf 定义结构化的用户偏好数据，确保多模块访问时的数据一致性和类型安全。
2. **单一职责**：只包含 `.proto` 文件定义。不包含业务逻辑或 Android 相关代码。
3. **生成的代码**：Gradle 插件会根据这些定义自动生成 Java/Kotlin 映射类。

## 准入规则
- **Proto 协议定义**：如 `user_preferences.proto`，定义用户设置、主题配置、已读状态等持久化结构。

## 测试规范
- **职责**：本模块通常不需要手动编写测试，因为其行为由 Protobuf 编译器保证。
- **集成验证**：在 `:core:datastore` 模块中通过单元测试验证序列化和反序列化逻辑。

---
*此文档用于指导 :core:datastore-proto 模块的迭代与维护。*
