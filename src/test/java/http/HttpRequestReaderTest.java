package http;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class HttpRequestReaderTest {

  private static HttpRequest getHttpRequest(byte[] bytes) throws IOException {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

    HttpRequestReader httpRequestReader = new HttpRequestReader(byteArrayInputStream);
    return httpRequestReader.readRequest();
  }

  @Test
  void testReadRequestCorrectlyParsesRequestLine() throws IOException {
    byte[] bytes = "GET /users HTTP/1.1\r\n".getBytes(StandardCharsets.UTF_8);
    HttpRequest httpRequest = getHttpRequest(bytes);

    HttpRequest expected =
        new HttpRequest(HttpMethod.GET, "/users", "HTTP/1.1", new LinkedHashMap<>());
    assertThat(httpRequest).isEqualTo(expected);
  }

  @Test
  void testReadRequestCorrectlyParsesHeaders() throws IOException {
    byte[] bytes = "GET /users HTTP/1.1\r\nConnection: Close\r\nX-Test: Hello World\r\n\r\n".getBytes(StandardCharsets.UTF_8);
    HttpRequest httpRequest = getHttpRequest(bytes);

    Map<String, String> expected = Map.of("Connection", "Close", "X-Test", "Hello World");
    assertThat(httpRequest.headers()).isEqualTo(expected);
  }
}
