package http.writer;

import http.model.HttpResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map.Entry;

public class HttpResponseWriter {
  private final HttpLineWriter writer;

  public HttpResponseWriter(OutputStream outputStream) {
    this.writer = new HttpLineWriter(outputStream);
  }

  public void writeResponse(HttpResponse response) throws IOException {
    writer.writeln(
        String.format(
            "%s %d %s",
            "HTTP/1.1", response.getStatusCode().getCode(), response.getStatusCode().getStatusText()));

    for (Entry<String, String> entry : response.getHeaders().entrySet()) {
      writer.writeln(String.format("%s: %s", entry.getKey(), entry.getValue()));
    }
    writer.writeln();
    if (!response.getBody().isEmpty()) {
      writer.writeln(response.getBody());
    }
  }
}
