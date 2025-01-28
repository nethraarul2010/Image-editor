package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import logger.ViewLogger;
import model.imageprocessingmodel.ImageProcessorModelInterface;

/**
 * This class represents an image processing controller that handles user commands.
 */
public class ImageProcessorController implements ControllerInterface {

  private final Readable in;
  private final ViewLogger viewLogger;
  private final ImageProcessorModelInterface imageProcessorModel;

  /**
   * Constructs an ImageProcessorController with the specified dependencies.
   *
   * @param viewLogger          the logger for displaying messages
   * @param imageProcessorModel the model for processing image commands
   * @param in                  the input source (e.g., user input)
   * @param out                 the output destination (e.g., console or file)
   */
  public ImageProcessorController(ViewLogger viewLogger,
      ImageProcessorModelInterface imageProcessorModel,
      Readable in, Appendable out) {
    this.in = in;
    this.viewLogger = viewLogger;
    this.imageProcessorModel = imageProcessorModel;
  }

  /**
   * Starts the image processing controller, allowing users to enter and execute commands.
   */
  public void startImageProcessingController() throws IOException {
    try {
      handleCommands();
    } catch (Exception e) {
      viewLogger.logException(e);
    }
  }

  /**
   * Handles user commands for image processing.
   *
   * @throws IOException if there is an issue with input or output
   */
  private void handleCommands() throws IOException {
    Scanner scanner = new Scanner(this.in);
    String input;

    while (scanner.hasNext()) {
      try {
        input = scanner.nextLine();
        String[] parts = input.split(" ");
        if (parts.length == 1 && parts[0].isBlank()) {
          continue;
        }
        if (parts.length == 1 && !parts[0].equals("exit")) {
          throw new IllegalArgumentException("Invalid command. Try again.");
        }
        if (parts[0].equals("exit")) {
          break;
        }
        if (parts[0].equals("run")) {
          handleScriptFile(parts);
        } else {
          processCommands(parts);
        }
        viewLogger.logString("Command ran successfully \n");
      } catch (Exception e) {
        viewLogger.logException(e);
      }
    }
  }

  /**
   * Handles a script file containing image processing commands.
   *
   * @param commandInput the command input
   * @throws IOException if there is an issue with input or output
   */
  private void handleScriptFile(String[] commandInput) throws IOException {
    if (commandInput.length != 2) {
      throw new IllegalArgumentException(
          "Invalid run command. Usage: run <script-file-name>");
    } else {
      File scriptFile = new File(commandInput[1]);

      if (scriptFile.exists() && scriptFile.isFile()) {
        try (BufferedReader reader = new BufferedReader(new FileReader(scriptFile))) {
          String line;
          while ((line = reader.readLine()) != null) {
            try {
              String[] parts = line.split(" ");
              if (parts.length == 0) {
                throw new IllegalArgumentException("Invalid command in the script file.");
              }
              processCommands(parts);
            } catch (Exception e) {
              viewLogger.logException(e);
            }
          }
        }
      } else {
        throw new IllegalArgumentException("Invalid command : "
            + "File does not exist with name " + commandInput[1]);
      }
    }
  }

