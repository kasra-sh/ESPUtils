package ir.kasra_sh.ESPUtils.eson.example;

import ir.kasra_sh.ESPUtils.eson.EsonField;

import java.util.ArrayList;

public class Boss {
    private long id;

    @EsonField(name = "boss_name")
    public String name;

    @EsonField(name = "worker_list", arrayType = Worker.class)
    public ArrayList<Worker> workers;

    public Boss() {
    }

    public Boss(long id, String name, ArrayList<Worker> workers) {
        this.id = id;
        this.name = name;
        this.workers = workers;
    }
}