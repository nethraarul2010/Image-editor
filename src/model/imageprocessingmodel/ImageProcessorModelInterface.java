package model.imageprocessingmodel;

import java.io.IOException;
import java.util.List;
import model.image.ImageInterface;

/**
 * This interface represents an image processor model.
 */
public interface ImageProcessorModelInterface {

  /**
   * Applies a blur filter to the specified image and stores the result with the given destination
   * name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   * @param operator      The blur filter operator.
   */
  void blurImage(String imageName, String destImageName, Object operator);

  /**
   * Applies a blur filter to the specified image and stores the result with the given destination
   * name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   */
  void blurImage(String imageName, String destImageName);

  /**
   * Applies a sharpen filter to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   * @param operator      The sharpen filter operator.
   */
  void sharpenImage(String imageName, String destImageName, Object operator);

  /**
   * Applies a sharpen filter to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   */
  void sharpenImage(String imageName, String destImageName);

  /**
   * Applies a horizontal flip operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   */
  void horizontalFlipImage(String imageName, String destImageName);

  /**
   * Applies a vertical flip operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   */
  void verticalFlipImage(String imageName, String destImageName);

  /**
   * Applies a grayscale operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   * @param operator      The grayscale operation operator.
   */
  void greyScaleImage(String imageName, String destImageName, Object operator);

  /**
   * Applies a grayscale operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   */
  void greyScaleImage(String imageName, String destImageName);

  /**
   * Applies a luma operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.x
   */
  void lumaImage(String imageName, String destImageName);

  /**
   * Applies a sepia operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   * @param operator      The sepia operation operator.
   */
  void sepiaImage(String imageName, String destImageName, Object operator);

  /**
   * Applies a sepia operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   */
  void sepiaImage(String imageName, String destImageName);

  /**
   * Applies a split image operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   * @param operator      The split image operation operator.
   */
  void splitImage(String imageName, String destImageName, Object operator);

  /**
   * Applies an RGB split image operation to the specified image and stores the results with the
   * given destination names.
   *
   * @param imageName      The name of the source image.
   * @param destImageNames The names for the destination images.
   */
  void rgbSplitImage(String imageName, List<String> destImageNames);

  /**
   * Applies a brightness operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   * @param operator      The brightness operation operator.
   */
  void brightenImage(String imageName, String destImageName, Object operator);

  /**
   * Applies a compression operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   * @param operator      The compression operation operator.
   */
  void compressImage(String imageName, String destImageName, Object operator);

  /**
   * Applies a value operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   */
  void valueImage(String imageName, String destImageName);

  /**
   * Applies an intensity operation to the specified image and stores the result with the given
   * destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   */
  void intensityImage(String imageName, String destImageName);

  /**
   * Merges a list of images into a single image and stores the result with the given destination
   * name.
   *
   * @param imagesToMergeName The names of the images to merge.
   * @param destImageName     The name for the destination image.
   */
  void mergeImage(List<String> imagesToMergeName, String destImageName);

  /**
   * Applies a histogram visualization operation to the specified image and stores the result with
   * the given destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   */
  void histogramImage(String imageName, String destImageName);

  /**
   * Applies a level adjustment operation to the specified image and stores the result with the
   * given destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   * @param operator      The level adjustment operation operator.
   */
  void levelAdjustImage(String imageName, String destImageName, Object operator);

  /**
   * Applies a color correction operation to the specified image and stores the result with the
   * given destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   * @param operator      The color correction operation operator.
   */
  void colorCorrectImage(String imageName, String destImageName, Object operator);

  /**
   * Applies a color correction operation to the specified image and stores the result with the
   * given destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   */
  void colorCorrectImage(String imageName, String destImageName);

  /**
   * Loads an image from the specified file path and stores it with the given destination name.
   *
   * @param imagePath     The file path of the image to be loaded.
   * @param destImageName The name for the destination image.
   * @throws IOException If an error occurs during image loading.
   */
  void loadImage(String imagePath, String destImageName) throws IOException;

  /**
   * Saves the image with the specified name to the specified file path.
   *
   * @param imagePath The file path where the image will be saved.
   * @param imageName The name of the image to be saved.
   * @throws IOException If an error occurs during image saving.
   */
  void saveImage(String imagePath, String imageName) throws IOException;

  /**
   * Retrieves the image with the specified name.
   *
   * @param imageName The name of the image to retrieve.
   * @return The ImageInterface associated with the given name.
   */
  ImageInterface getImage(String imageName);

  /**
   * Applies dithering operation to the specified image and stores the result with the
   * given destination name.
   *
   * @param imageName     The name of the source image.
   * @param destImageName The name for the destination image.
   */
  void ditherImage(String imageName, String destImageName);

  /**
   * Applies dithering operation to the specified image  based on the given percentage
   * in the operator field and stores the result with the given destination name.
   *
   * @param imageName The name of the source image.
   * @param destImageName The name for the destination image.
   * @param operator The percentage up to which the dither operation is to be applied.
   */
  void ditherImage(String imageName, String destImageName, Object operator);

}