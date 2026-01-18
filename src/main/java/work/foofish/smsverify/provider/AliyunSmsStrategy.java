package work.foofish.smsverify.provider;

import com.aliyun.dypnsapi20170525.Client;
import com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeRequest;
import com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeResponse;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import work.foofish.smsverify.config.SmsProperties;
import work.foofish.smsverify.core.*;

@Slf4j
@RequiredArgsConstructor
public class AliyunSmsStrategy implements SmsStrategy {
    private final SmsProperties.Aliyun config;
    private final Client client;
    private final Gson gson = new Gson();

    @Override
    public SmsResponse send (SmsRequest request) {
        String phone = request.getPhone();
        String templateId = request.getTemplateId();
        
        try {
            SendSmsVerifyCodeRequest req = new SendSmsVerifyCodeRequest();
            req.phoneNumber = phone;
            req.templateCode = templateId;
            req.templateParam = gson.toJson(request.getParams());
            req.signName = config.getSignName();
            req.interval = config.getInterval().longValue();

            SendSmsVerifyCodeResponse resp = client.sendSmsVerifyCode(req);

            if (resp.getBody().getSuccess() != true) {
                String code = resp.getBody().getCode();
                String message = resp.getBody().getMessage();
                String requestId = resp.getBody().getRequestId();
                SmsErrorType errorType = SmsErrorType.fromAliyunCode(code);
                return SmsResponse.fail(code, message, getProviderName(), phone, templateId, requestId, errorType);
            }

            return SmsResponse.success(resp.getBody().getRequestId(), getProviderName(), phone, templateId);
        } catch (Exception e) {
            SmsErrorType errorType = SmsErrorType.NETWORK_ERROR;
            return SmsResponse.fail("EXCEPTION", e.getMessage(), getProviderName(), phone, templateId, errorType);
        }
    }

    @Override
    public String getProviderName () {
        return "aliyun";
    }
}
