# :core:notifications 模块设计规范

## 模块定位
`:core:notifications` 是项目的**系统通知层**。它负责向用户展示系统级通知（如通知栏消息）。

## 核心设计逻辑
1. **抽象化**：定义 `Notifier` 接口，解耦具体通知实现。
2. **实现多样性**：
    - `SystemTrayNotifier`：真正的系统托盘通知实现。
    - `NoOpNotifier`：空实现，用于某些不需要通知的环境（如部分测试场景）。
3. **Deep Linking 集成**：负责构建通知点击后的 PendingIntent，引导用户通过深链接进入应用特定页面。

## 准入规则
- **通知接口与实现**：`Notifier` 接口及其各种实现类。
- **通知资源**：与通知相关的特定字符串资源或图标（如果没放在 designsystem）。

## 测试规范
- **测试类型**：**Unit Tests (test)**。
- **测试重点**：
    - **通知构建逻辑**：验证 `NotificationCompat.Builder` 是否正确配置了标题、内容和 PendingIntent。
    - **深链接生成**：验证生成的 URI 是否符合应用定义的深链接规则。

---
*此文档用于指导 :core:notifications 模块的迭代与维护。*
