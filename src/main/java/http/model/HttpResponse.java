package http.model;

public record HttpResponse(HttpStatusCode statusCode, HttpHeaders headers, String body) {

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
