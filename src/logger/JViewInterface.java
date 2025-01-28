package logger;

import controller.UIFeatures;
import model.image.ImageInterface;

/**
 * This interface supports different operations helping controller
 * and view to communicate.
 */
public interface JViewInterface {
  /**
   * This method provides with different features to the view.
   *
   * @param f the features
   */
  void addFeatures(UIFeatures f);

  /**
   * This method sets the current image.
   *
   * @param img the image
   */
  void setCurrentImage(ImageInterface img);

  /**
   * This method sets the histogram image.
   *
   * @param histogramImage the histogram image
   */
  void setHistogramImage(ImageInterface histogramImage);

  /**
   * This method enables the operations after image is loaded.
   */
  void enableOperations();

  /**
   * This method displays the error popup.
   *
   * @param message the message
   */
  void displayErrorPopup(String message);
}
