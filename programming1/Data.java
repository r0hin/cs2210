// Purpose: This class is used to store the configuration and score of a single game.

public class Data {
  private String configuration;
  private int score;

  public Data(String config, int score) {
    // Constructor
    configuration = config;
    this.score = score;
  }

  public String getConfiguration() {
    // Returns the configuration
    return configuration;
  }

  public int getScore() {
    // Returns the score
    return score;
  }

}
