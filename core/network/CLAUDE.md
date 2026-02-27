# :core:network 模块设计规范

## 模块定位
`:core:network` 是项目的**远程数据源层**。它负责所有与服务器交互的逻辑，通常使用 Retrofit 或其他网络库。

## 核心设计逻辑
1. **API 定义**：定义 Retrofit 接口和网络请求参数。
2. **DTO 模型**：定义网络传输对象（Network Model），这些模型应仅在网络层使用，并在 `data` 层转换为领域模型。
3. **策略切换**：支持通过 DI 切换 Demo（本地 JSON）和生产（真实 API）数据源。

## 准入规则
- **NetworkDataSource 接口与实现**：如 `NiaNetworkDataSource`。
- **网络 DTO 类**：如 `NetworkNewsResource`。
- **网络库配置**：Retrofit 实例、OkHttp 拦截器等。

## 测试规范
- **测试类型**：**Unit Tests (test)**。
- **测试重点**：
    - **JSON 解析**：验证 Retrofit 接口是否能正确解析远程返回的 JSON 数据（通常配合 MockWebServer）。
    - **网络实现逻辑**：验证 `RetrofitNiaNetwork` 或 `DemoNiaNetwork` 是否能正确调用 API 并返回 DTO。
- **要求**：
    - 复杂的 JSON DTO 必须有解析测试，确保其字段映射与服务器一致。

---
*此文档用于指导 :core:network 模块的迭代与维护。*
