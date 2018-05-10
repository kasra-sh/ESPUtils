package ir.kasra_sh.ESPUtils.test;

import com.google.gson.annotations.SerializedName;
import ir.kasra_sh.ESPUtils.eson.EsonElement;
import ir.kasra_sh.ESPUtils.eson.EsonField;
import ir.kasra_sh.ESPUtils.eson.EsonMapper;
import ir.kasra_sh.ESPUtils.eson.EsonObject;

import java.util.ArrayList;

public class TestModel {

    @SerializedName("string_test1")
    @EsonField(name = "string_test1")
    private String stringTest1;
    private String stringTest2;

    @EsonField(name = "int_test1")
    @SerializedName("int_test1")
    private Integer intTest1;

    @EsonField(name = "long_test1")
    @SerializedName("long_test1")
    private Long longTest1 ;

    private Double double_test;

    @EsonField(name = "obj")
    @SerializedName("obj")
    private TestModel objectTest1 = null;

    @EsonField(name = "list_of_string",genericListType = TestModel.class)
    @SerializedName("list_of_string")
    private ArrayList<TestModel> stringArrayListTest1;

    public TestModel() {
    }

    public TestModel(String stringTest1, String stringTest2, Integer intTest1, Long longTest1, TestModel objectTest1, ArrayList<TestModel> stringArrayListTest1) {
        this.stringTest1 = stringTest1;
        this.stringTest2 = stringTest2;
        this.intTest1 = intTest1;
        this.longTest1 = longTest1;
        this.objectTest1 = objectTest1;
        this.stringArrayListTest1 = stringArrayListTest1;
    }

    public String getStringTest1() {
        return stringTest1;
    }

    public void setStringTest1(String stringTest1) {
        this.stringTest1 = stringTest1;
    }

    public String getStringTest2() {
        return stringTest2;
    }

    public void setStringTest2(String stringTest2) {
        this.stringTest2 = stringTest2;
    }

    public Integer getIntTest1() {
        return intTest1;
    }

    public void setIntTest1(Integer intTest1) {
        this.intTest1 = intTest1;
    }

    public Long getLongTest1() {
        return longTest1;
    }

    public void setLongTest1(Long longTest1) {
        this.longTest1 = longTest1;
    }

    public TestModel getObjectTest1() {
        return objectTest1;
    }

    public void setObjectTest1(TestModel objectTest1) {
        this.objectTest1 = objectTest1;
    }

    public ArrayList<TestModel> getStringArrayListTest1() {
        return stringArrayListTest1;
    }

    public void setStringArrayListTest1(ArrayList<TestModel> stringArrayListTest1) {
        this.stringArrayListTest1 = stringArrayListTest1;
    }

    public Double getDouble_test() {
        return double_test;
    }

    public void setDouble_test(Double double_test) {
        this.double_test = double_test;
    }

    @EsonMapper
    private TestModel map(EsonObject obj) {
        setStringTest1(obj.getString("string_test1"));
        setDouble_test(obj.get("double_test").getDouble());
        setIntTest1(1);
        return this;
    }
}
