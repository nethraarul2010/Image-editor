package model.operations.visualization;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.image.ImageFactory;
import model.image.ImageInterface;
import model.operations.operationinterfaces.SingleImageProcessor;

/**
 * This class represents a histogram visualization operation on an image.
 */
public class HistogramVisualization implements SingleImageProcessor {

  private static final double MAX_COLOR = 255.0;
  private static final int MAX_SIZE = 256;

  /**
   * Helps visualize image with an histogram with intensity in red green blue.
   *
   * @param image the image to be processed
   * @return a histogram image
   */
  @Override
  public ImageInterface apply(ImageInterface image) {
    List<int[]> frequencyChannels = VisualizeImageUtil.calculateFrequencyPerChannel(image);
    int maxFrequencyValue = findMaxFrequency(frequencyChannels);
    List<int[][]> processedNormalizedChannels = new ArrayList<>();
    // Normalize the frequencies and create the histogram
    for (int x = 0; x < image.getChannel().size(); x++) {
      int[] normalizedFrequencyChannel = normalizeFrequency(frequencyChannels.get(x),
          maxFrequencyValue);
      processedNormalizedChannels.add(processNormalizedImages(normalizedFrequencyChannel));
    }
    // Delete common pixels in the histogram across multiple channels to avoid overlapping
    postProcessingOfChannels(processedNormalizedChannels);
    return ImageFactory.createImage(processedNormalizedChannels);
  }

  /**
   * This method finds the peak of the histogram.
   *
   * @param frequencies frequency channels
   * @return the peak of the histogram
   */
  private int findMaxFrequency(List<int[]> frequencies) {
    int maxFrequencyValue = 0;
    for (int[] frequencyPerChannel : frequencies) {
      int localMax = Arrays.stream(frequencyPerChannel).max().getAsInt();
      if (localMax > maxFrequencyValue) {
        maxFrequencyValue = localMax;
      }
    }
    return maxFrequencyValue;
  }

  private int[] normalizeFrequency(int[] frequencies, int maxFrequencyValue) {
    int[] normalizedFrequencies = new int[frequencies.length];
    for (int i = 0; i < frequencies.length; i++) {
      normalizedFrequencies[i] = (int) (frequencies[i] * MAX_COLOR / maxFrequencyValue);
    }
    return normalizedFrequencies;
  }

  private void postProcessingOfChannels(List<int[][]> processedNormalizedChannels) {
    for (int i = 0; i < processedNormalizedChannels.get(0).length; i++) {
      for (int j = 0; j < processedNormalizedChannels.get(0)[0].length; j++) {
        int pixelRed = processedNormalizedChannels.get(0)[i][j];
        int pixelGreen = processedNormalizedChannels.get(1)[i][j];
        int pixelBlue = processedNormalizedChannels.get(2)[i][j];

        if (pixelRed == pixelGreen && pixelGreen != pixelBlue) {
          processedNormalizedChannels.get(0)[i][j] = 0;
        } else if (pixelRed == pixelBlue && pixelBlue != pixelGreen) {
          processedNormalizedChannels.get(0)[i][j] = 0;
        } else if (pixelGreen == pixelBlue && pixelBlue != pixelRed) {
          processedNormalizedChannels.get(1)[i][j] = 0;
        } else if (pixelRed == pixelGreen) {
          processedNormalizedChannels.get(0)[i][j] = 0;
          processedNormalizedChannels.get(1)[i][j] = 0;
        }
      }
    }
  }

  private int[][] processNormalizedImages(int[] normalizedFrequencyChannel) {
    BufferedImage bufferedImage = createBufferHistogram(normalizedFrequencyChannel);
    return convertToImageFormat(bufferedImage);
  }

  private int[][] convertToImageFormat(BufferedImage bufferedImage) {
    int[][] pixels = new int[MAX_SIZE][MAX_SIZE];
    for (int i = 0; i < MAX_SIZE; i++) {
      for (int j = 0; j < MAX_SIZE; j++) {
        pixels[i][j] = bufferedImage.getRGB(j, i) & 0xFF;
      }
    }
    return pixels;
  }

  private BufferedImage createBufferHistogram(int[] normalizedFrequencyChannel) {
    BufferedImage bufferedImage = new BufferedImage(MAX_SIZE, MAX_SIZE,
        BufferedImage.TYPE_BYTE_GRAY);
    Graphics2D g2d = bufferedImage.createGraphics();
    g2d.translate(0, MAX_SIZE);
    g2d.scale(1.0, -1.0);
    g2d.setBackground(Color.BLACK);
    g2d.setColor(Color.WHITE);
    for (int j = 0; j < normalizedFrequencyChannel.length - 1; j++) {
      g2d.drawLine(j, normalizedFrequencyChannel[j], j + 1, normalizedFrequencyChannel[j + 1]);
    }
    return bufferedImage;
  }
}
