package model.operations.pixeloffset;

import java.util.ArrayList;
import java.util.List;
import model.image.ImageFactory;
import model.image.ImageInterface;
import model.operations.operationinterfaces.SingleImageProcessorWithOffset;

/**
 * This class represents a simple offset operation of brightening on image applied on every pixel.
 */
public class BrightnessOperation implements SingleImageProcessorWithOffset {

  int maxValue = 255;

  /**
   * This method applies a simple offset operation on the image.
   *
   * @param image    the image to be operated on
   * @param operator the operator to be applied
   * @return new image with offset
   * @throws IllegalArgumentException if the operator is not integer
   */
  @Override
  public ImageInterface apply(ImageInterface image,
      Object operator) throws IllegalArgumentException {
    int factor = Integer.parseInt(operator.toString());
    int height = image.getHeight();
    int width = image.getWidth();
    List<int[][]> imageChannel = image.getChannel();
    List<int[][]> newChannel = new ArrayList<>();
    for (int[][] channel : imageChannel) {
      int[][] newPixels = new int[height][width];
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          newPixels[i][j] = Math.min(channel[i][j] + factor, maxValue);
          newPixels[i][j] = Math.max(newPixels[i][j], 0);
        }
      }
      newChannel.add(newPixels);
    }
    return ImageFactory.createImage(newChannel);
  }
}
