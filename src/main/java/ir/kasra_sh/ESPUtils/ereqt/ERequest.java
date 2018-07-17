package ir.kasra_sh.ESPUtils.ereqt;


import ir.kasra_sh.ESPUtils.MimeTypes;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ERequest {

    private HttpRequest request;
    private Map<String,String> params = new HashMap<>();

    public HttpRequest req() {
        return request;
    }

    /**
     * GET REQUEST
     */

    public static ERequest get(String url, Map<String,String> params, boolean encode) {
        ERequest er = new ERequest();
        er.request = HttpRequest.get(url, params, encode);
        return er;
    }

    public static ERequest get(String url, boolean encode) {
        ERequest er = new ERequest();
        er.request = HttpRequest.get(url, encode);
        return er;
    }

    public static ERequest get(String url) {
        ERequest er = new ERequest();
        er.request = HttpRequest.get(url);
        return er;
    }

    /**
     * POST REQUEST
     */

    public static ERequest post(String url, Map<String,String> params, boolean encode) {
        ERequest er = new ERequest();
        er.request = HttpRequest.post(url, params, encode);
        return er;
    }

    public static ERequest post(String url, boolean encode) {
        ERequest er = new ERequest();
        er.request = HttpRequest.post(url, encode);
        return er;
    }

    public static ERequest post(String url) {
        ERequest er= new ERequest();
        er.request = HttpRequest.post(url);
        return er;
    }

    /**
     * PUT REQUEST
     */

    public static ERequest put(String url, Map<String,String> params, boolean encode) {
        ERequest er = new ERequest();
        er.request = HttpRequest.put(url, params, encode);
        return er;
    }

    public static ERequest put(String url, boolean encode) {
        ERequest er = new ERequest();
        er.request = HttpRequest.put(url, encode);
        return er;
    }

    public static ERequest put(String url) {
        ERequest er= new ERequest();
        er.request = HttpRequest.put(url);
        return er;
    }

    /**
     * HEAD REQUEST
     */

    public static ERequest head(String url, Map<String,String> params, boolean encode) {
        ERequest er = new ERequest();
        er.request = HttpRequest.head(url, params, encode);
        return er;
    }

    public static ERequest head(String url, boolean encode) {
        ERequest er = new ERequest();
        er.request = HttpRequest.head(url, encode);
        return er;
    }

    public static ERequest head(String url) {
        ERequest er= new ERequest();
        er.request = HttpRequest.head(url);
        return er;
    }

    /**
     * OPTIONS REQUEST
     */

    public static ERequest options(String url) {
        ERequest er= new ERequest();
        er.request = HttpRequest.options(url);
        return er;
    }

    /**
     * REQUEST BODY
     */

    public ERequest body(InputStream is, int len, String mime) {
        request.contentLength(len);
        request.contentType(mime).send(is);
        return this;
    }

    public ERequest body(byte[] body, String mime) {
        return body(new ByteArrayInputStream(body), body.length, mime);
    }

    public ERequest body(String str, String mime){
        return body(str.getBytes(StandardCharsets.UTF_8), mime);
    }

    public ERequest bodyPart(String name, byte[] data) {
        request.part(name, new ByteArrayInputStream(data));
        return this;
    }

    public ERequest bodyPart(String name, String data) {
        request.part(name, data);
        return this;
    }

    public ERequest bodyPart(String name, String mime, byte[] data) {
        request.part(name, name, mime, new ByteArrayInputStream(data));
        return this;
    }

    public ERequest bodyPart(String name, Number value) {
        request.part(name, value);
        return this;
    }

    public ERequest bodyJson(String json) {
        return body(json, MimeTypes.Application.JSON);
    }

    public ERequest bodyText(String text) {
        return body(text, MimeTypes.Text.TXT);
    }

    public ERequest bodyBin(byte[] data) {
        return body(data, MimeTypes.Application.BIN);
    }

    public ERequest bodyXml(String xml) {
        return body(xml, MimeTypes.Application.XML);
    }

    /**
     * REQUEST HEADERS
     */

    public ERequest header(String k, String v) {
        request.header(k,v);
        return this;
    }

    public ERequest header(String k, Number v) {
        request.header(k,v);
        return this;
    }

    public String header(String name) {
        return request.header(name);
    }

    public List<String> headers(String name){
        return new ArrayList<>(Arrays.asList(request.headers(name)));
    }

    /**
     * REQUEST RESPONSE
     */

    public int status() {
        return request.code();
    }

    public boolean isOk() {
        return request.ok();
    }

    public boolean isBadRequest() {
        return request.badRequest();
    }

    public boolean isServerError() {
        return request.serverError();
    }

    public byte[] getBodyBytes() {
        return request.bytes();
    }

    public String getBodyStr() {
        return request.body(StandardCharsets.UTF_8.name());
    }

    public BufferedInputStream getBodyBuffer() {
        return request.buffer();
    }


    /**
     * EXTRAS
     */

    public ERequest userAgent(String ua) {
        request.userAgent(ua);
        return this;
    }

    public ERequest accept(String acc){
        request.accept(acc);
        return this;
    }

    public ERequest acceptCharset(String charset) {
        request.acceptCharset(charset);
        return this;
    }

    public ERequest acceptEncoding(String encoding) {
        request.acceptEncoding(encoding);
        return this;
    }

    public ERequest progress(final ProgressListener listener) {
        request.progress(new HttpRequest.UploadProgress() {
            @Override
            public void onUpload(long uploaded, long total) {
                listener.onProgress(total, uploaded);
            }
        });
        return this;
    }

    public ERequest timeoutRead(int ms) {
        request.readTimeout(ms);
        return this;
    }

    public ERequest timeoutConnect(int ms) {
        request.connectTimeout(ms);
        return this;
    }

    public String eTag(){
        return request.eTag();
    }

    public ERequest basicAuth(String name, String password) {
        request.basic(name, password);
        return this;
    }

    public interface ProgressListener {
        void onProgress(long all, long sent);
    }

    public ERequest acceptInsecureSSL() {
        request.trustAllCerts();
        request.trustAllHosts();
        return this;
    }

    public String cacheControl() {
        return request.cacheControl();
    }

}
