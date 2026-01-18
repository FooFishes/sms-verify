package work.foofish.smsverify.core;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SmsResponse {
    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 请求 ID (成功和失败时都可能有)
     */
    private String requestId;

    /**
     * 错误码 (原始厂商错误码)
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 提供商
     */
    private String provider;

    /**
     * 目标手机号
     */
    private String phone;

    /**
     * 模板 ID
     */
    private String templateId;

    /**
     * 错误类型 (便于调用方做分类处理)
     */
    @Builder.Default
    private SmsErrorType errorType = SmsErrorType.NONE;

    /**
     * 时间戳 (毫秒)
     */
    @Builder.Default
    private long timestamp = System.currentTimeMillis();

    /**
     * 判断是否可重试
     */
    public boolean isRetryable() {
        return !success && errorType != null && errorType.isRetryable();
    }

    public static SmsResponse success(String requestId, String provider, String phone, String templateId) {
        return SmsResponse.builder()
                .success(true)
                .requestId(requestId)
                .provider(provider)
                .phone(phone)
                .templateId(templateId)
                .errorType(SmsErrorType.NONE)
                .build();
    }

    public static SmsResponse fail(String errorCode, String errorMessage, String provider,
                                   String phone, String templateId, SmsErrorType errorType) {
        return SmsResponse.builder()
                .success(false)
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .provider(provider)
                .phone(phone)
                .templateId(templateId)
                .errorType(errorType)
                .build();
    }

    public static SmsResponse fail(String errorCode, String errorMessage, String provider,
                                   String phone, String templateId, String requestId, SmsErrorType errorType) {
        return SmsResponse.builder()
                .success(false)
                .requestId(requestId)
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .provider(provider)
                .phone(phone)
                .templateId(templateId)
                .errorType(errorType)
                .build();
    }
}
