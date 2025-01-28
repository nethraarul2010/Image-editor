import controller.ControllerInterface;
import controller.ImageProcessorController;
import controller.ImageProcessorControllerV2;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import logger.JFrameView;
import logger.JViewInterface;
import logger.ViewLogger;
import model.imageprocessingmodel.ImageProcessorModel;

/**
 * This class represents the main class for the image processing application and runs this program.
 */
public class ImageProcessingApplication {

  /**
   * Main method for starting the application.
   *
   * @param args argument
   * @throws IOException Exception
   */
  public static void main(String[] args) throws IOException {
    if (args.length > 0 && args[0].equals("-file")) {
      ImageProcessorController imageProcessorController =
          new ImageProcessorController(new ViewLogger(System.out),
              new ImageProcessorModel(),
              new InputStreamReader(new ByteArrayInputStream(("run " + args[1]).getBytes())),
              System.out);
      imageProcessorController.startImageProcessingController();

    } else if (args.length > 0 && args[0].equals("-text")) {
      ImageProcessorController imageProcessorController =
          new ImageProcessorController(new ViewLogger(System.out),
              new ImageProcessorModel(), new InputStreamReader(System.in), System.out);
      imageProcessorController.startImageProcessingController();
    } else {
      JViewInterface view = new JFrameView("Image Processing Application");
      ControllerInterface controllerInterface = new ImageProcessorControllerV2(view,
          new ImageProcessorModel());
      controllerInterface.startImageProcessingController();

    }
  }
}