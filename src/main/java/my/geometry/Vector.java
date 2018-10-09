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

}
