package model.operations.visualization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import model.image.ImageFactory;
import model.image.ImageInterface;
import model.operations.operationinterfaces.SingleImageProcessorWithOffset;
import model.operations.split.PartialImageOperation;

/**
 * This class represents a level adjustment operation on an image.
 */
public class LevelAdjustment implements SingleImageProcessorWithOffset {

  /**
   * This method applies a level adjustment operation on the image.
   *
   * @param image    the image to be operated on
   * @param operator the operator to be applied
   * @return new image with level adjustment
   * @throws IllegalArgumentException if the operator is less than 3 and not in ascending order
   */
  @Override
  public ImageInterface apply(ImageInterface image, Object operator)
      throws IllegalArgumentException {
    List<Integer> levelAdjustmentParameters = new ArrayList<>();
    try {
      levelAdjustmentParameters =
          Arrays.stream(operator.toString().trim().split("\\s+"))
              .map(Integer::parseInt).collect(Collectors.toList());
    } catch (Exception e) {
      throw new IllegalArgumentException("B M W values should be integer value");
    }
    String splitOperation;
    if (levelAdjustmentParameters.size() != 4 && levelAdjustmentParameters.size() != 3) {
      throw new IllegalArgumentException("Invalid number of arguments");
    } else if (levelAdjustmentParameters.size() == 4) {
      splitOperation = operator.toString().split("\\s+")[3];
    } else {
      splitOperation = "100";
    }
    if (levelAdjustmentParameters.get(2) < 0 || levelAdjustmentParameters.get(2) > 255
        || levelAdjustmentParameters.get(0) < 0 || levelAdjustmentParameters.get(0) > 255
        || levelAdjustmentParameters.get(1) < 0 || levelAdjustmentParameters.get(1) > 255) {
      throw new IllegalArgumentException("Invalid value for constant");
    }

    if (!(levelAdjustmentParameters.get(0) < levelAdjustmentParameters.get(1)
        && levelAdjustmentParameters.get(1) < levelAdjustmentParameters.get(2))) {
      throw new IllegalArgumentException("Invalid ordering of parameters");
    }
    ImageInterface newImage = ImageFactory
        .createImage(createLevelAdjustment(image, levelAdjustmentParameters));
    return new PartialImageOperation()
        .apply(List.of(image, newImage), splitOperation);
  }

  private List<int[][]> createLevelAdjustment(ImageInterface image,
      List<Integer> levelAdjustmentParameters) {
    // b is black
    // m is middle
    // w is white
    int b = levelAdjustmentParameters.get(0);
    int m = levelAdjustmentParameters.get(1);
    int w = levelAdjustmentParameters.get(2);
    double commonDivisorForAdjustmentParameter = (b * b * (m - w)) - b * ((m * m) - (w * w))
        + w * m * m
        - m * w * w;
    double levelAdjustmentForQuadratic = -b * (128 - 255)
        + 128 * w
        - 255 * m;
    double levelAdjustmentForLinearity = b * b * (128 - 255)
        + 255 * m * m
        - 128 * w * w;
    double levelAdjustmentForConstant = b * b
        * ((255 * m) - (128 * w))
        - b * (255 * m * m
        - 128 * w * w);
    double parameterForLinearity = (levelAdjustmentForLinearity
        / commonDivisorForAdjustmentParameter);
    double parameterForQuadratic = (levelAdjustmentForQuadratic
        / commonDivisorForAdjustmentParameter);
    double parameterForConstant = (levelAdjustmentForConstant
        / commonDivisorForAdjustmentParameter);
    List<int[][]> channels = new ArrayList<>();
    for (int[][] channel : image.getChannel()) {
      channels.add(createLevelAdjustmenPerChannel(channel,
          parameterForQuadratic, parameterForLinearity,
          parameterForConstant));
    }
    return channels;
  }

  private int[][] createLevelAdjustmenPerChannel(int[][] channel,
      double parameterForQuadratic,
      double parameterForLinearity,
      double parameterForConstant) {
    int[][] newArr = new int[channel.length][channel[0].length];
    for (int i = 0; i < channel.length; i++) {
      for (int j = 0; j < channel[0].length; j++) {
        int pixel = channel[i][j];
        newArr[i][j] = clamp((int) (parameterForQuadratic * pixel * pixel
            + parameterForLinearity * pixel
            + parameterForConstant));
      }
    }
    return newArr;
  }

  private int clamp(int pixel) {
    return Math.min(255, Math.max(0, pixel));
  }
}
