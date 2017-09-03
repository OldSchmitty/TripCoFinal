package edu.csu2017fa314.T15.Model;

/**
 * <p>Calculates the distance from two latitude and longitude points</p>
 * @author jmay
 * @version 1.0 -9/2/17 - initial
 */
public class CalculateDistance {

  public static double stringToDoubeForCoordinate (final String coordinate){

    String str = coordinate.toLowerCase();
    int mod = 1;
    if(str.endsWith("s") || str.endsWith("w") ) {
      mod = -1;
    }

    String[] stringElements = str.replaceAll("[^-0-9\\.]+", " ").split(" ");
    Double[] doubleElements = new Double[stringElements.length];

    for (int i = 0; i < stringElements.length; i++) {
      doubleElements[i] = Double.parseDouble(stringElements[i]);
    }

    return 0;
  }
}
