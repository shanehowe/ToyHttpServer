package http.router;

import http.handlers.Handler;

public interface HttpRouter {
  Handler route(String path);
}
