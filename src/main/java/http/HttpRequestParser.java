package http;

import http.parsers.HeaderParser;
import http.parsers.RequestLineParser;
import http.reader.LineReader;
import java.io.IOException;

public class HttpRequestParser {

  private final LineReader reader;
  private final RequestLineParser requestLineParser;
  private final HeaderParser headerParser;

  public HttpRequestParser(
      LineReader reader, RequestLineParser requestLineParser, HeaderParser headerParser) {
    this.reader = reader;
    this.requestLineParser = requestLineParser;
    this.headerParser = headerParser;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public HttpRequest parseRequest() throws IOException {
    String requestLineString = reader.readLine().trim();
    RequestLine requestLine = requestLineParser.parse(requestLineString);
    HttpHeaders headers = headerParser.parse(reader);
    return new HttpRequest(
        requestLine.method(), requestLine.path(), requestLine.version(), headers);
  }

  public static class Builder {
    private LineReader reader;
    private RequestLineParser requestLineParser;
    private HeaderParser headerParser;

    public Builder reader(LineReader reader) {
      this.reader = reader;
      return this;
    }

    public Builder requestLineParser(RequestLineParser requestLineParser) {
      this.requestLineParser = requestLineParser;
      return this;
    }

    public Builder headerParser(HeaderParser headerParser) {
      this.headerParser = headerParser;
      return this;
    }

    public HttpRequestParser build() {
      return new HttpRequestParser(reader, requestLineParser, headerParser);
    }
  }
}
