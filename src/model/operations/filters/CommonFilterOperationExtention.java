package model.operations.filters;

import java.util.List;
import model.image.ImageInterface;
import model.operations.operationinterfaces.SingleImageProcessorWithOffset;
import model.operations.split.PartialImageOperation;

/**
 * This class represents a common filter operation that implements the FilterOperation interface. It
 * contains a method that applies a filter to an image.
 */
public abstract class CommonFilterOperationExtention extends CommonFilterOperation implements
    SingleImageProcessorWithOffset {

  /**
   * This method applies a filter to an image.
   *
   * @param image    the image to be filtered
   * @param operator the operator object for the filter
   * @return the filtered image
   * @throws IllegalArgumentException if the kernel is larger than the image
   */
  @Override
  public ImageInterface apply(ImageInterface image, Object operator)
      throws IllegalArgumentException {
    ImageInterface newImage = super.apply(image);
    return new PartialImageOperation()
        .apply(List.of(image, newImage), operator);
  }
}
