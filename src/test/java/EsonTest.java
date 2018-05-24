import ir.kasra_sh.ESPUtils.eson.*;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class EsonTest {

    @Test
    public void generateJson() {
        ArrayList<Worker> workers = new ArrayList<>();
        workers.add(new Worker(1, true, "Worker 1"));
        workers.add(new Worker(2, true, "Worker 2"));
        workers.add(new Worker(3, true, "Worker 3"));
        Boss boss = new Boss(1, "Jack", workers);
        System.out.println(Eson.generate(boss, 3));
    }

    @Test
    public void loadJson() {
//        ArrayList<Worker> list = new ArrayList<>();
//        list.add(new Worker(0L,0,0L,"\n 0"));
//        list.add(new Worker(1L,1,1L,"\t 1"));
//        list.add(new Worker(2L,2,2L,"\\ 2"));
//        Boss td2 = new Boss(new Worker(0L,1,null,"\\t\\n\n"), list, Date.from(Instant.now()));
//        System.out.println(Eson.generate(td2,3));
//        System.out.println(Eson.load(Eson.load(Eson.generate(td2)).toString()));
    }

    @Test
    public void dynamicJson() {
        EsonObject object = new EsonObject();
        object.put("id", 5)
                .put("name", "human")
                .put("abilities",
                        new EsonArray()
                                .put(new EsonObject().put("ability", "breathing"))
                                .put(new EsonObject().put("ability", "eating"))
                                .put(new EsonObject().put("ability", "sleeping"))
                )
                .put("is_alive", true)
                .put("extra_info",
                        new EsonObject()
                                .put("age", 44)
                                .put("phone", "+88833377713")
                                .put("address", "somewhere/someplace/someaddress")
                );
        System.out.println(object.toString(3));
    }
}
