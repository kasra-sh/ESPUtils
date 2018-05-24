import ir.kasra_sh.ESPUtils.eson.EsonField;

public class Worker {
    private long id;

    @EsonField(name = "is_working")
    private boolean working;
    private String name;

    public Worker() {
    }

    public Worker(long id, boolean working, String name) {
        this.id = id;
        this.working = working;
        this.name = name;
    }
}