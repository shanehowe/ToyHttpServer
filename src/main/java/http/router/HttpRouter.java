package http.router;

import http.Handler;

public interface HttpRouter {
  Handler route(String path);
}
