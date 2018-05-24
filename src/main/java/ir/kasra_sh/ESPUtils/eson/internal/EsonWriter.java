package ir.kasra_sh.ESPUtils.eson.internal;

import ir.kasra_sh.ESPUtils.eson.EsonArray;
import ir.kasra_sh.ESPUtils.eson.EsonElement;
import ir.kasra_sh.ESPUtils.eson.EsonObject;
import ir.kasra_sh.ESPUtils.eson.EsonType;
import ir.kasra_sh.ESPUtils.estore.EKey;
import ir.kasra_sh.ESPUtils.estore.EStoreDB;
import ir.kasra_sh.ESPUtils.estore.EValue;

import java.io.Writer;
import java.util.ArrayList;

public class EsonWriter {

    private StringBuilder js;
    private Writer writer;
    private boolean error = false;
    private int indent;

    public EsonWriter() {
        js = new StringBuilder(512);
    }

    public EsonWriter(int minCap) {
        js = new StringBuilder(minCap);
    }

    public EsonWriter(Writer writer) {
        this.writer = writer;
    }

    private void mAppend(char c) {
        js.append(c);
        if (writer != null) {
            try {
                writer.append(c);
            } catch (Exception e) {
                error = true;
            }
        }

    }

    private void mAppend(String s) {
        js.append(s);
        if (writer != null) {
            try {
                writer.append(s);
            } catch (Exception e) {
                error = true;
            }
        }

    }

    private void mAppendSpace(int i) {
        for (int j = 0; j < i; j++) {
            js.append(' ');
        }
    }

    public EsonWriter writeNum(int num) {
        mAppend(String.valueOf(num));
        return this;
    }

    public EsonWriter writeNum(long num) {
        mAppend(String.valueOf(num));
        return this;
    }

    public EsonWriter writeBool(boolean bool) {
        mAppend(bool ? "true" : "false");
        return this;
    }

    public EsonWriter writePair(String key, String value) {
        mAppend(key);
        mAppend(':');
        space(1);
        mAppend(value);
        return this;
    }

    public EsonWriter comma() {
        mAppend(',');
        return this;
    }

    public EsonWriter colon() {
        mAppend(':');
        return this;
    }

    public EsonWriter writeString(String s) {
        mAppend('"');
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (appendEscaped(c)) {
                continue;
            } else {
                mAppend(s.charAt(i));
            }
        }
        mAppend('"');
        return this;
    }

    public EsonWriter space(int count) {
        for (int i = 0; i < count; i++) {
            mAppend(' ');
        }
        return this;
    }

    public EsonWriter stringQuote() {
        mAppend('"');
        return this;
    }

    public EsonWriter startObject() {
        mAppend('{');
        return this;
    }

    public EsonWriter endObject() {
        mAppend('}');
        return this;
    }

    public EsonWriter startArray() {
        mAppend('[');
        return this;
    }

    public EsonWriter endArray() {
        mAppend(']');
        return this;
    }

    private boolean appendEscaped(char c) {
        switch (c) {
            case (int) '"':  // Quote
                mAppend('\\');
                mAppend('\"');
                return true;
            case (int) '\\': // BackSlash
                mAppend('\\');
                mAppend('\\');
                return true;
//            case (int) '/':  // Slash
//                mAppend('\\');mAppend('/');
//                return true;
            case (int) '\f': // FormFeed
                mAppend('\\');
                mAppend('f');
                return true;
            case (int) '\b': // BackSpace
                mAppend('\\');
                mAppend('b');
                return true;
            case (int) '\n': // NewLine
                mAppend('\\');
                mAppend('n');
                return true;
            case (int) '\r': // Carriage Return
                mAppend('\\');
                mAppend('r');
                return true;
            case (int) '\t': // Tab
                mAppend('\\');
                mAppend('t');
                return true;
            default:
                if (c < 32) {
                    mAppend("\\u00");
                    String h = Integer.toHexString(c);
                    if (h.length() == 1) {
                        mAppend('0');
                    }
                    mAppend(h);
                    return true;
                } else
                    return false;
        }
    }

    public String writeObject(EsonObject object, int indent, int preindent) {
        EStoreDB d = object.getKeyValues();
        ArrayList<EKey> keys = d.getKeys();
        ArrayList<EValue> values = d.getValues();
        if (keys.size() == 0) {
            startObject();
            endObject();
            return js.toString();
        }
        // OBJ
        startObject();
        for (int i = 0; i < keys.size(); i++) {
            if ((preindent + indent) > 0) {
                mAppend('\n');
            }
            String key = keys.get(i).getKey();
            EsonElement value = (EsonElement) values.get(i).getValue();
            space(preindent).space(indent);
            writeString(key);
            colon();
            if (value.getType() == EsonType.OBJECT) {
                writeObject(value.getObject(), indent, indent + preindent);
            } else if (value.getType() == EsonType.ARRAY) {
                writeArray(value.getArray(), indent, indent + preindent);
            } else if (value.getType() == EsonType.STRING) {
                writeString(value.toString());
            } else {
                mAppend(value.toString());
            }
            if (i < (keys.size() - 1)) {
                comma();
            }
        }
        if (indent > 0) {
            mAppend('\n');
        }
        space(preindent);
        endObject();
        return js.toString();
    }

    public String writeArray(EsonArray array, int indent, int preindent) {
        if (array.length() == 0) {
            startArray();
            endArray();
            return js.toString();
        }
        startArray();
        for (int i = 0; i < array.length(); i++) {
            EsonElement element = array.get(i);
            if ((preindent + indent) > 0) {
                mAppend('\n');
            }
            space(preindent).space(indent);
            if (element.getType() == EsonType.OBJECT) {
                writeObject(element.getObject(), indent, indent + preindent);
            } else if (element.getType() == EsonType.ARRAY) {
                writeArray(element.getArray(), indent, indent + preindent);
            } else {
                mAppend(element.toString());
            }
            if (i < (array.length() - 1)) {
                comma();
            }
        }
        if (indent > 0) {
            mAppend('\n');
        }
        space(preindent);
        endArray();
        return js.toString();
    }

    @Override
    public String toString() {
        return js.toString();
    }
}
