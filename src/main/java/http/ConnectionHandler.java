package http;

import java.net.Socket;

public interface ConnectionHandler {
  void handleConnection(Socket connection);
}
