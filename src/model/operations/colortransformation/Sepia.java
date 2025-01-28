package model.operations.colortransformation;

/**
 * This class represents a sepia color transformation operation that can be applied to an image.
 */
public class Sepia extends CommonColorTransformOperationExtention {
  private final double[][] sepiamatrix;

  /**
   * Constructs a sepia color transformation object.
   */
  public Sepia() {
    this.sepiamatrix = new double[][]{{0.393, 0.769, 0.189},
                                      {0.349, 0.686, 0.168},
                                      {0.272, 0.534, 0.131}};
  }

  /**
   * Returns the transformation coefficients used for the Sepia operation.
   *
   * @return a 2D array of transformation coefficients
   */
  @Override
  public double[][] getTransformCoefficient() {
    return sepiamatrix;
  }
}
