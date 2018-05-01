package ir.kasra_sh.ESPUtils.eventman;

public class PrimitiveParameterException extends Exception{

    public PrimitiveParameterException(String s) {
        super(s);
    }

    @Override
    public String getMessage() {
        return "Receiver Method ("+super.getMessage()+") primitive parameter(int, float , ...) not allowed!";
    }
}
