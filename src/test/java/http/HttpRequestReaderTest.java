package http;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class HttpRequestReaderTest {

  @Test
  void testReadRequestCorrectlyParsesRequestLine() throws IOException {
    byte[] bytes = "GET /users HTTP/1.1\r\n".getBytes(StandardCharsets.UTF_8);
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

    HttpRequestReader httpRequestReader = new HttpRequestReader(byteArrayInputStream);
    HttpRequest httpRequest = httpRequestReader.readRequest();

    HttpRequest expected = new HttpRequest(HttpMethod.GET, "/users", "HTTP/1.1");
    assertThat(httpRequest).isEqualTo(expected);
  }
}
