package model.operations.visualization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.image.ImageFactory;
import model.image.ImageInterface;
import model.operations.operationinterfaces.SingleImageProcessor;
import model.operations.operationinterfaces.SingleImageProcessorWithOffset;
import model.operations.split.PartialImageOperation;

/**
 * Class to perform Dither operation on image.
 */
public class Dither implements SingleImageProcessor, SingleImageProcessorWithOffset {

  @Override
  public ImageInterface apply(ImageInterface image) throws IllegalArgumentException {
    String fullImageOperator = "100";
    return getImage(image, fullImageOperator);
  }

  @Override
  public ImageInterface apply(ImageInterface image, Object operator) {
    return getImage(image, operator);
  }
  
  private ImageInterface getImage(ImageInterface image, Object operation) {
    List<int[][]> imageChannel = image.getChannel();
    List<int[][]> originalImg = copy(imageChannel);
    ImageInterface orgImg = ImageFactory.createImage(originalImg);
    int[][] arrayToBeOperatedOn = new int[image.getHeight()][image.getWidth()];

    // Copy values from the blue channel of the original image
    for (int y = 0; y < image.getWidth(); y++) {
      for (int x = 0; x < image.getHeight(); x++) {
        arrayToBeOperatedOn[x][y] = imageChannel.get(2)[x][y];
      }
    }

    int[][] ditheredImage = new int[image.getHeight()][image.getWidth()];

    // Dithering logic
    for (int y = 0; y < image.getWidth(); y++) {
      for (int x = 0; x < image.getHeight(); x++) {
        int oldColor = arrayToBeOperatedOn[x][y];
        int newColor = (oldColor < 128) ? 0 : 255;

        int pixel = newColor;
        ditheredImage[x][y] = pixel;

        int quantError = oldColor - newColor;

        if (x + 1 < image.getHeight()) {
          arrayToBeOperatedOn[x + 1][y] += (quantError * 7) / 16;
        }
        if (x - 1 >= 0 && y + 1 < image.getWidth()) {
          arrayToBeOperatedOn[x - 1][y + 1] += (quantError * 3) / 16;
        }
        if (y + 1 < image.getWidth()) {
          arrayToBeOperatedOn[x][y + 1] += (quantError * 5) / 16;
        }
        if (x + 1 < image.getHeight() && y + 1 < image.getWidth()) {
          arrayToBeOperatedOn[x + 1][y + 1] += (quantError * 1) / 16;
        }
      }
    }
    List<int[][]> temp = Arrays.asList(ditheredImage, ditheredImage, ditheredImage);

    // Create a new image with dithered values only in the blue channel
    List<int[][]> channelList = copy(temp);

    ImageInterface newImage = ImageFactory.createImage(channelList);
    return new PartialImageOperation().apply(List.of(orgImg, newImage), operation);
  }


  private List<int[][]> copy(List<int[][]> value) {
    int width = value.get(0).length;
    int height = value.get(0)[0].length;
    int[][] r = new int[width][height];
    int[][] g = new int[width][height];
    int[][] b = new int[width][height];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        r[x][y] = value.get(0)[x][y];
        g[x][y] = value.get(1)[x][y];
        b[x][y] = value.get(2)[x][y];
      }
    }

    List<int[][]> imgCopy = new ArrayList<>();
    imgCopy.add(r);
    imgCopy.add(g);
    imgCopy.add(b);

    return imgCopy;

  }


}
