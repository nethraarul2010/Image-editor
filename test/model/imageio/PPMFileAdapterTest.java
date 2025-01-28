package model.imageio;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import commonlabels.ImageFormats;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;

import model.image.ImageFactory;
import model.image.ImageInterface;

import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the PPMFileAdapter class.
 */
public class PPMFileAdapterTest {

  private PPMFileAdapter fileAdapter;

  @Before
  public void setUp() {
    fileAdapter = new PPMFileAdapter();
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
        redPixels[i][j] = i * width + j;
        greenPixels[i][j] = i * width + j + 1;
        bluePixels[i][j] = i * width + j + 2;
      }
    }
    ImageInterface image = ImageFactory
            .createImage(Arrays.asList(redPixels, greenPixels, bluePixels));

    // Encoding and saving image
    String outputFileName = "test_output.ppm";
    fileAdapter.encodeAndSaveImage(outputFileName, image, ImageFormats.PPM);

    // Decoding the saved image for verification
    ImageInterface decodedImage = fileAdapter.decodeImage(outputFileName);

    // Verifying that the encoded and decoded image match
    assertArrayEquals(redPixels, decodedImage.getChannel().get(0));
    assertArrayEquals(greenPixels, decodedImage.getChannel().get(1));
    assertArrayEquals(bluePixels, decodedImage.getChannel().get(2));

    // Clean up the generated test file
    File file = new File(outputFileName);
    assertTrue(Files.deleteIfExists(file.toPath()));
  }

  @Test
  public void testDecodeImage() throws IOException {
    // Create a test PPM image file
    int width = 2;
    int height = 2;
    int maxValue = 255;
    int[][] redPixels = new int[][]{{0, 20}, {255, 0}};
    int[][] greenPixels = new int[][]{{255, 5}, {0, 255}};
    int[][] bluePixels = new int[][]{{0, 0}, {10, 0}};

    String testFileName = "test_image.ppm";

    String ppmContent = "P3" + System.lineSeparator()
            + width + " " + height + System.lineSeparator()
            + maxValue + System.lineSeparator()
            + "0 255 0 20 5 0" + System.lineSeparator()
            + "255 0 10 0 255 0" + System.lineSeparator();
    Files.write(new File(testFileName).toPath(), ppmContent.getBytes());

    // Decoding the test image
    ImageInterface decodedImage = fileAdapter.decodeImage(testFileName);

    // Verifying the decoded image
    assertArrayEquals(redPixels, decodedImage.getChannel().get(0));
    assertArrayEquals(greenPixels, decodedImage.getChannel().get(1));
    assertArrayEquals(bluePixels, decodedImage.getChannel().get(2));

    // Clean up the generated test file
    File file = new File(testFileName);
    assertTrue(Files.deleteIfExists(file.toPath()));
  }

  @Test
  public void testEncodeAndSaveGrayscaleImage() throws IOException {
    int width = 3;
    int height = 2;
    int[][] greyPixels = new int[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        greyPixels[i][j] = i * width + j;
      }
    }
    ImageInterface image = ImageFactory.createImage(Collections.singletonList(greyPixels));

    // Encoding and saving grayscale image
    String outputFileName = "test_output.ppm";
    fileAdapter.encodeAndSaveImage(outputFileName, image, ImageFormats.PPM);

    // Decoding the saved image for verification
    ImageInterface decodedImage = fileAdapter.decodeImage(outputFileName);

    // Verifying that the encoded and decoded grayscale image match
    assertArrayEquals(greyPixels, decodedImage.getChannel().get(0));

    // Clean up the generated test file
    File file = new File(outputFileName);
    assertTrue(Files.deleteIfExists(file.toPath()));
  }

}
