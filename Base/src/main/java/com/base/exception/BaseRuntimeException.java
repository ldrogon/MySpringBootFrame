package com.base.exception;

import com.base.i18n.I18NData;
import com.base.json.JsonMessage;
import com.base.message.BaseErrorMessage;
import com.base.message.DefaultErrorMessage;

/**
 * Created by Administrator on 2017/7/26.
 */
public abstract class BaseRuntimeException extends RuntimeException{
    public abstract JsonMessage toJsonMessage();


    public static BaseRuntimeException getException(String msg){
        return new DefaultRuntimeException(msg);
    }
    public static BaseRuntimeException getException(String msg,String code){
        return new DefaultRuntimeException(code,msg);
    }
    public static BaseRuntimeException getException(I18NData i18NData){
        return new DefaultRuntimeException(i18NData);
    }
    public static BaseRuntimeException getException(I18NData i18NData,String code){
        return new DefaultRuntimeException(code,i18NData);
    }
    public static BaseRuntimeException getException(BaseErrorMessage errorMessage){
        return errorMessage.toRuntimeException();
    }

}
