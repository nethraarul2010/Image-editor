package model.operations.operatorutil;

/**
 * Utility class for operations related to operators.
 */
public class OperatorUtil {

  /**
   * Converts a string operator to an integer percentage.
   *
   * @param operator The string representation of the operator.
   * @return The corresponding integer percentage.
   * @throws IllegalArgumentException If the operator is not a valid integer or
   *                                  if the parsed percentage is not within
   *                                  the valid range [0, 100].
   */
  public static int castOperatorToDouble(String operator) {
    int percentage;
    try {
      percentage = Integer.parseInt(operator);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Percentage should be an integer value");
    }

    if (percentage < 0 || percentage > 100) {
      throw new IllegalArgumentException("Percentage should be between 0 and 100");
    }
    return percentage;
  }
}

