package http.model;

public record HttpRequest(HttpMethod method, String path, String version, HttpHeaders headers) {

  public boolean keepAlive() {
    String connectionHeader = headers().get("Connection");
    if (connectionHeader != null) {
      return connectionHeader.equalsIgnoreCase("keep-alive");
    }
    return version().equalsIgnoreCase("HTTP/1.1");
  }
}
