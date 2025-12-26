package http.handlers;

import http.model.HttpRequest;
import http.model.HttpResponse;

@FunctionalInterface
public interface Handler {
  void handle(HttpRequest request, HttpResponse response);
}
