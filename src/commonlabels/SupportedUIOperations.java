package commonlabels;

/**
 * This enum represents the different image Operations through UI.
 */
public enum SupportedUIOperations {
  BLUR("Blur"),
  SHARPEN("Sharpen"),
  GREYSCALE("Greyscale"),
  SEPIA("Sepia"),
  HORIZONTALFLIP("Horizontal Flip"),
  VERTICALFLIP("Vertical Flip"),
  REDCOMPONENT("Red Component"),
  GREENCOMPONENT("Green Component"),
  BLUECOMPONENT("Blue Component"),
  COMPRESSION("Compress"),
  LEVELADJUST("Level Adjust"),
  COLORCORRECTION("Color Correction"),
  DITHER("Dither");

  private final String stringValue;

  /**
   * Constructs an SupportedUIOperations object with the given string value.
   *
   * @param stringValue the string value of the image format
   */
  SupportedUIOperations(String stringValue) {
    this.stringValue = stringValue;
  }

  /**
   * Returns the string value of the image format.
   *
   * @return the string value of the image format
   */
  @Override
  public String toString() {
    return stringValue;
  }

  /**
   * Returns all the supported UI operations.
   *
   * @return all the supported UI operations
   */
  public static String[] getSupportedUIOperations() {
    String[] allOperations = new String[SupportedUIOperations.values().length];
    int i = 0;
    for (SupportedUIOperations operation : SupportedUIOperations.values()) {
      allOperations[i] = operation.toString();
      i++;
    }
    return allOperations;
  }

}
