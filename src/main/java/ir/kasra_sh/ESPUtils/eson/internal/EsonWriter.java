package ir.kasra_sh.ESPUtils.eson.internal;

import java.io.Writer;

public class EsonWriter {

    private StringBuilder js;
    private Writer writer;
    private boolean error = false;

    public EsonWriter() {
        js = new StringBuilder();
    }

    public EsonWriter(int minCap) {
        js = new StringBuilder(minCap);
    }

    public EsonWriter(Writer writer) {
        this.writer = writer;
    }

    private void mAppend(char c){
        js.append(c);
        if (writer!=null) {
            try {
                writer.append(c);
            } catch (Exception e) {
                error = true;
            }
        }

    }

    private void mAppend(String s){
        js.append(s);
        if (writer!=null) {
            try {
                writer.append(s);
            } catch (Exception e) {
                error = true;
            }
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
        mAppend(bool?"true":"false");
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

    public EsonWriter writeString(String s) {
        for (int i = 0; i < s.length(); i++) {
            Integer c = (int) s.charAt(i);
            if (appendEscaped(c)) {
                continue;
            } else {
                mAppend(s.charAt(i));
            }
        }
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

    public EsonWriter startObject(){
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

    private boolean appendEscaped(int c) {
        switch (c) {
            case (int) '"':  // Quote
                mAppend('\\');mAppend('\"');
                return true;
            case (int) '\\': // BackSlash
                mAppend('\\');mAppend('\\');
                return true;
            case (int) '/':  // Slash
                mAppend('\\');mAppend('/');
                return true;
            case (int) '\f': // FormFeed
                mAppend('\\');mAppend('f');
                return true;
            case (int) '\b': // BackSpace
                mAppend('\\');mAppend('b');
                return true;
            case (int) '\n': // NewLine
                mAppend('\\');mAppend('n');
                return true;
            case (int) '\r': // Carriage Return
                mAppend('\\');mAppend('r');
                return true;
            case (int) '\t': // Tab
                mAppend('\\');mAppend('t');
                return true;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return js.toString();
    }
}
