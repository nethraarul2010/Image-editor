package model.operations.colortransformation;

import java.util.List;
import model.image.ImageInterface;
import model.operations.operationinterfaces.SingleImageProcessorWithOffset;
import model.operations.split.PartialImageOperation;

/**
 * This interface represents a color transformation operation on an image.
 */
public abstract class CommonColorTransformOperationExtention extends
    CommonColorTransformOperation implements SingleImageProcessorWithOffset {

  /**
   * This method applies the color transformation operation on the given image.
   *
   * @param image    the image to be transformed
   * @param operator the operator object for the filter
   * @return the transformed image
   * @throws IllegalArgumentException if the image doesn't have 3 channels
   */
  @Override
  public ImageInterface apply(ImageInterface image, Object operator)
      throws IllegalArgumentException {
    ImageInterface newImage = super.apply(image);
    return new PartialImageOperation()
        .apply(List.of(image, newImage), operator);
  }

}
