package http.requestfilters;

import java.util.List;
import java.util.Objects;

import http.handlers.Handler;
import http.model.HttpRequest;
import http.model.HttpResponse;

public class FilterChain {
  private List<RequestFilter> filters;
  private int index = 0;
  private Handler finalHandler;

  public FilterChain(List<RequestFilter> filters, Handler finalHandler) {
    this.filters = filters;
    this.finalHandler = Objects.requireNonNull(finalHandler, "finalHandler cannot be null");
  }

  public void run(HttpRequest request, HttpResponse response) {
    next(request, response);
  }

  public void next(HttpRequest request, HttpResponse response) {
    if (index < filters.size()) {
      RequestFilter requestFilter = filters.get(index++);
      requestFilter.filter(request, response, this);
    } else {
      finalHandler.handle(request, response);
    }
  }
}
