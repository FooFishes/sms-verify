package work.foofish.smsverify.core;

public interface SmsStrategy {
    /**
     * 发送短信
     */
    SmsResponse send(SmsRequest request);

    /**
     * 获取提供商名称
     */
    String getProviderName();
}
