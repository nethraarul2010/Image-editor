package model.operations.colorrepresentation;

import model.image.ImageInterface;

/**
 * This class represents a color representation operation that can be applied to an image.
 */
public class Value extends AbstractColorRepresentation {

  /**
   * Constructs a value color representation object which is the maximum value of the three
   * components for each pixel.
   */
  @Override
  public void performOperation(ImageInterface image,
      int[][] valueChannel, int i, int j) {
    int previousMax = -1;
    for (int[][] channel : image.getChannel()) {
      int value = Math.max(channel[i][j], previousMax);
      if (channel[i][j] > previousMax) {
        previousMax = value;
      }
    }
    valueChannel[i][j] = previousMax;
  }
}
