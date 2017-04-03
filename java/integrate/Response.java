package game;

public class Response {

  private boolean isWinner;
  private boolean isValid;


  Response(boolean isValid, boolean isWinner) {
    this.isWinner = isWinner;
    this.isValid = isValid;
  }

  public boolean isWinner() {
    return isWinner;
  }

  public boolean isValid() {
    return isValid;
  }
}
