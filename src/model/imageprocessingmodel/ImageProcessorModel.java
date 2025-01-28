package model.imageprocessingmodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.image.ImageInterface;
import model.imageio.IOFileFactory;
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

/**
 * The ImageProcessorModel class manages a collection of images and provides various image
 * processing operations.
 */
public class ImageProcessorModel implements
    ImageProcessorModelInterface {

  private final Map<String, ImageInterface> images;

  /**
   * Constructs a new ImageProcessorModel with an empty image map.
   */
  public ImageProcessorModel() {
    this.images = new HashMap<>();
  }

  /**
   * Applies the blur filter to the specified image and stores the result with the given destination
   * name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   * @param operator      The blur filter operator.
   */
  @Override
  public void blurImage(String imageName, String destImageName, Object operator) {
    checkImageNames(Collections.singletonList(imageName));
    checkDestinationImageNames(Collections.singletonList(destImageName));
    ImageInterface newImage = new BlurFilter().apply(images.get(imageName), operator);
    images.put(destImageName, newImage);
  }

  /**
   * Applies the blur filter to the specified image and stores the result with the given destination
   * name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   */
  @Override
  public void blurImage(String imageName, String destImageName) {
    checkImageNames(Collections.singletonList(imageName));
    checkDestinationImageNames(Collections.singletonList(destImageName));
    ImageInterface newImage = new BlurFilter().apply(images.get(imageName));
    images.put(destImageName, newImage);
  }

  /**
   * Applies the sharpen filter to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   * @param operator      The sharpen filter operator.
   */
  @Override
  public void sharpenImage(String imageName, String destImageName, Object operator) {
    checkImageNames(Collections.singletonList(imageName));
    checkDestinationImageNames(Collections.singletonList(destImageName));
    ImageInterface newImage = new SharpenFilter().apply(images.get(imageName), operator);
    images.put(destImageName, newImage);
  }

  /**
   * Applies the sharpen filter to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   */
  @Override
  public void sharpenImage(String imageName, String destImageName) {
    checkImageNames(Collections.singletonList(imageName));
    checkDestinationImageNames(Collections.singletonList(destImageName));
    ImageInterface newImage = new SharpenFilter().apply(images.get(imageName));
    images.put(destImageName, newImage);
  }

  /**
   * Applies a horizontal flip operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   */
  @Override
  public void horizontalFlipImage(String imageName, String destImageName) {
    checkImageNames(Collections.singletonList(imageName));
    checkDestinationImageNames(Collections.singletonList(destImageName));
    ImageInterface newImage = new HorizontalFlipOperation().apply(images.get(imageName));
    images.put(destImageName, newImage);
  }

  /**
   * Applies a vertical flip operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   */
  @Override
  public void verticalFlipImage(String imageName, String destImageName) {
    checkImageNames(Collections.singletonList(imageName));
    checkDestinationImageNames(Collections.singletonList(destImageName));
    ImageInterface newImage = new VerticalFlipOperation().apply(images.get(imageName));
    images.put(destImageName, newImage);
  }

  /**
   * Applies a grayscale operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   * @param operator      The grayscale operation operator.
   */
  @Override
  public void greyScaleImage(String imageName, String destImageName, Object operator) {
    checkImageNames(Collections.singletonList(imageName));
    checkDestinationImageNames(Collections.singletonList(destImageName));
    ImageInterface newImage = new Greyscale().apply(images.get(imageName), operator);
    images.put(destImageName, newImage);
  }

  /**
   * Applies a grayscale operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   */
  @Override
  public void greyScaleImage(String imageName, String destImageName) {
    checkImageNames(Collections.singletonList(imageName));
    checkDestinationImageNames(Collections.singletonList(destImageName));
    ImageInterface newImage = new Greyscale().apply(images.get(imageName));
    images.put(destImageName, newImage);
  }

  /**
   * Applies a luma operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   */
  @Override
  public void lumaImage(String imageName, String destImageName) {
    checkImageNames(Collections.singletonList(imageName));
    checkDestinationImageNames(Collections.singletonList(destImageName));
    ImageInterface newImage = new Greyscale().apply(images.get(imageName));
    images.put(destImageName, newImage);
  }

  /**
   * Applies a sepia operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   * @param operator      The sepia operation operator.
   */
  @Override
  public void sepiaImage(String imageName, String destImageName, Object operator) {
    checkImageNames(Collections.singletonList(imageName));
    checkDestinationImageNames(Collections.singletonList(destImageName));
    ImageInterface newImage = new Sepia().apply(images.get(imageName), operator);
    images.put(destImageName, newImage);
  }

  /**
   * Applies a sepia operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   */
  @Override
  public void sepiaImage(String imageName, String destImageName) {
    checkImageNames(Collections.singletonList(imageName));
    checkDestinationImageNames(Collections.singletonList(destImageName));
    ImageInterface newImage = new Sepia().apply(images.get(imageName));
    images.put(destImageName, newImage);
  }

  /**
   * Applies a split image operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   * @param operator      The split image operation operator.
   */
  @Override
  public void splitImage(String imageName, String destImageName, Object operator) {
    checkImageNames(Collections.singletonList(imageName));
    checkDestinationImageNames(Collections.singletonList(destImageName));
    ImageInterface newImage = new SplitImageOperation().apply(images.get(imageName), operator);
    images.put(destImageName, newImage);
  }

  /**
   * Applies an RGB split image operation to the specified image and stores the results with the
   * given destination names.
   *
   * @param imageName      The name of the source image.
   * @param destImageNames The names for the destination images.
   */
  @Override
  public void rgbSplitImage(String imageName, List<String> destImageNames) {
    checkImageNames(Collections.singletonList(imageName));
    checkDestinationImageNames(destImageNames);
    for (int i = 0; i < destImageNames.size(); i++) {
      ImageInterface newImage = new SplitImageOperation().apply(images.get(imageName), i);
      images.put(destImageNames.get(i), newImage);
    }
  }

  /**
   * Applies a brightness operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   * @param operator      The brightness operation operator.
   */
  @Override
  public void brightenImage(String imageName, String destImageName, Object operator) {
    checkImageNames(Collections.singletonList(imageName));
    checkDestinationImageNames(Collections.singletonList(destImageName));
    ImageInterface newImage = new BrightnessOperation().apply(images.get(imageName), operator);
    images.put(destImageName, newImage);
  }

  /**
   * Applies a compression operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   * @param operator      The compression operation operator.
   */

  @Override
  public void compressImage(String imageName, String destImageName, Object operator) {
    checkImageNames(Collections.singletonList(imageName));
    checkDestinationImageNames(Collections.singletonList(destImageName));
    ImageInterface newImage = new CompressionOperation().apply(images.get(imageName), operator);
    images.put(destImageName, newImage);
  }


  /**
   * Applies a value operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   */
  @Override
  public void valueImage(String imageName, String destImageName) {
    checkImageNames(Collections.singletonList(imageName));
    checkDestinationImageNames(Collections.singletonList(destImageName));
    ImageInterface newImage = new Value().apply(images.get(imageName));
    images.put(destImageName, newImage);
  }

  /**
   * Applies an intensity operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   */
  @Override
  public void intensityImage(String imageName, String destImageName) {
    checkImageNames(Collections.singletonList(imageName));
    checkDestinationImageNames(Collections.singletonList(destImageName));
    ImageInterface newImage = new Intensity().apply(images.get(imageName));
    images.put(destImageName, newImage);
  }

  /**
   * Merges a list of images into a single image and stores the result with the given destination
   * name.
   *
   * @param imagesToMergeName The names of the images to merge.
   * @param destImageName     The name for the destination image.
   */
  @Override
  public void mergeImage(List<String> imagesToMergeName, String destImageName) {
    checkImageNames(imagesToMergeName);
    checkDestinationImageNames(Collections.singletonList(destImageName));
    List<ImageInterface> imageToMerge = new ArrayList<>();
    imagesToMergeName.forEach(a -> imageToMerge.add(images.get(a)));
    ImageInterface newImage = new MergeSingleChannelImages().apply(imageToMerge);
    images.put(destImageName, newImage);
  }

  /**
   * Applies a histogram visualization operation to the specified image and stores the result with
   * the given destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   */
  @Override
  public void histogramImage(String imageName, String destImageName) {
    checkImageNames(Collections.singletonList(imageName));
    checkDestinationImageNames(Collections.singletonList(destImageName));
    ImageInterface newImage = new HistogramVisualization().apply(images.get(imageName));
    images.put(destImageName, newImage);
  }

  /**
   * Applies a level adjustment operation to the specified image and stores the result with the
   * given destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   * @param operator      The level adjustment operation operator.
   */
  @Override
  public void levelAdjustImage(String imageName, String destImageName, Object operator) {
    checkImageNames(Collections.singletonList(imageName));
    checkDestinationImageNames(Collections.singletonList(destImageName));
    ImageInterface newImage = new LevelAdjustment().apply(images.get(imageName), operator);
    images.put(destImageName, newImage);
  }

  /**
   * Applies a color correction operation to the specified image and stores the result with the
   * given destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   * @param operator      The color correction operation operator.
   */
  @Override
  public void colorCorrectImage(String imageName, String destImageName, Object operator) {
    checkImageNames(Collections.singletonList(imageName));
    checkDestinationImageNames(Collections.singletonList(destImageName));
    ImageInterface newImage = new ColorCorrection().apply(images.get(imageName), operator);
    images.put(destImageName, newImage);
  }

  /**
   * Applies a color correction operation to the specified image and stores the result with the
   * given destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   */
  @Override
  public void colorCorrectImage(String imageName, String destImageName) {
    checkImageNames(Collections.singletonList(imageName));
    checkDestinationImageNames(Collections.singletonList(destImageName));
    ImageInterface newImage = new ColorCorrection().apply(images.get(imageName));
    images.put(destImageName, newImage);
  }

  @Override
  public void ditherImage(String imageName, String destImageName) {
    checkImageNames(Collections.singletonList(imageName));
    checkDestinationImageNames(Collections.singletonList(destImageName));
    ImageInterface newImage = new Dither().apply(images.get(imageName));
    images.put(destImageName, newImage);
  }

  @Override
  public void ditherImage(String imageName, String destImageName, Object operator) {
    checkImageNames(Collections.singletonList(imageName));
    checkDestinationImageNames(Collections.singletonList(destImageName));
    ImageInterface newImage = new Dither().apply(images.get(imageName), operator);
    images.put(destImageName, newImage);

  }

  /**
   * Loads an image from the specified file path and stores it with the given destination name.
   *
   * @param imagePath     The file path of the image to be loaded.
   * @param destImageName The name for the destination image.
   * @throws IOException If an error occurs during image loading.
   */
  @Override
  public void loadImage(String imagePath, String destImageName) throws IOException {
    checkDestinationImageNames(Collections.singletonList(destImageName));
    images.put(destImageName, IOFileFactory.decodeImage(imagePath));
  }

  /**
   * Saves the image with the specified name to the specified file path.
   *
   * @param imagePath The file path where the image will be saved.
   * @param imageName The name of the image to be saved.
   * @throws IOException If an error occurs during image saving.
   */
  @Override
  public void saveImage(String imagePath, String imageName) throws IOException {
    checkImageNames(Collections.singletonList(imageName));
    IOFileFactory.encodeAndSaveImage(imagePath, images.get(imageName));
  }

  /**
   * Retrieves the image with the specified name.
   *
   * @param imageName The name of the image to retrieve.
   * @return The ImageInterface associated with the given name.
   */
  @Override
  public ImageInterface getImage(String imageName) {
    return this.images.get(imageName);
  }

  private void checkImageNames(List<String> imageNames) {
    for (String imageName : imageNames) {
      if (!images.containsKey(imageName)) {
        throw new IllegalArgumentException(
            "Invalid request : No image exist with the name " + imageName);
      }
    }
  }

  private void checkDestinationImageNames(List<String> imageNames) {
    for (String imageName : imageNames) {
      if (images.containsKey(imageName)) {
        throw new IllegalArgumentException(
            "Invalid request : An Image exist with the name " + imageName);
      }
    }
  }

}
