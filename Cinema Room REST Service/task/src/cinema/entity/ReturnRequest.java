package cinema.entity;

public class ReturnRequest {
    private String token;

    public ReturnRequest() {
    }

    public ReturnRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
