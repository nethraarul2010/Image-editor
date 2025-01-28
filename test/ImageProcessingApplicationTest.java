import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import model.image.CommonImage;
import model.image.ImageInterface;
import model.imageio.IOFileFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the ImageProcessingApplication class which has main method for checking if -file
 * script command works correctly.
 */
public class ImageProcessingApplicationTest {

  private final String imagePath = "test_image.jpg";

  @Before
  public void setUp() throws IOException {
    int[][] redChannel = new int[][]{{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] blueChannel = new int[][]{{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};
    ImageInterface imageInterface = new CommonImage.ImageBuilder().addChannel(redChannel)
        .addChannel(greenChannel)
        .addChannel(blueChannel)
        .build();
    IOFileFactory.encodeAndSaveImage(imagePath, imageInterface);
  }

  @Test
  public void testScriptFile() throws IOException {
    String scriptContent = "load " + imagePath + " image1\n"
        + "brighten 10 image1 image2\n"
        + "save brightImage.jpg image2";
    File tempFile = File.createTempFile("temp", ".txt");
    String filePath = tempFile.getAbsolutePath();
    PrintWriter writer = new PrintWriter(new FileWriter(tempFile));
    writer.println(scriptContent);
    writer.close();

    String runString = "run " + filePath;
    ImageProcessingApplication.main(new String[]{"-file", filePath});
    ImageInterface img = IOFileFactory.decodeImage("brightImage.jpg");

    int[][] redChannel1 = new int[][]{{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel1 = new int[][]{{10, 10, 10}, {10, 10, 10}, {10, 96, 10}};
    int[][] blueChannel1 = new int[][]{{255, 255, 250}, {250, 10, 100}, {93, 255, 54}};
    ImageInterface imageInterface1 = new CommonImage.ImageBuilder().addChannel(redChannel1)
        .addChannel(greenChannel1)
        .addChannel(blueChannel1)
        .build();
    assertEqualImages(imageInterface1, img);
    cleanupImages(Arrays.asList(imagePath));
    cleanupImages(Arrays.asList("brightImage.jpg"));
  }

  private void cleanupImages(List<String> paths) {
    for (String path : paths) {
      File file = new File(path);
      file.delete();
    }
  }

  private void assertEqualImages(ImageInterface imageAfterIntensity, ImageInterface newImage) {
    assertEquals(imageAfterIntensity.getHeight(), newImage.getHeight());
    assertEquals(imageAfterIntensity.getWidth(), newImage.getWidth());
    assertEquals(imageAfterIntensity.getChannel().size(), newImage.getChannel().size());
    for (int i = 0; i < imageAfterIntensity.getChannel().size(); i++) {
      for (int j = 0; j < imageAfterIntensity.getChannel().get(i).length; j++) {
        for (int k = 0; k < imageAfterIntensity.getChannel().get(i)[j].length; k++) {
          assertEquals(imageAfterIntensity.getChannel().get(i)[j][k],
              newImage.getChannel().get(i)[j][k]);
        }
      }
    }
  }
}
