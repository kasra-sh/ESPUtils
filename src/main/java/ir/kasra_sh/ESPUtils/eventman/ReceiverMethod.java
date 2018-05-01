package ir.kasra_sh.ESPUtils.eventman;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ReceiverMethod {
    private Method method;
    private Parameter parameter;
    private Object receiverObject;

    public ReceiverMethod(Method method, Parameter parameter, Object receiverObject) {
        this.method = method;
        this.parameter = parameter;
        this.receiverObject = receiverObject;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public Object getReceiverObject() {
        return receiverObject;
    }

    public void setReceiverObject(Object receiverObject) {
        this.receiverObject = receiverObject;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
