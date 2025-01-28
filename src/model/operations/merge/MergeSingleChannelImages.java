package model.operations.merge;

import java.util.ArrayList;
import java.util.List;
import model.image.ImageFactory;
import model.image.ImageInterface;
import model.operations.operationinterfaces.MultipleToSingleImageProcessor;

/**
 * This class merges single channel images to a new image.
 */
public class MergeSingleChannelImages implements MultipleToSingleImageProcessor {

  /**
   * This method applies the merge operation on the given image.
   *
   * @param images the images to be merged
   * @return the merged image
   * @throws IllegalArgumentException if image has single channel, has different height or width
   */
  @Override
  public ImageInterface apply(List<ImageInterface> images)
      throws IllegalArgumentException {
    int previousHeight = -1;
    int previousWidth = -1;
    validateImages(images);
    List<int[][]> imageChannel = new ArrayList<>();
    int imageNumber = 0;
    for (ImageInterface image : images) {
      if (previousHeight != -1 && image.getHeight() != previousHeight) {
        throw new IllegalArgumentException("Images should have the same height");
      }
      if (previousWidth != -1 && image.getWidth() != previousWidth) {
        throw new IllegalArgumentException("Images should have the same width");
      }
      if (images.get(0).getChannel().size() == 1) {
        imageChannel.add(image.getChannel().get(0));
      } else {
        imageChannel.add(image.getChannel().get(imageNumber));
      }
      previousHeight = image.getHeight();
      previousWidth = image.getWidth();
      imageNumber++;
    }
    return ImageFactory.createImage(imageChannel);
  }

  /**
   * This method validates the images.
   *
   * @param images the images to be validated
   * @throws IllegalArgumentException if the images are null or less than 2 images
   */
  private void validateImages(List<ImageInterface> images) {
    if (images == null) {
      throw new IllegalArgumentException("Images cannot be null");
    }
    if (images.size() < 2) {
      throw new IllegalArgumentException("Images should have at least 2 images");
    }
  }
}
