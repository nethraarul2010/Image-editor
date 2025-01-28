package integration;

import static org.junit.Assert.assertEquals;

import commonlabels.SupportedUIOperations;
import controller.ImageProcessorControllerV2;
import controller.UIFeatures;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import logger.JViewInterface;
import model.image.CommonImage;
import model.image.ImageInterface;
import model.imageio.IOFileFactory;
import model.imageprocessingmodel.ImageProcessorModel;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the Integration for the application.
 */
public class IntegrationtestV2 {

  private final String imagePath = "test_image.jpg";
  private final String imageName = "image";
  ImageProcessorControllerV2 imageProcessorController;
  ImageInterface orgimage;

  @Before
  public void setUp() throws IOException {
    int[][] redChannel = new int[][]{{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] blueChannel = new int[][]{{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};
    orgimage = new CommonImage.ImageBuilder().addChannel(redChannel)
        .addChannel(greenChannel)
        .addChannel(blueChannel)
        .build();
    IOFileFactory.encodeAndSaveImage(imagePath, orgimage);
  }

  @Test
  public void testCompressionFlow() {
    StringBuilder vs = new StringBuilder();
    MockView mockView = new MockView(vs);
    int[][] newChannelAfterIncreaseBrightnessRed = {{255, 255, 255}, {255, 255, 255},
        {223, 223, 255}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{254, 254, 202}, {240, 0, 82},
        {132, 132, 48}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterIncreaseBrightnessRed, newChannelAfterIncreaseBrightnessGreen,
            newChannelAfterIncreaseBrightnessBlue);
    imageProcessorController = new ImageProcessorControllerV2(mockView, new ImageProcessorModel());
    imageProcessorController.loadImage(imagePath, imageName);
    imageProcessorController.executeOperation(SupportedUIOperations.COMPRESSION.toString(),
        "50");
    assertImageEquals(newChannleList, mockView.imageInterface);
  }


  @Test
  public void testColorCorrection() throws IOException {
    StringBuilder vs = new StringBuilder();
    MockView mockView = new MockView(vs);
    int[][] newChannelAfterIncreaseBrightnessRed = {{255, 255, 255}, {255, 255, 255},
        {255, 255, 255}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{0, 0, 0}, {0, 0, 0},
        {0, 0, 0}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterIncreaseBrightnessRed, newChannelAfterIncreaseBrightnessGreen,
            newChannelAfterIncreaseBrightnessBlue);
    imageProcessorController = new ImageProcessorControllerV2(mockView, new ImageProcessorModel());
    imageProcessorController.loadImage(imagePath, imageName);
    imageProcessorController
        .executeOperation(SupportedUIOperations.COLORCORRECTION.toString(), null);
    assertImageEquals(newChannleList, mockView.imageInterface);
  }

  @Test
  public void testColorCorrectionWithSplit() {
    StringBuilder vs = new StringBuilder();
    MockView mockView = new MockView(vs);
    int[][] newChannelAfterIncreaseBrightnessRed = {{255, 255, 255}, {255, 255, 255},
        {255, 255, 255}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{0, 254, 240}, {0, 0, 90},
        {0, 255, 44}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterIncreaseBrightnessRed, newChannelAfterIncreaseBrightnessGreen,
            newChannelAfterIncreaseBrightnessBlue);
    imageProcessorController = new ImageProcessorControllerV2(mockView, new ImageProcessorModel());
    imageProcessorController.loadImage(imagePath, imageName);
    imageProcessorController
        .executeOperationWithSplit(SupportedUIOperations.COLORCORRECTION.toString(), "50");
    assertImageEquals(newChannleList, mockView.imageInterface);
  }

  @Test
  public void testLevelAdjust() throws IOException {
    StringBuilder vs = new StringBuilder();
    MockView mockView = new MockView(vs);
    int[][] newChannelAfterIncreaseBrightnessRed = {{0, 0, 0}, {0, 0, 0},
        {0, 0, 0}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{0, 0, 0}, {0, 0, 0}, {0, 230, 0}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{0, 0, 36}, {36, 0, 238},
        {223, 0, 105}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterIncreaseBrightnessRed, newChannelAfterIncreaseBrightnessGreen,
            newChannelAfterIncreaseBrightnessBlue);
    imageProcessorController = new ImageProcessorControllerV2(mockView, new ImageProcessorModel());
    imageProcessorController.loadImage(imagePath, imageName);
    imageProcessorController
        .executeOperation(SupportedUIOperations.LEVELADJUST.toString(), "20 50 100");
    assertImageEquals(newChannleList, mockView.imageInterface);
  }

  @Test
  public void testLevelAdjustErrorIncorrectOrder() throws IOException {
    StringBuilder vs = new StringBuilder();
    MockView mockView = new MockView(vs);
    int[][] newChannelAfterIncreaseBrightnessRed = {{0, 0, 0}, {0, 0, 0},
        {0, 0, 0}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{0, 0, 0}, {0, 0, 0}, {0, 230, 0}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{0, 0, 36}, {36, 0, 238},
        {223, 0, 105}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterIncreaseBrightnessRed, newChannelAfterIncreaseBrightnessGreen,
            newChannelAfterIncreaseBrightnessBlue);
    imageProcessorController = new ImageProcessorControllerV2(mockView, new ImageProcessorModel());
    imageProcessorController.loadImage(imagePath, imageName);
    imageProcessorController
        .executeOperationWithSplit(SupportedUIOperations.LEVELADJUST.toString(),
            "20 100 90");
    assertEquals(vs.toString(), "Invalid ordering of parameters");
  }

  @Test
  public void testLevelAdjustErrorIncorrectInput() throws IOException {
    StringBuilder vs = new StringBuilder();
    MockView mockView = new MockView(vs);
    int[][] newChannelAfterIncreaseBrightnessRed = {{0, 0, 0}, {0, 0, 0},
        {0, 0, 0}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{0, 0, 0}, {0, 0, 0}, {0, 230, 0}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{0, 0, 36}, {36, 0, 238},
        {223, 0, 105}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterIncreaseBrightnessRed, newChannelAfterIncreaseBrightnessGreen,
            newChannelAfterIncreaseBrightnessBlue);
    imageProcessorController = new ImageProcessorControllerV2(mockView, new ImageProcessorModel());
    imageProcessorController.loadImage(imagePath, imageName);
    imageProcessorController
        .executeOperationWithSplit(SupportedUIOperations.LEVELADJUST.toString(),
            "-20 100 90");
    assertEquals(vs.toString(), "Invalid value for constant");
  }

  @Test
  public void testSharpenFlow() throws IOException {
    StringBuilder vs = new StringBuilder();
    MockView mockView = new MockView(vs);
    int[][] newChannelAfterSharpenRed = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] newChannelAfterSharpenGreen = {{0, 0, 0}, {21, 21, 21}, {21, 86, 21}};
    int[][] newChannelAfterSharpenBlue = {{255, 255, 215}, {255, 255, 215}, {97, 255, 0}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterSharpenRed, newChannelAfterSharpenGreen,
            newChannelAfterSharpenBlue);
    imageProcessorController = new ImageProcessorControllerV2(mockView, new ImageProcessorModel());
    imageProcessorController.loadImage(imagePath, imageName);
    imageProcessorController.executeOperation(SupportedUIOperations.SHARPEN.toString(),
        null);
    assertImageEquals(newChannleList, mockView.imageInterface);
  }

  @Test
  public void testSharpenFlowWithSplit() {
    StringBuilder vs = new StringBuilder();
    MockView mockView = new MockView(vs);
    int[][] newChannelAfterSharpenRed = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] newChannelAfterSharpenGreen = {{0, 0, 0}, {21, 0, 0}, {21, 86, 0}};
    int[][] newChannelAfterSharpenBlue = {{255, 254, 240}, {255, 0, 90}, {97, 255, 44}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterSharpenRed, newChannelAfterSharpenGreen,
            newChannelAfterSharpenBlue);
    imageProcessorController = new ImageProcessorControllerV2(mockView, new ImageProcessorModel());
    imageProcessorController.loadImage(imagePath, imageName);
    imageProcessorController
        .executeOperationWithSplit(SupportedUIOperations.SHARPEN.toString(), "50");
    assertImageEquals(newChannleList, mockView.imageInterface);
  }

