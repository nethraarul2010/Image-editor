package model.imageio;

import commonlabels.ImageFormats;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import javax.imageio.ImageIO;
import model.image.ImageFactory;
import model.image.ImageInterface;

/**
 * For JPG and PNG files, we will use the ImageIO class from the Java standard library.
 */
public class CommonFormatsFileAdapter implements IOFileByFormat {

  /**
   * Encodes and saves an image in the specified file format.
   *
   * @param filename the name of the file to save the image to
   * @param image    the image to be saved
   * @param format   the image format to use (e.g., JPEG, PNG)
   * @throws IOException if an error occurs during the encoding and saving process
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
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {

        int rgb = (redPixels[i][j] << 16) | (greenPixels[i][j] << 8) | bluePixels[i][j];
        bufferedImage.setRGB(j, i, rgb);
      }
    }
    File outputFile = new File(filename);
    ImageIO.write(bufferedImage, format.toString(), outputFile);
  }

  /**
   * This method decodes the image from the given filename.
   *
   * @param filename the filename to read the image from
   * @return the decoded image
   * @throws IOException if the file cannot be read
   */
  @Override
  public ImageInterface decodeImage(String filename) throws IOException {
    File pngFile = new File(filename);
    BufferedImage image = ImageIO.read(pngFile);
    int height = image.getHeight();
    int width = image.getWidth();
    int[][] redPixels = new int[height][width];
    int[][] greenPixels = new int[height][width];
    int[][] bluePixels = new int[height][width];
    boolean isGrayscale = true;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {

        int pixel = image.getRGB(j, i);
        redPixels[i][j] = (pixel >> 16) & 0xff;
        greenPixels[i][j] = (pixel >> 8) & 0xff;
        bluePixels[i][j] = (pixel) & 0xff;
        if (redPixels[i][j] == greenPixels[i][j]
            && redPixels[i][j] == bluePixels[i][j] && isGrayscale) {
          continue;
        } else {
          isGrayscale = false;
        }
      }
    }
    if (isGrayscale) {
      return ImageFactory.createImage(Collections.singletonList(redPixels));
    }
    return ImageFactory.createImage(Arrays.asList(redPixels, greenPixels, bluePixels));

  }
}
