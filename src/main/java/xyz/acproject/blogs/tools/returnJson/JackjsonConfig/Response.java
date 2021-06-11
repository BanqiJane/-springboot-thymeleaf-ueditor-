package xyz.acproject.blogs.tools.returnJson.JackjsonConfig;

public class Response<T> {
	private String code;
    private String msg;
    @SuppressWarnings("rawtypes")
	private Data data;
    public Response() {
    }

    public Response(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    @SuppressWarnings("rawtypes")
    public Response(String code, String msg, Data data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    @SuppressWarnings("rawtypes")
    public Response(ResponseCode code, Data data) {
        this.code = code.toString();
        this.msg = code.getCnMsg();
        this.data = data;
    }
    @SuppressWarnings("rawtypes")
    public Response(ResponseCode code, String msg, Data data) {
        this.code = code.toString();
        this.msg = code.getCnMsg();
        this.data = data;
    }
    @SuppressWarnings("rawtypes")
    public static Response success(Data data) {
        return new Response(ResponseCode.normal, "", data);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    @SuppressWarnings("rawtypes")
    public Data getdata() {
        return this.data;
    }
    @SuppressWarnings("rawtypes")
    public void setdata(Data data) {
        this.data = data;
    }
}
