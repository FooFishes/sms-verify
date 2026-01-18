package work.foofish.smsverify;

import work.foofish.smsverify.config.SmsProperties;
import work.foofish.smsverify.core.SmsRequest;
import work.foofish.smsverify.core.SmsResponse;
import work.foofish.smsverify.core.SmsStrategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SmsTemplate {
    private final Map<String, SmsStrategy> strategyMap = new ConcurrentHashMap<>();
    private final String activeProvider;

    public SmsTemplate (Map<String, SmsStrategy> strategies, SmsProperties properties) {
        this.strategyMap.putAll(strategies);
        this.activeProvider = properties.getActive();
    }

    // 默认发送
    public SmsResponse send(SmsRequest request) {
        return send(activeProvider, request);
    }

    // 指定渠道发送
    public SmsResponse send(String provider, SmsRequest request) {
        SmsStrategy strategy = strategyMap.get(provider);
        if (strategy == null) {
            throw new RuntimeException("No SMS provider found: " + provider);
        }
        return strategy.send(request);
    }
}
