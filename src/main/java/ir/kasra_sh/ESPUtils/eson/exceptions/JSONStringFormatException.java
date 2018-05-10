package ir.kasra_sh.ESPUtils.eson.exceptions;

public class JSONStringFormatException extends Exception {
    public JSONStringFormatException(String s) {
        super(s);
    }

    public JSONStringFormatException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public JSONStringFormatException() {
        super();
    }

    public JSONStringFormatException(Throwable throwable) {
        super(throwable);
    }

    protected JSONStringFormatException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }


}
