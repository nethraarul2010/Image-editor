package model.operations.operationinterfaces;

import java.util.List;
import model.image.ImageInterface;

/**
 * This interface represents a multiple to single image processor.
 */
public interface MultipleToSingleImageProcessorWithOffset {

  /**
   * This method applies the operation on the given images and returns single image.
   *
   * @param images the images to be combined
   * @return new processed image
   * @throws IllegalArgumentException if the process not possible
   */
  ImageInterface apply(List<ImageInterface> images, Object operator)
      throws IllegalArgumentException;
}
