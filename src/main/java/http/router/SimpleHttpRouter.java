package http.router;

import http.Handler;
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
        path, (request, response) -> response.setStatusCode(HttpStatusCode.NOT_FOUND));
  }

  public SimpleHttpRouter addRoute(String path, Handler handler) {
    routes.put(path, handler);
    return this;
  }
}
