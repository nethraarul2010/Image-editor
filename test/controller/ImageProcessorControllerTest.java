package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import logger.ViewLogger;
import model.image.ImageFactory;
import model.image.ImageInterface;
import model.imageprocessingmodel.ImageProcessorModel;
import model.imageprocessingmodel.ImageProcessorModelInterface;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the ImageProcessorController class.
 */
public class ImageProcessorControllerTest {

  private ImageProcessorController controller;
  private StringWriter out;
  private ImageProcessorModel model;
  private ViewLogger logger;

  @Before
  public void setUp() {
    out = new StringWriter();
    logger = new ViewLogger(out);
    model = new ImageProcessorModel();
  }

  @Test
  public void testHandleValidCommands() throws IOException {
    StringBuilder s = new StringBuilder();
    MockModel modelMock = new MockModel(s);
    controller = new ImageProcessorController(logger, modelMock,
        new StringReader("load path/to/image.jpg image1 \nexit"), out);
    controller.startImageProcessingController();
    assertEquals("path/to/image.jpgimage1", s.toString());
    assertTrue(out.toString().contains("Command ran successfully"));
  }

  @Test
  public void testHandleExitCommand() throws IOException {
    controller = new ImageProcessorController(logger, model, new StringReader("exit"), out);
    controller.startImageProcessingController();
    assertEquals("", out.toString());
  }

  @Test
  public void testHandleInvalidCommands() throws IOException {
    controller = new ImageProcessorController(logger, model, new StringReader("\nexi1"), out);
    controller.startImageProcessingController();
    assertTrue(out.toString().contains("Invalid command. Try again."));
  }

  @Test
  public void testHandleMultipleCommands() throws IOException {
    String scriptContent = "load path/to/image.jpg image1\n"
        + "brighten 10 image1 image2\n"
        + "exit\n";
    StringBuilder s = new StringBuilder();
    MockModel modelMock = new MockModel(s);
    controller = new ImageProcessorController(logger, modelMock, new StringReader(scriptContent),
        out);
    controller.startImageProcessingController();
    assertEquals("path/to/image.jpgimage1image1image210", s.toString());
  }

  @Test
  public void testHandleScriptCommands() throws IOException {
    String scriptContent = "load path/to/image.jpg image1\n"
        + "brighten 10 image1 image2\n";
    File tempFile = createTempScriptFile(scriptContent);
    assert tempFile != null;
    String filePath = tempFile.getAbsolutePath();
    String runString = "run " + filePath + "\nexit";
    StringBuilder s = new StringBuilder();
    MockModel modelMock = new MockModel(s);
    controller = new ImageProcessorController(logger, modelMock, new StringReader(runString), out);
    controller.startImageProcessingController();
    assertTrue(s.toString().contains("path/to/image.jpgimage1image1image210"));
  }

  @Test
  public void testInvalidScriptFile() throws IOException {
    String scriptContent = "invalid_command\nexit";
    File tempFile = createTempScriptFile(scriptContent);
    assert tempFile != null;
    String filePath = tempFile.getAbsolutePath();
    ImageProcessorController controller = new ImageProcessorController(logger, model,
        new StringReader("run " + filePath + "\nexit"), out);
    controller.startImageProcessingController();
    assertTrue(out.toString().contains("Unknown command. Try again or type 'exit' to quit."));
  }

  @Test
  public void testScriptFileViaArgument() throws IOException {
    String scriptContent = "load path/to/image.jpg image1\n"
        + "brighten 10 image1 image2\n";
    File tempFile = createTempScriptFile(scriptContent);
    assert tempFile != null;
    String filePath = tempFile.getAbsolutePath();
    ImageProcessorController controller = new ImageProcessorController(logger, model,
        new StringReader("run " + filePath + "\nexit"), out);
    controller.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
  }

