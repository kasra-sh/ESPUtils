package ir.kasra_sh.ESPUtils.eson.internal;

import ir.kasra_sh.ESPUtils.ULog;
import ir.kasra_sh.ESPUtils.eson.EsonArray;
import ir.kasra_sh.ESPUtils.eson.EsonElement;
import ir.kasra_sh.ESPUtils.eson.EsonObject;
import ir.kasra_sh.ESPUtils.eson.EsonType;
import ir.kasra_sh.ESPUtils.eson.exceptions.IllegalEscapedCharacter;
import ir.kasra_sh.ESPUtils.eson.exceptions.IllegalJSONTokenException;
import ir.kasra_sh.ESPUtils.eson.exceptions.JSONNumberFormatException;
import ir.kasra_sh.ESPUtils.eson.exceptions.JSONStringFormatException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EsonParser {

    private EsonReader esonReader;
    private EsonReader.Token lastToken;

    public EsonParser(String json) {
        if (json.length()>0)
            esonReader = new EsonReader(json);
        else {
            esonReader = new EsonReader("{}");
        }
    }

    public EsonElement parse() throws Exception {
        try {
            return getBlock();
        } catch (Exception e) {
            throw e;
        }
    }

    private EsonElement getBlock() throws IllegalEscapedCharacter, JSONNumberFormatException, JSONStringFormatException, IOException, IllegalJSONTokenException {
        ULog.d("CALLS", "getBlock()");
        lastToken = getBlockStart();
        if (lastToken.getType() == TokenType.OBJ_START) {
            return EsonElement.make(getObject());
        }
        if (lastToken.getType() == TokenType.ARR_START) {
            return EsonElement.make(getArray());
        }
        return null;
    }

    private EsonObject getObject() throws IllegalEscapedCharacter, JSONNumberFormatException, JSONStringFormatException, IOException, IllegalJSONTokenException {
        EsonObject object = new EsonObject();
        ULog.d("CALLS", "getObject()");
        // Get pairs
        while (true) {
            lastToken = getKeyOrObjectEnd();
            if (lastToken.getType() == TokenType.OBJ_END) {
                return object;
            }
            if (lastToken.getType() == TokenType.STRING_LITERAL) {
                String key = lastToken.getStr();
                getColon();
                lastToken = getValue();
                if (lastToken.getType() != TokenType.OBJ_START
                        && lastToken.getType() != TokenType.ARR_START) {
                    putKeyValue(key, object);
                } else if (lastToken.getType() == TokenType.OBJ_START) {
                    object.put(key, getObject());
                } else if (lastToken.getType() == TokenType.ARR_START) {
                    object.put(key, getArray());
                }
//                System.out.println("BEFORE: "+lastToken.getStr());
                lastToken = getCommaOrObjectEnd();
                ULog.d("LAST", lastToken.getStr());
                if (lastToken.getType() == TokenType.OBJ_END) {
                    return object;
                }
            } else throw new IllegalJSONTokenException("WTF!");
        }
    }

    private EsonArray getArray() throws IllegalEscapedCharacter, JSONNumberFormatException, JSONStringFormatException, IOException, IllegalJSONTokenException {
        EsonArray array = new EsonArray();
        ULog.d("CALLS", "getObject()");
        // Get pairs
        while (true) {
            lastToken = getValueOrArrayEnd();
            if (lastToken.getType() == TokenType.ARR_END) {
                return array;
            } else {
                switch (lastToken.getType()) {
                    case OBJ_START:
                        array.put(getObject());
                        break;
                    case ARR_START:
                        array.put(getArray());
                        break;
                    case STRING_LITERAL:
                        array.put(lastToken.getStr());
                        break;
                    case NUMBER_LITERAL:
                        array.put(Double.parseDouble(lastToken.getStr()));
                        break;
                    case TRUE:
                        array.put(true);
                        break;
                    case FALSE:
                        array.put(false);
                        break;
                    case NULL:
                        array.put(new EsonElement(null, EsonType.NULL));
                }

            }

            lastToken = getCommaOrArrayEnd();
            ULog.d("LAST", lastToken.getStr());
            if (lastToken.getType() == TokenType.ARR_END) {
                return array;
            }
        }
    }

    // Helper methods
    private EsonReader.Token getBlockStart() throws JSONNumberFormatException, IllegalEscapedCharacter, IllegalJSONTokenException, JSONStringFormatException, IOException {
        return esonReader.getToken(TokenType.OBJ_START, TokenType.ARR_START);
    }

    private EsonReader.Token getKey() throws JSONNumberFormatException, IllegalEscapedCharacter, IllegalJSONTokenException, JSONStringFormatException, IOException {
        return esonReader.getToken(TokenType.STRING_LITERAL);
    }

    private EsonReader.Token getValue() throws JSONNumberFormatException, IllegalEscapedCharacter, IllegalJSONTokenException, JSONStringFormatException, IOException {
        return esonReader.getToken(TokenType.STRING_LITERAL, TokenType.NUMBER_LITERAL, TokenType.NULL, TokenType.TRUE, TokenType.FALSE, TokenType.OBJ_START, TokenType.ARR_START);
    }

    private EsonReader.Token getValueOrArrayEnd() throws JSONNumberFormatException, IllegalEscapedCharacter, IllegalJSONTokenException, JSONStringFormatException, IOException {
        return esonReader.getToken(TokenType.STRING_LITERAL, TokenType.NUMBER_LITERAL, TokenType.NULL, TokenType.TRUE, TokenType.FALSE, TokenType.OBJ_START, TokenType.ARR_START, TokenType.ARR_END);
    }


    private EsonReader.Token getColon() throws JSONNumberFormatException, IllegalEscapedCharacter, IllegalJSONTokenException, JSONStringFormatException, IOException {
        return esonReader.getToken(TokenType.COLON);
    }

    private EsonReader.Token getKeyOrObjectEnd() throws JSONNumberFormatException, IllegalEscapedCharacter, IllegalJSONTokenException, JSONStringFormatException, IOException {
        return esonReader.getToken(TokenType.OBJ_END, TokenType.STRING_LITERAL);
    }

    private EsonReader.Token getCommaOrObjectEnd() throws JSONNumberFormatException, IllegalEscapedCharacter, IllegalJSONTokenException, JSONStringFormatException, IOException {
        return esonReader.getToken(TokenType.OBJ_END, TokenType.COMMA);
    }

    private EsonReader.Token getCommaOrArrayEnd() throws JSONNumberFormatException, IllegalEscapedCharacter, IllegalJSONTokenException, JSONStringFormatException, IOException {
        return esonReader.getToken(TokenType.ARR_END, TokenType.COMMA);
    }

    private void putKeyValue(String key, EsonObject object) {
        switch (lastToken.getType()) {
            case STRING_LITERAL:
                object.put(key, lastToken.getStr());
//                ULog.d("VALUE", "STRING");
                break;
            case NUMBER_LITERAL:
                // Detect Int
                try {
                    object.put(key, Integer.parseInt(lastToken.getStr()));
//                    System.out.println("put int "+Integer.parseInt(lastToken.getStr()));
                } catch (Exception e){
                    try {
                        object.put(key, Long.parseLong(lastToken.getStr()));
//                        System.out.println("put long "+Long.parseLong(lastToken.getStr()));

                    } catch (Exception e1){
                        try {
//                            System.out.println("put double");
                            object.put(key, Double.parseDouble(lastToken.getStr()));
                        } catch (Exception e3){
                            ULog.e("EsonParser","Wtf? :( -> "+lastToken.getStr());
                        }
                    }
                }
//                ULog.d("VALUE", "NUMBER");
                break;
            case TRUE:
                object.put(key, true);
//                ULog.d("VALUE", "TRUE");
                break;
            case FALSE:
                object.put(key, false);
//                ULog.d("VALUE", "FALSE");
                break;
            case NULL:
                object.put(key, EsonElement.makeNull());
//                ULog.d("VALUE", "NULL");
                break;
        }
    }
}
