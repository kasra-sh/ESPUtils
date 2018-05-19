package ir.kasra_sh.ESPUtils.ereqt;

import java.util.ArrayList;
import java.util.Arrays;

public class RequestData {

    private RequestType type;
    private String url;
    private ArrayList<KeyValue> args = new ArrayList<>();
    private ArrayList<KeyValue> headers = new ArrayList<>();
    private byte[] body;

    private RequestData(String url, RequestType requestType) {
        this.url = url;
        this.type = requestType;
    }

    public static RequestData GET(String url) {
        return new RequestData(url, RequestType.GET);
    }

    public static RequestData POST(String url) {
        return new RequestData(url, RequestType.POST);
    }

//    public static RequestData PUT(String url) {
//        return new RequestData(url, RequestType.PUT);
//    }
//
//    public static RequestData DELETE(String url) {
//        return new RequestData(url, RequestType.DELETE);
//    }
//
//    public static RequestData OPTION(String url) {
//        return new RequestData(url, RequestType.OPTION);
//    }

    public RequestData withArgs(KeyValue... args) {
        this.args.addAll(Arrays.asList(args));
        return this;
    }

    public RequestData withArg(KeyValue arg) {
        args.add(arg);
        return this;
    }

    public RequestData withArg(String key, String value) {
        args.add(KeyValue.make(key, value));
        return this;
    }

    public RequestData withHeader(KeyValue header) {
        putOrReplaceIfExists(header);
        return this;
    }

    private void putOrReplaceIfExists(KeyValue kv){
        String lower = kv.key.toLowerCase();
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).key.toLowerCase().equals(lower)) {
                headers.remove(i);
                headers.add(kv);
                return;
            }
        }
        headers.add(kv);
    }

    public RequestData withHeader(String key, String value) {
        headers.add(KeyValue.make(key, value));
        return this;
    }

    public RequestData withHeaders(KeyValue... headers) {
        this.headers.addAll(Arrays.asList(headers));
        return this;
    }

    public RequestData withBody(byte[] body) {
        if (type == RequestType.GET) return this;
        if (body != null) {
            this.body = body;
            withHeader("Content-Length",String.valueOf(body.length));
        }
        return this;
    }

}
