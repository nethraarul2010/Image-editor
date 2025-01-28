package integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import controller.ImageProcessorController;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import logger.ViewLogger;
import model.image.CommonImage;
import model.image.ImageInterface;
import model.imageio.IOFileFactory;
import model.imageprocessingmodel.ImageProcessorModel;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests for the Integration of whole program which is CLI based application.
 */
public class IntegrationTest {

  private final String imagePath = "test_image.jpg";
  ImageProcessorController imageProcessorController;
  private StringWriter out;
  private ViewLogger logger;

  @Before
  public void setUp() throws IOException {
    out = new StringWriter();
    logger = new ViewLogger(out);
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
  public void testBrightenFlow() throws IOException {
    String newImagePath = "test_image_bright.jpg";
    int[][] newChannelAfterIncreaseBrightnessRed = {{245, 245, 245}, {245, 245, 245},
        {245, 245, 245}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{0, 0, 0}, {0, 0, 0}, {0, 76, 0}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{244, 244, 230}, {230, 0, 80},
        {73, 245, 34}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterIncreaseBrightnessRed, newChannelAfterIncreaseBrightnessGreen,
            newChannelAfterIncreaseBrightnessBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String brightenCommand = "brighten -10 test test_bright\n";
    String saveCommand = "save " + newImagePath + " test_bright\n";
    imageProcessorController = new ImageProcessorController(logger, new ImageProcessorModel(),
        new StringReader(loadCommand + brightenCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    assertImageEquals(newChannleList, image);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testCompressionFlow() throws IOException {
    String newImagePath = "test_image_compression.jpg";
    int[][] newChannelAfterIncreaseBrightnessRed = {{255, 255, 255}, {255, 255, 255},
        {223, 223, 255}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{254, 254, 202}, {240, 0, 82},
        {132, 132, 48}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterIncreaseBrightnessRed, newChannelAfterIncreaseBrightnessGreen,
            newChannelAfterIncreaseBrightnessBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String compressCommand = "compress 50 test test_compress\n";
    String saveCommand = "save " + newImagePath + " test_compress\n";
    imageProcessorController = new ImageProcessorController(logger, new ImageProcessorModel(),
        new StringReader(loadCommand + compressCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    assertImageEquals(newChannleList, image);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testHistogram() throws IOException {
    String newImagePath = "test_image_histogram.jpg";
    int[][] newChannelAfterIncreaseBrightnessRed = {{255, 255, 255}, {255, 255, 255},
        {223, 223, 255}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{254, 254, 202}, {240, 0, 82},
        {132, 132, 48}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterIncreaseBrightnessRed, newChannelAfterIncreaseBrightnessGreen,
            newChannelAfterIncreaseBrightnessBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String compressCommand = "histogram test test_hist\n";
    String saveCommand = "save " + newImagePath + " test_hist\n";
    imageProcessorController = new ImageProcessorController(logger, new ImageProcessorModel(),
        new StringReader(loadCommand + compressCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testColorCorrection() throws IOException {
    String newImagePath = "test_image_color_correct.jpg";
    int[][] newChannelAfterIncreaseBrightnessRed = {{255, 255, 255}, {255, 255, 255},
        {255, 255, 255}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{0, 0, 0}, {0, 0, 0},
        {0, 0, 0}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterIncreaseBrightnessRed, newChannelAfterIncreaseBrightnessGreen,
            newChannelAfterIncreaseBrightnessBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String compressCommand = "color-correct test test_colorcorrect\n";
    String saveCommand = "save " + newImagePath + " test_colorcorrect\n";
    imageProcessorController = new ImageProcessorController(logger, new ImageProcessorModel(),
        new StringReader(loadCommand + compressCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    assertImageEquals(newChannleList, image);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testColorCorrectionWithSplit() throws IOException {
    String newImagePath = "test_image_color_correct.jpg";
    int[][] newChannelAfterIncreaseBrightnessRed = {{255, 255, 255}, {255, 255, 255},
        {255, 255, 255}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{0, 254, 240}, {0, 0, 90},
        {0, 255, 44}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterIncreaseBrightnessRed, newChannelAfterIncreaseBrightnessGreen,
            newChannelAfterIncreaseBrightnessBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String compressCommand = "color-correct test test_colorcorrect split 50\n";
    String saveCommand = "save " + newImagePath + " test_colorcorrect\n";
    imageProcessorController = new ImageProcessorController(logger, new ImageProcessorModel(),
        new StringReader(loadCommand + compressCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    assertImageEquals(newChannleList, image);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testLevelAdjust() throws IOException {
    String newImagePath = "test_image_level_adjust.jpg";
    int[][] newChannelAfterIncreaseBrightnessRed = {{0, 0, 0}, {0, 0, 0},
        {0, 0, 0}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{0, 0, 0}, {0, 0, 0}, {0, 230, 0}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{0, 0, 36}, {36, 0, 238},
        {223, 0, 105}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterIncreaseBrightnessRed, newChannelAfterIncreaseBrightnessGreen,
            newChannelAfterIncreaseBrightnessBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String compressCommand = "levels-adjust 20 50 100 test test_levels_adjust\n";
    String saveCommand = "save " + newImagePath + " test_levels_adjust\n";
    imageProcessorController = new ImageProcessorController(logger, new ImageProcessorModel(),
        new StringReader(loadCommand + compressCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    assertImageEquals(newChannleList, image);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testLevelAdjustErrorIncorrectOrder() throws IOException {
    String newImagePath = "test_image_level_adjust.jpg";
    int[][] newChannelAfterIncreaseBrightnessRed = {{0, 0, 0}, {0, 0, 0},
        {0, 0, 0}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{0, 0, 0}, {0, 0, 0}, {0, 230, 0}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{0, 0, 36}, {36, 0, 238},
        {223, 0, 105}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterIncreaseBrightnessRed, newChannelAfterIncreaseBrightnessGreen,
            newChannelAfterIncreaseBrightnessBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String compressCommand = "levels-adjust 20 100 90 test test_levels_adjust\n";
    String saveCommand = "save " + newImagePath + " test_levels_adjust\n";
    imageProcessorController = new ImageProcessorController(logger, new ImageProcessorModel(),
        new StringReader(loadCommand + compressCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Invalid ordering of parameters"));
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testLevelAdjustErrorIncorrectInput() throws IOException {
    String newImagePath = "test_image_level_adjust.jpg";
    int[][] newChannelAfterIncreaseBrightnessRed = {{0, 0, 0}, {0, 0, 0},
        {0, 0, 0}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{0, 0, 0}, {0, 0, 0}, {0, 230, 0}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{0, 0, 36}, {36, 0, 238},
        {223, 0, 105}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterIncreaseBrightnessRed, newChannelAfterIncreaseBrightnessGreen,
            newChannelAfterIncreaseBrightnessBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String compressCommand = "levels-adjust -20 100 90 test test_levels_adjust\n";
    String saveCommand = "save " + newImagePath + " test_levels_adjust\n";
    imageProcessorController = new ImageProcessorController(logger, new ImageProcessorModel(),
        new StringReader(loadCommand + compressCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Invalid value for constant"));
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testLevelAdjustErrorIncorrectInput2() throws IOException {
    String newImagePath = "test_image_level_adjust.jpg";
    int[][] newChannelAfterIncreaseBrightnessRed = {{0, 0, 0}, {0, 0, 0},
        {0, 0, 0}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{0, 0, 0}, {0, 0, 0}, {0, 230, 0}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{0, 0, 36}, {36, 0, 238},
        {223, 0, 105}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterIncreaseBrightnessRed, newChannelAfterIncreaseBrightnessGreen,
            newChannelAfterIncreaseBrightnessBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String compressCommand = "levels-adjust 20.0 100 90 test test_levels_adjust\n";
    String saveCommand = "save " + newImagePath + " test_levels_adjust\n";
    imageProcessorController = new ImageProcessorController(logger, new ImageProcessorModel(),
        new StringReader(loadCommand + compressCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Invalid request"));
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testLevelAdjustSplit() throws IOException {
    String newImagePath = "test_image_level_adjust.jpg";
    int[][] newChannelAfterIncreaseBrightnessRed = {{0, 0, 0}, {0, 0, 0},
        {0, 0, 0}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{0, 0, 0}, {0, 0, 0}, {0, 230, 0}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{0, 0, 36}, {36, 0, 238},
        {223, 0, 105}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterIncreaseBrightnessRed, newChannelAfterIncreaseBrightnessGreen,
            newChannelAfterIncreaseBrightnessBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String compressCommand = "levels-adjust 20 50 100 test test_levels_adjust split 50\n";
    String saveCommand = "save " + newImagePath + " test_levels_adjust\n";
    imageProcessorController = new ImageProcessorController(logger, new ImageProcessorModel(),
        new StringReader(loadCommand + compressCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    assertImageEquals(newChannleList, image);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testBrightenFlowAfterIncorrectCommand() throws IOException {
    String newImagePath = "test_image_bright.jpg";
    int[][] newChannelAfterIncreaseBrightnessRed = {{245, 245, 245}, {245, 245, 245},
        {245, 245, 245}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{0, 0, 0}, {0, 0, 0}, {0, 76, 0}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{244, 244, 230}, {230, 0, 80},
        {73, 245, 34}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterIncreaseBrightnessRed, newChannelAfterIncreaseBrightnessGreen,
            newChannelAfterIncreaseBrightnessBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String wrongCommand = "bright -10 test test_bright\n";
    String brightenCommand = "brighten -10 test test_bright\n";
    String saveCommand = "save " + newImagePath + " test_bright\n";
    imageProcessorController = new ImageProcessorController(logger, new ImageProcessorModel(),
        new StringReader(loadCommand + wrongCommand + brightenCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < image.getHeight(); j++) {
        for (int w = 0; w < image.getWidth(); w++) {
          assertEquals(image.getChannel().get(i)[j][w], newChannleList.get(i)[j][w]);
        }
      }
    }
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test()
  public void testIncorrectImageName() throws IOException {
    String newImagePath = "test_image_bright.jpg";
    String newImagePath1 = "inc_test_image_bright.jpg";
    String loadCommand = "load " + newImagePath1 + " test\n";
    String brightenCommand = "brighten -10 test test_bright\n";
    String saveCommand = "save " + newImagePath + " test_bright\n";
    imageProcessorController = new ImageProcessorController(logger, new ImageProcessorModel(),
        new StringReader(loadCommand + brightenCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Can't read input file"));
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test()
  public void testIncorrectImageExtension() throws IOException {
    String newImagePath = "test_image_bright.xtf";
    String loadCommand = "load " + newImagePath + " test\n";
    String brightenCommand = "brighten -10 test test_bright\n";
    String saveCommand = "save " + newImagePath + " test_bright\n";
    imageProcessorController = new ImageProcessorController(logger, new ImageProcessorModel(),
        new StringReader(loadCommand + brightenCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Invalid file extension"));
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testRedComponentFlow() throws IOException {
    // Define your expected image and other necessary variables
    String newImagePath = "test_image_red_component.jpg";
    int[][] newChannelAfterIncreaseBrightnessRed = {{255, 255, 255}, {255, 255, 255},
        {255, 255, 255}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterIncreaseBrightnessRed, newChannelAfterIncreaseBrightnessGreen,
            newChannelAfterIncreaseBrightnessBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String redComponentCommand = "red-component test test_red_component\n";
    String saveCommand = "save " + newImagePath + " test_red_component\n";
    imageProcessorController = new ImageProcessorController(logger, new ImageProcessorModel(),
        new StringReader(loadCommand + redComponentCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    assertImageEquals(newChannleList, image);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testUnknownCommand() throws IOException {
    String loadCommand = "load " + imagePath + " test\n";
    String unknownCommand = "unknown test test_unknown\n";
    imageProcessorController = new ImageProcessorController(logger, new ImageProcessorModel(),
        new StringReader(loadCommand + unknownCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Unknown command"));
    cleanupImages(Collections.singletonList(imagePath));
  }

  @Test
  public void testGreenComponentFlow() throws IOException {
    String newImagePath = "test_image_green_component.jpg";
    int[][] newChannelAfterIncreaseBrightnessRed = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterIncreaseBrightnessRed, newChannelAfterIncreaseBrightnessGreen,
            newChannelAfterIncreaseBrightnessBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String greenComponentCommand = "green-component test test_green_component\n";
    String saveCommand = "save " + newImagePath + " test_green_component\n";
    imageProcessorController = new ImageProcessorController(logger, new ImageProcessorModel(),
        new StringReader(loadCommand + greenComponentCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    assertImageEquals(newChannleList, image);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testBlueComponentFlow() throws IOException {
    String newImagePath = "test_image_blue_component.jpg";
    int[][] newChannelAfterIncreaseBrightnessRed = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterIncreaseBrightnessRed, newChannelAfterIncreaseBrightnessGreen,
            newChannelAfterIncreaseBrightnessBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String greenComponentCommand = "blue-component test test_blue_component\n";
    String saveCommand = "save " + newImagePath + " test_blue_component\n";
    imageProcessorController = new ImageProcessorController(logger, new ImageProcessorModel(),
        new StringReader(loadCommand + greenComponentCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    assertImageEquals(newChannleList, image);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testHorizontalFlipFlow() throws IOException {
    String newImagePath = "test_image_horizontal_flip.jpg";
    int[][] newChannelAfterHorizontalRotationRed = {{255, 255, 255}, {255, 255, 255},
        {255, 255, 255}};
    int[][] newChannelAfterHorizontalRotationGreen = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] newChannelAfterHorizontalRotationBlue = {{240, 254, 254}, {90, 0, 240},
        {44, 255, 83}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterHorizontalRotationRed, newChannelAfterHorizontalRotationGreen,
            newChannelAfterHorizontalRotationBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String horizontalFlipCommand = "horizontal-flip test test_horizontal_flip\n";
    String saveCommand = "save " + newImagePath + " test_horizontal_flip\n";
    imageProcessorController = new ImageProcessorController(logger, new ImageProcessorModel(),
        new StringReader(loadCommand + horizontalFlipCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    assertImageEquals(newChannleList, image);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testVerticalFlipFlow() throws IOException {
    String newImagePath = "test_image_vertical_flip.jpg";
    int[][] newChannelAfterVerticalRotationRed = {{255, 255, 255}, {255, 255, 255},
        {255, 255, 255}};
    int[][] newChannelAfterVerticalRotationGreen = {{0, 86, 0}, {0, 0, 0}, {0, 0, 0}};
    int[][] newChannelAfterVerticalRotationBlue = {{83, 255, 44}, {240, 0, 90}, {254, 254, 240}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterVerticalRotationRed, newChannelAfterVerticalRotationGreen,
            newChannelAfterVerticalRotationBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String verticalFlipCommand = "vertical-flip test test_vertical_flip\n";
    String saveCommand = "save " + newImagePath + " test_vertical_flip\n";
    imageProcessorController = new ImageProcessorController(logger, new ImageProcessorModel(),
        new StringReader(loadCommand + verticalFlipCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    assertImageEquals(newChannleList, image);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testduplicateName() throws IOException {
    String newImagePath = "test_image_vertical_flip.jpg";
    String loadCommand = "load " + imagePath + " test\n";
    String verticalFlipCommand_1 = "vertical-flip test test_vertical_flip\n";
    String verticalFlipCommand_2 = "vertical-flip test test_vertical_flip\n";
    String saveCommand = "save " + newImagePath + " test_vertical_flip\n";
    imageProcessorController = new ImageProcessorController(logger, new ImageProcessorModel(),
        new StringReader(
            loadCommand + verticalFlipCommand_1 + verticalFlipCommand_2 + saveCommand + "exit"),
        out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString()
        .contains("Invalid request : An Image exist with the name test_vertical_flip"));
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testNoImage() throws IOException {
    String newImagePath = "test_image_vertical_flip.jpg";
    String loadCommand = "load " + imagePath + " test\n";
    String verticalFlipCommand_1 = "vertical-flip test1 test_vertical_flip\n";
    String saveCommand = "save " + newImagePath + " test_vertical_flip\n";
    imageProcessorController = new ImageProcessorController(logger, new ImageProcessorModel(),
        new StringReader(loadCommand + verticalFlipCommand_1 + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Invalid request : No image exist with the name test1"));
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testBlurFlow() throws IOException {
    // Define your expected image and other necessary variables
    String newImagePath = "test_image_blur.jpg";
    int[][] newChannelAfterBlurRed = {{140, 186, 140}, {186, 247, 186}, {140, 186, 140}};
    int[][] newChannelAfterBlurGreen = {{0, 0, 0}, {5, 10, 5}, {10, 21, 10}};
    int[][] newChannelAfterBlurBlue = {{124, 144, 102}, {131, 140, 87}, {81, 98, 53}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterBlurRed, newChannelAfterBlurGreen,
            newChannelAfterBlurBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String blurCommand = "blur test test_blur\n";
    String saveCommand = "save " + newImagePath + " test_blur\n";
    imageProcessorController = new ImageProcessorController(logger,
        new ImageProcessorModel(), new StringReader(loadCommand
        + blurCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    assertImageEquals(newChannleList, image);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testBlurFlowWithSplit() throws IOException {
    // Define your expected image and other necessary variables
    String newImagePath = "test_image_blur.jpg";
    int[][] newChannelAfterBlurRed = {{140, 255, 255}, {186, 255, 255}, {140, 255, 255}};
    int[][] newChannelAfterBlurGreen = {{0, 0, 0}, {5, 0, 0}, {10, 86, 0}};
    int[][] newChannelAfterBlurBlue = {{124, 254, 240}, {131, 0, 90}, {81, 255, 44}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterBlurRed, newChannelAfterBlurGreen,
            newChannelAfterBlurBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String blurCommand = "blur test test_blur split 50\n";
    String saveCommand = "save " + newImagePath + " test_blur\n";
    imageProcessorController = new ImageProcessorController(logger,
        new ImageProcessorModel(), new StringReader(loadCommand
        + blurCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    assertImageEquals(newChannleList, image);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testSharpenFlow() throws IOException {
    String newImagePath = "test_image_sharpen.jpg";
    int[][] newChannelAfterSharpenRed = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] newChannelAfterSharpenGreen = {{0, 0, 0}, {21, 21, 21}, {21, 86, 21}};
    int[][] newChannelAfterSharpenBlue = {{255, 255, 215}, {255, 255, 215}, {97, 255, 0}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterSharpenRed, newChannelAfterSharpenGreen,
            newChannelAfterSharpenBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String sharpenCommand = "sharpen test test_sharpen\n";
    String saveCommand = "save " + newImagePath + " test_sharpen\n";
    imageProcessorController = new ImageProcessorController(logger,
        new ImageProcessorModel(), new StringReader(loadCommand
        + sharpenCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    assertImageEquals(newChannleList, image);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testSharpenFlowWithSplit() throws IOException {
    String newImagePath = "test_image_sharpen.jpg";
    int[][] newChannelAfterSharpenRed = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] newChannelAfterSharpenGreen = {{0, 0, 0}, {21, 0, 0}, {21, 86, 0}};
    int[][] newChannelAfterSharpenBlue = {{255, 254, 240}, {255, 0, 90}, {97, 255, 44}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterSharpenRed, newChannelAfterSharpenGreen,
            newChannelAfterSharpenBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String sharpenCommand = "sharpen test test_sharpen split 50\n";
    String saveCommand = "save " + newImagePath + " test_sharpen\n";
    imageProcessorController = new ImageProcessorController(logger,
        new ImageProcessorModel(), new StringReader(loadCommand
        + sharpenCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    assertImageEquals(newChannleList, image);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testMergeImage() throws IOException {
    String newImagePath = "test_image_merge.jpg";
    int[][] newChannelAfterMergeRed = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] newChannelAfterMergeGreen = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] newChannelAfterMergeBlue = new int[][]{{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterMergeRed, newChannelAfterMergeGreen,
            newChannelAfterMergeBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String mergeCommand = "rgb-combine test_merge test test test\n";
    String saveCommand = "save " + newImagePath + " test_merge\n";
    imageProcessorController = new ImageProcessorController(logger,
        new ImageProcessorModel(), new StringReader(loadCommand
        + mergeCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    assertImageEquals(newChannleList, image);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testMergeImageWithError() throws IOException {
    String newImagePath = "test_image_merge.jpg";
    String loadCommand = "load " + imagePath + " test\n";
    String mergeCommand = "rgb-combine test_merge test test\n";
    String saveCommand = "save " + newImagePath + " test_merge\n";
    imageProcessorController = new ImageProcessorController(logger,
        new ImageProcessorModel(), new StringReader(loadCommand
        + mergeCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Invalid rgb-combine command"));
    cleanupImages(List.of(imagePath));
  }

  @Test
  public void testSplitImage() throws IOException {
    String newImagePathRed = "test_image_split_red.jpg";
    String newImagePathGreen = "test_image_split_green.jpg";
    String newImagePathBlue = "test_image_split_blue.jpg";
    int[][] newChannelAfterSplitRed = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] newChannelAfterSplitGreen = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] newChannelAfterSplitBlue = new int[][]{{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};
    int[][] newZeroChannel = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    List<int[][]> newChannelListRed = Arrays
        .asList(newChannelAfterSplitRed, newZeroChannel,
            newZeroChannel);
    List<int[][]> newChannelListGreen = Arrays
        .asList(newZeroChannel, newChannelAfterSplitGreen,
            newZeroChannel);
    List<int[][]> newChannleListBlue = Arrays
        .asList(newZeroChannel, newZeroChannel,
            newChannelAfterSplitBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String splitCommand = "rgb-split test test_red test_green test_blue\n";
    String saveCommand1 = "save " + newImagePathRed + " test_red\n";
    String saveCommand2 = "save " + newImagePathGreen + " test_green\n";
    String saveCommand3 = "save " + newImagePathBlue + " test_blue\n";
    imageProcessorController = new ImageProcessorController(logger,
        new ImageProcessorModel(), new StringReader(loadCommand
        + splitCommand + saveCommand1 +
        saveCommand2 + saveCommand3 +
        "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface imageRed = IOFileFactory.decodeImage(newImagePathRed);
    assertImageEquals(newChannelListRed, imageRed);

    ImageInterface imageGreen = IOFileFactory.decodeImage(newImagePathGreen);
    assertImageEquals(newChannelListGreen, imageGreen);

    ImageInterface imageBlue = IOFileFactory.decodeImage(newImagePathBlue);
    assertImageEquals(newChannleListBlue, imageBlue);

    cleanupImages(Arrays.asList(imagePath, newImagePathRed, newImagePathBlue, newImagePathGreen));
  }

  @Test
  public void testScriptFile() throws IOException {
    String newImagePath = "test_image_sharpen.jpg";

    String loadCommand = "load " + imagePath + " test\n";
    String sharpenCommand = "sharpen test test_sharpen\n";
    String saveCommand = "save " + newImagePath + " test_sharpen\n";
    imageProcessorController = new ImageProcessorController(logger,
        new ImageProcessorModel(), new StringReader(loadCommand
        + sharpenCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();

    out = new StringWriter();
    logger = new ViewLogger(out);
    String scriptContent = "load " + newImagePath + " image1\n"
        + "brighten 10 image1 image2\n"
        + "save brightImage.jpg image2";
    File tempFile = File.createTempFile("temp", ".txt");
    String filePath = tempFile.getAbsolutePath();
    PrintWriter writer = new PrintWriter(new FileWriter(tempFile));
    writer.println(scriptContent);
    writer.close();

    String runString = "run " + filePath;
    imageProcessorController = new ImageProcessorController(logger,
        new ImageProcessorModel(), new StringReader(runString + "\nexit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    cleanupImages(Arrays.asList(imagePath, newImagePath));
    cleanupImages(Arrays.asList("brightImage.jpg", newImagePath));
  }

  @Test
  public void testIncorrectScriptFile() throws IOException {
    String newImagePath = "test_image_sharpen.jpg";

    String loadCommand = "load " + imagePath + " test\n";
    String sharpenCommand = "sharpen test test_sharpen\n";
    String saveCommand = "save " + newImagePath + " test_sharpen\n";
    imageProcessorController = new ImageProcessorController(logger,
        new ImageProcessorModel(), new StringReader(loadCommand
        + sharpenCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();

    out = new StringWriter();
    logger = new ViewLogger(out);
    String scriptContent = "load " + newImagePath + " image1\n"
        + "brighten 10 image1 image2\n"
        + "save brightImage.jpg image2";
    File tempFile = File.createTempFile("temp", ".txt");
    String filePath = tempFile.getAbsolutePath();
    PrintWriter writer = new PrintWriter(new FileWriter(tempFile));
    writer.println(scriptContent);
    writer.close();

    String runString = "run " + filePath + "1";
    imageProcessorController = new ImageProcessorController(logger,
        new ImageProcessorModel(), new StringReader(runString + "\nexit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("File does not exist with name"));
    cleanupImages(Arrays.asList(imagePath, newImagePath));
    cleanupImages(Arrays.asList("brightImage.jpg", newImagePath));
  }

  @Test
  public void testSepiaFlow() throws IOException {
    String newImagePath = "test_image_sepia.jpg";
    int[][] newChannelAfterSepiaRed = {{148, 148, 145}, {145, 100, 117}, {115, 214, 108}};
    int[][] newChannelAfterSepiaGreen = {{131, 131, 129}, {129, 88, 104}, {102, 190, 96}};
    int[][] newChannelAfterSepiaBlue = {{102, 102, 100}, {100, 69, 81}, {80, 148, 75}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterSepiaRed, newChannelAfterSepiaGreen,
            newChannelAfterSepiaBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String sepiaCommand = "sepia test test_sepia\n";
    String saveCommand = "save " + newImagePath + " test_sepia\n";
    imageProcessorController = new ImageProcessorController(logger,
        new ImageProcessorModel(), new StringReader(loadCommand
        + sepiaCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    assertImageEquals(newChannleList, image);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testSepiaFlowWithPercentage() throws IOException {
    String newImagePath = "test_image_sepia.jpg";
    int[][] newChannelAfterSepiaRed = {{148, 255, 255}, {145, 255, 255}, {115, 255, 255}};
    int[][] newChannelAfterSepiaGreen = {{131, 0, 0}, {129, 0, 0}, {102, 86, 0}};
    int[][] newChannelAfterSepiaBlue = {{102, 254, 240}, {100, 0, 90}, {80, 255, 44}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterSepiaRed, newChannelAfterSepiaGreen,
            newChannelAfterSepiaBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String sepiaCommand = "sepia test test_sepia split 50\n";
    String saveCommand = "save " + newImagePath + " test_sepia\n";
    imageProcessorController = new ImageProcessorController(logger,
        new ImageProcessorModel(), new StringReader(loadCommand
        + sepiaCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    assertImageEquals(newChannleList, image);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testGreyscaleFlow() throws IOException {
    String newImagePath = "test_image_grey.jpg";
    int[][] newChannelAfterRed = {{72, 72, 71}, {71, 54, 60}, {60, 134, 57}};
    List<int[][]> newChannleList = Collections.singletonList(newChannelAfterRed);
    String loadCommand = "load " + imagePath + " test\n";
    String sepiaCommand = "greyscale test test_grey\n";
    String saveCommand = "save " + newImagePath + " test_grey\n";
    imageProcessorController = new ImageProcessorController(logger,
        new ImageProcessorModel(), new StringReader(loadCommand
        + sepiaCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    assertImageEquals(newChannleList, image);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testGreyscaleFlowWithSplit() throws IOException {
    String newImagePath = "test_image_grey.jpg";
    int[][] newChannelAfterSepiaRed = {{72, 255, 255}, {71, 255, 255}, {60, 255, 255}};
    int[][] newChannelAfterSepiaGreen = {{72, 0, 0}, {71, 0, 0}, {60, 86, 0}};
    int[][] newChannelAfterSepiaBlue = {{72, 254, 240}, {71, 0, 90}, {60, 255, 44}};
    List<int[][]> newChannleList = List
        .of(newChannelAfterSepiaRed, newChannelAfterSepiaGreen, newChannelAfterSepiaBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String sepiaCommand = "greyscale test test_grey split 50\n";
    String saveCommand = "save " + newImagePath + " test_grey\n";
    imageProcessorController = new ImageProcessorController(logger,
        new ImageProcessorModel(), new StringReader(loadCommand
        + sepiaCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    assertImageEquals(newChannleList, image);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }


  @Test
  public void testMultipleOperations() throws IOException {
    String newImagePath = "test_image_sepia.jpg";
    int[][] newChannelAfterSepiaRed = {{148, 148, 145}, {145, 100, 117}, {115, 214, 108}};
    int[][] newChannelAfterSepiaGreen = {{131, 131, 129}, {129, 88, 104}, {102, 190, 96}};
    int[][] newChannelAfterSepiaBlue = {{102, 102, 100}, {100, 69, 81}, {80, 148, 75}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterSepiaRed, newChannelAfterSepiaGreen,
            newChannelAfterSepiaBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String sepiaCommand = "sepia test test_sepia\n";
    String brightenCommand = "brighten -10 test test_bright\n";
    String saveCommand = "save " + newImagePath + " test_sepia\n";
    imageProcessorController = new ImageProcessorController(logger, new ImageProcessorModel(),
        new StringReader(loadCommand + sepiaCommand
            + brightenCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    assertImageEquals(newChannleList, image);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testDitherFlow() throws IOException {
    String newImagePath = "test_image_dither.jpg";
    int[][] newChannelAfterDitherRed = {{255, 255, 255}, {255, 0, 0}, {0, 255, 0}};
    int[][] newChannelAfterDitherGreen = {{255, 255, 255}, {255, 0, 0}, {0, 255, 0}};
    int[][] newChannelAfterDitherBlue = {{255, 255, 255}, {255, 0, 0}, {0, 255, 0}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterDitherRed, newChannelAfterDitherGreen,
            newChannelAfterDitherBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String ditherCommand = "dither test test_dither\n";
    String saveCommand = "save " + newImagePath + " test_dither\n";
    imageProcessorController = new ImageProcessorController(logger,
        new ImageProcessorModel(), new StringReader(loadCommand
        + ditherCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    assertImageEquals(newChannleList, image);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
  }

  @Test
  public void testDitherFlowWithPercentage() throws IOException {
    String newImagePath = "test_image_dither.jpg";
    int[][] newChannelAfterDitherRed = {{255, 255, 255}, {255, 255, 255}, {0, 255, 255}};
    int[][] newChannelAfterDitherGreen = {{255, 0, 0}, {255, 0, 0}, {0, 86, 0}};
    int[][] newChannelAfterDitherBlue = {{255, 254, 240}, {255, 0, 90}, {0, 255, 44}};
    List<int[][]> newChannleList = Arrays
        .asList(newChannelAfterDitherRed, newChannelAfterDitherGreen,
            newChannelAfterDitherBlue);
    String loadCommand = "load " + imagePath + " test\n";
    String ditherCommand = "dither test test_dither split 50\n";
    String saveCommand = "save " + newImagePath + " test_dither\n";
    imageProcessorController = new ImageProcessorController(logger,
        new ImageProcessorModel(), new StringReader(loadCommand
        + ditherCommand + saveCommand + "exit"), out);
    imageProcessorController.startImageProcessingController();
    assertTrue(out.toString().contains("Command ran successfully"));
    ImageInterface image = IOFileFactory.decodeImage(newImagePath);
    assertImageEquals(newChannleList, image);
    cleanupImages(Arrays.asList(imagePath, newImagePath));
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
}
