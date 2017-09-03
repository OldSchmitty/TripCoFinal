package edu.csu2017fa314.T15.Model;

/**
 * <p>Calculates the distance from two latitude and longitude points</p>
 * @author jmay
 * @version 1.0 -9/2/17 - initial
 */
public class CalculateDistance {

  /**
   * <p>Converts a string atitude or longitude into its decimal form</p>
   * <p>Can read formants like 106°49'43.24" W, 106°49.24' W, 106.24° W, or -106.24</p>
   *
   * @param coordinate -The coordinate string to read
   * @return - Decimal form of the coordinate
   */
  public static double stringToDoubeForCoordinate (final String coordinate){

    //Parse the string
    String str = coordinate.toLowerCase();
    int mod = 1; // needed for adding minutes and seconds
    if(str.endsWith("s") || str.endsWith("w") ) {
      mod = -1;
    }
    //Find all the doubles in the Coordinate
    String[] stringElements = str.replaceAll("[^-0-9\\.]+", " ").split(" ");
    Double[] doubleElements = new Double[stringElements.length];
    //Convert strings to doubles
    for (int i = 0; i < stringElements.length; i++) {
      doubleElements[i] = Double.parseDouble(stringElements[i]);
    }

    //Find dec of coordinate
    //If in form -x*y'z" need to change to postivie for adding minutes and seconds
    if(doubleElements[0] < 0){
      mod = -1;
      doubleElements[0] *= mod;
    }

    double rt = doubleElements[0];
    //Add seconds and minutes to dec
    for (int i = 1; i < doubleElements.length; i++) {
      rt += doubleElements[i]/Math.pow(60,i);
    }
    return rt*mod;
  }
}
