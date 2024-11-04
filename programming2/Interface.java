// Interface.java, Programming Assignment 2.
// Provides an interface for the user to interact with the dictionary.

import java.io.*;
import java.util.StringTokenizer;

public class Interface {
  private static BSTDictionary dictionary = new BSTDictionary();
  private static StringReader keyboard = new StringReader();

  // Main method to read the input file and process commands
  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("Error: Please provide an input file.");
      return;
    }

    // Read the input file
    String inputFile = args[0];
    try {
      loadRecords(inputFile);
      processCommands();
    } catch (IOException e) {
      System.out.println("Error reading input file: " + e.getMessage());
    }
  }

  // Load records from the input file
  private static void loadRecords(String filename) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    String label, line;
    // Read the input file line by line, given that each record is in two lines
    while ((label = reader.readLine()) != null && (line = reader.readLine()) != null) {
      label = label.toLowerCase();
      int type;
      String data;

      if (line.startsWith("-")) {
        // Define
        type = 3;
        data = line.substring(1);
      } else if (line.startsWith("+")) {
        // Translate
        type = 4;
        data = line.substring(1);
      } else if (line.startsWith("*")) {
        // Say
        type = 5;
        data = line.substring(1);
      } else if (line.startsWith("/")) {
        // Play
        type = 2;
        data = line.substring(1);
      } else if (line.endsWith(".gif")) {
        // Animate
        type = 7;
        data = line;
      } else if (line.endsWith(".jpg")) {
        // Show
        type = 6;
        data = line;
      } else if (line.endsWith(".html")) {
        // Browse
        type = 8;
        data = line;
      } else {
        // Define
        type = 1;
        data = line;
      }

      // Add the record to the dictionary
      try {
        dictionary.put(new Record(new Key(label, type), data));
      } catch (DictionaryException e) {
        System.err.println(e.getMessage());
      }
    }
    reader.close();
  }

  // Process commands from the user
  private static void processCommands() {
    while (true) {
      // Read the next command from the user
      String commandLine = keyboard.read("Enter next command: ");
      // Tokenize the command, ignoring leading/trailing whitespace
      StringTokenizer tokenizer = new StringTokenizer(commandLine);
      if (!tokenizer.hasMoreTokens())
        continue;
      String command = tokenizer.nextToken().toLowerCase();

      // Process the command
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

  // Command: define, simply print the data item associated with the key
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

  // Command: translate, simply print the data item associated with the key
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

  // Command: sound, play the sound file associated with the key
  private static void soundCommand(StringTokenizer tokenizer) {
    if (!tokenizer.hasMoreTokens()) {
      System.out.println("Invalid command.");
      return;
    }
    String label = tokenizer.nextToken().toLowerCase();
    Record record = dictionary.get(new Key(label, 3));
    if (record != null) {
      try {
        // Play the sound file using SoundPlayer
        SoundPlayer sp = new SoundPlayer();
        sp.play(record.getDataItem());
      } catch (MultimediaException e) {
        // Catch any exceptions thrown by SoundPlayer
        System.out.println("Error playing sound file: " + e.getMessage());
      }
    } else {
      System.out.println("There is no sound file for " + label);
    }
  }

  // Command: play, play the music file associated with the key
  private static void playCommand(StringTokenizer tokenizer) {
    if (!tokenizer.hasMoreTokens()) {
      System.out.println("Invalid command.");
      return;
    }
    String label = tokenizer.nextToken().toLowerCase();
    Record record = dictionary.get(new Key(label, 4));
    if (record != null) {
      try {
        // Play the music file using SoundPlayer
        SoundPlayer sp = new SoundPlayer();
        sp.play(record.getDataItem());
      } catch (MultimediaException e) {
        // Catch any exceptions thrown by SoundPlayer
        System.out.println("Error playing music file: " + e.getMessage());
      }
    } else {
      System.out.println("There is no music file for " + label);
    }
  }

  // Command: say, play the voice file associated with the key
  private static void sayCommand(StringTokenizer tokenizer) {
    if (!tokenizer.hasMoreTokens()) {
      System.out.println("Invalid command.");
      return;
    }
    String label = tokenizer.nextToken().toLowerCase();
    Record record = dictionary.get(new Key(label, 5));
    if (record != null) {
      try {
        // Play the voice file using SoundPlayer
        SoundPlayer sp = new SoundPlayer();
        sp.play(record.getDataItem());
      } catch (MultimediaException e) {
        // Catch any exceptions thrown by SoundPlayer
        System.out.println("Error playing voice file: " + e.getMessage());
      }
    } else {
      System.out.println("There is no voice file for " + label);
    }
  }

  // Command: show, show the image file associated with the key
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
        // Show the image file using PictureViewer
        new PictureViewer().show(record.getDataItem());
      } catch (MultimediaException e) {
        // Catch any exceptions thrown by PictureViewer
        System.out.println("Error showing image file: " + e.getMessage());
      }
    } else {
      System.out.println("There is no image file for " + label);
    }
  }

  // Command: animate, show the animated image file associated with the key
  private static void animateCommand(StringTokenizer tokenizer) {
    if (!tokenizer.hasMoreTokens()) {
      System.out.println("Invalid command.");
      return;
    }
    String label = tokenizer.nextToken().toLowerCase();
    Record record = dictionary.get(new Key(label, 7));
    if (record != null) {
      try {
        // Show the animated image file using PictureViewer
        new PictureViewer().show(record.getDataItem());
      } catch (MultimediaException e) {
        // Catch any exceptions thrown by PictureViewer
        System.out.println("Error showing animated image file: " + e.getMessage());
      }
    } else {
      System.out.println("There is no animated image file for " + label);
    }
  }

  // Command: browse, show the webpage associated with the key
  private static void browseCommand(StringTokenizer tokenizer) {
    if (!tokenizer.hasMoreTokens()) {
      System.out.println("Invalid command.");
      return;
    }
    String label = tokenizer.nextToken().toLowerCase();
    Record record = dictionary.get(new Key(label, 8));
    if (record != null) {
      // Show the webpage using ShowHTML
      (new ShowHTML()).show(record.getDataItem());
    } else {
      // If the webpage is not found, print an error message
      System.out.println("There is no webpage called " + label);
    }
  }

  // Command: delete, remove the record with the given key
  private static void deleteCommand(StringTokenizer tokenizer) {
    if (tokenizer.countTokens() < 2) {
      System.out.println("Invalid command.");
      return;
    }
    String label = tokenizer.nextToken().toLowerCase();
    int type = Integer.parseInt(tokenizer.nextToken());
    try {
      // Remove the record from the dictionary
      dictionary.remove(new Key(label, type));
    } catch (DictionaryException e) {
      // Catch any exceptions thrown by the dictionary
      System.out.println(e.getMessage());
    }
  }

  // Command: add, add a new record to the dictionary
  private static void addCommand(StringTokenizer tokenizer) {
    if (tokenizer.countTokens() < 3) {
      // Check if the command has enough arguments
      System.out.println("Invalid command.");
      return;
    }
    String label = tokenizer.nextToken().toLowerCase();
    int type = Integer.parseInt(tokenizer.nextToken());
    String data = tokenizer.nextToken();
    if (dictionary.get(new Key(label, type)) == null) {
      try {
        // Add the record to the dictionary
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

  // Command: list, list all records with keys starting with the given prefix
  private static void listCommand(StringTokenizer tokenizer) {
    if (!tokenizer.hasMoreTokens()) {
      System.out.println("Invalid command.");
      return;
    }
    String prefix = tokenizer.nextToken().toLowerCase();

    // To list the records without adjusting the dictionary class, I'll use
    // the smallest and successor methods to iterate through the dictionary
    // in-order and print the records with keys starting with the prefix.
    Record curr = dictionary.smallest();
    while (curr != null) {
      if (curr.getKey().getLabel().startsWith(prefix)) {
        System.out.println(curr.getDataItem());
      }
      // Get the next record in the dictionary
      curr = dictionary.successor(curr.getKey());
    }
  }

  // Command: first, print the data item of the smallest key in the dictionary
  private static void firstCommand() {
    Record record = dictionary.smallest();
    if (record != null) {
      System.out.println(record.getDataItem());
    } else {
      System.out.println("The ordered dictionary is empty.");
    }
  }

  // Command: last, print the data item of the largest key in the dictionary
  private static void lastCommand() {
    Record record = dictionary.largest();
    if (record != null) {
      System.out.println(record.getDataItem());
    } else {
      System.out.println("The ordered dictionary is empty.");
    }
  }
}