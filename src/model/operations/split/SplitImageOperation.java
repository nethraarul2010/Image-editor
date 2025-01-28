package model.operations.split;

import java.util.ArrayList;
import java.util.List;
import model.image.ImageFactory;
import model.image.ImageInterface;
import model.operations.operationinterfaces.SingleImageProcessorWithOffset;

/**
 * This class represents a split image operation.
 */
public class SplitImageOperation implements SingleImageProcessorWithOffset {

  /**
   * This method splits the image on the given channel (all other channels are zero) and returns the
   * processed image.
   *
   * @param image    the image to be processed
   * @param operator the operator to be applied
   * @return new processed image
   * @throws IllegalArgumentException if the process not possible
   */
  @Override
  public ImageInterface apply(ImageInterface image, Object operator)
      throws IllegalArgumentException {
    String[] operators = operator.toString().split(" ");
    String splitPercent = "100";
    if (operators.length == 2) {
      splitPercent = operators[1];
    }
    List<int[][]> imageChannel = image.getChannel();
    int imageComponentNumber;
    try {
      imageComponentNumber = Integer.parseInt(operators[0]);
    } catch (Exception e) {
      throw new IllegalArgumentException("Percentage should be integer value");
    }
    if (imageChannel.size() == 1) {
      throw new IllegalArgumentException("Image must have more than 1 channel");
    }
    List<int[][]> channelList = new ArrayList<>();
    for (int i = 0; i < imageChannel.size(); i++) {
      if (i == imageComponentNumber) {
        channelList.add(imageChannel.get(i));
      } else {
        channelList.add(new int[image.getHeight()][image.getWidth()]);
      }
    }
    ImageInterface newImage = ImageFactory
        .createImage(channelList);
    return new PartialImageOperation()
        .apply(List.of(image, newImage), splitPercent);
  }
}
