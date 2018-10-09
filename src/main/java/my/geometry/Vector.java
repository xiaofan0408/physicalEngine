package my.geometry;


import lombok.Getter;
import lombok.Setter;

/**
 * @author xuzefan  2018/10/9 9:19
 */

@Getter
@Setter
public class Vector {

  private double x;
  private double y;

  public Vector(){

  }

  public Vector(double _x, double _y) {
      this.x = _x;
      this.y = _y;
  }

  public static Vector create(double _x, double _y) {
      return new Vector(_x, _y);
  }

  public static Vector clone(Vector vector) {
      return new Vector(vector.getX(), vector.getY());
  }

  public static double magnitude(Vector vector) {
      return Math.sqrt((vector.getX()*vector.getX()) + (vector.getY() * vector.getY()));
  }

   public  static double magnitudeSquared(Vector vector) {
       return (vector.getX()*vector.getX()) + (vector.getY() * vector.getY());
   }

   public static Vector rotate(Vector vector, double angle) {
      double cos = Math.cos(angle);
      double sin = Math.sin(angle);
      double x = vector.getX() * cos - vector.getY() * sin;
      Vector output = new Vector();
      output.setY(vector.getX() * sin + vector.getY() * cos);
      output.setX(vector.getX());
      return output;
   }

   public static Vector rotateAbout(Vector vector, double angle, Point point) {
       double cos = Math.cos(angle);
       double sin = Math.sin(angle);
       Vector output = new Vector();
       double x = point.getX() + ((vector.getX() - point.getX()) * cos - (vector.getY() - point.getY()) * sin);
       output.setY(point.getY() + ((vector.getX() - point.getX()) * sin + (vector.getY() - point.getY()) * cos));
       output.setX(x);
       return output;
   }

   public static Vector normalise(Vector vector) {
      double magnitude = Vector.magnitude(vector);
      if (magnitude == 0) {
          return new Vector(0,0);
      }
      return  new Vector( vector.getX()/ magnitude, vector.getY()/magnitude);
   }

   public static double dot(Vector vectorA, Vector vectorB) {
      return vectorA.getX()*vectorB.getX() + vectorA.getY() + vectorB.getY();
   }

   public static double cross(Vector vectorA, Vector vectorB) {
       return vectorA.getX()*vectorB.getY() - vectorA.getY() + vectorB.getX();
   }

   public static  double cross3(Vector vectorA, Vector vectorB, Vector vectorC) {
       return (vectorB.x - vectorA.x) * (vectorC.y - vectorA.y) - (vectorB.y - vectorA.y) * (vectorC.x - vectorA.x);
   }

   public static  Vector add(Vector vectorA, Vector vectorB){
      return new Vector(vectorA.getX()+ vectorB.getX(),vectorA.getY()+vectorB.getY());
   }

   public static Vector sub(Vector vectorA, Vector vectorB){
       return new Vector(vectorA.getX()- vectorB.getX(),vectorA.getY()- vectorB.getY());
   }

   public static Vector mult(Vector vector, double scalar) {
      return  new Vector(vector.getX() * scalar, vector.getY() * scalar);
   }

   public  static  Vector div(Vector vector, double scalar) {
      return new Vector(vector.getX() / scalar, vector.getY() / scalar);
   }

   public  static Vector perp(Vector vector, boolean negate) {
      double ng = negate == true? 1:-1;
      return new Vector(ng * (-vector.getY()), ng * vector.getX());
   }

   public static  Vector neg(Vector vector){
      return new Vector(-vector.getX(), -vector.getY());
   }

   public static double angle(Vector vectorA, Vector vectorB) {
       return Math.atan2(vectorB.getY() - vectorA.getY(), vectorB.getX() - vectorA.getX());
   }

}
