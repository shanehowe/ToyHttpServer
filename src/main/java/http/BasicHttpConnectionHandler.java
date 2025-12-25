package http;

import http.model.HttpRequest;
import http.model.HttpResponse;
import http.requestfilters.FilterChain;
import http.router.HttpRouter;
import http.writer.HttpResponseWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BasicHttpConnectionHandler implements ConnectionHandler {
  private final HttpRouter router;
  private final HttpRequestParserProvider parserProvider;
  private final HttpResponseWriterProvider responseWriterProvider;
  private static final Logger logger = LogManager.getLogger(BasicHttpConnectionHandler.class);

  public BasicHttpConnectionHandler(HttpRouter router, HttpRequestParserProvider parserProvider,
      HttpResponseWriterProvider responseWriterProvider) {
    this.router = router;
    this.parserProvider = parserProvider;
    this.responseWriterProvider = responseWriterProvider;
  }

  @Override
  public void handleConnection(Socket connection) {
    boolean keepAlive;
    try {
      HttpRequestParser requestParser = parserProvider.provide(connection);
      HttpResponseWriter httpResponseWriter = responseWriterProvider.provide(connection);
      do {
        HttpRequest request = requestParser.parseRequest();
        HttpResponse response = HttpResponse.newBuilder().build();

        Handler handler = router.route(request.path());
        FilterChain filterChain = new FilterChain(List.of(), handler::handle);
        filterChain.next(request, response);

        httpResponseWriter.writeResponse(response);

        keepAlive = request.keepAlive();
      } while (keepAlive);

      connection.close();
    } catch (IOException e) {
      // TODO it will be better to write a respone with 500 status code here.
      logger.error("failed to handle http request", e);
      throw new RuntimeException(e);
    }
  }
}
