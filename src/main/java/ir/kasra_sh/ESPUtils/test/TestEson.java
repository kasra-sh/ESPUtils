package ir.kasra_sh.ESPUtils.test;

import ir.kasra_sh.ESPUtils.ULog;
import ir.kasra_sh.ESPUtils.eson.*;
import ir.kasra_sh.ESPUtils.eson.internal.EsonReader;
import ir.kasra_sh.ESPUtils.eson.internal.TokenType;

import java.util.ArrayList;

public class TestEson {
    public TestEson() {

        //Test Parser
        new TestEson("   ,,{:   , } \"hello\"}");
        System.out.println("-------------------");

        // Normal Test
        TestModel tm = new TestModel(); tm.setStringTest1("tm");
        TestModel tm1 = new TestModel(); tm1.setStringTest1("tm1");
        TestModel tm2 = new TestModel(); tm2.setStringTest1("tm2");
        tm.setObjectTest1(tm1);
        tm1.setObjectTest1(tm2);
        tm2.setObjectTest1(new TestModel("tm3", "tm3str2", 3, 3L, null, null));

        EsonObject element = Eson.wrap(tm);
        System.out.println(element.toString(5));
        ULog.LogFlags.setLogDebug(false);
        TestModel em = Eson.load(element.toString(), TestModel.class);
        System.out.println(Eson.wrap(em).toString(5));

        TestModel tm3 = new TestModel("tm3", "tm3str2", 3, 3L, null, null);
        tm3.setDouble_test(44.33);
        String tm3string = Eson.wrap(tm3).toString();

        ArrayList<TestModel> tmArray = new ArrayList<>();
        tmArray.add(tm3);
        tmArray.add(tm2);
        tmArray.add(tm3);

        System.out.println(Eson.load(tm3string, TestModel.class).getDouble_test());

        System.out.println(tm3string);

        tm1.setStringArrayListTest1(tmArray);
        System.out.println(Eson.wrap(tm1).toString(5));

        String bigStr = Eson.generate(tm1);
        long c = System.currentTimeMillis();
        try {
            for (int i = 0; i < 50000; i++) {
//                new Gson().fromJson(bigStr, TestModel.class);
                Eson.load(bigStr, TestModel.class);
            }
        }catch (StackOverflowError se){
        }

        System.out.println(System.currentTimeMillis()-c);


    }

    public TestEson(String s) {
        try {
//            System.out.println((char) new EsonReader("").parseUnicodeEscaped("\\u0068"));
            EsonReader reader = new EsonReader(s);
            System.out.println(reader.getToken(TokenType.COMMA).getStr());
            System.out.println(reader.getToken(TokenType.COMMA).getStr());
            System.out.println(reader.getToken(TokenType.OBJ_START).getStr());
            System.out.println(reader.getToken(TokenType.COLON).getStr());
            System.out.println(reader.getToken(TokenType.COMMA).getStr());
            System.out.println(reader.getToken(TokenType.OBJ_END).getStr());
            System.out.println(reader.getToken(TokenType.STRING_LITERAL).getStr());
            System.out.println(reader.getToken(TokenType.OBJ_END, TokenType.COMMA).getStr());
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
