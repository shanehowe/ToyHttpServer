package http.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

public class HttpHeaders {
  private final Map<String, String> headers;

  public HttpHeaders() {
    this(new LinkedHashMap<>());
  }

  public HttpHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public void add(String key, String value) {
    headers.put(normalize(key), value);
  }

  public String get(String key) {
    return headers.get(normalize(key));
  }

  public Set<Entry<String, String>> entrySet() {
    return headers.entrySet();
  }

  private String normalize(String key) {
    return Objects.requireNonNull(key).toLowerCase();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;

    HttpHeaders that = (HttpHeaders) o;
    return headers.equals(that.headers);
  }

  @Override
  public int hashCode() {
    return headers.hashCode();
  }
}
