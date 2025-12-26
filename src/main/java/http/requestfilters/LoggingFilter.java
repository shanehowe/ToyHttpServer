package http.requestfilters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import http.model.HttpRequest;
import http.model.HttpResponse;

public class LoggingFilter implements RequestFilter {

  private static final Logger logger = LogManager.getLogger(LoggingFilter.class);

  @Override
  public void filter(HttpRequest request, HttpResponse response, FilterChain filterChain) {
    filterChain.next(request, response);
    logger.info("{} {} {}", response.getStatusCode(), request.method(), request.path());
  }
}
