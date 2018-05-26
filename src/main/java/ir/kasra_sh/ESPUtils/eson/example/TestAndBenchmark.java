package ir.kasra_sh.ESPUtils.eson.example;

import ir.kasra_sh.ESPUtils.eson.Eson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public final class TestAndBenchmark {

    private ArrayList<Worker> workers = new ArrayList<>();

    public static void main(String[] args) {
        new TestAndBenchmark();
    }

    TestAndBenchmark() {
        workers.add(new Worker(1L, true, "A-Worker"));
        workers.add(new Worker(2L, false, "B-Worker"));
        workers.add(new Worker(3L, true, "C-Worker"));
        workers.add(new Worker(4L, false, "D-Worker"));
        workers.add(new Worker(5L, true, "E-Worker"));

        Boss boss1 = new Boss(
                1,
                "Jack",
                new ArrayList<>(
                        Arrays.asList(workers.get(0), workers.get(1), workers.get(2), workers.get(3), workers.get(4))
                )
        );

        Boss boss2 = new Boss(
                2,
                "Jack",
                new ArrayList<>(
                        Arrays.asList(workers.get(3), workers.get(4))
                )
        );

        System.out.println("############ Generate ############");

        System.out.println(Eson.generate(boss1));
        System.out.println(Eson.generate(boss2, 3));

        System.out.println("############ Load ############");

        Boss boss1Loaded = Eson.load(Eson.generate(boss1), Boss.class);
        Boss boss2Loaded = Eson.load(Eson.generate(boss2), Boss.class);

        System.out.println(Eson.generate(boss1Loaded, 3));
        System.out.println(Eson.generate(boss2Loaded, 3));

        System.out.println("<<<<<----- Round 1 ----->>>>>");
        long s = System.nanoTime();
        String json = "";
        for (int i = 0; i < 100000; i++) {
            json = Eson.generate(boss1);
        }
        long dif = (System.nanoTime() - s);
        System.out.println("100000 Serialize : " + dif / 1000000 + "ms");

        Boss b;
        s = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            b = Eson.load(json, Boss.class);
        }
        dif = (System.nanoTime() - s);
        System.out.println("100000 Deserialize : " + dif / 1000000 + "ms");

        System.out.println("<<<<<----- Round 2 ----->>>>>");
        s = System.nanoTime();
        json = "";
        for (int i = 0; i < 100000; i++) {
            json = Eson.generate(boss1);
        }
        dif = (System.nanoTime() - s);
        System.out.println("100000 Serialize : " + dif / 1000000 + "ms");

        s = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            b = Eson.load(json, Boss.class);
        }
        dif = (System.nanoTime() - s);
        System.out.println("100000 Deserialize : " + dif / 1000000 + "ms");

        System.out.println("<<<<<----- Round 3 ----->>>>>");
        s = System.nanoTime();
        json = "";
        for (int i = 0; i < 100000; i++) {
            json = Eson.generate(boss1);
        }
        dif = (System.nanoTime() - s);
        System.out.println("100000 Serialize : " + dif / 1000000 + "ms");

        s = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            b = Eson.load(json, Boss.class);
        }
        dif = (System.nanoTime() - s);
        System.out.println("100000 Deserialize : " + dif / 1000000 + "ms");

        System.out.println("<<<<<----- Round 4 ----->>>>>");
        s = System.nanoTime();
        json = "";
        for (int i = 0; i < 100000; i++) {
            json = Eson.generate(boss1);
        }
        dif = (System.nanoTime() - s);
        System.out.println("100000 Serialize : " + dif / 1000000 + "ms");

        s = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            b = Eson.load(json, Boss.class);
        }
        dif = (System.nanoTime() - s);
        System.out.println("100000 Deserialize : " + dif / 1000000 + "ms");

        new Scanner(System.in).nextLine();

    }

}
