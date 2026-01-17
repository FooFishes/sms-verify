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
     * 请求 ID
     */
    private String requestId;

    /**
     * 错误码
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

    public static SmsResponse success(String requestId, String provider) {
        return SmsResponse.builder()
                .success(true)
                .requestId(requestId)
                .provider(provider)
                .build();
    }

    public static SmsResponse fail(String errorCode, String errorMessage, String provider) {
        return SmsResponse.builder()
                .success(false)
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .provider(provider)
                .build();
    }
}
