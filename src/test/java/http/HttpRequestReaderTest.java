package http;

import static org.assertj.core.api.Assertions.*;

import http.model.HttpHeaders;
import http.model.HttpMethod;
import http.model.HttpRequest;
import http.parsers.HeaderParser;
import http.parsers.RequestLineParser;
import http.reader.HttpLineReader;
import http.reader.LineReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.junit.jupiter.api.Test;

class HttpRequestReaderTest {

  private static HttpRequest getHttpRequest(byte[] bytes) throws IOException {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
    LineReader reader = new HttpLineReader(byteArrayInputStream);
    HttpRequestParser httpRequestReader =
        new HttpRequestParser(reader, new RequestLineParser(), new HeaderParser());
    return httpRequestReader.parseRequest();
  }

  @Test
  void testReadRequestCorrectlyParsesRequestLine() throws IOException {
    byte[] bytes = "GET /users HTTP/1.1\r\n".getBytes(StandardCharsets.UTF_8);
    HttpRequest httpRequest = getHttpRequest(bytes);

    HttpRequest expected = new HttpRequest(HttpMethod.GET, "/users", "HTTP/1.1", new HttpHeaders());
    assertThat(httpRequest).isEqualTo(expected);
  }

  @Test
  void testReadRequestCorrectlyParsesHeaders() throws IOException {
    byte[] bytes =
        "GET /users HTTP/1.1\r\nConnection: Close\r\nX-Test: Hello World\r\n\r\n"
            .getBytes(StandardCharsets.UTF_8);
    HttpRequest httpRequest = getHttpRequest(bytes);

    HttpHeaders expected = new HttpHeaders(Map.of("connection", "Close", "x-test", "Hello World"));
    assertThat(httpRequest.headers()).isEqualTo(expected);
  }
}
