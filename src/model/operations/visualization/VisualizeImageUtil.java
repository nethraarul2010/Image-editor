package model.operations.visualization;

import java.util.ArrayList;
import java.util.List;
import model.image.ImageInterface;

/**
 * Util class to help perform different conversions for operations related to visualisation.
 */
public class VisualizeImageUtil {

  private static final double MAX_COLOR = 255.0;

  /**
   * This method calculates the frequency of each pixel in each channel of the image.
   *
   * @param image the image to be processed
   * @return the list of frequencies of each pixel in each channel of the image
   * @throws IllegalArgumentException if the image does not have 3 channels
   */
  public static List<int[]> calculateFrequencyPerChannel(ImageInterface image)
      throws IllegalArgumentException {
    if (image.getChannel().size() != 3) {
      throw new IllegalArgumentException("Image must have 3 channels");
    }
    List<int[]> frequencies = new ArrayList<>();
    // Found max element in the channel so that we can normalize the values
    int maxElement = findMaxElementInTheChannel(image.getChannel());
    for (int i = 0; i < image.getChannel().size(); i++) {
      int[][] normalizedChannel = createNormalizedChannel(image.getChannel().get(i),
          maxElement);
      int[] frequenciesInChannel = createFrequencyList(normalizedChannel);
      frequencies.add(frequenciesInChannel);
    }

    return frequencies;
  }

  private static int[] createFrequencyList(int[][] normalizedChannel) {
    int[] frequencies = new int[256];
    for (int i = 0; i < normalizedChannel.length; i++) {
      for (int j = 0; j < normalizedChannel[0].length; j++) {
        frequencies[normalizedChannel[i][j]]++;
      }
    }
    return frequencies;
  }

  private static int[][] createNormalizedChannel(int[][] channel, int maxElement) {
    int[][] normalizedChannel = new int[channel.length][channel[0].length];
    for (int i = 0; i < channel.length; i++) {
      for (int j = 0; j < channel[0].length; j++) {
        normalizedChannel[i][j] = (int) (channel[i][j] * MAX_COLOR / maxElement);
      }
    }
    return normalizedChannel;
  }

  private static int findMaxElementInTheChannel(List<int[][]> channels) {
    int max = Integer.MIN_VALUE;
    for (int[][] channel : channels) {
      for (int[] rows : channel) {
        for (int rowElement : rows) {
          if (rowElement > max) {
            max = rowElement;
          }
        }
      }
    }
    return max;
  }
}
