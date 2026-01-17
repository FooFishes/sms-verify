package work.foofish.smsverify.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {
    private String active = "aliyun";
    private Aliyun aliyun;
    private Tencent tencent;

    @Data
    public static class Aliyun {
        public static final Integer DEFAULT_INTERVAL = 60;
        private String accessKeyId;
        private String accessKeySecret;
        private String signName;
        private String endpoint = "dypnsapi.aliyuncs.com";
        private Integer interval = DEFAULT_INTERVAL;
    }

    @Data
    public static class Tencent {
        private String secretId;
        private String secretKey;
        private String signName;
        private String appId;
        private String region = "ap-guangzhou";
    }
}
