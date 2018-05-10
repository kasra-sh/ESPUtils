package ir.kasra_sh.ESPUtils.eson.exceptions;

public class JSONNumberFormatException extends Exception {
    public JSONNumberFormatException(String s) {
        super(s);
    }

    public JSONNumberFormatException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public JSONNumberFormatException() {
        super();
    }

    public JSONNumberFormatException(Throwable throwable) {
        super(throwable);
    }

    protected JSONNumberFormatException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }


}
