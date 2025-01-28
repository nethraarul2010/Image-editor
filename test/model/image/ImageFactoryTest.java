package model.image;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * This class tests the ImageFactory class.
 */
public class ImageFactoryTest {

  @Test
  public void testCreateImageWithValidChannels() {
    List<int[][]> channelList = new ArrayList<>();
    int[][] channel1 = new int[3][3];
    int[][] channel2 = new int[3][3];
    int[][] channel3 = new int[3][3];
    channelList.add(channel1);
    channelList.add(channel2);
    channelList.add(channel3);
    ImageInterface image = ImageFactory.createImage(channelList);
    assertEquals(image.getChannel().size(), 3);
  }

  @Test
  public void testCreateImageWithSingleChannel() {
    List<int[][]> channelList = new ArrayList<>();
    int[][] channel1 = new int[3][3];
    channelList.add(channel1);
    ImageInterface image = ImageFactory.createImage(channelList);
    assertEquals(image.getChannel().size(), 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateImageWithInvalidChannels() {
    List<int[][]> channelList = new ArrayList<>();
    int[][] channel1 = new int[3][3];
    int[][] channel2 = new int[3][3];
    channelList.add(channel1);
    channelList.add(channel2);
    ImageFactory.createImage(channelList);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateImageWithNoChannels() {
    List<int[][]> channelList = new ArrayList<>();
    ImageFactory.createImage(channelList);
  }

  @Test
  public void testCreateImageValue() {
    int[][] channel1 = {{255, 0}, {0, 255}};
    int[][] channel2 = {{0, 255}, {255, 0}};
    int[][] channel3 = {{0, 255}, {255, 0}};
    List<int[][]> channelList = List.of(channel1, channel2, channel3);
    List<int[][]> returnChannleList = ImageFactory.createImage(channelList).getChannel();

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        for (int w = 0; w < 2; w++) {
          assertEquals(channelList.get(i)[j][w], returnChannleList.get(i)[j][w]);
        }
      }
    }
  }
}
