package controller;

/**
 * This interface represents the features of the UI supported by controller.
 */
public interface UIFeatures {

  /**
   * This method loads image for the image processing application.
   */
  void loadImage(String imagePath, String destImageName);

  /**
   * This method saves image for the image processing application.
   */
  void saveImage(String imagePath);

  /**
   * This method undos the split operation for the image processing application.
   */
  void undoSplit();

  /**
   * This method executes the preview operation with split on the image.
   */
  void executeOperationWithSplit(String operationName, Object operator);

  /**
   * This method executes the operation on the image.
   */
  void executeOperation(String operationName, Object operator);

  /**
   * This method checks if the image is saved or not after operation is performed.
   */
  boolean checkIfImageIsSaved();

}
