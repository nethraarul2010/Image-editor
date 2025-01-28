- Dithering has been implemented along with split in GUI, Text and Script file implementation.

Dithering implementation: Y
Script command for Dithering: Y
Dithering from GUI: Y

To enable Dithering within this application, we've introduced a new class named Dither in the model, to support Dithering functionality. This class includes overloaded methods specifically designed for both standalone dithering and dithering with Split View. To facilitate dithering with Split View, we reused the existing PartialImageOperation class. 

Within this application, there are two controllers. In the controller for Script Command, a new case block has been incorporated to call the Dither class of the model. In the GUI controller, a new KeyReleaseEvent has been implemented to invoke the image operation. Additionally, a new value has been appended to the ENUM, which is utilized in the dropdown menu for image manipulation operations in the GUI.


###Citations

test-image.jpg Image by PublicDomainPictures from Pixabay

ImageLink: https://pixabay.com/photos/city-bridge-evening-twilight-bay-6220689/

PublicDomainPictures's Pixabay Profile: https://pixabay.com/users/xegxef-1679630/?utm_source=link-attribution&utm_medium=referral&utm_campaign=image&utm_content=6220689

Pixabay: https://pixabay.com//?utm_source=link-attribution&utm_medium=referral&utm_campaign=image&utm_content=6220689
