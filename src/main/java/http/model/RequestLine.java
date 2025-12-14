package http.model;

public record RequestLine(HttpMethod method, String path, String version) {

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private HttpMethod method;
    private String path;
    private String version;

    public Builder method(String method) {
      this.method = HttpMethod.valueOf(method.toUpperCase());
      return this;
    }

    public Builder path(String path) {
      this.path = path;
      return this;
    }

    public Builder version(String version) {
      this.version = version;
      return this;
    }

    public RequestLine build() {
      return new RequestLine(method, path, version);
    }
  }
}
