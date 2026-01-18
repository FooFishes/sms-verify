package work.foofish.smsverify.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 短信发送错误类型枚举
 * 用于帮助调用方根据错误类型做不同的处理策略
 */
@Getter
@RequiredArgsConstructor
public enum SmsErrorType {

    /**
     * 无错误
     */
    NONE(false, "无错误"),

    /**
     * 参数错误 - 手机号格式错误、参数缺失等
     * 不可重试，需要调用方修正参数
     */
    PARAM_ERROR(false, "参数错误"),

    /**
     * 频率限制 - 发送频率过快或当日已达上限
     * 可延迟重试，建议通知用户稍后再试
     */
    RATE_LIMIT(true, "频率限制"),

    /**
     * 配置/权限错误 - 签名未审核、模板不存在、功能未开通等
     * 不可重试，需要检查短信服务配置
     */
    CONFIG_ERROR(false, "配置错误"),

    /**
     * 余额不足
     * 不可重试，需要充值后告警
     */
    BALANCE_ERROR(false, "余额不足"),

    /**
     * 网络/服务错误 - 网络超时、服务暂时不可用
     * 可重试
     */
    NETWORK_ERROR(true, "网络错误"),

    /**
     * 未知错误
     * 建议记录日志并人工排查
     */
    UNKNOWN(false, "未知错误");

    /**
     * 是否可重试
     */
    private final boolean retryable;

    /**
     * 错误类型描述
     */
    private final String description;

    /**
     * 根据阿里云错误码解析错误类型
     */
    public static SmsErrorType fromAliyunCode(String errorCode) {
        if (errorCode == null) {
            return UNKNOWN;
        }
        return switch (errorCode) {
            // 参数错误
            case "MOBILE_NUMBER_ILLEGAL",
                 "INVALID_PARAMETERS",
                 "isv.MOBILE_NUMBER_ILLEGAL",
                 "isv.PARAM_LENGTH_LIMIT",
                 "isv.PARAM_NOT_SUPPORT_URL" -> PARAM_ERROR;

            // 频率限制
            case "BUSINESS_LIMIT_CONTROL",
                 "FREQUENCY_FAIL",
                 "isv.BUSINESS_LIMIT_CONTROL",
                 "isv.DAY_LIMIT_CONTROL",
                 "isv.MONTH_LIMIT_CONTROL" -> RATE_LIMIT;

            // 配置/权限错误
            case "FUNCTION_NOT_OPENED",
                 "isv.SMS_SIGN_ILLEGAL",
                 "isv.SMS_TEMPLATE_ILLEGAL",
                 "isv.TEMPLATE_MISSING_PARAMETERS",
                 "isv.SIGN_NAME_ILLEGAL",
                 "isv.TEMPLATE_PARAMS_ILLEGAL",
                 "SignatureDoesNotMatch",
                 "InvalidAccessKeyId.NotFound" -> CONFIG_ERROR;

            // 余额不足
            case "isv.AMOUNT_NOT_ENOUGH",
                 "isv.OUT_OF_SERVICE" -> BALANCE_ERROR;

            // 网络/服务错误
            case "isp.SYSTEM_ERROR",
                 "isp.RAM_PERMISSION_DENY",
                 "EXCEPTION",
                 "ServiceUnavailable",
                 "Throttling" -> NETWORK_ERROR;

            default -> UNKNOWN;
        };
    }
}
