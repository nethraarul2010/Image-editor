Image Processing Application
Overview
The Image Processing Application is designed to perform various image operations on different image formats. This README provides an overview of the key classes, interfaces, and components in the codebase.

Classes and Interfaces

Controller Package

ControllerInterface
Purpose: This interface defines the contract for controllers in the application. Controllers are responsible for managing the flow of user commands and interacting with the model.

ImageProcessorController
Purpose: This class implements the ControllerInterface and manages user input, command execution, and interaction with the model. It handles commands such as loading, saving, and applying image operations.

commonlabels Package

ImageFormats Enum
Purpose: The ImageFormats enum represents different image formats, such as JPEG, JPG, PNG, and PPM. It is used for specifying the format when encoding or decoding images.
JPEG: Represents the JPEG image format.
JPG: Represents the JPG image format.
PNG: Represents the PNG image format.
PPM: Represents the PPM image format.


View Logger Package

ViewLogger Class
Purpose: The ViewLogger class is responsible for logging messages and exceptions to the specified output source. It provides a simple way to log information relevant to the view component of the application.

Image package

CommonImage Class
Purpose: Represents an RGB image with three color channels and provides consistency checks for image data.

ImageBuilder Inner Class (inside CommonImage)
Purpose: Builds CommonImage objects by incrementally adding channels while ensuring consistency.

ImageFactory Class
Purpose: Creates image objects based on a list of channel arrays, enforcing constraints on the number of channels (1 or 3).

ImageInterface Interface
Purpose: Defines a common interface for interacting with different image representations in the application.


Imageio package

CommonFormatsFileAdapter Class
Purpose: Provides encoding (saving) and decoding (reading) functionality for common image formats like JPG and PNG. It serves as an adapter to interface with the ImageIO class from the Java standard library, facilitating the conversion of internal image representations to external formats and vice versa.

IOFileByFormat Interface
Purpose: Provides a common interface for performing input and output (IO) operations related to image files in various formats. It serves as a contract for classes that handle the encoding (saving) and decoding (reading) of image files, abstracting the underlying details of specific file formats. This interface allows the decoupling of image processing and IO logic and ensures flexibility in supporting multiple image formats.

IOFileFactory Class
Purpose: The IOFileFactory class serves as a factory for creating and handling different IOFileByFormat objects, enabling the encoding (saving) and decoding (reading) of images in various file formats based on their file extensions. It provides methods for encoding and saving images as well as decoding images from files. This class abstracts the specific implementations for different image formats and allows clients to work with images using a unified interface.

PPMFileAdapter Class
Purpose: The PPMFileAdapter class is responsible for encoding and saving PPM images to files and decoding PPM images from files. PPM (Portable Pixmap) is a simple, human-readable image file format. This class provides the necessary methods to interact with PPM image files.


imageprocessingmodel package

ImageProcessorModel Class

Purpose: The ImageProcessorModel class serves as a central component for image processing, providing a range of image operations, including filtering, color manipulation, and transformations. It manages images, allowing users to load, save, and apply operations through simple commands.

ImageProcessorModelInterface Interface
Purpose: The ImageProcessorModelInterface defines the contract for an image processor model, ensuring that different implementations can process user commands related to image operations effectively.


colorrepresentation package

AbstractColorRepresentation Class
Purpose: The AbstractColorRepresentation class is the base class for various color representation operations that can be applied to an image. It provides a framework for implementing color representation operations, such as intensity and value, which modify an image's color channels and transform them into single-channel representations.

Intensity Class
Purpose: The Intensity class represents an intensity color representation operation on an image. It calculates the average value of the three color components (red, green, and blue) for each pixel, resulting in a grayscale image that represents the intensity of the original image.

Value Class
Purpose: The Value class represents a value color representation operation on an image. It determines the maximum value among the three color components (red, green, and blue) for each pixel, resulting in a single-channel image that emphasizes the highest color component's intensity.


colortransformation package
CommonColorTransformOperation Class
Purpose: The CommonColorTransformOperation class represents a common framework for color transformation operations that can be applied to an image. It defines a set of methods for applying these transformations, ensuring that the image has three color channels (red, green, and blue), and calculating the new color values based on transformation coefficients.

Greyscale Class
Purpose: The Greyscale class represents a greyscale color transformation operation that converts an image into grayscale by applying specific transformation coefficients. It provides the transformation coefficients that produce a grayscale image.

Sepia Class
Purpose: The Sepia class represents a sepia color transformation operation that gives the image a sepia-toned effect by applying specific transformation coefficients. It provides the transformation coefficients used for the sepia effect.

filters package

