import java.io.*;
import java.util.StringTokenizer;

public class Interface {

  private static BSTDictionary dictionary = new BSTDictionary();
  private static StringReader keyboard = new StringReader();

  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("Error: Please provide an input file.");
      return;
    }

    String inputFile = args[0];
    try {
      loadRecords(inputFile);
      processCommands();
    } catch (IOException e) {
      System.out.println("Error reading input file: " + e.getMessage());
    }
  }

  private static void loadRecords(String filename) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    String label, line;
    while ((label = reader.readLine()) != null && (line = reader.readLine()) != null) {
      label = label.toLowerCase();
      int type;
      String data;

      if (line.startsWith("-")) {
        type = 3;
        data = line.substring(1);
      } else if (line.startsWith("+")) {
        type = 4;
        data = line.substring(1);
      } else if (line.startsWith("*")) {
        type = 5;
        data = line.substring(1);
      } else if (line.startsWith("/")) {
        type = 2;
        data = line.substring(1);
      } else if (line.endsWith(".gif")) {
        type = 7;
        data = line;
      } else if (line.endsWith(".jpg")) {
        type = 6;
        data = line;
      } else if (line.endsWith(".html")) {
        type = 8;
        data = line;
      } else {
        type = 1;
        data = line;
      }

      try {
        dictionary.put(new Record(new Key(label, type), data));
      } catch (DictionaryException e) {
        System.err.println(e.getMessage());
      }
    }
    reader.close();
  }

  private static void processCommands() {
    while (true) {
      String commandLine = keyboard.read("Enter next command: ");
      StringTokenizer tokenizer = new StringTokenizer(commandLine);
      if (!tokenizer.hasMoreTokens())
        continue;

      String command = tokenizer.nextToken().toLowerCase();

      System.err.println(command);

      switch (command) {
        case "define":
          defineCommand(tokenizer);
          break;
        case "translate":
          translateCommand(tokenizer);
          break;
        case "sound":
          soundCommand(tokenizer);
          break;
        case "play":
          playCommand(tokenizer);
          break;
        case "say":
          sayCommand(tokenizer);
          break;
        case "show":
          showCommand(tokenizer);
          break;
        case "animate":
          animateCommand(tokenizer);
          break;
        case "browse":
          browseCommand(tokenizer);
          break;
        case "delete":
          deleteCommand(tokenizer);
          break;
        case "add":
          addCommand(tokenizer);
          break;
        case "list":
          listCommand(tokenizer);
          break;
        case "first":
          firstCommand();
          break;
        case "last":
          lastCommand();
          break;
        case "exit":
          return;
        default:
          System.out.println("Invalid command.");
          break;
      }
    }
  }

  private static void defineCommand(StringTokenizer tokenizer) {
    if (!tokenizer.hasMoreTokens()) {
      System.out.println("Invalid command.");
      return;
    }
    String label = tokenizer.nextToken().toLowerCase();
    Record record = dictionary.get(new Key(label, 1));
    if (record != null) {
      System.out.println(record.getDataItem());
    } else {
      System.out.println("The word " + label + " is not in the ordered dictionary.");
    }
  }

  private static void translateCommand(StringTokenizer tokenizer) {
    if (!tokenizer.hasMoreTokens()) {
      System.out.println("Invalid command.");
      return;
    }
    String label = tokenizer.nextToken().toLowerCase();
    Record record = dictionary.get(new Key(label, 2));
    if (record != null) {
      System.out.println(record.getDataItem());
    } else {
      System.out.println("There is no definition for the word " + label);
    }
  }

  private static void soundCommand(StringTokenizer tokenizer) {
    if (!tokenizer.hasMoreTokens()) {
      System.out.println("Invalid command.");
      return;
    }
    String label = tokenizer.nextToken().toLowerCase();
    Record record = dictionary.get(new Key(label, 3));
    if (record != null) {
      try {
        SoundPlayer sp = new SoundPlayer();
        sp.play(record.getDataItem());
      } catch (MultimediaException e) {
        System.out.println("Error playing sound file: " + e.getMessage());
      }
    } else {
      System.out.println("There is no sound file for " + label);
    }
  }

  private static void playCommand(StringTokenizer tokenizer) {
    if (!tokenizer.hasMoreTokens()) {
      System.out.println("Invalid command.");
      return;
    }
    String label = tokenizer.nextToken().toLowerCase();
    Record record = dictionary.get(new Key(label, 4));
    if (record != null) {
      try {
        SoundPlayer sp = new SoundPlayer();
        sp.play(record.getDataItem());
      } catch (MultimediaException e) {
        System.out.println("Error playing music file: " + e.getMessage());
      }
    } else {
      System.out.println("There is no music file for " + label);
    }
  }

  private static void sayCommand(StringTokenizer tokenizer) {
    if (!tokenizer.hasMoreTokens()) {
      System.out.println("Invalid command.");
      return;
    }
    String label = tokenizer.nextToken().toLowerCase();
    Record record = dictionary.get(new Key(label, 5));
    if (record != null) {
      try {
        SoundPlayer sp = new SoundPlayer();
        sp.play(record.getDataItem());
      } catch (MultimediaException e) {
        System.out.println("Error playing voice file: " + e.getMessage());
      }
    } else {
      System.out.println("There is no voice file for " + label);
    }
  }

  private static void showCommand(StringTokenizer tokenizer) {
    if (!tokenizer.hasMoreTokens()) {
      System.out.println("Invalid command.");
      return;
    }
    String label = tokenizer.nextToken().toLowerCase();
    System.err.println(label);
    Record record = dictionary.get(new Key(label, 6));
    if (record != null) {
      try {
        new PictureViewer().show(record.getDataItem());
      } catch (MultimediaException e) {
        System.out.println("Error showing image file: " + e.getMessage());
      }
    } else {
      System.out.println("There is no image file for " + label);
    }
  }

  private static void animateCommand(StringTokenizer tokenizer) {
    if (!tokenizer.hasMoreTokens()) {
      System.out.println("Invalid command.");
      return;
    }
    String label = tokenizer.nextToken().toLowerCase();
    Record record = dictionary.get(new Key(label, 7));
    if (record != null) {
      try {
        new PictureViewer().show(record.getDataItem());
      } catch (MultimediaException e) {
        System.out.println("Error showing animated image file: " + e.getMessage());
      }
    } else {
      System.out.println("There is no animated image file for " + label);
    }
  }

  private static void browseCommand(StringTokenizer tokenizer) {
    if (!tokenizer.hasMoreTokens()) {
      System.out.println("Invalid command.");
      return;
    }
    String label = tokenizer.nextToken().toLowerCase();
    Record record = dictionary.get(new Key(label, 8));
    if (record != null) {
      (new ShowHTML()).show(record.getDataItem());
    } else {
      System.out.println("There is no webpage called " + label);
    }
  }

  private static void deleteCommand(StringTokenizer tokenizer) {
    if (tokenizer.countTokens() < 2) {
      System.out.println("Invalid command.");
      return;
    }
    String label = tokenizer.nextToken().toLowerCase();
    int type = Integer.parseInt(tokenizer.nextToken());
    try {
      dictionary.remove(new Key(label, type));
    } catch (DictionaryException e) {
      System.out.println(e.getMessage());
    }
  }

  private static void addCommand(StringTokenizer tokenizer) {
    if (tokenizer.countTokens() < 3) {
      System.out.println("Invalid command.");
      return;
    }
    String label = tokenizer.nextToken().toLowerCase();
    int type = Integer.parseInt(tokenizer.nextToken());
    String data = tokenizer.nextToken();
    if (dictionary.get(new Key(label, type)) == null) {
      try {
        dictionary.put(new Record(new Key(label, type), data));
      } catch (Exception e) {
        System.out.println("Error adding record: " + e.getMessage());
        return;
      }
      System.out.println("Record added.");
    } else {
      System.out
          .println("A record with the given key (" + label + "," + type + ") is already in the ordered dictionary");
    }
  }

  private static void listCommand(StringTokenizer tokenizer) {
    if (!tokenizer.hasMoreTokens()) {
      System.out.println("Invalid command.");
      return;
    }
    String prefix = tokenizer.nextToken().toLowerCase();
    // Use smallest and successor methods to go through the dictionary
    Record curr = dictionary.smallest();
    while (curr != null) {
      if (curr.getKey().getLabel().startsWith(prefix)) {
        System.out.println(curr.getDataItem());
      }
      curr = dictionary.successor(curr.getKey());
    }
  }

  private static void firstCommand() {
    Record record = dictionary.smallest();
    if (record != null) {
      System.out.println(record.getDataItem());
    } else {
      System.out.println("The ordered dictionary is empty.");
    }
  }

  private static void lastCommand() {
    Record record = dictionary.largest();
    if (record != null) {
      System.out.println(record.getDataItem());
    } else {
      System.out.println("The ordered dictionary is empty.");
    }
  }
}