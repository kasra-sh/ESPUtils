package ir.kasra_sh.ESPUtils.ereqt;

import ir.kasra_sh.ESPUtils.MimeTypes;

import java.util.List;

public class RequestResult {
    private int status;

    private ERequest request;

    public RequestResult(ERequest request) {
        this.request = request;
        this.status = request.status();
    }

    public boolean isOk() {
        return request.isOk();
    }

    public ERequest getRequest() {
        return request;
    }

    public String bodyStr() {
        return request.getBodyStr();
    }

    public byte[] bodyBytes() {
        return request.getBodyBytes();
    }

    public String header(String name) {
        return request.header(name);
    }

    public List<String> headers(String name) {
        return request.headers(name);
    }

    public int status() {
        return status;
    }

    public String contentType() {
        return request.req().contentType();
    }

    public int contentLength() {
        return request.req().contentLength();
    }

    public boolean isType(String mime){
        return contentType().equalsIgnoreCase(mime);

    }

    public boolean isJson() {
        return isType(MimeTypes.Application.JSON);
    }

    public boolean isText() {
        return isType(MimeTypes.Text.HTML);
    }

    public boolean isHtml() {
        return isType(MimeTypes.Text.HTML);
    }
}
