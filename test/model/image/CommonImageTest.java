package model.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the CommonImage class.
 */
public class CommonImageTest {

  private ImageInterface image;

  @Before
  public void setUp() {
    CommonImage.ImageBuilder builder = new CommonImage.ImageBuilder();
    int[][] redChannel = {{255, 0}, {0, 255}};
    int[][] greenChannel = {{0, 255}, {255, 0}};
    int[][] blueChannel = {{0, 0}, {255, 255}};
    builder.addChannel(redChannel);
    builder.addChannel(greenChannel);
    builder.addChannel(blueChannel);
    image = builder.build();
  }

  @Test
  public void testGetChannel() {
    assertNotNull(image.getChannel());
    assertEquals(3, image.getChannel().size());
    assertEquals(2, image.getChannel().get(0).length);
    assertEquals(2, image.getChannel().get(0)[0].length);
  }

  @Test
  public void testGetHeight() {
    assertEquals(2, image.getHeight());
  }

  @Test
  public void testGetWidth() {
    assertEquals(2, image.getWidth());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWidth() {
    CommonImage.ImageBuilder builder = new CommonImage.ImageBuilder();
    int[][] channel1 = {{255, 0}, {0, 255}};
    int[][] channel2 = {{0, 255, 0}, {255, 0, 0}};
    builder.addChannel(channel1);
    builder.addChannel(channel2);
    builder.build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidHeight() {
    CommonImage.ImageBuilder builder = new CommonImage.ImageBuilder();
    int[][] channel1 = {{255, 0}, {0, 255}};
    int[][] channel2 = {{0, 255}, {255, 0}, {255, 0}};
    builder.addChannel(channel1);
    builder.addChannel(channel2);
    builder.build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalid() {
    CommonImage.ImageBuilder builder = new CommonImage.ImageBuilder();
    int[][] channel1 = {{255, 0}, {0, 255}};
    int[][] channel2 = {{0, 255, 0}, {255, 0}};
    builder.addChannel(channel1);
    builder.addChannel(channel2);
    builder.build();
  }
}
