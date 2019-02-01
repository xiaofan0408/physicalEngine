package my.geometry;

import java.util.List;

/**
 * @author xuzefan  2019/2/1 16:41
 */
public class Bounds {

    private Vector min;

    private Vector max;


    public static Bounds create(List<Vertices.Vertex> vertices) {
        Bounds bounds = new Bounds();
        bounds.min = new Vector();
        bounds.max = new Vector();

        if (vertices!=null) {
            Bounds.update(bounds, vertices, null);
        }
        return bounds;
    }

    public static void update(Bounds bounds, List<Vertices.Vertex> vertices, Vector velocity) {
        bounds.min.setX(Double.POSITIVE_INFINITY);
        bounds.max.setX(Double.NEGATIVE_INFINITY);
        bounds.min.setX(Double.POSITIVE_INFINITY);
        bounds.max.setY(Double.NEGATIVE_INFINITY);

        for (int  i = 0; i < vertices.size(); i++) {
            Vertices.Vertex vertex = vertices.get(i);
            if (vertex.getX() > bounds.max.getX()) {
                bounds.max.setX(vertex.getX());
            };
            if (vertex.getX() < bounds.min.getX()) {
                bounds.min.setX(vertex.getX());
            }
            if (vertex.getY() > bounds.max.getY()){
                bounds.max.setY(vertex.getY());
            }
            if (vertex.getY() < bounds.min.getY()) {
                bounds.min.setY(vertex.getY());
            }
        }

        if (velocity!=null) {
            if (velocity.getX() > 0) {
                bounds.max.setX(bounds.max.getX() + velocity.getX());
            } else {
                bounds.min.setX(bounds.min.getX() + velocity.getX());
            }

            if (velocity.getY() > 0) {
                bounds.max.setY(bounds.max.getY() + velocity.getY());
            } else {
                bounds.min.setY(bounds.min.getY() + velocity.getY());
            }
        }
    }

    public static Boolean contains(Bounds bounds, Vector point) {
        return point.getX() >= bounds.min.getX() && point.getX() <= bounds.max.getX()
                && point.getY() >= bounds.min.getY() && point.getY() <= bounds.max.getY();
    }

    public static Boolean overlaps(Bounds boundsA,Bounds boundsB) {
        return (boundsA.min.getX() <= boundsB.max.getX() && boundsA.max.getX() >= boundsB.min.getX()
                && boundsA.max.getY() >= boundsB.min.getY() && boundsA.min.getY() <= boundsB.max.getY());
    }

    public static void  translate(Bounds bounds, Vector vector) {
        bounds.min.setX(bounds.min.getX() + vector.getX());
        bounds.max.setX(bounds.max.getX() + vector.getX());
        bounds.min.setY(bounds.min.getY() + vector.getY());
        bounds.max.setY(bounds.max.getY() + vector.getY());
    }

    public static void shift(Bounds bounds, Vector position) {
        double deltaX = bounds.max.getX() - bounds.min.getX();
        double deltaY = bounds.max.getY() - bounds.min.getY();

        bounds.min.setX(position.getX());
        bounds.max.setX(position.getX() + deltaX);
        bounds.min.setY(position.getY());
        bounds.max.setY(position.getY() + deltaY);
    }
}
