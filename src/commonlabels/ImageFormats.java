package commonlabels;

/**
 * This enum represents the different image formats.
 */
public enum ImageFormats {
  JPEG("jpeg"),
  JPG("jpeg"),
  PNG("png"),
  PPM("ppm");

  private final String stringValue;

  /**
   * Constructs an ImageFormats object with the given string value.
   *
   * @param stringValue the string value of the image format
   */
  ImageFormats(String stringValue) {
    this.stringValue = stringValue;
  }

  @Override
  public String toString() {
    return stringValue;
  }
}
