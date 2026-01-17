package work.foofish.smsverify.core;

import lombok.Builder;

public class SmsException extends RuntimeException {
    public SmsException(String phone, String provider, String templateId, String errorMessage) {
        super(String.format("短信发送失败: phone=%s, provider=%s, templateId=%s, errorMessage=%s", phone, provider, templateId, errorMessage));
    }

    public SmsException(String phone, String provider, String templateId, String errorCode, String errorMessage) {
        super(String.format("短信发送失败: phone=%s, provider=%s, templateId=%s, errorCode=%s, errorMessage=%s", phone, provider, templateId, errorCode, errorMessage));
    }

    public SmsException(String phone, String provider, String errorMessage) {
        super(String.format("短信发送失败: phone=%s, provider=%s, errorMessage=%s", phone, provider, errorMessage));
    }

    public SmsException(String phone, String errorMessage) {
        super(String.format("短信发送失败: phone=%s, errorMessage=%s", phone, errorMessage));
    }
}
