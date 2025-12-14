package http;

import http.model.HttpRequest;
import http.model.HttpResponse;

@FunctionalInterface
public interface Handler {
  HttpResponse handle(HttpRequest request);
}
