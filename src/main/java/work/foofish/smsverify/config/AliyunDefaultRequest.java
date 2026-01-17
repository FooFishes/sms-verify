package work.foofish.smsverify.config;

import work.foofish.smsverify.core.SmsRequest;

import java.util.LinkedHashMap;


public class AliyunDefaultRequest {
    private static SmsRequest build(String phone, String templateId, String code, String minutes) {
        return SmsRequest.builder()
                .phone(phone)
                .templateId(templateId)
                .params(new LinkedHashMap<String, String>() {{
                    put("code", code);
                    put("min", minutes);
                }})
                .build();
    }


    /**
     * 登录/注册验证码短信
     * 模板ID: 100001
     * 模板内容: 您的验证码为${code}。尊敬的客户，以上验证码${min}分钟内有效，请注意保密，切勿告知他人。
     * 占位符: ${code} - 验证码, ${min} - 有效时间(分钟)
     */
    public static SmsRequest auth(String phone, String code, String minutes) {
        return build(phone, "100001", code, minutes);
    }

    /**
     * 修改绑定手机号验证码短信
     * 模板ID: 100002
     * 模板内容: 尊敬的客户，您正在进行修改手机号操作，您的验证码为${code}。以上验证码${min}分钟内有效，请注意保密，切勿告知他人。
     * 占位符: ${code} - 验证码, ${min} - 有效时间(分钟)
     */
    public static SmsRequest change(String phone, String code, String minutes) {
        return build(phone, "100002", code, minutes);
    }

    /**
     * 重置密码验证码短信
     * 模板ID: 100003
     * 模板内容: 尊敬的客户，您正在进行重置密码操作，您的验证码为${code}。以上验证码${min}分钟内有效，请注意保密，切勿告知他人。
     * 占位符: ${code} - 验证码, ${min} - 有效时间(分钟)
     */
    public static SmsRequest forget(String phone, String code, String minutes) {
        return build(phone, "100003", code, minutes);
    }

    /**
     * 绑定新手机号验证码短信
     * 模板ID: 100004
     * 模板内容: 尊敬的客户，您正在进行绑定手机号操作，您的验证码为${code}。以上验证码${min}分钟内有效，请注意保密，切勿告知他人。
     * 占位符: ${code} - 验证码, ${min} - 有效时间(分钟)
     */
    public static SmsRequest bind(String phone, String code, String minutes) {
        return build(phone, "100004", code, minutes);
    }

    /**
     * 验证绑定手机号验证码短信
     * 模板ID: 100005
     * 模板内容: 尊敬的客户，您正在验证绑定手机号操作，您的验证码为${code}。以上验证码${min}分钟内有效，请注意保密，切勿告知他人。
     * 占位符: ${code} - 验证码, ${min} - 有效时间(分钟)
     */
    public static SmsRequest verifyBind(String phone, String code, String minutes) {
        return build(phone, "100005", code, minutes);
    }

}



