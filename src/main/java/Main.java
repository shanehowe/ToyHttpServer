import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import http.BasicHttpConnectionHandler;
import http.ConnectionHandler;
import http.HttpRequestParserProvider;
import http.HttpResponseWriterProvider;
import http.HttpServer;
import http.model.HttpStatusCode;
import http.requestfilters.DefaultHeadersFilter;
import http.requestfilters.HeaderProviderFunction;
import http.requestfilters.LoggingFilter;
import http.requestfilters.RequestFilter;
import http.router.HttpRouter;
import http.router.SimpleHttpRouter;

public class Main {
  public static void main(String[] args) throws IOException {
    HttpRouter router = new SimpleHttpRouter()
        .addRoute(
            "/", ((request, response) -> {
              response.setStatusCode(HttpStatusCode.OK);
              response.setBody("Hello from JavaHttpServer!");
            }));

    Map<String, HeaderProviderFunction> headersToApply = new HashMap<>();
    headersToApply.put("Connection", (request, response) -> {
      String value = request.keepAlive() ? "keep-alive" : "close";
      return Optional.of(value);
    });
    headersToApply.put("Content-Length", (request, response) -> {
      if (response.getBody() != null && !response.getBody().isEmpty()) {
        return Optional.of(Integer.toString(response.getBody().length()));
      }
      return Optional.empty();
    });
    headersToApply.put("Server", (request, response) -> {
      return Optional.of("JavaHttpServer");
    });
    headersToApply.put("Date", (request, response) -> {
      ZonedDateTime dateTime = ZonedDateTime.now(ZoneOffset.UTC);
      String rfcFormatted = dateTime.format(DateTimeFormatter.RFC_1123_DATE_TIME);
      return Optional.of(rfcFormatted);
    });

    List<RequestFilter> filters = List.of(new LoggingFilter(), new DefaultHeadersFilter(headersToApply));
    ExecutorService connectionPool = Executors.newFixedThreadPool(100);

    ConnectionHandler connectionHandler = new BasicHttpConnectionHandler(router, new HttpRequestParserProvider(),
        new HttpResponseWriterProvider(), filters);

    HttpServer server = new HttpServer(connectionHandler, connectionPool);
    server.start(8000);
  }
}