  private void processCommands(String[] parts) throws IOException {
    if (parts.length == 0) {
      throw new IllegalArgumentException("Invalid command. Try again.");
    }
    String command = parts[0];
    switch (command) {
      case "load":
        if (parts.length != 3) {
          throw new IllegalArgumentException(
              "Invalid load command. Usage: load <image-path> <image-name>");
        } else {
          imageProcessorModel.loadImage(parts[1], parts[2]);
        }
        break;
      case "save":
        if (parts.length != 3) {
          throw new IllegalArgumentException(
              "Invalid save command. Usage: save <image-path> <image-name>");
        } else {
          imageProcessorModel.saveImage(parts[1], parts[2]);
        }
        break;
      case "compress":
        if (parts.length != 4) {
          throw new IllegalArgumentException(
              "Invalid save command. Usage: brighten <brightness-factor> "
                  + "<current-image-name> <new-image-name>");
        } else {
          imageProcessorModel.compressImage(parts[2], parts[3], parts[1]);
        }
        break;
      case "brighten":
        if (parts.length != 4) {
          throw new IllegalArgumentException(
              "Invalid save command. Usage: brighten <brightness-factor> "
                  + "<current-image-name> <new-image-name>");
        } else {
          imageProcessorModel.brightenImage(parts[2], parts[3], parts[1]);
        }
        break;
      case "red-component":
        if (parts.length != 3) {
          throw new IllegalArgumentException(
              "Invalid red-component command. Usage: command <image-name> <dest-image-name>");
        } else {
          imageProcessorModel.splitImage(parts[1], parts[2], 0);
        }
        break;
      case "green-component":
        if (parts.length != 3) {
          throw new IllegalArgumentException(
              "Invalid green-component command. Usage: command <image-name> <dest-image-name>");
        } else {
          imageProcessorModel.splitImage(parts[1], parts[2], 1);
        }
        break;
      case "blue-component":
        if (parts.length != 3) {
          throw new IllegalArgumentException(
              "Invalid red-component command. Usage: command <image-name> <dest-image-name>");
        } else {
          imageProcessorModel.splitImage(parts[1], parts[2], 2);
        }
        break;
      case "value-component":
        if (parts.length != 3) {
          throw new IllegalArgumentException(
              "Invalid component command. Usage: command <image-name> <dest-image-name>");
        } else {
          imageProcessorModel.valueImage(parts[1], parts[2]);
        }
        break;
      case "luma-component":
        if (parts.length != 3) {
          throw new IllegalArgumentException(
              "Invalid component command. Usage: command <image-name> <dest-image-name> "
                  + "or command <image-name>");
        } else {
          imageProcessorModel.lumaImage(parts[1], parts[2]);
        }
        break;
      case "intensity-component":
        if (parts.length != 3) {
          throw new IllegalArgumentException(
              "Invalid component command. Usage: command <image-name> <dest-image-name>");
        } else {
          imageProcessorModel.intensityImage(parts[1], parts[2]);
        }
        break;
      case "horizontal-flip":
        if (parts.length != 3) {
          throw new IllegalArgumentException(
              "Invalid component command. Usage: command <image-name> <dest-image-name>");
        } else {
          imageProcessorModel.horizontalFlipImage(parts[1], parts[2]);
        }
        break;
      case "vertical-flip":
        if (parts.length != 3) {
          throw new IllegalArgumentException(
              "Invalid component command. Usage: command <image-name> <dest-image-name>");
        } else {
          imageProcessorModel.verticalFlipImage(parts[1], parts[2]);
        }
        break;
      case "histogram":
        if (parts.length != 3) {
          throw new IllegalArgumentException(
              "Invalid component command. Usage: command <image-name> <dest-image-name>");
        } else {
          imageProcessorModel.histogramImage(parts[1], parts[2]);
        }
        break;
      case "blur":
        if (parts.length != 3 && parts.length != 5) {
          throw new IllegalArgumentException(
              "Invalid component command. Usage: command <image-name> <dest-image-name> "
                  + "or command <image-name> <dest-image-name> <percentage>");
        } else {
          if (parts.length == 3) {
            imageProcessorModel.blurImage(parts[1], parts[2]);
          } else {
            imageProcessorModel.blurImage(parts[1], parts[2], parts[4]);
          }
        }
        break;
      case "sepia":
        if (parts.length != 3 && parts.length != 5) {
          throw new IllegalArgumentException(
              "Invalid component command. Usage: command <image-name> <dest-image-name> "
                  + "or command <image-name> <dest-image-name> <percentage>");
        } else {
          if (parts.length == 3) {
            imageProcessorModel.sepiaImage(parts[1], parts[2]);
          } else {
            imageProcessorModel.sepiaImage(parts[1], parts[2], parts[4]);
          }
        }
        break;
      case "sharpen":
        if (parts.length != 3 && parts.length != 5) {
          throw new IllegalArgumentException(
              "Invalid component command. Usage: command <image-name> <dest-image-name> "
                  + "or command <image-name> <dest-image-name> <percentage>");
        } else {
          if (parts.length == 3) {
            imageProcessorModel.sharpenImage(parts[1], parts[2]);
          } else {
            imageProcessorModel.sharpenImage(parts[1], parts[2], parts[4]);
          }
        }
        break;
      case "greyscale":
        if (parts.length != 3 && parts.length != 5) {
          throw new IllegalArgumentException(
              "Invalid component command. Usage: command <image-name> <dest-image-name> "
                  + "or command <image-name> <dest-image-name> <percentage>");
        } else {
          if (parts.length == 3) {
            imageProcessorModel.greyScaleImage(parts[1], parts[2]);
          } else {
            imageProcessorModel.greyScaleImage(parts[1], parts[2], parts[4]);
          }
        }
        break;
      case "color-correct":
        if (parts.length != 3 && parts.length != 5) {
          throw new IllegalArgumentException(
              "Invalid component command. Usage: command <image-name> <dest-image-name> or command "
                  + "<image-name> <dest-image-name> <percentage>");
        } else {
          if (parts.length == 3) {
            imageProcessorModel.colorCorrectImage(parts[1], parts[2]);
          } else {
            imageProcessorModel.colorCorrectImage(parts[1], parts[2], parts[4]);
          }
        }
        break;
      case "rgb-split":
        if (parts.length != 5) {
          throw new IllegalArgumentException(
              "Invalid rgb-split command. Usage: rgb-split <image-name> "
                  + "<dest-image-name-red> <dest-image-name-green> <dest-image-name-blue>");
        } else {
          List<String> imageNamelist = new ArrayList<>(Arrays.asList(parts).subList(2, 5));
          imageProcessorModel.rgbSplitImage(parts[1], imageNamelist);
        }
        break;
      case "rgb-combine":
        if (parts.length != 5) {
          throw new IllegalArgumentException(
              "Invalid rgb-combine command. Usage: rgb-combine <image-name> "
                  + "<red-image> <green-image> <blue-image>");
        } else {
          List<String> imageNameList = new ArrayList<>(Arrays.asList(parts).subList(2, 5));
          imageProcessorModel.mergeImage(imageNameList, parts[1]);
        }
        break;
      case "dither":
        if (parts.length != 3 && parts.length != 5) {
          throw new IllegalArgumentException(
              "Invalid component command. Usage: command <image-name> <dest-image-name> "
                  + "or command <image-name> <dest-image-name> <percentage>");
        } else {
          if (parts.length == 3) {
            imageProcessorModel.ditherImage(parts[1], parts[2]);
          } else {
            imageProcessorModel.ditherImage(parts[1], parts[2], parts[4]);
          }
        }
        break;


      case "levels-adjust":
        if (parts.length != 6 && parts.length != 8) {
          throw new IllegalArgumentException(
              "Invalid levels-adjust command. Usage: levels-adjust <b> <m> <w> "
                  + "<image-name> <dest-image-name> split <p>");
        } else {
          if (parts.length == 6) {
            imageProcessorModel.levelAdjustImage(parts[4], parts[5],
                parts[1] + " " + parts[2] + " " + parts[3] + " " + "100");
          } else {
            imageProcessorModel.levelAdjustImage(parts[4], parts[5],
                parts[1] + " " + parts[2] + " " + parts[3] + " " + parts[7]);
          }
        }
        break;
      default:
        throw new IllegalArgumentException(
            "Unknown command. Try again or type 'exit' to quit.");
    }
  }
}
