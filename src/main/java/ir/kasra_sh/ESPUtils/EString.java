package ir.kasra_sh.ESPUtils;

import java.util.ArrayList;

public class EString {
    private StringBuilder str;
    private static final String enNums = "0123456789";
    private static final String faNums = "۰۱۲۳۴۵۶۷۸۹";
    private static final String enAlpha = "abcdefghijklmnopqrstuvwxyz";
    private int cursor = 0;

    public EString(String input) {
        str = new StringBuilder(input);
    }

    public EString() {
        str = new StringBuilder();
    }

    public int length() {
        return str.length();
    }

    public void append(String s) {
        str = str.append(s);
    }

    public EString delete(int start, int len) {
        String temp = str.toString();
        str.setLength(0);
        for (int i = 0; i < temp.length(); i++) {
            if (i < start || (i >= start + len)) {
                str.append(temp.charAt(i));
            }
        }
        return this;
    }

    public EString insert(int i, String s) {
        str.insert(i, s);
        if (i <= cursor) {
            cursor += s.length();
        }
        return this;
    }

    public EString insert(int i, char c) {
        str.insert(i, c);
        if (i <= cursor) {
            cursor++;
        }
        return this;
    }

    public StringBuilder getStr() {
        return str;
    }

    public int getCursor() {
        return cursor;
    }

    @Override
    public String toString() {
        return str.toString();
    }

    public static boolean isNum(char c) {
        for (int j = 0; j < enNums.length(); j++) {
            if (c == enNums.charAt(j) || c == faNums.charAt(j)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAlpha(char c) {
        c = Character.toLowerCase(c);
        for (int j = 0; j < enAlpha.length(); j++) {
            if (c == enAlpha.charAt(j)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUpper(char c) {
        return Character.isUpperCase(c);
    }

    public static boolean isLower(char c) {
        return Character.isLowerCase(c);
    }

    public static int countNums(String s) {
        return new EString(s).countNums();
    }

    public int countNums() {
        int cnt = 0;
        for (int i = 0; i < str.length(); i++) {
            if (isNum(str.charAt(i))) {
                cnt++;
            }
        }
        return cnt;
    }

    public static int countAlpha(String s) {
        return new EString(s).countAlpha();
    }

    public int countAlpha() {
        int cnt = 0;
        for (int i = 0; i < str.length(); i++) {
            if (isAlpha(str.charAt(i))) {
                cnt++;
            }
        }
        return cnt;
    }

    public static int countUpper(String s) {
        return new EString(s).countUpper();
    }

    public int countUpper() {
        int cnt = 0;
        for (int i = 0; i < str.length(); i++) {
            if (isUpper(str.charAt(i))) {
                cnt++;
            }
        }
        return cnt;
    }

    public int find(String s, int occurrence) {
        int i = 0;
        int j = 0;
        int occur = 0;
        int lindex = -1;
        while (i < str.length()) {
            if (s.charAt(j) == str.charAt(i)) {
                if (j == (s.length() - 1)) {
                    lindex = i - j;
                    if (occur == occurrence) {
                        return lindex;
                    }
                    occur++;
                }
                j++;
            } else {
                j = 0;
            }
            i++;
        }
        return lindex;
    }

    public int findLast(String s) {
        int i = 0;
        int j = 0;
        int occur = 0;
        int occurrence = Integer.MAX_VALUE;
        int lindex = -1;
        while (i < str.length()) {
            if (s.charAt(j) == str.charAt(i)) {
                if (j == (s.length() - 1)) {
                    lindex = i - j;
                    if (occur == occurrence) {
                        return lindex;
                    }
                    occur++;
                }
                j++;
            } else {
                j = 0;
            }
            i++;
        }
        return lindex;
    }

    public int findFirst(String s) {
        int i = 0;
        int j = 0;
        int lindex = -1;
        while (i < str.length()) {
            if (s.charAt(j) == str.charAt(i)) {
                if (j == (s.length() - 1)) {
                    lindex = i - j;
                    return lindex;
                }
                j++;
            } else {
                j = 0;
            }
            i++;
        }
        return lindex;
    }

    // split
    // replace
    //

    public int get(int index) {
        return str.charAt(index);
    }

    public void rewind(int offset) {
        cursor-=offset;
    }

    public void skip(int offset) {
        cursor+=offset;
    }

    public boolean skipWhite(){
        while (str.length()>cursor) {
            if (Character.isWhitespace(str.charAt(cursor)))
                cursor++;
            else {
                break;
            }
        }

        return finished();
    }

    public boolean finished() {
        return str.length()<=cursor || cursor==-1;
    }

    public int getCur() {
        if (cursor>= str.length())
            return get(str.length()-1);
        return get(cursor);
    }

    public int peekNext() {
        if (cursor<(str.length()-1)) {
            return str.charAt(cursor+1);
        } else {
            return '\0';
        }
    }

    public boolean nextMatches(int c) {
        return peekNext() == c;
    }

    public int findFrom(String s, int start, int occurrence) {
        int i = start;
        int j = 0;
        int occur = 0;
        int lindex = -1;
        while (i < str.length()) {
            if (s.charAt(j) == str.charAt(i)) {
                if (j == (s.length() - 1)) {
                    lindex = i - j;
                    if (occur == occurrence) {
                        return lindex;
                    }
                    occur++;
                }
                j++;
            } else {
                j = 0;
            }
            i++;
        }
        return lindex;
    }

    public ArrayList<Integer> findAll(String s) {
        int i = 0;
        int j = 0;
        ArrayList<Integer> lindex = new ArrayList<>();
        while (i < str.length()) {
            if (s.charAt(j) == str.charAt(i)) {
                if (j == (s.length() - 1)) {
                    lindex.add(i - j);
                }
                j++;
            } else {
                j = 0;
            }
            i++;
        }
        return lindex;
    }

    public int seekTo(String s) {
        return cursor = findFrom(s, cursor, 0);
    }

    public int seekAfter(String s) {
        return cursor = findFrom(s, cursor, 0) + s.length();
    }

    public int seekBefore(String s) {
        return cursor = findFrom(s, cursor, 0) - 1;
    }

}
