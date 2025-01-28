package model.image;

import java.util.List;
import model.image.CommonImage.ImageBuilder;

/**
 * This class represents an image factory.
 */
public class ImageFactory {

  /**
   * Creates an image with the given height, width and channels.
   *
   * @param channelList the channels of the image
   * @return the image
   * @throws IllegalArgumentException if the number of channels is not 1 or 3
   */
  public static ImageInterface createImage(List<int[][]> channelList) {
    if (channelList.size() == 1 || channelList.size() == 3) {
      ImageBuilder imageBuilder = new CommonImage.ImageBuilder();
      for (int[][] ints : channelList) {
        imageBuilder.addChannel(ints);
      }
      return imageBuilder.build();
    } else {
      throw new IllegalArgumentException(channelList.size()
          + " number of channel is not supported");
    }
  }

}
