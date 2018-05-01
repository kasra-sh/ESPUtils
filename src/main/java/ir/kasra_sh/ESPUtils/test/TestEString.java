package ir.kasra_sh.ESPUtils.test;

import ir.kasra_sh.ESPUtils.EString;

public class TestEString {
    public TestEString() {
        String test = "I have to GOTO therso i can fuck him";
        EString eString = new EString(test);

        eString.seekAfter("have");
        eString.seekTo("GOTO");
        System.out.println(eString.getCursor());
        eString.insert(eString.getCursor(),"$$$");
        eString.insert(eString.getCursor(),"##");

        System.out.println(eString.getCursor());
        System.out.println(eString.getStr().charAt(eString.getCursor()));
//        System.out.println(eString.getStr().substring(eString.getCursor(), eString.find("have", 0)));
        System.out.println(eString);
    }
}