  @Test
  public void testHandleScriptAndCliCommands() throws IOException {
    String scriptContent = "load path/to/image.jpg image1\n"
        + "brighten 10 image1 image2\n";
    File tempFile = File.createTempFile("temp", ".txt");
    String filePath = tempFile.getAbsolutePath();
    PrintWriter writer = new PrintWriter(new FileWriter(tempFile));
    writer.println(scriptContent);
    writer.close();
    String runString = "run " + filePath + "\nbrighten 10 image1 image3" + "\nexit";
    StringBuilder s = new StringBuilder();
    MockModel modelMock = new MockModel(s);
    controller = new ImageProcessorController(logger, modelMock, new StringReader(runString), out);
    controller.startImageProcessingController();
    assertTrue(s.toString()
        .contains("path/to/image.jpgimage1image1image210image1image310"));
  }

  private File createTempScriptFile(String scriptContent) {
    try {
      File tempFile = File.createTempFile("temp", ".txt");
      PrintWriter writer = new PrintWriter(new FileWriter(tempFile));
      writer.println(scriptContent);
      writer.close();
      return tempFile;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  static class MockModel implements ImageProcessorModelInterface {

    private final StringBuilder log;
    private final Map<String, ImageInterface> images;

    public MockModel(StringBuilder log) {
      this.log = log;
      images = new HashMap<>();
    }


    @Override
    public void blurImage(String imageName, String destImageName, Object operator) {
      log.append(imageName).append(destImageName).append(operator.toString());
    }

    @Override
    public void blurImage(String imageName, String destImageName) {
      //comment
    }

    @Override
    public void sharpenImage(String imageName, String destImageName, Object operator) {
      //comment
    }

    @Override
    public void sharpenImage(String imageName, String destImageName) {
      //comment
    }

    @Override
    public void horizontalFlipImage(String imageName, String destImageName) {
      //comment
    }

    @Override
    public void verticalFlipImage(String imageName, String destImageName) {
      //comment
    }

    @Override
    public void greyScaleImage(String imageName, String destImageName, Object operator) {
      //comment
    }

    @Override
    public void greyScaleImage(String imageName, String destImageName) {
      //comment
    }

    @Override
    public void lumaImage(String imageName, String destImageName) {
      //comment
    }

    @Override
    public void sepiaImage(String imageName, String destImageName, Object operator) {
      //comment
    }

    @Override
    public void sepiaImage(String imageName, String destImageName) {
      //comment
    }

    @Override
    public void splitImage(String imageName, String destImageName, Object operator) {
      //comment
    }

    @Override
    public void rgbSplitImage(String imageName, List<String> destImageNames) {
      //comment
    }

    @Override
    public void brightenImage(String imageName, String destImageName, Object operator) {
      log.append(imageName).append(destImageName).append(operator.toString());
    }

    @Override
    public void compressImage(String imageName, String destImageName, Object operator) {
      //comment
    }

    @Override
    public void valueImage(String imageName, String destImageName) {
      //comment
    }

    @Override
    public void intensityImage(String imageName, String destImageName) {
      //comment
    }

    @Override
    public void mergeImage(List<String> imagesToMergeName, String destImageName) {
      //comment
    }

    @Override
    public void histogramImage(String imageName, String destImageName) {
      log.append(imageName).append(destImageName);
      List<int[][]> list = Arrays.asList(new int[10][10], new int[10][10], new int[10][10]);
      images.put(destImageName, ImageFactory.createImage(list));
    }

    @Override
    public void levelAdjustImage(String imageName, String destImageName, Object operator) {
      //comment
    }

    @Override
    public void colorCorrectImage(String imageName, String destImageName, Object operator) {
      //comment
    }

    @Override
    public void colorCorrectImage(String imageName, String destImageName) {
      //comment
    }

    @Override
    public void loadImage(String imagePath, String imageName) throws IOException {
      log.append(imagePath).append(imageName);
      List<int[][]> list = Arrays.asList(new int[10][10], new int[10][10], new int[10][10]);
      images.put(imageName, ImageFactory.createImage(list));
    }

    @Override
    public void saveImage(String imagePath, String imageName) throws IOException {
      if (!Objects.nonNull(imageName)) {
        throw new IllegalArgumentException("null name");
      }
      log.append(imagePath).append(imageName);
    }

    @Override
    public ImageInterface getImage(String imageName) {
      return images.get(imageName);
    }

    @Override
    public void ditherImage(String imagePath, String imagename) {
      //comment
    }

    @Override
    public void ditherImage(String imageName, String destImageName, Object operator) {
      //comment
    }
  }

}
