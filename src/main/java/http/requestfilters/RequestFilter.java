package http.requestfilters;

import http.model.HttpRequest;
import http.model.HttpResponse;

public interface RequestFilter {

  void filter(HttpRequest request, HttpResponse response);
}
