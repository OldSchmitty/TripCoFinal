package edu.csu2017fa314.T15.Model;

/**
 * <p>Calculates the distance from two latitude and longitude points</p>
 * @author jmay
 * @version 1.0 -9/2/17 - initial
 */
public class CalculateDistance {

  /**
   * <p>Converts a string latitude or longitude into its decimal form</p>
   * <p>Can read formats like 106°49'43.24" W, 106°49.24' W, 106.24° W, or -106.24</p>
   * @param coordinate -The coordinate string to read
   * @return - Decimal form of the coordinate
   */
  public static double stringToDoubleForCoordinate (final String coordinate) {

    //Parse the string
    String str = coordinate.toLowerCase();
    int mod = 1; // needed for adding minutes and seconds
    if (str.endsWith("s") || str.endsWith("w")) {
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
    //If in form -x*y'z" need to change to positive for adding minutes and seconds
    if (doubleElements[0] < 0) {
      mod = -1;
      doubleElements[0] *= mod;
    }

    double rt = doubleElements[0];
    //Add seconds and minutes to dec
    for (int i = 1; i < doubleElements.length; i++) {
      rt += doubleElements[i] / Math.pow(60, i);
    }
    return rt*mod;
  }

  /**
   * <p>Find the distance between two latitude and longitude by using the
   * great circle distance formula</p>
   * <p>Formula found at https://en.wikipedia.org/wiki/Great-circle_distance</p>
   * @param lat1 - latitude of first location
   * @param log1 - longitude of the first location
   * @param lat2 - latitude of second location
   * @param log2 - longitude of second location
   * @return - The distance between the two
   */
  public static long greatDistanceCalculation(final double lat1, final double log1,
      final double lat2, final double log2){
    double lat1Rad = Math.toRadians(lat1);
    double lat2Rad = Math.toRadians(lat2);
    double logDiff = Math.toRadians(Math.abs(log2 -log1));
    double earthRadiusMiles = 3958.7613; // in miles

    double dividendPart1 = Math.cos(lat2Rad)*Math.sin(logDiff);
    double dividendPart2 = Math.cos(lat1Rad)*Math.sin(lat2Rad) - Math.sin(lat1Rad)*Math.cos(lat2Rad)*Math.cos(logDiff) ;
    double divisor = Math.sin(lat1Rad)*Math.sin(lat2Rad) + Math.cos(lat1Rad)*Math.cos(lat2Rad)*Math.cos(logDiff);

    double arcLength = Math.atan2(Math.sqrt(Math.pow(dividendPart1,2)+Math.pow(dividendPart2,2)),divisor);

    return Math.round(arcLength*earthRadiusMiles);// radius of earth in miles
  }

  /**
   * <p>Find the distance between two latitude and longitude strings by using the
   * great circle distance formula in miles</p>
   * <p>Formula found at https://en.wikipedia.org/wiki/Great-circle_distance</p>
   * @param lat1 - latitude of first location
   * @param log1 - longitude of the first location
   * @param lat2 - latitude of second location
   * @param log2 - longitude of second location
   * @return - The distance between the two rounded
   */
   public static long findDistanceBetween(final String lat1, final String log1,
      final String lat2, final String log2){

    return greatDistanceCalculation(
        stringToDoubleForCoordinate(lat1),
        stringToDoubleForCoordinate(log1),
        stringToDoubleForCoordinate(lat2),
        stringToDoubleForCoordinate(log2));
  }

  /**
   * <p>Find the distance between two latitude and longitude strings by using the
   * great circle distance formula in miles</p>
   * <p>Formula found at https://en.wikipedia.org/wiki/Great-circle_distance</p>
   * @param brew1 - Start
   * @param brew2 - End
   * @return - distance in miles
   */
  public static long findDistanceBetween(final Destination brew1, final Destination brew2){
     return findDistanceBetween(
         brew1.getLatitude(), brew1.getLongitude(),
         brew2.getLatitude(), brew2.getLongitude());
  }
}
