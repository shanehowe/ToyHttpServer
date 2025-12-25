package http;

import java.io.IOException;
import java.net.Socket;

import http.writer.HttpResponseWriter;

public class HttpResponseWriterProvider {
  public HttpResponseWriter provide(Socket connection) throws IOException {
    return new HttpResponseWriter(connection.getOutputStream());
  }
}
