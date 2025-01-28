package model.operations.filters;

/**
 * Represents a blur filter operation object.
 */
public class BlurFilter extends CommonFilterOperationExtention {

  double[][] gaussianFilter;

  /**
   * Constructs a blur filter object with a default Gaussian filter.
   */
  public BlurFilter() {
    this.gaussianFilter = new double[][]{
        {0.0625, 0.125, 0.0625},
        {0.125, 0.25, 0.125},
        {0.0625, 0.125, 0.0625}
    };
  }

  /**
   * Get the Gaussian filter used for blurring.
   *
   * @return a 2D array representing the Gaussian filter
   */
  @Override
  public double[][] getFilter() {
    return gaussianFilter;
  }
}

