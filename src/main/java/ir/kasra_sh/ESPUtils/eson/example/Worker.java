package ir.kasra_sh.ESPUtils.eson.example;

import ir.kasra_sh.ESPUtils.eson.EsonField;

import java.util.Calendar;
import java.util.Date;

public class Worker {
    private long id;

    @EsonField(name = "is_working")
    private boolean working;
    private String name;
    private Date regDate;
    public Worker() {
    }

    public Worker(long id, boolean working, String name) {
        this.id = id;
        this.working = working;
        this.name = name;
        try {
            this.regDate = Calendar.getInstance().getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}