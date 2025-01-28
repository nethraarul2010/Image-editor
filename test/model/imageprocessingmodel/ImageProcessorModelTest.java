package model.imageprocessingmodel;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Collections;
import model.image.CommonImage;
import model.image.ImageInterface;
import model.imageio.IOFileFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests valid and invalid calls to its public function.
 */
public class ImageProcessorModelTest {

  private final String imagePath = "test_image.jpg";
  private final String initialImageName = "test_name";
  private ImageProcessorModel imageProcessorModel;

  @Before
  public void setUp() throws IOException {
    imageProcessorModel = new ImageProcessorModel();
    int[][] redChannel = new int[][]{{255, 255, 255}, {255, 255, 255}, {255, 255, 255}};
    int[][] greenChannel = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 86, 0}};
    int[][] blueChannel = new int[][]{{254, 254, 240}, {240, 0, 90}, {83, 255, 44}};
    ImageInterface imageInterface = new CommonImage.ImageBuilder().addChannel(redChannel)
        .addChannel(greenChannel)
        .addChannel(blueChannel)
        .build();
    IOFileFactory.encodeAndSaveImage(imagePath, imageInterface);
    imageProcessorModel.loadImage(imagePath, initialImageName);
  }

  @Test
  public void testBlurImage() {
    String destImageName = "destImageName";
    imageProcessorModel.blurImage(initialImageName, destImageName, "100");
    assertNotNull(imageProcessorModel.getImage(destImageName));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlurImageWithNonexistentImage() {
    String imageName = "nonexistentImage";
    String destImageName = "destImageName";
    imageProcessorModel.blurImage(imageName, destImageName, "100");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlurImageWithExistingDestinationImage() {
    imageProcessorModel.blurImage(initialImageName, initialImageName, "100");
  }

  @Test
  public void testSharpenImage() {
    String destImageName = "destImageName";
    imageProcessorModel.sharpenImage(initialImageName, destImageName, "100");
    assertNotNull(imageProcessorModel.getImage(destImageName));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSharpenWithNonexistentImage() {
    String imageName = "nonexistentImage";
    String destImageName = "destImageName";
    imageProcessorModel.sharpenImage(imageName, destImageName, "100");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSharpenWithExistingDestinationImage() {
    imageProcessorModel.sharpenImage(initialImageName, initialImageName, "100");
  }

  @Test
  public void testHorizontalFlipImage() {
    String destImageName = "destImageName";
    imageProcessorModel.horizontalFlipImage(initialImageName, destImageName);
    assertNotNull(imageProcessorModel.getImage(destImageName));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHorizontalFlipImageWithNonexistentImage() {
    String imageName = "nonexistentImage";
    String destImageName = "destImageName";
    imageProcessorModel.horizontalFlipImage(imageName, destImageName);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHorizontalFlipImageWithExistingDestinationImage() {
    imageProcessorModel.horizontalFlipImage(initialImageName, initialImageName);
  }

  @Test
  public void testVerticalFlipImage() {
    String destImageName = "destImageName";
    imageProcessorModel.verticalFlipImage(initialImageName, destImageName);
    assertNotNull(imageProcessorModel.getImage(destImageName));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVerticalFlipImageWithNonexistentImage() {
    String imageName = "nonexistentImage";
    String destImageName = "destImageName";
    imageProcessorModel.verticalFlipImage(imageName, destImageName);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVerticalFlipImageWithExistingDestinationImage() {
    imageProcessorModel.verticalFlipImage(initialImageName, initialImageName);
  }

  @Test
  public void testSepiaImage() {
    String destImageName = "destImageName";
    imageProcessorModel.sepiaImage(initialImageName, destImageName, "100");
    assertNotNull(imageProcessorModel.getImage(destImageName));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSepiaImageWithNonexistentImage() {
    String imageName = "nonexistentImage";
    String destImageName = "destImageName";
    imageProcessorModel.sepiaImage(imageName, destImageName, "100");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSepiaImageWithExistingDestinationImage() {
    imageProcessorModel.sepiaImage(initialImageName, initialImageName, "100");
  }


  @Test
  public void testSplitImage() {
    String destImageName = "destImageName";
    imageProcessorModel.splitImage(initialImageName, destImageName, 1);
    assertNotNull(imageProcessorModel.getImage(destImageName));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSplitImageWithNonexistentImage() {
    String imageName = "nonexistentImage";
    String destImageName = "destImageName";
    imageProcessorModel.splitImage(imageName, destImageName, "100");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSplitImageWithExistingDestinationImage() {
    imageProcessorModel.splitImage(initialImageName, initialImageName, "100");
  }


  @Test
  public void testBrightenImage() {
    String destImageName = "destImageName";
    imageProcessorModel.brightenImage(initialImageName, destImageName, "100");
    assertNotNull(imageProcessorModel.getImage(destImageName));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenImageWithNonexistentImage() {
    String imageName = "nonexistentImage";
    String destImageName = "destImageName";
    imageProcessorModel.brightenImage(imageName, destImageName, "100");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenImageWithExistingDestinationImage() {
    imageProcessorModel.brightenImage(initialImageName, initialImageName, "100");
  }

  @Test
  public void testCompressImage() {
    String destImageName = "destImageName";
    imageProcessorModel.compressImage(initialImageName, destImageName, "10");
    assertNotNull(imageProcessorModel.getImage(destImageName));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompressImageWithNonexistentImage() {
    String imageName = "nonexistentImage";
    String destImageName = "destImageName";
    imageProcessorModel.compressImage(imageName, destImageName, "10");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompressImageWithExistingDestinationImage() {
    imageProcessorModel.compressImage(initialImageName, initialImageName, "10");
  }

  @Test
  public void testIntensityImage() {
    String destImageName = "destImageName";
    imageProcessorModel.intensityImage(initialImageName, destImageName);
    assertNotNull(imageProcessorModel.getImage(destImageName));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIntensityImageWithNonexistentImage() {
    String imageName = "nonexistentImage";
    String destImageName = "destImageName";
    imageProcessorModel.intensityImage(imageName, destImageName);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIntensityImageWithExistingDestinationImage() {
    imageProcessorModel.intensityImage(initialImageName, initialImageName);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMergeImageWithNonexistentImage() {
    String imageName = "nonexistentImage";
    String destImageName = "destImageName";
    imageProcessorModel.mergeImage(Collections.singletonList(imageName), destImageName);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMergeImageWithExistingDestinationImage() {
    imageProcessorModel.mergeImage(Collections.singletonList(initialImageName), initialImageName);
  }

  @Test
  public void testDitherImage() {
    String destImageName = "destImageName";
    imageProcessorModel.ditherImage(initialImageName, destImageName, "100");
    assertNotNull(imageProcessorModel.getImage(destImageName));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDitherImageWithNonexistentImage() {
    String imageName = "nonexistentImage";
    String destImageName = "destImageName";
    imageProcessorModel.ditherImage(imageName, destImageName, "100");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDitherImageWithExistingDestinationImage() {
    imageProcessorModel.ditherImage(initialImageName, initialImageName, "100");
  }

}
