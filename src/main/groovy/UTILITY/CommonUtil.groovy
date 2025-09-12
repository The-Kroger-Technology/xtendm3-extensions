/**
*  Business Engine Utility
*/
/****************************************************************************************
Extension Name: CommonUtil
Type : ExtendM3Utility
Script Author: Jonard Tapang
Date: 2025-07-08

Description:
  Common validation utility for KROGER Company

Revision History:
Name                    Date             Version          Description of Changes
Jonard Tapang		      2025-07-08				 1.0             Imported from XtendM3 example
******************************************************************************************/
public class CommonUtil extends ExtendM3Utility {
  
  /**
   * Check if Value is Y or N
   * @param flag value to check
   * @param flag1 allowed value
   * @param flag2 allowed value
   * @return {@code true} if flag value is valid
  */
  boolean isValidFlag(String flag, String flag1, String flag2) {
    if (flag.equalsIgnoreCase(flag1) || flag.equalsIgnoreCase(flag2)) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Check if inputTime is valid HHmmss
   * @param time value to check
   * @return {@code true} if flag value is valid
  */
  boolean isValidTime(String time) {
    if (time == null || !time.matches("\\d{6}")) return false;

    int hour = Integer.parseInt(time.substring(0, 2));
    int minute = Integer.parseInt(time.substring(2, 4));
    int second = Integer.parseInt(time.substring(4, 6));

    return hour >= 0 && hour <= 23 &&
           minute >= 0 && minute <= 59 &&
           second >= 0 && second <= 59;
  }
  
  /**
   * Check if inputNumber is valid positive int
   * @param input value to check
   * @return {@code true} if int value is valid
  */
  boolean isValidPositiveInt(String input) {
    try {
        // Try parsing the input as an integer
        int number = Integer.parseInt(input);
        
        // Check if the number is positive and greater than 0
        return number > 0;
    } catch (NumberFormatException e) {
        return false;  // Return false if the input is not a valid integer
    }
}
  
}
