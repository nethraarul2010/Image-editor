package model.operations.filters;

/**
 * Represents a sharpen filter operation object.
 */
public class SharpenFilter extends CommonFilterOperationExtention {

  double[][] sharpenFilter;

  /**
   * Constructs a sharpen filter object.
   */
  public SharpenFilter() {
    this.sharpenFilter = new double[][]{
        {-0.125, -0.125, -0.125, -0.125, -0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, 0.25, 1, 0.25, -0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, -0.125, -0.125, -0.125, -0.125}
    };
  }

  /**
   * Returns the sharpen filter.
   *
   * @return the sharpen filter
   */
  @Override
  public double[][] getFilter() {
    return sharpenFilter;
  }
}
