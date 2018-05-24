import ir.kasra_sh.ESPUtils.eson.Eson;
import ir.kasra_sh.ESPUtils.eson.EsonObject;
import ir.kasra_sh.ESPUtils.eson.EsonType;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class EsonTest {

    @Test
    public void generateJson() {
        String json = Eson.generate(new TData1(99999,888,null, null));
        assert Eson.load(json).getType() == EsonType.OBJECT;
        assert Eson.load(json).getObject().get("l").getLong() == 99999;
    }

    @Test
    public void loadJson() {
        ArrayList<TData1> list = new ArrayList<>();
        list.add(new TData1(0L,0,0L,"\n 0"));
        list.add(new TData1(1L,1,1L,"\t 1"));
        list.add(new TData1(2L,2,2L,"\\ 2"));
        TData2 td2 = new TData2(new TData1(0L,1,null,"\\t\\n\n"), list, Date.from(Instant.now()));
        System.out.println(Eson.generate(td2,3));
        System.out.println(Eson.load(Eson.load(Eson.generate(td2)).toString()));
    }

    @Test
    public void dynamicJson() {
        EsonObject object = new EsonObject();
        object.put("x","x\n\0\t\t\t\t");
        object.put("1289^&$%^#@@3","sss");
        System.out.println(object);
        System.out.println(object.toString(5));
    }
}