  @Test
  public void testSepiaFlow() throws IOException {
    StringBuilder vs = new StringBuilder();
    MockView mockView = new MockView(vs);
    int[][] newChannelAfterSepiaRed = {{148, 148, 145}, {145, 100, 117}, {115, 214, 108}};
    int[][] newChannelAfterSepiaGreen = {{131, 131, 129}, {129, 88, 104}, {102, 190, 96}};
    int[][] newChannelAfterSepiaBlue = {{102, 102, 100}, {100, 69, 81}, {80, 148, 75}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterSepiaRed, newChannelAfterSepiaGreen,
            newChannelAfterSepiaBlue);
    imageProcessorController = new ImageProcessorControllerV2(mockView, new ImageProcessorModel());
    imageProcessorController.loadImage(imagePath, imageName);
    imageProcessorController.executeOperation(SupportedUIOperations.SEPIA.toString(),
        null);
    assertImageEquals(newChannleList, mockView.imageInterface);
  }

  @Test
  public void testSepiaFlowWithSplit() throws IOException {
    StringBuilder vs = new StringBuilder();
    MockView mockView = new MockView(vs);
    int[][] newChannelAfterSepiaRed = {{148, 255, 255}, {145, 255, 255}, {115, 255, 255}};
    int[][] newChannelAfterSepiaGreen = {{131, 0, 0}, {129, 0, 0}, {102, 86, 0}};
    int[][] newChannelAfterSepiaBlue = {{102, 254, 240}, {100, 0, 90}, {80, 255, 44}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterSepiaRed, newChannelAfterSepiaGreen,
            newChannelAfterSepiaBlue);
    imageProcessorController = new ImageProcessorControllerV2(mockView, new ImageProcessorModel());
    imageProcessorController.loadImage(imagePath, imageName);
    imageProcessorController
        .executeOperationWithSplit(SupportedUIOperations.SEPIA.toString(), "50");
    assertImageEquals(newChannleList, mockView.imageInterface);
  }

