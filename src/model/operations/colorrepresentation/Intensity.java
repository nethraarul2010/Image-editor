package model.operations.colorrepresentation;

import model.image.ImageInterface;

/**
 * This class represents the intensity operation on an image.
 */
public class Intensity extends AbstractColorRepresentation {

  /**
   * Constructs an intensity color representation object which is the average value of the three
   * components for each pixel.
   */
  @Override
  public void performOperation(ImageInterface image,
      int[][] valueChannel, int i, int j) {
    int value = 0;
    for (int[][] channel : image.getChannel()) {
      value += channel[i][j];
    }
    valueChannel[i][j] = value / (image.getChannel().size());
  }
}
