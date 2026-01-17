package work.foofish.smsverify.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@Data
@Builder
public class SmsRequest {
    /**
     * 接收手机号 (仅传入纯数字, 目前仅支持 +86 号码)
     */
    private String phone;

    /**
     * 短信模板 ID
     */
    private String templateId;

    /**
     * 短信参数
     * 使用 LinkedHashMap 保持顺序, 便于兼容腾讯云的数组参数顺序要求
     */
    @Builder.Default
    private LinkedHashMap<String, String> params = new LinkedHashMap<>();


    /**
     * 辅助方法：获取腾讯云需要的数组参数 (只取Value)
     */
    public String[] getParamValuesArray() {
        return params.values().toArray(new String[0]);
    }
}
