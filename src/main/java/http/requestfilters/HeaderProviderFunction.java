package http.requestfilters;

import java.util.Optional;

import http.model.HttpRequest;
import http.model.HttpResponse;

@FunctionalInterface
public interface HeaderProviderFunction {
  Optional<String> provide(HttpRequest request, HttpResponse response);
}
