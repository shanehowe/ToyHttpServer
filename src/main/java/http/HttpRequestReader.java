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
}
