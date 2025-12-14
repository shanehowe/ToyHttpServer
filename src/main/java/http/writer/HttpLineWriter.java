package http.writer;

import java.io.IOException;
import java.io.OutputStream;

public class HttpLineWriter {
  private final OutputStream outputStream;

  public HttpLineWriter(OutputStream outputStream) {
    this.outputStream = outputStream;
  }

  public void writeln() throws IOException {
    writeln("");
  }

  public void writeln(String line) throws IOException {
    outputStream.write(String.format("%s\r\n", line).getBytes());
    outputStream.flush();
  }
}
