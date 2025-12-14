package http;

import http.model.HttpRequest;
import http.model.HttpResponse;
import http.router.HttpRouter;
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
      do {
        HttpRequest request = requestParser.parseRequest();

        Handler handler = router.route(request.path());
        HttpResponse response = handler.handle(request);

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
