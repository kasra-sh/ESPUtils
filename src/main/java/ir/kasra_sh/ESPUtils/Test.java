package ir.kasra_sh.ESPUtils;

import ir.kasra_sh.ESPUtils.ereqt.EReqt;
import ir.kasra_sh.ESPUtils.ereqt.ERequest;
import ir.kasra_sh.ESPUtils.eson.Eson;
import ir.kasra_sh.ESPUtils.eson.EsonField;
import ir.kasra_sh.ESPUtils.eson.EsonObject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        new Test();
    }

    Test() {
        EReqt.getDefault().enqueue(
                ERequest.get("https://localhost/").acceptInsecureSSL(),
                result -> System.out.println(result.bodyStr())
        );
        System.out.println(Eson.generate(new FixResponse(), 3));
        System.out.println(new EsonObject().put("aAbBcCdD  \t\u001b", 23432).toString(5));
    }


    public static class FixResponse {
        @EsonField(name = "myname")
        private String name = "Kasra";
        private String fname = "Shamsaei";
        private int age = 24;
        @EsonField(name = "dates", arrayType = Date.class)
        private ArrayList<Date> dates = new ArrayList<>(Arrays.asList(Date.from(Instant.now()), Date.from(Instant.now())));

        public FixResponse() {
        }
    }
}
