package http;

public enum HttpMethod {
  HEAD,
  GET,
  PUT,
  POST,
  DELETE;

  public static HttpMethod caseInsensitiveValueOf(String s) {
    return HttpMethod.valueOf(s.toUpperCase());
  }
}
