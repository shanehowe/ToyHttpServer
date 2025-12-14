package http.router;

import http.Handler;
import http.model.HttpResponse;
import http.model.HttpStatusCode;
import java.util.HashMap;
import java.util.Map;

public class SimpleHttpRouter implements HttpRouter {
  Map<String, Handler> routes;

  public SimpleHttpRouter(Map<String, Handler> routes) {
    this.routes = routes;
  }

  public SimpleHttpRouter() {
    this(new HashMap<>());
  }

  @Override
  public Handler route(String path) {
    return routes.getOrDefault(
        path, request -> HttpResponse.newBuilder().statusCode(HttpStatusCode.NOT_FOUND).build());
  }

  public SimpleHttpRouter addRoute(String path, Handler handler) {
    routes.put(path, handler);
    return this;
  }
}
