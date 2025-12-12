package http.parsers;

import http.RequestLine;

public class RequestLineParser {

  public RequestLine parse(String requestLineString) {
    String[] requestLineSplit = requestLineString.split(" ");
    if (requestLineSplit.length != 3) {
      throw new IllegalArgumentException(
          "requestLineString when split by whitespace should be of length 3 but got:"
              + requestLineSplit.length);
    }
    return RequestLine.newBuilder()
        .method(requestLineSplit[0])
        .path(requestLineSplit[1])
        .version(requestLineSplit[2])
        .build();
  }
}
