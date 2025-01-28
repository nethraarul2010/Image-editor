package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import commonlabels.SupportedUIOperations;
import controller.ImageProcessorControllerTest.MockModel;
import logger.JViewInterface;
import model.image.ImageInterface;
import org.junit.Test;

/**
 * This class tests the ImageProcessorControllerV2 class.
 */
public class ImageProcessorControllerV2Test {

  private ImageProcessorControllerV2 controller;


  @Test
  public void testLoadImage() {
    StringBuilder ms = new StringBuilder();
    StringBuilder vs = new StringBuilder();
    MockView mockView = new MockView(vs);
    MockModel mockModel = new MockModel(ms);
    controller = new ImageProcessorControllerV2(mockView, mockModel);
    controller.loadImage("path", "destination");
    assertEquals("pathdestinationdestinationhistogram_destination", ms.toString());
    assertEquals(mockModel.getImage("destination"), mockView.imageInterface);
  }

  @Test
  public void testSaveImage() {
    StringBuilder ms = new StringBuilder();
    StringBuilder vs = new StringBuilder();
    MockView mockView = new MockView(vs);
    MockModel mockModel = new MockModel(ms);
    controller = new ImageProcessorControllerV2(mockView, mockModel);
    controller.loadImage("path", "destination");
    controller.saveImage("path");
    assertEquals("pathdestinationdestinationhistogram"
        + "_destinationpathdestination", ms.toString());
  }

  @Test(expected = NullPointerException.class)
  public void testSaveImageWithoutLoad() {
    StringBuilder ms = new StringBuilder();
    StringBuilder vs = new StringBuilder();
    MockView mockView = new MockView(vs);
    MockModel mockModel = new MockModel(ms);
    controller = new ImageProcessorControllerV2(mockView, mockModel);
    controller.saveImage("path");
  }


  @Test
  public void testUndoSplit() {
    StringBuilder ms = new StringBuilder();
    StringBuilder vs = new StringBuilder();
    MockView mockView = new MockView(vs);
    MockModel mockModel = new MockModel(ms);
    controller = new ImageProcessorControllerV2(mockView, mockModel);
    controller.loadImage("path", "destination");
    controller.undoSplit();
    assertEquals(mockModel.getImage("destination"), mockView.imageInterface);
    assertEquals(mockModel.getImage("histogram_destination"), mockView.histogramImage);
  }

  @Test
  public void testExecuteOperationWithSplit() {
    StringBuilder ms = new StringBuilder();
    StringBuilder vs = new StringBuilder();
    MockView mockView = new MockView(vs);
    MockModel mockModel = new MockModel(ms);
    controller = new ImageProcessorControllerV2(mockView, mockModel);
    controller.loadImage("path", "destination");
    controller.executeOperationWithSplit("Blur", "50");
    assertEquals("pathdestinationdestinationhistogram"
        + "_destinationdestinationdestinationBlur"
        + "_split5050destinationBlur_split50histogram_destinationBlur_split50", ms.toString());
    assertEquals(mockModel.getImage("destinationBlur_split50"), mockView.imageInterface);
    assertEquals(mockModel.getImage("histogram_destinationBlur_split50"),
        mockView.histogramImage);
  }

  @Test
  public void testExecuteOperationBlur() {
    StringBuilder ms = new StringBuilder();
    StringBuilder vs = new StringBuilder();
    MockView mockView = new MockView(vs);
    MockModel mockModel = new MockModel(ms);
    controller = new ImageProcessorControllerV2(mockView, mockModel);
    controller.loadImage("path", "destination");
    controller.executeOperation("Blur", null);
    assertEquals("pathdestinationdestinationhistogram_destinationdestinationBlurhistogram"
        + "_destinationBlur", ms.toString());
    assertEquals(mockModel.getImage("destinationBlur"), mockView.imageInterface);
    assertEquals(mockModel.getImage("histogram_destinationBlur"),
        mockView.histogramImage);
  }

