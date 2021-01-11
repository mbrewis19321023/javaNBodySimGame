public class VectorUtil {
  // Class containing additional utility functions for working with vectors.
  
  public static final Vector TWO_D_ZERO = new Vector(new double[]{0, 0});
  public static final Vector FARAWAY = new Vector(new double[]{500e10, 500e10});
  
//  static Vector rotate(Vector v, double ang) {
//    // Rotate v by ang radians - two dimensions only.
//  }
  
  static Vector direction(Vector v) {
    // Returns direction of v, but sets angle to Math.PI/2 when v is the zero vector
    // Used to avoid exception in Vector.java
   
    if (v.magnitude() == 0.0){
      return new Vector(new double[]{0,1});
    } else{
      return v.times(1.0 / v.magnitude());
    }
  }
  
  // angle between x-axis and vector v
  static double getAngle(Vector v) {
    double angle = Math.acos(v.cartesian(0) / v.magnitude());
    // if y < 0
    if (v.cartesian(1) < 0) {
      angle = -angle;
    }
    return angle;
  }
  
  // angle between two vectors v1 and v2
  static double getAngle(Vector v1, Vector v2) {
    return getAngle(v2) - getAngle(v1);    
  }
  
  // get a Vector of size 1 with the given orientation (radians)
  static Vector getVector(double angle) {
    return new Vector(new double[]{Math.cos(angle), Math.sin(angle)});
  }
}
