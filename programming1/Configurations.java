// Purpose: This class stores the configurations of the game, including the board size, the length to win, and the board itself.

public class Configurations {
  int boardSize;
  int lengthToWin;
  char[][] board;

  public Configurations(int boardSize, int lengthToWin, int maxLevels) {
    // Each entry stores a space
    this.boardSize = boardSize;
    this.lengthToWin = lengthToWin;
    board = new char[boardSize][boardSize];
    for (int i = 0; i < boardSize; i++) {
      for (int j = 0; j < boardSize; j++) {
        board[i][j] = ' ';
      }
    }
  }

  public HashDictionary createDictionary() {
    return new HashDictionary(6007);
  }

  public int repeatedConfiguration(HashDictionary hashTable) {
    // Returns the score of the configuration if it is stored in the dictionary
    String config = "";
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        config += board[i][j];
      }
    }

    // If the configuration is not found, return -1
    try {
      return hashTable.get(config);
    } catch (DictionaryException e) {
      return -1;
    }
  }

  public void addConfiguration(HashDictionary hashTable, int score) {
    // Adds the configuration and score to the dictionary
    String config = "";
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        config += board[i][j];
      }
    }

    // If the configuration is already in the dictionary, throw an exception
    try {
      hashTable.put(new Data(config, score));
    } catch (DictionaryException e) {
      System.out.println("Error: " + e);
    }
  }

  public void savePlay(int row, int col, char symbol) {
    // Saves the play on the board
    board[row][col] = symbol;
  }

  public boolean squareIsEmpty(int row, int col) {
    // Returns true if the square is empty, and false otherwise
    return board[row][col] == ' ';
  }

  public boolean wins(char symbol) {

    // Check rows
    for (int i = 0; i < board.length; i++) {
      int count = 0;
      for (int j = 0; j < board.length; j++) {
        if (board[i][j] == symbol) {
          count++;
        } else {
          count = 0;
        }
        if (count == lengthToWin) {
          return true;
        }
      }
    }

    // Check columns
    for (int i = 0; i < board.length; i++) {
      int count = 0;
      for (int j = 0; j < board.length; j++) {
        if (board[j][i] == symbol) {
          count++;
        } else {
          count = 0;
        }
        if (count == lengthToWin) {
          return true;
        }
      }
    }

    // Check diagonals from top left to bottom right
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        int count = 0;
        for (int k = 0; k < board.length; k++) {
          if (i + k < board.length && j + k < board.length) {
            if (board[i + k][j + k] == symbol) {
              count++;
            } else {
              count = 0;
            }
            if (count == lengthToWin) {
              return true;
            }
          }
        }
      }
    }

    // Check diagonals from top right to bottom left
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        int count = 0;
        for (int k = 0; k < board.length; k++) {
          if (i + k < board.length && j - k >= 0) {
            if (board[i + k][j - k] == symbol) {
              count++;
            } else {
              count = 0;
            }
            if (count == lengthToWin) {
              return true;
            }
          }
        }
      }
    }

    return false;

  }

  public boolean isDraw() {
    // Returns true if the board is full and no player has won the game, and false
    // otherwise.
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        if (board[i][j] == ' ') {
          return false;
        }
      }
    }
    return true;
  }

  public int evalBoard() {
    if (wins('O')) {
      return 3; // Computer wins
    } else if (wins('X')) {
      return 0; // Human wins
    } else if (isDraw()) {
      return 2; // Draw
    } else {
      return 1; // Game is still undecided
    }
  }

}
