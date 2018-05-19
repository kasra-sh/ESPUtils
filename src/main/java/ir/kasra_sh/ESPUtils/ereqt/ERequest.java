package ir.kasra_sh.ESPUtils.ereqt;


public class ERequest {

    private RequestData data;

    private ERequest(RequestData data){
        this.data = data;
    }


    /** Builder methods **/
    public static ERequest get(String url) {
        return new ERequest(RequestData.GET(url));
    }

    public static ERequest post(String url) {
        return new ERequest(RequestData.POST(url));
    }

    public ERequest addHeader(String key, String value) {
        data.withHeader(key, value);
        return this;
    }

    public ERequest addArg(String key, String value) {
        data.withArg(key, value);
        return this;
    }

    public ERequest setBody(byte[] body) {
        data.withBody(body);
        data.withHeader("Content-Type", "application/json");
        return this;
    }

    public ERequest send() {
        return this;
    }

}
