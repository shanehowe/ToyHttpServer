package http.reader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class HttpLineReader implements LineReader {

  private final InputStream stream;
  private final ByteArrayOutputStream buffer;

  public HttpLineReader(InputStream stream) {
    this.stream = stream;
    this.buffer = new ByteArrayOutputStream();
  }

  @Override
  public String readLine() throws IOException {
    int previous = -1;
    int current;
    while ((current = stream.read()) != -1) {
      if (previous == '\r' && current == '\n') {
        buffer.write(current);
        break;
      }
      buffer.write(current);
      previous = current;
    }
    String bufferString = buffer.toString(StandardCharsets.UTF_8);
    buffer.reset();
    return bufferString;
  }
}
