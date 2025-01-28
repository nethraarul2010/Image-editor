package model.imageio;

import commonlabels.ImageFormats;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import model.image.ImageFactory;
import model.image.ImageInterface;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class PPMFileAdapter implements IOFileByFormat {

  /**
   * This method encodes and saves the image in the given filename.
   *
   * @param filename the filename to save the image
   * @param image    the image to be saved
   * @throws IOException if the file cannot be saved
   */
  @Override
  public void encodeAndSaveImage(String filename, ImageInterface image,
      ImageFormats format) throws IOException {
    int height = image.getHeight();
    int width = image.getWidth();
    int[][] redPixels;
    int[][] greenPixels;
    int[][] bluePixels;
    if (image.getChannel().size() == 3) {
      redPixels = image.getChannel().get(0);
      greenPixels = image.getChannel().get(1);
      bluePixels = image.getChannel().get(2);
    } else {
      redPixels = image.getChannel().get(0);
      greenPixels = image.getChannel().get(0);
      bluePixels = image.getChannel().get(0);
    }
    int maxValue = 255;
    StringBuilder builder = new StringBuilder();
    builder.append("P3").append(System.lineSeparator());
    builder.append(width).append(" ").append(height).append(System.lineSeparator());
    builder.append(maxValue).append(System.lineSeparator());
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        builder.append(redPixels[i][j]).append(" ").append(greenPixels[i][j]).append(" ")
            .append(bluePixels[i][j]).append(" ");
      }
      builder.append(System.lineSeparator());
    }
    java.io.FileWriter fw = new java.io.FileWriter(filename, StandardCharsets.US_ASCII);
    fw.write(builder.toString());
    fw.close();
  }

  /**
   * This method decodes the image from the given filename.
   *
   * @param filename the filename to read the image
   * @return the image read from the file
   * @throws IOException if the file cannot be read
   */
  @Override
  public ImageInterface decodeImage(String filename) throws IOException {
    Scanner sc;
    InputStream stream = new FileInputStream(filename);
    sc = new Scanner(stream);

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    int[][] redPixels = new int[height][width];
    int[][] greenPixels = new int[height][width];
    int[][] bluePixels = new int[height][width];
    boolean isGrayscale = true;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        redPixels[i][j] = r;
        greenPixels[i][j] = g;
        bluePixels[i][j] = b;
        if (r == g && g == b && isGrayscale) {
          // Do nothing for given condition
        } else {
          isGrayscale = false;
        }
      }
    }
    stream.close();

    if (isGrayscale) {
      return ImageFactory.createImage(Collections.singletonList(redPixels));
    } else {
      return ImageFactory.createImage(Arrays.asList(redPixels, greenPixels, bluePixels));
    }
  }
}

