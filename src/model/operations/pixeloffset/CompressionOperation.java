package model.operations.pixeloffset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import model.image.ImageFactory;
import model.image.ImageInterface;
import model.operations.operationinterfaces.SingleImageProcessorWithOffset;

/**
 * A class representing a compression operation for image processing.
 */
public class CompressionOperation implements SingleImageProcessorWithOffset {

  /**
   * Applies the compression operation to the specified image.
   *
   * @param image    The source image.
   * @param operator The compression factor as a percentage (0 to 99).
   * @return The processed image.
   * @throws IllegalArgumentException If the compression factor is invalid.
   */
  @Override
  public ImageInterface apply(ImageInterface image, Object operator)
      throws IllegalArgumentException {
    Double compressionFactor;
    try {
      compressionFactor = Double.parseDouble((String) operator);
    } catch (Exception e) {
      throw new IllegalArgumentException("Percentage should be double value");
    }
    if (compressionFactor < 0 || compressionFactor > 99) {
      throw new IllegalArgumentException("Percentage should be between 0 and 99");
    }
    List<double[][]> paddedChannel = getPaddedImage(image.getChannel());
    for (double[][] channel : paddedChannel) {
      transform2D(channel);
    }
    resetValues(paddedChannel, compressionFactor / 100);
    for (double[][] channel : paddedChannel) {
      inverseTransform2D(channel);
    }
    List<int[][]> newChannelList = new ArrayList<>();
    for (double[][] channel : paddedChannel) {
      int[][] newChannel = new int[image.getHeight()][image.getWidth()];
      for (int i = 0; i < image.getHeight(); i++) {
        for (int j = 0; j < image.getWidth(); j++) {
          newChannel[i][j] = (int) Math
              .round(channel[i][j] < 0.0 ? 0 : Math.min(channel[i][j], 255.0));
        }
      }
      newChannelList.add(newChannel);
    }
    return ImageFactory.createImage(newChannelList);
  }

  private double[] inverseTransform1D(double[] s, int len) {
    List<Double> avgList = new ArrayList<>();
    List<Double> diffList = new ArrayList<>();
    for (int i = 0; i < len / 2; i++) {
      Double a = s[i];
      Double b = s[i + len / 2];
      Double avg = (a + b) / Math.sqrt(2);
      Double diff = (a - b) / Math.sqrt(2);
      avgList.add(avg);
      diffList.add(diff);
    }
    int index = 0;
    for (int i = 0; i < avgList.size(); i += 1) {
      s[index++] = avgList.get(i);
      s[index++] = diffList.get(i);
    }
    return s;
  }

  private double[] transform1D(double[] s, int len) {
    List<Double> avgList = new ArrayList<>();
    List<Double> diffList = new ArrayList<>();
    for (int i = 0; i < len - 1; i += 2) {
      Double a = s[i];
      Double b = s[i + 1];
      Double avg = (a + b) / Math.sqrt(2);
      Double diff = (a - b) / Math.sqrt(2);
      avgList.add(avg);
      diffList.add(diff);
    }
    int index = 0;
    for (Double value : avgList) {
      s[index] = value;
      index++;
    }
    for (Double aDouble : diffList) {
      s[index] = aDouble;
      index++;
    }
    return s;
  }


  private double[][] transform2D(double[][] matrix) {
    int len = matrix.length;
    int c = len;
    while (c > 1) {
      for (int i = 0; i < c; i++) {
        matrix[i] = transform1D(matrix[i], c);
      }
      for (int j = 0; j < c / 2; j++) {
        double[] column = new double[len];
        for (int i = 0; i < len; i++) {
          column[i] = matrix[i][j];
        }
        transform1D(column, c);
        for (int i = 0; i < len; i++) {
          matrix[i][j] = column[i];
        }
      }
      c = c / 2;
    }
    return matrix;
  }

  private double[][] inverseTransform2D(double[][] matrix) {
    int len = matrix.length;
    int c = 2;
    while (c <= len) {
      for (int j = 0; j < c / 2; j++) {
        double[] column = new double[len];
        for (int i = 0; i < len; i++) {
          column[i] = matrix[i][j];
        }
        inverseTransform1D(column, c);
        for (int i = 0; i < len; i++) {
          matrix[i][j] = column[i];
        }
      }
      for (int i = 0; i < c; i++) {
        matrix[i] = inverseTransform1D(matrix[i], c);
      }
      c = c * 2;
    }
    return matrix;
  }

  private List<double[][]> getPaddedImage(List<int[][]> channels) {
    List<double[][]> paddedList = new ArrayList<>();
    int height = channels.get(0).length;
    int width = channels.get(0)[0].length;
    int pow = 2;
    while (pow < height || pow < width) {
      pow = pow * 2;
    }
    for (int[][] channel : channels) {
      double[][] paddedMatrix = new double[pow][pow];
      for (int j = 0; j < height; j++) {
        for (int w = 0; w < width; w++) {
          paddedMatrix[j][w] = channel[j][w];
        }
      }
      paddedList.add(paddedMatrix);
    }
    return paddedList;
  }

  private void resetValues(List<double[][]> channels, Double factor) {
    Set<Double> uniqueValues = new TreeSet<>(Double::compareTo);
    for (double[][] channel : channels) {
      for (double[] row : channel) {
        Arrays.stream(row).map(Math::abs).forEach(uniqueValues::add);
      }
    }
    int thresholdIndex = Math
        .min(uniqueValues.size() - 1, (int) Math.abs(factor * uniqueValues.size()));
    List<Double> uniqueList = new ArrayList<>(uniqueValues);
    Double value = uniqueList.get(thresholdIndex);
    for (double[][] channel : channels) {
      for (int i = 0; i < channel.length; i++) {
        for (int j = 0; j < channel[0].length; j++) {
          if (Math.abs(channel[i][j]) < value) {
            channel[i][j] = 0;
          }
        }
      }
    }
  }


}