BlurFilter Class
Purpose: The BlurFilter class represents a blur filter operation that can be applied to an image. It applies a Gaussian filter to create a blur effect. The class provides a default Gaussian filter for blurring and allows obtaining this filter.

CommonFilterOperation Class
Purpose: The CommonFilterOperation class serves as a common framework for filter operations on images. It defines a method for applying a filter to an image. The class handles the process of filtering by applying the specified filter kernel and managing padding for the image if the kernel size is larger.

SharpenFilter Class
Purpose: The SharpenFilter class represents a sharpen filter operation that can be applied to an image. It applies a sharpening filter to enhance image details. The class provides a default sharpen filter and allows obtaining this filter.


merge package

MergeSingleChannelImages Class
Purpose: The MergeSingleChannelImages class represents an operation that merges single-channel images into a new image. It validates the input images and checks if they can be merged. The images should have the same dimensions (height and width), and the number of images and the number of channels should be the same.


operationinterfaces package

MultipleToSingleImageProcessor Interface
purpose: The purpose of this interface is to define image processors that take multiple input images and produce a single output image.

SingleImageProcessor Interface
purpose: This interface serves the purpose of defining image processors that operate on a single input image, producing a processed output image.

SingleImageProcessorWithOffset Interface
purpose: The purpose of this interface is to define image processors that operate on a single input image with an offset or additional parameter, resulting in a processed output image.


pixeloffset package

BrightnessOperation
purpose: The purpose of the BrightnessOperation class is to perform a simple offset operation on an input image to adjust its brightness. It brightens or darkens every pixel in the image by a specified factor, where a positive factor brightens the image, and a negative factor darkens it. The resulting image is created with the adjusted brightness.

CompressionOperation
purpose: The purpose of the CompressionOperation class is to perform haar transformation and pixel manipulation to compress image. It compresses the image according to the factor passed int he arugument. The resulting image is created with the compressed size.

rotation package

HorizontalFlipOperation
purpose: This class is responsible for flipping an image horizontally. It reverses the order of pixels in each row, effectively mirroring the image from left to right.

VerticalFlipOperation
purpose: This class is responsible for flipping an image vertically. It reverses the order of rows, effectively flipping the image upside down.


split package

SplitImageOperation
purpose: This class serves to split an input image by isolating a specific component or channel. It takes both an image and an operator as inputs, where the operator designates the desired component to keep while setting all other channels to zero. The result is multiple images, each containing only one component.


visualization package

ColorCorrection
purpose : The purpose of this class is to adjust the color balance of an image by analyzing the frequency distribution of colors in each channel. It identifies the peak values in the frequency distribution of each color channel, calculates an average peak value, and then applies color correction to each channel based on the difference between the average peak value and the peak value of each channel.

HistogramVisualization
purpose : The purpose of this class is to generate a histogram that visualizes the intensity distribution of an image across its color channels. It creates a histogram image with intensity information displayed in red, green, and blue, helping users to understand the distribution of pixel intensities in different color channels.

LevelAdjustment
purpose : The purpose of this class is to apply a level adjustment transformation to an image, altering the distribution of pixel intensities to achieve a desired visual effect. Level adjustment is a common image processing technique used to enhance the contrast and brightness of an image.

### Design
MVC design pattern is used to design the application. The model contains the image class which contains the image data and the methods to manipulate the image. The view contains the viewlogger class which is responsible for displaying the success and error messages. The controller contains the controller class which is responsible for taking the input from the user and calling the appropriate methods in the model and view.

### Citation
Image used for testing is provided in the res/images folder and is named as open-source-image-original.png image is open source (https://www.istockphoto.com/vector/open-source-icon-open-source-symbol-design-from-vector-stock-illustration-gm1208176833-349123511) and is credited to Oleksandr Hruts and can be freely used as per the license.
All the images shown in screenshots is owned by me(Yash Anand).

### Complete Program parts
We completed every part of the assignment which were asked for till current part of the assignment.
Controller is complete as it is able to handle all the commands asked for.
Model is complete as it will server every request that comes in from controller.
View is complete as it will log every thing given to it by controller.

List of features supported :
load
save
blur
blur split
sharpen
sharpen split
greyscale
greyscale split
sepia
sepia split
intensity-component
value-component
luma-component
red-component
green-component
blue-component
horizontal-flip
vertical-flip
rgb-combine
rgb-split
compress
histogram
levels-adjust
color-correct
color-correct split
run script_file
-file name-of-script

### change in design
Nothing has been changes in design we just added new controller which extends controller interface and also UIfeatures interface for GUI and rest of the calls and flows are as it is.
We added new controller as we wanted to follow command callback pattern for interaction between controller and view.

### change in code
Only new controller is added and support of GUI is added in main file.

