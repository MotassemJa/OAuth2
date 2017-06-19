package com.github.motassemja.moauth.exceptions;

/**
 * Created by moja on 12.06.2017.
 */

public class MoAuthExceptionManager extends MoAuthException {
    private MoAuthExceptionReason mOAuthExceptionReason;

    public MoAuthExceptionManager(String exceptionMsg, MoAuthExceptionReason reason) {
        super(exceptionMsg);
        this.mOAuthExceptionReason = reason;
    }

    public MoAuthExceptionManager(Exception e, MoAuthExceptionReason reason) {
        super(e.getMessage(), e);
        this.mOAuthExceptionReason = reason;
    }

    public MoAuthExceptionManager(Exception e, String msg, MoAuthExceptionReason reason) {
        super(msg, e);
        this.mOAuthExceptionReason = reason;
    }

    @Override
    public MoAuthExceptionType getExceptionType() {
        MoAuthExceptionType type;
        switch (mOAuthExceptionReason) {
            case REASON_INVALID_REQUEST:
                type = MoAuthExceptionType.INVALID_REQUEST;
                break;
            case REASON_UNAUTHORIZED_CLIENT:
                type = MoAuthExceptionType.UNAUTHORIZED_CLIENT;
                break;
            case REASON_INVALID_CLIENT:
                type = MoAuthExceptionType.INVALID_CLIENT;
                break;

            case REASON_INVALID_CALLBACK:
                type = MoAuthExceptionType.INVALID_CALLBACK;
                break;

            case REASON_INVALID_GRANT:
                type = MoAuthExceptionType.INVALID_GRANT;
                break;
            case REASON_UNSUPPORTED_GRANT_TYPE:
                type = MoAuthExceptionType.UNSUPPORTED_GRANT_TYPE;
                break;

            case REASON_INVALID_SCOPE:
                type = MoAuthExceptionType.INVALID_SCOPE;
                break;

            case REASON_ACCESS_DENIED:
                type = MoAuthExceptionType.ACCESS_DENIED;
                break;

            case REASON_UNSUPPORTED_RESPONSE_TYPE:
                type = MoAuthExceptionType.UNSUPPORTED_RESOURCE_TYPE;
                break;

            case REASON_SERVER_ERROR:
                type = MoAuthExceptionType.SERVER_ERROR;
                break;

            case REASON_RESOURCE_NOT_FOUND:
                type = MoAuthExceptionType.TEMPORARILY_UNAVAILABLE;
                break;

            case REASON_NETWORK_ERROR:
                type = MoAuthExceptionType.NETWORK_ERROR;
                break;

            case REASON_INTERNAL_ERROR:
            default:
                type = MoAuthExceptionType.INTERNAL_ERROR;
                break;

        }

        return type;
    }
}