  @Test
  public void testExecuteOperationSepia() {
    StringBuilder ms = new StringBuilder();
    StringBuilder vs = new StringBuilder();
    MockView mockView = new MockView(vs);
    MockModel mockModel = new MockModel(ms);
    controller = new ImageProcessorControllerV2(mockView, mockModel);
    controller.loadImage("path", "destination");
    controller.executeOperation(SupportedUIOperations.SEPIA.toString(), null);
    assertEquals("pathdestinationdestinationhistogram_destinationdestination"
        + "Sepiahistogram_destinationSepia", ms.toString());
    assertEquals(mockModel.getImage("destinationSepia"), mockView.imageInterface);
    assertEquals(mockModel.getImage("histogram_destinationSepia"),
        mockView.histogramImage);
  }

  @Test
  public void testExecuteOperationCompress() {
    StringBuilder ms = new StringBuilder();
    StringBuilder vs = new StringBuilder();
    MockView mockView = new MockView(vs);
    MockModel mockModel = new MockModel(ms);
    controller = new ImageProcessorControllerV2(mockView, mockModel);
    controller.loadImage("path", "destination");
    controller.executeOperation(SupportedUIOperations.COMPRESSION.toString(), "50");
    assertEquals("pathdestinationdestinationhistogram_destinationdestinationCompr"
        + "esshistogram_destinationCompress", ms.toString());
    assertEquals(mockModel.getImage("destinationCompress"), mockView.imageInterface);
    assertEquals(mockModel.getImage("histogram_destinationCompress"),
        mockView.histogramImage);
  }

  @Test
  public void testExecuteOperationGreyScale() {
    StringBuilder ms = new StringBuilder();
    StringBuilder vs = new StringBuilder();
    MockView mockView = new MockView(vs);
    MockModel mockModel = new MockModel(ms);
    controller = new ImageProcessorControllerV2(mockView, mockModel);
    controller.loadImage("path", "destination");
    controller.executeOperation(SupportedUIOperations.GREYSCALE.toString(), null);
    assertEquals("pathdestinationdestinationhistogram_destinationdestinationGrey"
        + "scalehistogram_destinationGreyscale", ms.toString());
    assertEquals(mockModel.getImage("destinationGreyscale"), mockView.imageInterface);
    assertEquals(mockModel.getImage("histogram_destinationGreyscale"),
        mockView.histogramImage);
  }

  @Test
  public void checkIfImageIsSaved() {
    StringBuilder ms = new StringBuilder();
    StringBuilder vs = new StringBuilder();
    MockView mockView = new MockView(vs);
    MockModel mockModel = new MockModel(ms);
    controller = new ImageProcessorControllerV2(mockView, mockModel);
    controller.loadImage("path", "destination");
    controller.saveImage("path");
    assertFalse(controller.checkIfImageIsSaved());
  }

  @Test
  public void testExecuteOperationDither() {
    StringBuilder ms = new StringBuilder();
    StringBuilder vs = new StringBuilder();
    MockView mockView = new MockView(vs);
    MockModel mockModel = new MockModel(ms);
    controller = new ImageProcessorControllerV2(mockView, mockModel);
    controller.loadImage("path", "destination");
    controller.executeOperation(SupportedUIOperations.DITHER.toString(), null);
    assertEquals("pathdestinationdestinationhistogram_destinationdestinationDither"
        + "histogram_destinationDither", ms.toString());
    assertEquals(mockModel.getImage("destinationDither"), mockView.imageInterface);
    assertEquals(mockModel.getImage("histogram_destinationDither"),
        mockView.histogramImage);
  }

  static class MockView implements JViewInterface {

    private final StringBuilder log;
    UIFeatures features;
    ImageInterface imageInterface;
    ImageInterface histogramImage;

    public MockView(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void addFeatures(UIFeatures f) {
      features = f;
    }

    @Override
    public void setCurrentImage(ImageInterface img) {
      imageInterface = img;
    }

    @Override
    public void setHistogramImage(ImageInterface histogramImage) {
      this.histogramImage = histogramImage;
    }

    @Override
    public void enableOperations() {
      // enable operation function.
    }

    @Override
    public void displayErrorPopup(String message) {
      log.append(message);
    }
  }
}