package model.operations.split;

import java.util.List;

import model.image.ImageInterface;
import model.operations.operationinterfaces.MultipleToSingleImageProcessorWithOffset;

import static model.operations.operatorutil.OperatorUtil.castOperatorToDouble;

/**
 * This class represents a partial image operation, where a percentage of the original image is
 * replaced with a modified version.
 */
public class PartialImageOperation implements MultipleToSingleImageProcessorWithOffset {

  /**
   * Applies a partial image operation to the given images.
   *
   * @param images   The list of images, where the first image is the original image, and the second
   *                 image is the modified version.
   * @param operator The operator representing the percentage of the width to be replaced.
   * @return The modified image after applying the partial operation.
   * @throws IllegalArgumentException If the operator is not a valid integer or if the parsed
   *                                  percentage is not within the valid range [0, 100].
   */
  @Override
  public ImageInterface apply(List<ImageInterface> images, Object operator)
          throws IllegalArgumentException {
    int percentage = castOperatorToDouble((String) operator);
    ImageInterface originalImage = images.get(0);
    ImageInterface newImage = images.get(1);
    int height = newImage.getHeight();
    int width = newImage.getWidth();
    int perWidth = width * percentage / 100;

    for (int w = 0; w < newImage.getChannel().size(); w++) {
      int[][] origChannel = originalImage.getChannel().get(w);
      int[][] newChannel = newImage.getChannel().get(w);
      for (int i = 0; i < height; i++) {
        if (width - perWidth >= 0) {
          System.arraycopy(origChannel[i], perWidth, newChannel[i], perWidth, width - perWidth);
        }
      }
    }
    return newImage;

  }
}