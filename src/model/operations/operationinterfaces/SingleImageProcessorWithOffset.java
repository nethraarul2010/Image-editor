package model.operations.operationinterfaces;

import model.image.ImageInterface;

/**
 * This interface represents a single image processor with offset.
 */
public interface SingleImageProcessorWithOffset {

  /**
   * This method applies the operation on the given image and returns the processed image.
   *
   * @param image    the image to be processed
   * @param operator the operator to be applied
   * @return new processed image
   * @throws IllegalArgumentException if the process not possible
   */
  ImageInterface apply(ImageInterface image,
      Object operator) throws IllegalArgumentException;
}
