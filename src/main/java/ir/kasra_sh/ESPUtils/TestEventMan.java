package ir.kasra_sh.ESPUtils;

import ir.kasra_sh.ESPUtils.eventman.*;
import ir.kasra_sh.ESPUtils.eson.EsonObj;

import java.util.Arrays;

public class TestEventMan {
    public TestEventMan() {
        ULog.LogFlags.setLogDebug(true);
        EventMan.unsafeRegister(this);

        new TClass1();
        TClass1 tClass1 = new TClass1();

        EventMan.unsafeRegister(null);
        EventMan.post(Arrays.asList("Haha", "hehe"));
        EventMan.post("Hello !");
        EventMan.post(new EsonObj().add("key1", "value1"));

        tClass1.unr();
        ULog.i("TestEventMan", "after unregister");
        EventMan.post(3);

        EventMan.post(Arrays.asList("Haha", "hehe"));
        EventMan.post("Hello !");
        EventMan.post(new EsonObj().add("key1", "value1"));
        EventMan.post(new DefMessage<Integer>(3));

    }

    @EventReceiver
    private void goodRcv(DefMessage<Integer> a) {
        System.out.println(a.getData());

    }

}
