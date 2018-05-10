package ir.kasra_sh.ESPUtils.eson.exceptions;

public class IllegalEscapedCharacter extends Exception {
    public IllegalEscapedCharacter(String s) {
        super(s);
    }

    public IllegalEscapedCharacter(String s, Throwable throwable) {
        super(s, throwable);
    }

    public IllegalEscapedCharacter() {
        super();
    }

    public IllegalEscapedCharacter(Throwable throwable) {
        super(throwable);
    }

    protected IllegalEscapedCharacter(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }


}
