package http.model;

public class HttpResponse {
  private HttpStatusCode statusCode;
  private HttpHeaders headers;
  private String body;

  public HttpResponse(HttpStatusCode statusCode, HttpHeaders headers, String body) {
    this.statusCode = statusCode;
    this.headers = headers;
    this.body = body;
  }

  public HttpStatusCode getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(HttpStatusCode statusCode) {
    this.statusCode = statusCode;
  }

  public HttpHeaders getHeaders() {
    return headers;
  }

  public void setHeaders(HttpHeaders headers) {
    this.headers = headers;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private HttpStatusCode statusCode;
    private HttpHeaders headers = new HttpHeaders();
    private String body = "";

    public Builder statusCode(HttpStatusCode statusCode) {
      this.statusCode = statusCode;
      return this;
    }

    public Builder headers(HttpHeaders headers) {
      this.headers = headers;
      return this;
    }

    public Builder body(String body) {
      this.body = body;
      return this;
    }

    public HttpResponse build() {
      return new HttpResponse(statusCode, headers, body);
    }
  }
}
