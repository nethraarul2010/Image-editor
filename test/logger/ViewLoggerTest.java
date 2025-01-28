package logger;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the ViewLogger class.
 */
public class ViewLoggerTest {

  private StringWriter stringWriter;
  private ViewLogger viewLogger;

  @Before
  public void setUp() {
    stringWriter = new StringWriter();
    viewLogger = new ViewLogger(stringWriter);
  }

  @Test
  public void testLogException() throws IOException {
    Exception exception = new Exception("Test Exception Message");
    viewLogger.logException(exception);
    assertEquals("Test Exception Message", stringWriter.toString());
  }

  @Test
  public void testLogString() throws IOException {
    viewLogger.logString("Test Log String");
    assertEquals("Test Log String", stringWriter.toString());
  }

  @Test(expected = IOException.class)
  public void testLogExceptionWithIOException() throws IOException {
    ViewLogger loggerWithException = new ViewLogger(new Appendable() {
      @Override
      public Appendable append(CharSequence csq) throws IOException {
        throw new IOException("IO Exception");
      }

      @Override
      public Appendable append(CharSequence csq, int start, int end) throws IOException {
        throw new IOException("IO Exception");
      }

      @Override
      public Appendable append(char c) throws IOException {
        throw new IOException("IO Exception");
      }
    });

    Exception exception = new Exception("Test Exception Message");
    loggerWithException.logException(exception);
  }

  @Test(expected = IOException.class)
  public void testLogStringWithIOException() throws IOException {
    ViewLogger loggerWithException = new ViewLogger(new Appendable() {
      @Override
      public Appendable append(CharSequence csq) throws IOException {
        throw new IOException("IO Exception");
      }

      @Override
      public Appendable append(CharSequence csq, int start, int end) throws IOException {
        throw new IOException("IO Exception");
      }

      @Override
      public Appendable append(char c) throws IOException {
        throw new IOException("IO Exception");
      }
    });

    loggerWithException.logString("Test Log String");
  }
}
