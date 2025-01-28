package model.operations.rotation;

import java.util.ArrayList;
import java.util.List;
import model.image.ImageFactory;
import model.image.ImageInterface;
import model.operations.operationinterfaces.SingleImageProcessor;

/**
 * This class represents a horizontal flip operation on an image.
 */
public class HorizontalFlipOperation implements SingleImageProcessor {

  /**
   * This method applies the horizontal flip operation on the given image.
   *
   * @param image the image to be flipped
   * @return the flipped image
   */
  public ImageInterface apply(ImageInterface image) {
    int height = image.getHeight();
    int width = image.getWidth();
    List<int[][]> imageChannel = image.getChannel();
    List<int[][]> flippedChannel = new ArrayList<>();

    for (int[][] channel : imageChannel) {
      int[][] flippedPixels = new int[height][width];

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          flippedPixels[i][j] = channel[i][width - 1 - j];
        }
      }

      flippedChannel.add(flippedPixels);
    }

    return ImageFactory.createImage(flippedChannel);
  }


}
