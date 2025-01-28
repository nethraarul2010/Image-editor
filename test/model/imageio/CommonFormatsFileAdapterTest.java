package model.imageio;

import static org.junit.Assert.assertArrayEquals;

import commonlabels.ImageFormats;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import javax.imageio.ImageIO;
import model.image.ImageFactory;
import model.image.ImageInterface;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the CommonFormatsFileAdapter class.
 */
public class CommonFormatsFileAdapterTest {

  private CommonFormatsFileAdapter fileAdapter;

  @Before
  public void setUp() {
    fileAdapter = new CommonFormatsFileAdapter();
  }

  @Test
  public void testEncodeAndSaveImage() throws IOException {
    int width = 3;
    int height = 2;
    int[][] redPixels = new int[height][width];
    int[][] greenPixels = new int[height][width];
    int[][] bluePixels = new int[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int rgb = (i * width + j) << 16 | (i * width + j) << 8 | (i * width + j);
        redPixels[i][j] = (i * width + j);
        greenPixels[i][j] = (i * width + j + 1);
        bluePixels[i][j] = (i * width + j + 2);
      }
    }
    ImageInterface image = ImageFactory
        .createImage(Arrays.asList(redPixels, greenPixels, bluePixels));
    String outputFileName = "test_output.png";
    fileAdapter.encodeAndSaveImage(outputFileName, image, ImageFormats.PNG);
    ImageInterface decodedImage = fileAdapter.decodeImage(outputFileName);
    assertArrayEquals(redPixels, decodedImage.getChannel().get(0));
    assertArrayEquals(greenPixels, decodedImage.getChannel().get(1));
    assertArrayEquals(bluePixels, decodedImage.getChannel().get(2));
    new File(outputFileName).delete();
  }

  @Test
  public void testDecodeImage() throws IOException {
    BufferedImage testImage = new BufferedImage(3, 2, BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 3; j++) {
        int redValue = (i * 3 + j) * 10;
        int greenValue = (i * 3 + j + 1) * 10;
        int blueValue = (i * 3 + j + 2) * 10;
        testImage.setRGB(j, i, (redValue << 16) | (greenValue << 8) | blueValue);
      }
    }
    File testImageFile = new File("test_image.png");
    ImageIO.write(testImage, "png", testImageFile);
    ImageInterface decodedImage = fileAdapter.decodeImage("test_image.png");
    int[][] redPixels = new int[][]{
        {0, 10, 20},
        {30, 40, 50}
    };
    int[][] greenPixels = new int[][]{
        {10, 20, 30},
        {40, 50, 60}
    };
    int[][] bluePixels = new int[][]{
        {20, 30, 40},
        {50, 60, 70}
    };
    assertArrayEquals(redPixels, decodedImage.getChannel().get(0));
    assertArrayEquals(greenPixels, decodedImage.getChannel().get(1));
    assertArrayEquals(bluePixels, decodedImage.getChannel().get(2));
    testImageFile.delete();
  }

  @Test(expected = IOException.class)
  public void testDecodeImageIOException() throws IOException {
    fileAdapter.decodeImage("non_existing_image.png");
  }

  @Test
  public void testDecodeGrayscaleImage() throws IOException {
    BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        int grayValue = (i * 2 + j) * 10;
        testImage.setRGB(j, i, (grayValue << 16) | (grayValue << 8) | grayValue);
      }
    }
    File testImageFile = new File("grayscale_image.png");
    ImageIO.write(testImage, "png", testImageFile);
    ImageInterface decodedImage = fileAdapter.decodeImage("grayscale_image.png");
    int[][] grayPixels = new int[][]{
        {0, 10},
        {20, 30}
    };
    assertArrayEquals(grayPixels, decodedImage.getChannel().get(0));
    testImageFile.delete();
  }

  @Test
  public void testEncodeAndSaveImageGreyscale() throws IOException {
    int width = 3;
    int height = 2;
    int[][] greyPixels = new int[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int rgb = (i * width + j) << 16 | (i * width + j) << 8 | (i * width + j);
        greyPixels[i][j] = (i * width + j);
      }
    }
    ImageInterface image = ImageFactory
        .createImage(Collections.singletonList(greyPixels));
    String outputFileName = "test_output.png";
    fileAdapter.encodeAndSaveImage(outputFileName, image, ImageFormats.PNG);
    ImageInterface decodedImage = fileAdapter.decodeImage(outputFileName);
    assertArrayEquals(greyPixels, decodedImage.getChannel().get(0));
    new File(outputFileName).delete();
  }
}
