package model.imageio;

import commonlabels.ImageFormats;
import java.io.IOException;
import model.image.ImageInterface;

/**
 * This class is a factory for IOFileByFormat objects. It provides methods for encoding and saving
 * images as well as decoding images from files based on their file extensions.
 */
public class IOFileFactory {

  /**
   * Encodes and saves an image to a file with the specified filename and format.
   *
   * @param filename the filename to save the image
   * @param image    the image to be saved
   * @throws IOException              if an error occurs during the encoding and saving process
   * @throws IllegalArgumentException if the filename has an invalid file extension
   */
  public static void encodeAndSaveImage(String filename, ImageInterface image)
      throws IOException, IllegalArgumentException {
    // Extract the file extension from the filename
    String fileExtension = getFileExtension(filename);

    if (fileExtension == null) {
      throw new IllegalArgumentException("Invalid file");
    }

    // Select the appropriate IOFileByFormat implementation based on the file extension
    switch (fileExtension) {
      case "ppm":
        IOFileByFormat ppmFileAdapter = new PPMFileAdapter();
        ppmFileAdapter.encodeAndSaveImage(filename, image, ImageFormats.PPM);
        break;
      case "png":
      case "jpg":
      case "jpeg":
        IOFileByFormat commonFormatsFileAdapter = new CommonFormatsFileAdapter();
        commonFormatsFileAdapter.encodeAndSaveImage(filename, image, ImageFormats.PNG);
        break;
      default:
        throw new IllegalArgumentException("Invalid file extension");
    }
  }

  /**
   * Decodes an image from the given filename.
   *
   * @param filename the filename to read the image from
   * @return the decoded image
   * @throws IOException              if the file cannot be read
   * @throws IllegalArgumentException if the filename has an invalid file extension
   */
  public static ImageInterface decodeImage(String filename)
      throws IOException, IllegalArgumentException {
    // Extract the file extension from the filename
    String fileExtension = getFileExtension(filename);

    if (fileExtension == null) {
      throw new IllegalArgumentException("Invalid file");
    }

    // Select the appropriate IOFileByFormat implementation based on the file extension
    switch (fileExtension) {
      case "ppm":
        IOFileByFormat ppmFileAdapter = new PPMFileAdapter();
        return ppmFileAdapter.decodeImage(filename);
      case "png":
      case "jpg":
      case "jpeg":
        IOFileByFormat commonFormatsFileAdapter = new CommonFormatsFileAdapter();
        return commonFormatsFileAdapter.decodeImage(filename);
      default:
        throw new IllegalArgumentException("Invalid file extension");
    }
  }

  /**
   * Extracts the file extension from a file path.
   *
   * @param filePath the file path containing the extension
   * @return the file extension (e.g., "jpg", "png"), or null if no extension is found
   */
  public static String getFileExtension(String filePath) {
    int lastDotIndex = filePath.lastIndexOf('.');
    if (lastDotIndex > 0) {
      return filePath.substring(lastDotIndex + 1);
    } else {
      return null;
    }
  }
}
