package http;

import http.parsers.RequestLineParser;
import http.reader.LineReader;
import java.io.IOException;

public class HttpRequestReader {

  private final LineReader reader;
  private final RequestLineParser requestLineParser;

  public HttpRequestReader(LineReader reader, RequestLineParser requestLineParser) {
    this.reader = reader;
    this.requestLineParser = requestLineParser;
  }

  public HttpRequest readRequest() throws IOException {
    String requestLineString = reader.readLine().trim();
    RequestLine requestLine = requestLineParser.parse(requestLineString);
    HttpHeaders headers = parseHeaders();
    return new HttpRequest(
        requestLine.method(), requestLine.path(), requestLine.version(), headers);
  }

  private HttpHeaders parseHeaders() throws IOException {
    String headerLine;
    HttpHeaders headers = new HttpHeaders();
    while (!(headerLine = reader.readLine().trim()).isBlank()) {
      String[] splitLine = headerLine.split(": ", 2);
      headers.add(splitLine[0], splitLine[1]);
    }
    return headers;
  }

  public record RequestLine(HttpMethod method, String path, String version) {

    public static Builder newBuilder() {
      return new Builder();
    }

    public static class Builder {
      private HttpMethod method;
      private String path;
      private String version;

      public Builder method(String method) {
        this.method = HttpMethod.caseInsensitiveValueOf(method);
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
}
