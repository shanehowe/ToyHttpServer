import java.io.IOException;

public class Main {
  public static void main(String[] args){
    try {
      ToyHttpServer.run();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
