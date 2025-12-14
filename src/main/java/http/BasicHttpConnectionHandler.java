package http;

import http.model.HttpRequest;
import http.model.HttpResponse;
import http.router.HttpRouter;
import http.writer.HttpResponseWriter;
import java.io.IOException;
import java.net.Socket;

public class BasicHttpConnectionHandler implements ConnectionHandler {
  private final HttpRouter router;
  private final HttpRequestParserProvider parserProvider;

  public BasicHttpConnectionHandler(HttpRouter router, HttpRequestParserProvider parserProvider) {
    this.router = router;
    this.parserProvider = parserProvider;
  }

  @Override
  public void handleConnection(Socket connection) {
    boolean keepAlive;
    try {
      HttpRequestParser requestParser = parserProvider.provide(connection);
      HttpResponseWriter httpResponseWriter = new HttpResponseWriter(connection.getOutputStream());
      do {
        HttpRequest request = requestParser.parseRequest();

        Handler handler = router.route(request.path());
        HttpResponse response = handler.handle(request);
        response.headers().add("Connection", keepAlive(request) ? "keep-alive" : "close");
        response.headers().add("Content-Length", String.valueOf(response.body().length()));

        httpResponseWriter.writeResponse(response);

        keepAlive = keepAlive(request);
      } while (keepAlive);

      connection.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private boolean keepAlive(HttpRequest request) {
    String connectionHeader = request.headers().get("Connection");
    if (connectionHeader != null) {
      return connectionHeader.equalsIgnoreCase("keep-alive");
    }
    return request.version().equals("HTTP/1.1");
  }
}
