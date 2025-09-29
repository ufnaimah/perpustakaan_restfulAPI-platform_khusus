package com.polstat.perpustakaan.rpc;

public class JsonRpcResponse {
    private String jsonrpc = "2.0";
    private Object result;
    private Object error;
    private String id;

    public JsonRpcResponse(Object result, String id) {
        this.result = result;
        this.id = id;
    }

    // Getter & Setter
    public String getJsonrpc() { return jsonrpc; }
    public void setJsonrpc(String jsonrpc) { this.jsonrpc = jsonrpc; }
    public Object getResult() { return result; }
    public void setResult(Object result) { this.result = result; }
    public Object getError() { return error; }
    public void setError(Object error) { this.error = error; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}
