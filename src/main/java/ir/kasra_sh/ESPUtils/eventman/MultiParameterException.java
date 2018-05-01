package ir.kasra_sh.ESPUtils.eventman;

public class MultiParameterException extends Exception{

    public MultiParameterException(String s) {
        super(s);
    }

    @Override
    public String getMessage() {
        return "Receiver Method ("+super.getMessage()+") must have only one parameter!";
    }
}
