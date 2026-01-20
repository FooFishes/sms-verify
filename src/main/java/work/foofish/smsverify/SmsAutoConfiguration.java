package work.foofish.smsverify;

import com.aliyun.dypnsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import work.foofish.smsverify.config.SmsProperties;
import work.foofish.smsverify.core.SmsStrategy;
import work.foofish.smsverify.provider.AliyunSmsStrategy;

import java.util.Map;

@Configuration
@EnableConfigurationProperties(SmsProperties.class)
public class SmsAutoConfiguration {

    /**
     * 初始化阿里云 SDK Client
     */
    @Bean
    @ConditionalOnProperty(prefix = "sms.aliyun", name = "access-key-id")
    public Client aliyunClient(SmsProperties props) throws Exception {
        SmsProperties.Aliyun aliyunProps = props.getAliyun();
        Config config = new Config()
                .setAccessKeyId(aliyunProps.getAccessKeyId())
                .setAccessKeySecret(aliyunProps.getAccessKeySecret())
                .setEndpoint(aliyunProps.getEndpoint());
        return new Client(config);
    }

    /**
     * 初始化阿里云策略
     */
    @Bean("aliyun")
    @ConditionalOnProperty(prefix = "sms.aliyun", name = "access-key-id")
    public SmsStrategy aliyunSmsStrategy(SmsProperties props, Client client) {
        return new AliyunSmsStrategy(props.getAliyun(), client);
    }

    /**
     * 核心服务入口
     */
    @Bean
    public SmsTemplate smsTemplate (Map<String, SmsStrategy> strategies, SmsProperties prop) {
        return new SmsTemplate(strategies, prop);
    }
}
