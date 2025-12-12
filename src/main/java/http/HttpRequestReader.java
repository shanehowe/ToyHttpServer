package http;

import http.parsers.HeaderParser;
import http.parsers.RequestLineParser;
import http.reader.LineReader;
import java.io.IOException;

public class HttpRequestReader {

  private final LineReader reader;
  private final RequestLineParser requestLineParser;
  private final HeaderParser headerParser;

  public HttpRequestReader(
      LineReader reader, RequestLineParser requestLineParser, HeaderParser headerParser) {
    this.reader = reader;
    this.requestLineParser = requestLineParser;
    this.headerParser = headerParser;
  }

  public HttpRequest readRequest() throws IOException {
    String requestLineString = reader.readLine().trim();
    RequestLine requestLine = requestLineParser.parse(requestLineString);
    HttpHeaders headers = headerParser.parse(reader);
    return new HttpRequest(
        requestLine.method(), requestLine.path(), requestLine.version(), headers);
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
