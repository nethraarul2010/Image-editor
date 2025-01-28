package model.operations;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.image.ImageFactory;
import model.image.ImageInterface;
import model.operations.colorrepresentation.Intensity;
import model.operations.colorrepresentation.Value;
import model.operations.visualization.Dither;
import model.operations.colortransformation.Greyscale;
import model.operations.colortransformation.Sepia;
import model.operations.filters.BlurFilter;
import model.operations.filters.SharpenFilter;
import model.operations.merge.MergeSingleChannelImages;
import model.operations.pixeloffset.BrightnessOperation;
import model.operations.pixeloffset.CompressionOperation;
import model.operations.rotation.HorizontalFlipOperation;
import model.operations.rotation.VerticalFlipOperation;
import model.operations.split.SplitImageOperation;
import model.operations.visualization.ColorCorrection;
import model.operations.visualization.HistogramVisualization;
import model.operations.visualization.LevelAdjustment;
import org.junit.Test;

/**
 * This class tests the operations on an image.
 */
public class OperationsTest {

  @Test
  public void testIntensity() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    int[][] blueChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};

    int[][] newChannelAfterIntensity = {{170, 170, 170}, {170, 170, 170}, {170, 170, 170}};
    ImageInterface imageAfterIntensity = ImageFactory.createImage(
        Collections.singletonList(newChannelAfterIntensity));

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new Intensity().apply(image);
    assertEqualImages(imageAfterIntensity, newImage);

  }

  @Test
  public void testValue() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] blueChannel = {{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};

    int[][] newChannelAfterValue = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        Collections.singletonList(newChannelAfterValue));

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new Value().apply(image);
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test
  public void testGreyscale() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] blueChannel = {{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};

    int[][] newChannelAfterGreyscale = {{72, 72, 71}, {71, 54, 60}, {60, 134, 57}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newChannelAfterGreyscale, newChannelAfterGreyscale, newChannelAfterGreyscale));

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new Greyscale().apply(image);
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test
  public void testGreyscale50Percent() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] blueChannel = {{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};

    int[][] newRedChannel = {{72, 255, 255}, {71, 255, 255}, {60, 255, 255}};
    int[][] newGreenChannel = {{72, 0, 0}, {71, 0, 0}, {60, 86, 0}};
    int[][] newBlueChannel = {{72, 254, 240}, {71, 0, 90}, {60, 255, 44}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newRedChannel, newGreenChannel, newBlueChannel));

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new Greyscale().apply(image, "50");
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreyscaleInvalidPercentage() {
    int[][] channel = {{100, 50, 60, 150}, {20, 60, 40, 30}, {50, 90, 70, 82}, {74, 66, 90, 58}};
    int[][] newChannel = {{68, 68, 58, 148}, {68, 68, 33, 33}, {68, 68, 68, 68}, {68, 68, 68, 68}};
    ImageInterface image = ImageFactory.createImage(Collections.singletonList(channel));
    new Greyscale().apply(image, "500");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreyscaleInvalidPercentageType() {
    int[][] channel = {{100, 50, 60, 150}, {20, 60, 40, 30}, {50, 90, 70, 82}, {74, 66, 90, 58}};
    int[][] newChannel = {{68, 68, 58, 148}, {68, 68, 33, 33}, {68, 68, 68, 68}, {68, 68, 68, 68}};
    ImageInterface image = ImageFactory.createImage(Collections.singletonList(channel));
    new Greyscale().apply(image, "T");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGreyscale() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel));
    new Greyscale().apply(image);
  }

  @Test
  public void testLuma() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] blueChannel = {{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};

    int[][] newChannelAfterGreyscale = {{72, 72, 71}, {71, 54, 60}, {60, 134, 57}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newChannelAfterGreyscale, newChannelAfterGreyscale, newChannelAfterGreyscale));

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new Greyscale().apply(image);
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidLuma() {
    int[][] channel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    ImageInterface image = ImageFactory.createImage(Collections.singletonList(channel));
    new Greyscale().apply(image, "100");
  }

  @Test
  public void testBlurFilter() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] blueChannel = {{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};

    int[][] newChannelAfterBlurRed = {{140, 186, 140}, {186, 247, 186}, {140, 186, 140}};
    int[][] newChannelAfterBlurGreen = {{0, 0, 0}, {5, 10, 5}, {10, 21, 10}};
    int[][] newChannelAfterBlurBlue = {{124, 144, 102}, {131, 140, 87}, {81, 98, 53}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newChannelAfterBlurRed, newChannelAfterBlurGreen, newChannelAfterBlurBlue));

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new BlurFilter().apply(image);
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlurInvalidPercentage() {
    int[][] channel = {{100, 50, 60, 150}, {20, 60, 40, 30}, {50, 90, 70, 82}, {74, 66, 90, 58}};
    int[][] newChannel = {{68, 68, 58, 148}, {68, 68, 33, 33}, {68, 68, 68, 68}, {68, 68, 68, 68}};
    ImageInterface image = ImageFactory.createImage(Collections.singletonList(channel));
    new BlurFilter().apply(image, "500");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlurInvalidPercentageType() {
    int[][] channel = {{100, 50, 60, 150}, {20, 60, 40, 30}, {50, 90, 70, 82}, {74, 66, 90, 58}};
    int[][] newChannel = {{68, 68, 58, 148}, {68, 68, 33, 33}, {68, 68, 68, 68}, {68, 68, 68, 68}};
    ImageInterface image = ImageFactory.createImage(Collections.singletonList(channel));
    new BlurFilter().apply(image, "T");
  }

  @Test
  public void testBlurFilter50Percent() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] blueChannel = {{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};

    int[][] newChannelAfterBlurRed = {{140, 255, 255}, {186, 255, 255}, {140, 255, 255}};
    int[][] newChannelAfterBlurGreen = {{0, 0, 0}, {5, 0, 0}, {10, 86, 0}};
    int[][] newChannelAfterBlurBlue = {{124, 254, 240}, {131, 0, 90}, {81, 255, 44}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newChannelAfterBlurRed, newChannelAfterBlurGreen, newChannelAfterBlurBlue));

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new BlurFilter().apply(image, "50");
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test
  public void testCompressionOperator() {
    int[][] channel = {{100, 50, 60, 150}, {20, 60, 40, 30}, {50, 90, 70, 82}, {74, 66, 90, 58}};
    int[][] newChannel = {{68, 68, 58, 148}, {68, 68, 33, 33}, {68, 68, 68, 68}, {68, 68, 68, 68}};
    ImageInterface image = ImageFactory.createImage(Collections.singletonList(channel));
    ImageInterface expectedImage = ImageFactory.createImage(Collections.singletonList(newChannel));
    ImageInterface newImage = new CompressionOperation().apply(image, "80");
    assertEqualImages(expectedImage, newImage);
  }

  @Test
  public void testCompressionOperatorFor0Percent() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] blueChannel = {{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};
    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));
    ImageInterface newImage = new CompressionOperation().apply(image, "0");
    assertEqualImages(image, newImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompressionInvalidPercentage() {
    int[][] channel = {{100, 50, 60, 150}, {20, 60, 40, 30}, {50, 90, 70, 82}, {74, 66, 90, 58}};
    int[][] newChannel = {{68, 68, 58, 148}, {68, 68, 33, 33}, {68, 68, 68, 68}, {68, 68, 68, 68}};
    ImageInterface image = ImageFactory.createImage(Collections.singletonList(channel));
    ImageInterface expectedImage = ImageFactory.createImage(Collections.singletonList(newChannel));
    new CompressionOperation().apply(image, "120");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompressionInvalidPercentageType() {
    int[][] channel = {{100, 50, 60, 150}, {20, 60, 40, 30}, {50, 90, 70, 82}, {74, 66, 90, 58}};
    int[][] newChannel = {{68, 68, 58, 148}, {68, 68, 33, 33}, {68, 68, 68, 68}, {68, 68, 68, 68}};
    ImageInterface image = ImageFactory.createImage(Collections.singletonList(channel));
    ImageInterface expectedImage = ImageFactory.createImage(Collections.singletonList(newChannel));
    new CompressionOperation().apply(image, "T");
  }

  @Test
  public void testSharpenFilter() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] blueChannel = {{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};

    int[][] newChannelAfterSharpenRed = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] newChannelAfterSharpenGreen = {{0, 0, 0}, {21, 21, 21}, {21, 86, 21}};
    int[][] newChannelAfterSharpenBlue = {{255, 255, 215}, {255, 255, 215}, {97, 255, 0}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newChannelAfterSharpenRed, newChannelAfterSharpenGreen,
            newChannelAfterSharpenBlue));

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new SharpenFilter().apply(image);
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test
  public void testSharpenFilter50Percent() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] blueChannel = {{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};

    int[][] newChannelAfterSharpenRed = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] newChannelAfterSharpenGreen = {{0, 0, 0}, {21, 0, 0}, {21, 86, 0}};
    int[][] newChannelAfterSharpenBlue = {{255, 254, 240}, {255, 0, 90}, {97, 255, 44}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newChannelAfterSharpenRed, newChannelAfterSharpenGreen,
            newChannelAfterSharpenBlue));

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new SharpenFilter().apply(image,"50");
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSharpenFilterInvalidPercentage() {
    int[][] channel = {{100, 50, 60, 150}, {20, 60, 40, 30}, {50, 90, 70, 82}, {74, 66, 90, 58}};
    int[][] newChannel = {{68, 68, 58, 148}, {68, 68, 33, 33}, {68, 68, 68, 68}, {68, 68, 68, 68}};
    ImageInterface image = ImageFactory.createImage(Collections.singletonList(channel));
    new SharpenFilter().apply(image, "500");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSharpenFilterInvalidPercentageType() {
    int[][] channel = {{100, 50, 60, 150}, {20, 60, 40, 30}, {50, 90, 70, 82}, {74, 66, 90, 58}};
    int[][] newChannel = {{68, 68, 58, 148}, {68, 68, 33, 33}, {68, 68, 68, 68}, {68, 68, 68, 68}};
    ImageInterface image = ImageFactory.createImage(Collections.singletonList(channel));
    new SharpenFilter().apply(image, "T");
  }

  @Test
  public void testIncreaseBrightness() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] blueChannel = {{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};
    int[][] newChannelAfterIncreaseBrightnessRed = {{255, 255, 255}, {255, 255, 255},
        {255, 255, 255}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{10, 10, 10}, {10, 10, 10}, {10, 96, 10}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{255, 255, 250}, {250, 10, 100},
        {93, 255, 54}};
    ImageInterface imageAfterValue = ImageFactory
        .createImage(List.of(newChannelAfterIncreaseBrightnessRed,
            newChannelAfterIncreaseBrightnessGreen, newChannelAfterIncreaseBrightnessBlue));
    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));
    ImageInterface newImage = new BrightnessOperation().apply(image, "10");
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test
  public void testIncreaseBrightnessClamp() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] blueChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] newChannelAfterIncreaseBrightnessRed = {{255, 255, 255}, {255, 255, 255},
        {255, 255, 255}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{255, 255, 255}, {255, 255, 255},
        {255, 255, 255}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{255, 255, 255}, {255, 255, 255},
        {255, 255, 255}};
    ImageInterface imageAfterValue = ImageFactory
        .createImage(List.of(newChannelAfterIncreaseBrightnessRed,
            newChannelAfterIncreaseBrightnessGreen, newChannelAfterIncreaseBrightnessBlue));
    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));
    ImageInterface newImage = new BrightnessOperation().apply(image, "10");
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test
  public void testDecreaseBrightness() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] blueChannel = {{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};
    int[][] newChannelAfterIncreaseBrightnessRed = {{245, 245, 245}, {245, 245, 245},
        {245, 245, 245}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{0, 0, 0}, {0, 0, 0}, {0, 76, 0}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{244, 244, 230}, {230, 0, 80},
        {73, 245, 34}};
    ImageInterface imageAfterValue = ImageFactory
        .createImage(List.of(newChannelAfterIncreaseBrightnessRed,
            newChannelAfterIncreaseBrightnessGreen, newChannelAfterIncreaseBrightnessBlue));
    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));
    ImageInterface newImage = new BrightnessOperation().apply(image, "-10");
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test
  public void testDecreaseBrightnessClamp() {
    int[][] redChannel = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    int[][] greenChannel = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    int[][] blueChannel = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    int[][] newChannelAfterIncreaseBrightnessRed = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    int[][] newChannelAfterIncreaseBrightnessGreen = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    int[][] newChannelAfterIncreaseBrightnessBlue = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    ImageInterface imageAfterValue = ImageFactory
        .createImage(List.of(newChannelAfterIncreaseBrightnessRed,
            newChannelAfterIncreaseBrightnessGreen, newChannelAfterIncreaseBrightnessBlue));
    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));
    ImageInterface newImage = new BrightnessOperation().apply(image, "-10");
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test
  public void testSepia() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] blueChannel = {{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};

    int[][] newChannelAfterSepiaRed = {{148, 148, 145}, {145, 100, 117}, {115, 214, 108}};
    int[][] newChannelAfterSepiaGreen = {{131, 131, 129}, {129, 88, 104}, {102, 190, 96}};
    int[][] newChannelAfterSepiaBlue = {{102, 102, 100}, {100, 69, 81}, {80, 148, 75}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newChannelAfterSepiaRed, newChannelAfterSepiaGreen, newChannelAfterSepiaBlue));

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new Sepia().apply(image);
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test
  public void testSepia50Percent() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] blueChannel = {{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};

    int[][] newChannelAfterSepiaRed = {{148, 255, 255}, {145, 255, 255}, {115, 255, 255}};
    int[][] newChannelAfterSepiaGreen = {{131, 0, 0}, {129, 0, 0}, {102, 86, 0}};
    int[][] newChannelAfterSepiaBlue = {{102, 254, 240}, {100, 0, 90}, {80, 255, 44}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newChannelAfterSepiaRed, newChannelAfterSepiaGreen, newChannelAfterSepiaBlue));

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new Sepia().apply(image, "50");
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSepiaFilterInvalidPercentage() {
    int[][] channel = {{100, 50, 60, 150}, {20, 60, 40, 30}, {50, 90, 70, 82}, {74, 66, 90, 58}};
    int[][] newChannel = {{68, 68, 58, 148}, {68, 68, 33, 33}, {68, 68, 68, 68}, {68, 68, 68, 68}};
    ImageInterface image = ImageFactory.createImage(Collections.singletonList(channel));
    new Sepia().apply(image, "150");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSepiaFilterInvalidPercentageType() {
    int[][] channel = {{100, 50, 60, 150}, {20, 60, 40, 30}, {50, 90, 70, 82}, {74, 66, 90, 58}};
    int[][] newChannel = {{68, 68, 58, 148}, {68, 68, 33, 33}, {68, 68, 68, 68}, {68, 68, 68, 68}};
    ImageInterface image = ImageFactory.createImage(Collections.singletonList(channel));
    new Sepia().apply(image, "T");
  }

  @Test
  public void testSepiaClamp() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] blueChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};

    int[][] newChannelAfterSepiaRed = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] newChannelAfterSepiaGreen = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] newChannelAfterSepiaBlue = {{238, 238, 238}, {238, 238, 238}, {238, 238, 238}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newChannelAfterSepiaRed, newChannelAfterSepiaGreen, newChannelAfterSepiaBlue));

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new Sepia().apply(image);
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test
  public void testVerticalRotation() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] blueChannel = {{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};

    int[][] newChannelAfterVerticalRotationRed = {{255, 255, 255}, {255, 255, 255},
        {255, 255, 255}};
    int[][] newChannelAfterVerticalRotationGreen = {{0, 86, 0}, {0, 0, 0}, {0, 0, 0}};
    int[][] newChannelAfterVerticalRotationBlue = {{83, 255, 44}, {240, 0, 90}, {254, 254, 240}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newChannelAfterVerticalRotationRed, newChannelAfterVerticalRotationGreen,
            newChannelAfterVerticalRotationBlue));

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new VerticalFlipOperation().apply(image);
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test
  public void testHorizontalRotation() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] blueChannel = {{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};

    int[][] newChannelAfterHorizontalRotationRed = {{255, 255, 255}, {255, 255, 255},
        {255, 255, 255}};
    int[][] newChannelAfterHorizontalRotationGreen = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] newChannelAfterHorizontalRotationBlue = {{240, 254, 254}, {90, 0, 240},
        {44, 255, 83}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newChannelAfterHorizontalRotationRed, newChannelAfterHorizontalRotationGreen,
            newChannelAfterHorizontalRotationBlue));

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new HorizontalFlipOperation().apply(image);
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test
  public void testSplitOperationRed() {
    int[][] redChannel = {{255, 0}, {0, 255}};
    int[][] greenChannel = {{0, 255}, {255, 0}};
    int[][] blueChannel = {{0, 0}, {255, 255}};

    int[][] newChannelAfterSplitRed = {{255, 0}, {0, 255}};
    int[][] newChannelAfterSplitGreen = {{0, 0}, {0, 0}};
    int[][] newChannelAfterSplitBlue = {{0, 0}, {0, 0}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newChannelAfterSplitRed, newChannelAfterSplitGreen, newChannelAfterSplitBlue));

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new SplitImageOperation().apply(image, 0);
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSplit() {
    int[][] redChannel = {{255, 0}, {0, 255}};

    ImageInterface image = ImageFactory.createImage(Collections.singletonList(redChannel));
    new SplitImageOperation().apply(image, 0);
  }

  @Test
  public void testSplitOperationGreen() {
    int[][] redChannel = {{255, 0}, {0, 255}};
    int[][] greenChannel = {{0, 255}, {255, 0}};
    int[][] blueChannel = {{0, 0}, {255, 255}};

    int[][] newChannelAfterSplitRed = {{0, 0}, {0, 0}};
    int[][] newChannelAfterSplitGreen = {{0, 255}, {255, 0}};
    int[][] newChannelAfterSplitBlue = {{0, 0}, {0, 0}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newChannelAfterSplitRed, newChannelAfterSplitGreen, newChannelAfterSplitBlue));

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new SplitImageOperation().apply(image, 1);
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test
  public void testSplitOperationBlue() {
    int[][] redChannel = {{255, 0}, {0, 255}};
    int[][] greenChannel = {{0, 255}, {255, 0}};
    int[][] blueChannel = {{0, 0}, {255, 255}};

    int[][] newChannelAfterSplitRed = {{0, 0}, {0, 0}};
    int[][] newChannelAfterSplitGreen = {{0, 0}, {0, 0}};
    int[][] newChannelAfterSplitBlue = {{0, 0}, {255, 255}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newChannelAfterSplitRed, newChannelAfterSplitGreen, newChannelAfterSplitBlue));

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new SplitImageOperation().apply(image, 2);
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test
  public void testSplitOperationAll() {
    int[][] redChannel = {{255, 0}, {0, 255}};
    int[][] greenChannel = {{0, 255}, {255, 0}};
    int[][] blueChannel = {{0, 0}, {255, 255}};
    List<ImageInterface> newChannels = new ArrayList<>();

    int[][] newChannelAfterSplitRedForRed = {{255, 0}, {0, 255}};
    int[][] newChannelAfterSplitGreenForRed = {{0, 0}, {0, 0}};
    int[][] newChannelAfterSplitBlueForRed = {{0, 0}, {0, 0}};
    ImageInterface imageAfterValue1 = ImageFactory.createImage(
        List.of(newChannelAfterSplitRedForRed, newChannelAfterSplitGreenForRed,
            newChannelAfterSplitBlueForRed));
    newChannels.add(imageAfterValue1);

    int[][] newChannelAfterSplitRedForGreen = {{0, 0}, {0, 0}};
    int[][] newChannelAfterSplitGreenForGreen = {{0, 255}, {255, 0}};
    int[][] newChannelAfterSplitBlueForGreen = {{0, 0}, {0, 0}};
    ImageInterface imageAfterValue2 = ImageFactory.createImage(
        List.of(newChannelAfterSplitRedForGreen, newChannelAfterSplitGreenForGreen,
            newChannelAfterSplitBlueForGreen));
    newChannels.add(imageAfterValue2);

    int[][] newChannelAfterSplitRedForBlue = {{0, 0}, {0, 0}};
    int[][] newChannelAfterSplitGreenForBlue = {{0, 0}, {0, 0}};
    int[][] newChannelAfterSplitBlueForBlue = {{0, 0}, {255, 255}};
    ImageInterface imageAfterValue3 = ImageFactory.createImage(
        List.of(newChannelAfterSplitRedForBlue, newChannelAfterSplitGreenForBlue,
            newChannelAfterSplitBlueForBlue));
    newChannels.add(imageAfterValue3);

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    for (int i = 0; i < 3; i++) {
      ImageInterface newImage = new SplitImageOperation().apply(image, i);
      assertEqualImages(newChannels.get(i), newImage);
    }
  }

  @Test
  public void testMergeOperation() {
    int[][] redChannel1 = {{255, 0}, {0, 255}};
    int[][] greenChannel1 = {{255, 0}, {0, 255}};
    int[][] blueChannel1 = {{255, 0}, {0, 255}};
    ImageInterface imageAfterValue1 = ImageFactory.createImage(
        List.of(redChannel1, greenChannel1,
            blueChannel1));

    int[][] redChannel2 = {{0, 255}, {255, 0}};
    int[][] greenChannel2 = {{0, 255}, {255, 0}};
    int[][] blueChannel2 = {{0, 255}, {255, 0}};
    ImageInterface imageAfterValue2 = ImageFactory.createImage(
        List.of(redChannel2, greenChannel2,
            blueChannel2));

    int[][] redChannel3 = {{0, 0}, {255, 255}};
    int[][] greenChannel3 = {{0, 0}, {255, 255}};
    int[][] blueChannel3 = {{0, 0}, {255, 255}};
    ImageInterface imageAfterValue3 = ImageFactory.createImage(
        List.of(redChannel3, greenChannel3,
            blueChannel3));

    int[][] newChannelAfterValueRed = {{255, 0}, {0, 255}};
    int[][] newChannelAfterValueGreen = {{0, 255}, {255, 0}};
    int[][] newChannelAfterValueBlue = {{0, 0}, {255, 255}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newChannelAfterValueRed, newChannelAfterValueGreen,
            newChannelAfterValueBlue));

    ImageInterface newImage = new MergeSingleChannelImages().apply(List.of(imageAfterValue1,
        imageAfterValue2, imageAfterValue3));
    assertEqualImages(imageAfterValue, newImage);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMergeWithMoreChannels() {
    int[][] redChannel1 = {{255, 0}, {0, 255}};
    int[][] greenChannel1 = {{255, 0}, {0, 255}};
    int[][] blueChannel1 = {{255, 0}, {0, 255}};
    ImageInterface imageAfterValue1 = ImageFactory.createImage(
        List.of(redChannel1, greenChannel1,
            blueChannel1));

    int[][] redChannel2 = {{0, 255}, {255, 0}};
    int[][] greenChannel2 = {{0, 255}, {255, 0}};
    int[][] blueChannel2 = {{0, 255}, {255, 0}};
    ImageInterface imageAfterValue2 = ImageFactory.createImage(
        List.of(redChannel2, greenChannel2,
            blueChannel2));

    new MergeSingleChannelImages().apply(List.of(imageAfterValue1,
        imageAfterValue2));
  }

  @Test
  public void testColorCorrection() {
    int[][] redChannel = {{80, 0}, {0, 255}};
    int[][] greenChannel = {{0, 87}, {255, 0}};
    int[][] blueChannel = {{0, 0}, {200, 200}};

    int[][] newChannelAfterSplitRed = {{122, 42}, {42, 255}};
    int[][] newChannelAfterSplitGreen = {{35, 122}, {255, 35}};
    int[][] newChannelAfterSplitBlue = {{0, 0}, {122, 122}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newChannelAfterSplitRed, newChannelAfterSplitGreen, newChannelAfterSplitBlue));

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new ColorCorrection().apply(image);
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test
  public void testColorCorrectionWithSplit() {
    int[][] redChannel = {{80, 0}, {0, 255}};
    int[][] greenChannel = {{0, 87}, {255, 0}};
    int[][] blueChannel = {{0, 0}, {200, 200}};

    int[][] newChannelAfterSplitRed = {{122, 0}, {42, 255}};
    int[][] newChannelAfterSplitGreen = {{35, 87}, {255, 0}};
    int[][] newChannelAfterSplitBlue = {{0, 0}, {122, 200}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newChannelAfterSplitRed, newChannelAfterSplitGreen, newChannelAfterSplitBlue));

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new ColorCorrection().apply(image, "50");
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testColorCorrectionWithSplitInvalidPercentage() {
    int[][] redChannel = {{80, 0}, {0, 255}};
    int[][] greenChannel = {{0, 87}, {255, 0}};
    int[][] blueChannel = {{0, 0}, {200, 200}};

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new ColorCorrection().apply(image, "-50");
  }

  @Test
  public void testHistogram() {
    int[][] redChannel = {{255, 255}, {255, 255}};
    int[][] greenChannel = {{0, 0}, {0, 0}};
    int[][] blueChannel = {{0, 0}, {0, 0}};

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new HistogramVisualization().apply(image);
    int[][] redNewChannel = newImage.getChannel().get(0);
    int[][] greenNewChannel = newImage.getChannel().get(1);
    int[][] blueNewChannel = newImage.getChannel().get(2);

    assertEquals(255, redNewChannel[4][255]);
    assertEquals(0, greenNewChannel[1][0]);
    assertEquals(255, blueNewChannel[2][0]);
  }

  @Test
  public void testLevelAdjustmentClamping() {
    int[][] redChannel = {{255, 255}, {255, 255}};
    int[][] greenChannel = {{0, 0}, {0, 0}};
    int[][] blueChannel = {{0, 0}, {0, 0}};

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    int[][] newChannelAfterSplitRed = {{0, 0}, {0, 0}};
    int[][] newChannelAfterSplitGreen = {{0, 0}, {0, 0}};
    int[][] newChannelAfterSplitBlue = {{0, 0}, {0, 0}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newChannelAfterSplitRed, newChannelAfterSplitGreen, newChannelAfterSplitBlue));

    ImageInterface newImage = new LevelAdjustment().apply(image, "10 20 50 100");
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test
  public void testLevelAdjustment() {
    int[][] redChannel = {{40, 80}, {100, 250}};
    int[][] greenChannel = {{50, 90}, {100, 230}};
    int[][] blueChannel = {{80, 0}, {50, 0}};

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    int[][] newChannelAfterSplitRed = {{255, 0}, {0, 0}};
    int[][] newChannelAfterSplitGreen = {{255, 0}, {0, 0}};
    int[][] newChannelAfterSplitBlue = {{0, 0}, {255, 0}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newChannelAfterSplitRed, newChannelAfterSplitGreen, newChannelAfterSplitBlue));

    ImageInterface newImage = new LevelAdjustment().apply(image, "10 20 50 100");
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test
  public void testLevelAdjustmentWithSplit() {
    int[][] redChannel = {{40, 80}, {100, 250}};
    int[][] greenChannel = {{50, 90}, {100, 230}};
    int[][] blueChannel = {{80, 0}, {50, 0}};

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    int[][] newChannelAfterSplitRed = {{255, 80}, {0, 250}};
    int[][] newChannelAfterSplitGreen = {{255, 90}, {0, 230}};
    int[][] newChannelAfterSplitBlue = {{0, 0}, {255, 0}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newChannelAfterSplitRed, newChannelAfterSplitGreen, newChannelAfterSplitBlue));

    ImageInterface newImage = new LevelAdjustment().apply(image, "10 20 50 50");
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLevelAdjustmentWithSplitIncorrectAmount() {
    int[][] redChannel = {{40, 80}, {100, 250}};
    int[][] greenChannel = {{50, 90}, {100, 230}};
    int[][] blueChannel = {{80, 0}, {50, 0}};

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new LevelAdjustment().apply(image, "10 20 50 -95");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLevelAdjustmentWithIncorrectOrder() {
    int[][] redChannel = {{40, 80}, {100, 250}};
    int[][] greenChannel = {{50, 90}, {100, 230}};
    int[][] blueChannel = {{80, 0}, {50, 0}};

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new LevelAdjustment().apply(image, "10 80 50");

  }

  @Test(expected = IllegalArgumentException.class)
  public void testLevelAdjustmentWithIncorrectNumberOfOperants() {
    int[][] redChannel = {{40, 80}, {100, 250}};
    int[][] greenChannel = {{50, 90}, {100, 230}};
    int[][] blueChannel = {{80, 0}, {50, 0}};

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new LevelAdjustment().apply(image, "10 50 100 50 100");

  }

  @Test(expected = IllegalArgumentException.class)
  public void testLevelAdjustmentWithIncorrectInput() {
    int[][] redChannel = {{40, 80}, {100, 250}};
    int[][] greenChannel = {{50, 90}, {100, 230}};
    int[][] blueChannel = {{80, 0}, {50, 0}};

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new LevelAdjustment().apply(image, "10 L 90 100");

  }

  @Test(expected = IllegalArgumentException.class)
  public void testLevelAdjustmentWithIncorrectInput2() {
    int[][] redChannel = {{40, 80}, {100, 250}};
    int[][] greenChannel = {{50, 90}, {100, 230}};
    int[][] blueChannel = {{80, 0}, {50, 0}};

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new LevelAdjustment().apply(image, "10 50.5 90 100");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLevelAdjustmentWithIncorrectInput3() {
    int[][] redChannel = {{40, 80}, {100, 250}};
    int[][] greenChannel = {{50, 90}, {100, 230}};
    int[][] blueChannel = {{80, 0}, {50, 0}};

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new LevelAdjustment().apply(image, "-10 50 90 100");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLevelAdjustmentWithIncorrectInput4() {
    int[][] redChannel = {{40, 80}, {100, 250}};
    int[][] greenChannel = {{50, 90}, {100, 230}};
    int[][] blueChannel = {{80, 0}, {50, 0}};

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new LevelAdjustment().apply(image, "10 50 259 100");
  }

  @Test
  public void testDither() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] blueChannel = {{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};

    int[][] newChannelAfterDitherRed = {{255, 255, 255}, {255, 0, 0}, {0, 255, 0}};
    int[][] newChannelAfterDitherGreen = {{255, 255, 255}, {255, 0, 0}, {0, 255, 0}};
    int[][] newChannelAfterDitherBlue = {{255, 255, 255}, {255, 0, 0}, {0, 255, 0}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newChannelAfterDitherRed, newChannelAfterDitherGreen, newChannelAfterDitherBlue));

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new Dither().apply(image);
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test
  public void testDither50Percent() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] blueChannel = {{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};

    int[][] newChannelAfterDitherRed = {{255, 255, 255}, {255, 255, 255}, {0, 255, 255}};
    int[][] newChannelAfterDitherGreen = {{255, 0, 0}, {255, 0, 0}, {0, 86, 0}};
    int[][] newChannelAfterDitherBlue = {{255, 254, 240}, {255, 0, 90}, {0, 255, 44}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newChannelAfterDitherRed, newChannelAfterDitherGreen, newChannelAfterDitherBlue));

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new Dither().apply(image, "50");
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDitherFilterInvalidPercentage() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] blueChannel = {{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};
    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));
    int[][] newChannel = {{68, 68, 58, 148}, {68, 68, 33, 33}, {68, 68, 68, 68}, {68, 68, 68, 68}};
    new Dither().apply(image, "150");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDitherFilterInvalidPercentageType() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] blueChannel = {{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};
    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));
    int[][] newChannel = {{68, 68, 58, 148}, {68, 68, 33, 33}, {68, 68, 68, 68}, {68, 68, 68, 68}};
    new Dither().apply(image, "T");
  }

  @Test
  public void testDitherClamp() {
    int[][] redChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] blueChannel = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};

    int[][] newChannelAfterDitherRed = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] newChannelAfterDitherGreen = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] newChannelAfterDitherBlue = {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    ImageInterface imageAfterValue = ImageFactory.createImage(
        List.of(newChannelAfterDitherRed, newChannelAfterDitherGreen, newChannelAfterDitherBlue));

    ImageInterface image = ImageFactory.createImage(List.of(redChannel, greenChannel, blueChannel));

    ImageInterface newImage = new Dither().apply(image);
    assertEqualImages(imageAfterValue, newImage);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUnequalHeightForMerge() {
    int[][] redChannel1 = {{255, 0}, {0, 255}, {0, 255}};
    int[][] greenChannel1 = {{255, 0}, {0, 255}, {0, 255}};
    int[][] blueChannel1 = {{255, 0}, {0, 255}, {0, 255}};
    ImageInterface imageAfterValue1 = ImageFactory.createImage(
        List.of(redChannel1, greenChannel1,
            blueChannel1));

    int[][] redChannel2 = {{0, 255}, {255, 0}};
    int[][] greenChannel2 = {{0, 255}, {255, 0}};
    int[][] blueChannel2 = {{0, 255}, {255, 0}};
    ImageInterface imageAfterValue2 = ImageFactory.createImage(
        List.of(redChannel2, greenChannel2,
            blueChannel2));

    new MergeSingleChannelImages().apply(List.of(imageAfterValue1,
        imageAfterValue2));
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
