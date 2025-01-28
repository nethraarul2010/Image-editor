package model.operations.colortransformation;

/**
 * This class represents a greyscale color transformation operation that can be applied to an image.
 */
public class Greyscale extends CommonColorTransformOperationExtention {
  private final double[][] greymatrix;

  /**
   * Constructs a greyscale color transformation object.
   */
  public Greyscale() {
    this.greymatrix = new double[][]{{0.2126, 0.7152, 0.0722},
                                     {0.2126, 0.7152, 0.0722},
                                     {0.2126, 0.7152, 0.0722}};
  }

  /**
   * Returns the transformation coefficients used for the greyscale operation.
   *
   * @return a 2D array of transformation coefficients
   */
  @Override
  public double[][] getTransformCoefficient() {
    return greymatrix;
  }
}
