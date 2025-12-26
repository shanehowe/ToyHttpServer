package http.requestfilters;

import java.util.Map;
import java.util.Optional;

import http.model.HttpHeaders;
import http.model.HttpRequest;
import http.model.HttpResponse;

public class DefaultHeadersFilter implements RequestFilter {
  private final Map<String, HeaderProviderFunction> defaultHeaders;

  public DefaultHeadersFilter(Map<String, HeaderProviderFunction> defaultHeaders) {
    this.defaultHeaders = defaultHeaders;
  }

  @Override
  public void filter(HttpRequest request, HttpResponse response, FilterChain filterChain) {
    filterChain.next(request, response);

    HttpHeaders headers = response.getHeaders();

    for (var entry : defaultHeaders.entrySet()) {
      HeaderProviderFunction func = entry.getValue();
      Optional<String> maybeHeaderValue = func.provide(request, response);
      if (maybeHeaderValue.isPresent()) {
        headers.add(entry.getKey(), maybeHeaderValue.get());
      }
    }
  }
}
