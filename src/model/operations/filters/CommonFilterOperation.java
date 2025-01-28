package model.operations.filters;

import java.util.ArrayList;
import java.util.List;
import model.image.ImageFactory;
import model.image.ImageInterface;
import model.operations.operationinterfaces.SingleImageProcessor;

/**
 * This class represents a common filter operation that implements the FilterOperation interface. It
 * contains a method that applies a filter to an image.
 */
public abstract class CommonFilterOperation implements SingleImageProcessor {

  /**
   * This method applies a filter to an image.
   *
   * @param image the image to be filtered
   * @return the filtered image
   * @throws IllegalArgumentException if the kernel is larger than the image
   */
  @Override
  public ImageInterface apply(ImageInterface image) {
    double[][] kernel = getFilter();
    int height = image.getHeight();
    int width = image.getWidth();
    ImageInterface newImage = image;
    if (kernel.length > height || kernel[0].length > width) {
      newImage = addPadding(image, kernel.length, kernel[0].length);
    }
    List<int[][]> imageChannel = newImage.getChannel();
    List<int[][]> filteredChannel = new ArrayList<>();

    for (int[][] channel : imageChannel) {
      int[][] filteredPixels = new int[height][width];

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int sum = 0;
          int kernelSize = kernel.length;
          for (int k = 0; k < kernelSize; k++) {
            for (int l = 0; l < kernelSize; l++) {
              int rowIndex = i - kernelSize / 2 + k;
              int colIndex = j - kernelSize / 2 + l;

              if (rowIndex >= 0 && rowIndex < height && colIndex >= 0 && colIndex < width) {
                sum += kernel[k][l] * channel[rowIndex][colIndex];
              }
            }
          }
          sum = Math.min(Math.max(sum, 0), 255);
          filteredPixels[i][j] = sum;
        }
      }

      filteredChannel.add(filteredPixels);
    }

    return ImageFactory.createImage(filteredChannel);
  }

  private ImageInterface addPadding(ImageInterface image,
      int kernalHeight, int kernalWidth) {
    int height = image.getHeight();
    int width = image.getWidth();
    List<int[][]> imageChannel = image.getChannel();
    List<int[][]> paddedChannel = new ArrayList<>();

    for (int[][] channel : imageChannel) {
      int[][] paddedPixels = new int[height + kernalHeight - 1][width + kernalWidth - 1];

      for (int i = 0; i < height; i++) {
        System.arraycopy(channel[i], 0, paddedPixels[i], 0, width);
      }

      paddedChannel.add(paddedPixels);
    }

    return ImageFactory.createImage(paddedChannel);
  }

  /**
   * Filter to be applied to the image is returned by this.
   *
   * @return the filter to be applied to the image
   */
  public abstract double[][] getFilter();
}