  @Test
  public void testDitherFlow() throws IOException {
    StringBuilder vs = new StringBuilder();
    MockView mockView = new MockView(vs);
    int[][] newChannelAfterDitherRed = {{255, 255, 255}, {255, 0, 0}, {0, 255, 0}};
    int[][] newChannelAfterDitherGreen = {{255, 255, 255}, {255, 0, 0}, {0, 255, 0}};
    int[][] newChannelAfterDitherBlue = {{255, 255, 255}, {255, 0, 0}, {0, 255, 0}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterDitherRed, newChannelAfterDitherGreen,
            newChannelAfterDitherBlue);
    imageProcessorController = new ImageProcessorControllerV2(mockView, new ImageProcessorModel());
    imageProcessorController.loadImage(imagePath, imageName);
    imageProcessorController.executeOperation(SupportedUIOperations.DITHER.toString(),
        null);
    assertImageEquals(newChannleList, mockView.imageInterface);
  }

  @Test
  public void testDitherFlowWithSplit() throws IOException {
    StringBuilder vs = new StringBuilder();
    MockView mockView = new MockView(vs);
    int[][] newChannelAfterSepiaRed = {{255, 255, 255}, {255, 255, 255}, {0, 255, 255}};
    int[][] newChannelAfterSepiaGreen = {{255, 0, 0}, {255, 0, 0}, {0, 86, 0}};
    int[][] newChannelAfterSepiaBlue = {{255, 254, 240}, {255, 0, 90}, {0, 255, 44}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterSepiaRed, newChannelAfterSepiaGreen,
            newChannelAfterSepiaBlue);
    imageProcessorController = new ImageProcessorControllerV2(mockView, new ImageProcessorModel());
    imageProcessorController.loadImage(imagePath, imageName);
    imageProcessorController
        .executeOperationWithSplit(SupportedUIOperations.DITHER.toString(), "50");
    assertImageEquals(newChannleList, mockView.imageInterface);
  }


  private void assertImageEquals(List<int[][]> expectedChannel, ImageInterface image) {
    for (int i = 0; i < expectedChannel.size(); i++) {
      for (int j = 0; j < image.getHeight(); j++) {
        for (int w = 0; w < image.getWidth(); w++) {
          assertEquals(image.getChannel().get(i)[j][w], expectedChannel.get(i)[j][w]);
        }
      }
    }
  }

  private void cleanupImages(List<String> paths) {
    for (String path : paths) {
      File file = new File(path);
      file.delete();
    }
  }

  static class MockView implements JViewInterface {

    private final StringBuilder log;
    UIFeatures features;
    ImageInterface imageInterface;
    ImageInterface histogramImage;

    public MockView(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void addFeatures(UIFeatures f) {
      features = f;
    }

    @Override
    public void setCurrentImage(ImageInterface img) {
      imageInterface = img;
    }

    @Override
    public void setHistogramImage(ImageInterface histogramImage) {
      this.histogramImage = histogramImage;
    }

    @Override
    public void enableOperations() {
      // enable operation function.
    }

    @Override
    public void displayErrorPopup(String message) {
      log.append(message);
    }
  }

}
