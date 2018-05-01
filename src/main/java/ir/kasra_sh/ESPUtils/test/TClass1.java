package ir.kasra_sh.ESPUtils.test;

import ir.kasra_sh.ESPUtils.eventman.EventMan;
import ir.kasra_sh.ESPUtils.eventman.EventReceiver;
import ir.kasra_sh.ESPUtils.eventman.MultiParameterException;
import ir.kasra_sh.ESPUtils.eson.EsonObj;
import ir.kasra_sh.ESPUtils.eventman.PrimitiveParameterException;

import java.util.ArrayList;
import java.util.List;

public class TClass1 {
    public TClass1() {
        EventMan.unsafeRegister(this);
    }

    public void unr() {
        EventMan.unregister(this);
    }

    @EventReceiver
    private void Rcv(EsonObj esonObj) {
        System.out.println(esonObj.toString(3));
    }

    @EventReceiver
    public void Rcver2(List<String> s) {
        System.out.println(s.toArray()[0]);
    }

    @EventReceiver
    public void RcvS(String xx) {
        System.out.println(xx);
    }

}
