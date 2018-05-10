package ir.kasra_sh.ESPUtils.eson.exceptions;

public class IllegalJSONTokenException extends Exception {
    public IllegalJSONTokenException(String s) {
        super(s);
    }

    public IllegalJSONTokenException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public IllegalJSONTokenException() {
        super();
    }

    public IllegalJSONTokenException(Throwable throwable) {
        super(throwable);
    }

    protected IllegalJSONTokenException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }


}
