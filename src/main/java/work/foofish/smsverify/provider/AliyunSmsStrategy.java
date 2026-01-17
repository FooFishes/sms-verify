package work.foofish.smsverify.provider;

import com.aliyun.dypnsapi20170525.Client;
import com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeRequest;
import com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeResponse;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import work.foofish.smsverify.config.SmsProperties;
import work.foofish.smsverify.core.SmsException;
import work.foofish.smsverify.core.SmsRequest;
import work.foofish.smsverify.core.SmsResponse;
import work.foofish.smsverify.core.SmsStrategy;

@Slf4j
@RequiredArgsConstructor
public class AliyunSmsStrategy implements SmsStrategy {
    private final SmsProperties.Aliyun config;
    private final Client client;
    private final Gson gson = new Gson();

    @Override
    public SmsResponse send (SmsRequest request) {
        try {
            SendSmsVerifyCodeRequest req = new SendSmsVerifyCodeRequest();
            req.phoneNumber = request.getPhone();
            req.templateCode = request.getTemplateId();
            req.templateParam = gson.toJson(request.getParams());
            req.signName = config.getSignName();
            req.interval = config.getInterval().longValue();

            SendSmsVerifyCodeResponse resp = client.sendSmsVerifyCode(req);

            if (resp.getBody().getSuccess() != true) {
                String code = resp.getBody().getCode();
                String message = resp.getBody().getMessage();
                return SmsResponse.fail(code, message, getProviderName());
            }

            return SmsResponse.success(resp.getBody().getRequestId(), getProviderName());
        } catch (Exception e) {
            return SmsResponse.fail("EXCEPTION", e.getMessage(), getProviderName());
        }
    }

    @Override
    public String getProviderName () {
        return "aliyun";
    }
}
