import http.BasicHttpConnectionHandler;
import http.ConnectionHandler;
import http.HttpRequestParserProvider;
import http.HttpResponseWriterProvider;
import http.HttpServer;
import http.model.HttpResponse;
import http.model.HttpStatusCode;
import http.router.HttpRouter;
import http.router.SimpleHttpRouter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  public static void main(String[] args) throws IOException {
    HttpRouter router = new SimpleHttpRouter()
        .addRoute(
            "/",
            request -> HttpResponse.newBuilder()
                .statusCode(HttpStatusCode.OK)
                .body("Hello, World!")
                .build());

    ExecutorService connectionPool = Executors.newFixedThreadPool(100);
    ConnectionHandler connectionHandler = new BasicHttpConnectionHandler(router, new HttpRequestParserProvider(),
        new HttpResponseWriterProvider());

    HttpServer server = new HttpServer(connectionHandler, connectionPool);
    server.start(8000);
  }
}
