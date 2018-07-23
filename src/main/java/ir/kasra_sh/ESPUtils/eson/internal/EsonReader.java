package ir.kasra_sh.ESPUtils.eson.internal;

import ir.kasra_sh.ESPUtils.EString;
import ir.kasra_sh.ESPUtils.eson.exceptions.IllegalEscapedCharacter;
import ir.kasra_sh.ESPUtils.eson.exceptions.IllegalJSONTokenException;
import ir.kasra_sh.ESPUtils.eson.exceptions.JSONNumberFormatException;
import ir.kasra_sh.ESPUtils.eson.exceptions.JSONStringFormatException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class EsonReader {

    public class Token {
        private String str;
        private TokenType type;

        public Token() {
        }

        public Token(String str, TokenType type) {
            this.str = str;
            this.type = type;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        public TokenType getType() {
            return type;
        }

        public void setType(TokenType type) {
            this.type = type;
        }
    }

    private EString reader;

    private StringBuilder lastToken = new StringBuilder();

    public EsonReader(String string) {
        this.reader = new EString(string);
    }

    public Token getToken(TokenType... types) throws IOException, IllegalEscapedCharacter, IllegalJSONTokenException, JSONStringFormatException, JSONNumberFormatException {
        lastToken.setLength(0);
        TokenType tt = null;
        ArrayList<TokenType> tps = new ArrayList<>(Arrays.asList(types));
        reader.skipWhite();
        if (!reader.finished()) {
            int c = reader.getCur();

            if (c == 0) {
                while (!reader.finished()) {
                    if (reader.getCur() == 0) {
                        reader.skip(1);
                    }
                }
            }

            if (c == '{') {
                if (tps.contains(TokenType.OBJ_START)) {
                    mAppendToken(c);
                    reader.skip(1);
                    return new Token(lastToken.toString(), TokenType.OBJ_START);
                } else {
                    throw new IllegalJSONTokenException("Expected {, Got "+(char)c+" at "+reader.getCursor());
                }
            }
            if (c == '}') {
                if (tps.contains(TokenType.OBJ_END)) {
                    mAppendToken(c);
                    reader.skip(1);
                    return new Token(lastToken.toString(), TokenType.OBJ_END);
                } else throw new IllegalJSONTokenException(lastToken.toString()+(char)reader.getCur());
            }
            if (c == '[') {
                if (tps.contains(TokenType.ARR_START)) {
                    mAppendToken(c);
                    reader.skip(1);
                    return new Token(lastToken.toString(), TokenType.ARR_START);
                } else throw new IllegalJSONTokenException();
            }
            if (c == ']') {
                if ( tps.contains(TokenType.ARR_END)) {
                    mAppendToken(c);
                    reader.skip(1);
                    return new Token(lastToken.toString(), TokenType.ARR_END);
                } else throw new IllegalJSONTokenException();
            }
            if (c == ':') {
                if (tps.contains(TokenType.COLON)) {
                    mAppendToken(c);
                    reader.skip(1);
                    return new Token(lastToken.toString(), TokenType.COLON);
                } else {
                    throw new IllegalJSONTokenException("Expected Colon, Got "+(char)c+" at "+reader.getCursor());
                }
            }
            if (c == ',') {
                if (tps.contains(TokenType.COMMA)) {
                    mAppendToken(c);
                    reader.skip(1);
                    return new Token(lastToken.toString(), TokenType.COMMA);
                } else throw new IllegalJSONTokenException();
            }
            if (c == '"' ) {
                if (tps.contains(TokenType.STRING_LITERAL)) {
                    readStringLiteral();
                    reader.skip(1);
                    return new Token(lastToken.toString(), TokenType.STRING_LITERAL);
                } else {
                    throw new IllegalJSONTokenException();
                }
            }
            // Parse Number
            if (c == '-' || c == '+' || Character.isDigit(c)) {
                if (tps.contains(TokenType.NUMBER_LITERAL)) {
//                    mAppendToken(c);
                    readNumLiteral();
                    //reader.skip(1);
                    return new Token(lastToken.toString(), TokenType.NUMBER_LITERAL);
                } else {
                    throw new IllegalJSONTokenException();
                }
            }
            if (c == 'n') {
                if (tps.contains(TokenType.NULL)) {
                    mAppendToken(c);
                    readNullLiteral();
                    reader.skip(1);
                    return new Token(lastToken.toString(), TokenType.NULL);
                } else {
                    throw new IllegalJSONTokenException();
                }
            }

            if (c == 't' || c == 'f') {
                if (tps.contains(TokenType.FALSE) || tps.contains(TokenType.TRUE)) {
                    mAppendToken(c);
                    readBoolLiteral();
                    reader.skip(1);
                    String l = lastToken.toString();
                    if (l.startsWith("t"))
                        return new Token(l, TokenType.TRUE);
                    else
                        return new Token(l, TokenType.FALSE);
                }
            }

        } else {
            // ?
        }

        String[] ex = new String[tps.size()];
        for (int i = 0; i < tps.size(); i++) {
            ex[i] = tps.get(i).name();
        }
        throw new IllegalJSONTokenException("Expected "+Arrays.toString(ex)+", got EOF @ "+reader.getCursor());
    }

    private void readNumLiteral() throws IllegalJSONTokenException, JSONNumberFormatException {
        int flag = +1;
        boolean gotInt = false;
        if (!reader.finished()) {
            int c = reader.getCur();
            if (c == '-' || c =='+') {
                mAppendToken(c);
                reader.skip(1);
            }
            // Get int part
            if (Character.isDigit(reader.getCur())) {
                while (!reader.finished()) {
                    if (Character.isDigit(reader.getCur())) {
                        gotInt = true;
                        mAppendToken(reader.getCur());
                        reader.skip(1);
                    } else {
                        if (!gotInt)
                            throw new JSONNumberFormatException(lastToken.toString()+reader.getCur());
                        break;
                    }
                }
            } else {
                if (!gotInt)
                    throw new JSONNumberFormatException(lastToken.toString()+(char) reader.getCur());

            }
            // Get fraction part (Optional)
            if (reader.getCur() == '.') {
                mAppendToken(reader.getCur());
                reader.skip(1);
                gotInt = false;
                while (!reader.finished()) {
                    if (Character.isDigit(reader.getCur())) {
                        gotInt = true;
                        mAppendToken(reader.getCur());
                        reader.skip(1);
                    } else {
                        if (!gotInt)
                            throw new JSONNumberFormatException(lastToken.toString()+(char)reader.getCur());
                        break;
                    }
                }
            }

            // Get exp part
            if (reader.getCur() == 'e' || reader.getCur() == 'E') {
                mAppendToken(reader.getCur());
                reader.skip(1);
                if (reader.getCur() == '-' || reader.getCur() =='+') {
                    mAppendToken(reader.getCur());
                    reader.skip(1);
                }
                // Get exp/int part
                if (Character.isDigit(reader.getCur())) {
                    gotInt = false;
                    while (!reader.finished()) {
                        if (Character.isDigit(reader.getCur())) {
                            gotInt = true;
                            mAppendToken(reader.getCur());
                            reader.skip(1);
                        } else {
                            if (!gotInt)
                                throw new IllegalJSONTokenException(lastToken.toString()+reader.getCur());
                            break;
                        }
                    }
                    if (!gotInt) throw new JSONNumberFormatException(lastToken.toString()+(char)reader.getCur());
                } else {
                    throw new IllegalJSONTokenException(lastToken.toString()+(char)reader.getCur());
                }
            }
        }
    }

    private boolean isHexDigit(char c){
        if ((c >= '0' && c <= '9') || (c>='a' && c<='f') || (c>='A' && c<='F')) {
            return true;
        } else {
            return false;
        }
    }

    private void readStringLiteral() throws IllegalEscapedCharacter, IllegalJSONTokenException, JSONStringFormatException {
        if (reader.getCur() == '"') {
            reader.skip(1);
        }
        while (!reader.finished()) {
            if (reader.getCur() == '\0') {
                throw new JSONStringFormatException("EOF @ "+reader.getCursor());
            }
            if (reader.getCur()=='\\') {
                if (isEsc(reader.peekNext())) {
                    StringBuilder esc = new StringBuilder();
                    esc.setLength(0);
                    esc.append("\\").append((char)reader.peekNext());
//                    System.out.println("Escape : "+esc+" @"+reader.getCursor());
                    if (reader.peekNext() == 'u') {
                        reader.skip(2);
                        for (int i = 0; i < 4; i++) {
                            if (isHexDigit((char) reader.getCur())) {
                                esc.append((char) reader.getCur());
                            } else {
                                throw new JSONStringFormatException("Escaped char must have 4 digits!");
                            }
                            reader.skip(1);
                        }
                        mAppendToken(parseUnicodeEscaped(esc.toString()));
                        continue;
                    } else {
//                        System.out.println("BEF "+(char) reader.getCur());
                        reader.skip(2);
//                        System.out.println("AF "+ (char) reader.getCur());
//                        System.out.println("ESCAPED: "+esc.toString());
                        mAppendToken(parseEscaped(esc.toString()));
                        continue;
                    }
                }

            }
            if (reader.getCur() == '"') {
                return;
            }
            mAppendToken(reader.getCur());
            reader.skip(1);
        }
        if (reader.finished()) throw new JSONStringFormatException('"'+lastToken.toString());
    }

    private void readNullLiteral() throws IllegalJSONTokenException {
        if (!reader.nextMatches('u')) {
            throw new IllegalJSONTokenException();
        }
        reader.skip(1);
        if (!reader.nextMatches('l')) {
            throw new IllegalJSONTokenException();
        }
        reader.skip(1);
        if (!reader.nextMatches('l')) {
            throw new IllegalJSONTokenException();
        }
        reader.skip(1);
        lastToken.append("ull");
    }

    private void readBoolLiteral() throws IllegalJSONTokenException {
        int c = lastToken.charAt(0);
        if (c == 't') {
            if (!reader.nextMatches('r')) {
                throw new IllegalJSONTokenException();
            }
            reader.skip(1);
            if (!reader.nextMatches('u')) {
                throw new IllegalJSONTokenException();
            }
            reader.skip(1);
            if (!reader.nextMatches('e')) {
                throw new IllegalJSONTokenException();
            }
            reader.skip(1);
            lastToken.append("rue");
            return;
        }
        // Match False
        if (!reader.nextMatches('a')) {
            throw new IllegalJSONTokenException();
        }
        reader.skip(1);
        if (!reader.nextMatches('l')) {
            throw new IllegalJSONTokenException();
        }
        reader.skip(1);
        if (!reader.nextMatches('s')) {
            throw new IllegalJSONTokenException();
        }
        reader.skip(1);
        if (!reader.nextMatches('e')) {
            throw new IllegalJSONTokenException();
        }
        reader.skip(1);
        lastToken.append("alse");
    }


    // // // // Helpers // // // //
    private void mAppendToken(char c) {
        lastToken.append(c);
    }

    private void mAppendToken(int c) {
        lastToken.append((char) c);
    }

    private boolean isKeyChar(int c) {
        String kc = "{}[],:\"\\/";
        for (int i = 0; i < kc.length(); i++) {
            if (c == kc.charAt(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean isEsc(int c) {
        String esc = "bnfrtu\"\\";
        for (int i = 0; i < esc.length(); i++) {
            if (esc.charAt(i) == c) {
                return true;
            }
        }
        return false;
    }

    private int parseEscaped(String escaped) throws IllegalEscapedCharacter {
        switch (escaped.charAt(1)) {
            case 'r':
                return '\r';
            case 'n':
                return '\n';
            case 'b':
                return '\b';
            case 't':
                return '\t';
            case '\\':
                return '\\';
            case '"':
                return '"';
            case 'u':
                return parseUnicodeEscaped(escaped);
            default:
                throw new IllegalEscapedCharacter(escaped);
        }
    }

    public int parseUnicodeEscaped(String uEscaped) throws IllegalEscapedCharacter {
        if (uEscaped.length() < 6) throw new IllegalEscapedCharacter("< 6 : "+uEscaped);
        if (!uEscaped.startsWith("\\u")) throw new IllegalEscapedCharacter("> 6 : "+uEscaped);
        String hex4Digit = uEscaped.substring(2);
        return Integer.valueOf(hex4Digit, 16);
    }

}
