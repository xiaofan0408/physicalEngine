package my.geometry;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.body.Body;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author xuzefan  2018/10/23 14:20
 */
public class Vertices {


    public static List<Vertex> create(List<Point> points,Body body) {

        List<Vertex>  vertices = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            Vertex vertex = new Vertex(point.getX(), point.getY(),i,body,false);
            vertices.add(vertex);
        }
        return vertices;
    }

    public static Vector centre(List<Vertex> vertices) {
        double area = Vertices.area(vertices,true);
        Vector centre = new Vector(0,0);
        double cross;
        Vector temp;
        int j;

        for (int i = 0; i < vertices.size(); i++) {
            j = (i + 1) % vertices.size();
            cross = Vector.cross(vertices.get(i), vertices.get(j));
            temp = Vector.mult(Vector.add(vertices.get(i), vertices.get(j)), cross);
            centre = Vector.add(centre, temp);
        }

        return Vector.div(centre, 6 * area);
    }

    public static Vector mean(List<Vertex> vertices) {
        Vector average  = new Vector(0,0);
        for (int i = 0; i < vertices.size(); i++) {
            average.setX(average.getX() + vertices.get(i).getX());
            average.setY(average.getY() + vertices.get(i).getY());
        }
        return Vector.div(average, vertices.size());
    }


    public static double area(List<Vertex> vertices, boolean signed) {
        double area = 0;
        int len = vertices.size() - 1;
        for (int i = 0 ; i < vertices.size();i++) {
            area += (vertices.get(len).getX() - vertices.get(i).getX()) * (vertices.get(len).getY() + vertices.get(i).getY());
            len = i;
        }

        if (signed){
            return area / 2;
        }
        return Math.abs(area) / 2;
    }

    public static double inertia(List<Vertex> vertices,double mass) {
        double numerator = 0;
        double denominator = 0;
        List<Vertex> v = vertices;
        double cross;
        int j;

        // find the polygon's moment of inertia, using second moment of area
        // from equations at http://www.physicsforums.com/showthread.php?t=25293
        for (int n = 0; n < v.size(); n++) {
            j = (n + 1) % v.size();
            cross = Math.abs(Vector.cross(v.get(j), v.get(n)));
            numerator += cross * (Vector.dot(v.get(j), v.get(j)) + Vector.dot(v.get(j), v.get(n)) + Vector.dot(v.get(n), v.get(n)));
            denominator += cross;
        }
        return (mass / 6) * (numerator / denominator);
    }

    public static List<Vertex> translate(List<Vertex> vertices,Vector vector, double scalar) {
        int i;
        if (scalar != 0) {
            for (i = 0; i < vertices.size(); i++) {
                vertices.get(i).setX( vertices.get(i).getX() + vector.getX() * scalar);
                vertices.get(i).setY(vertices.get(i).getY() + vector.getY() * scalar);
            }
        } else {
            for (i = 0; i < vertices.size(); i++) {
                vertices.get(i).setX( vertices.get(i).getX() + vector.getX());
                vertices.get(i).setY(vertices.get(i).getY() + vector.getY());
            }
        }
        return vertices;
    }

    public static List<Vertex> rotate(List<Vertex> vertices, double angle, Vector point) {
        if (angle == 0) {
            return vertices;
        }

        double cos = Math.cos(angle);
        double  sin = Math.sin(angle);

        for (int i = 0; i < vertices.size(); i++) {
            Vertex vertice = vertices.get(i);
            double dx = vertice.x - point.getX();
            double dy = vertice.y - point.getY();

            vertice.setX(point.getX() + (dx * cos - dy * sin));
            vertice.setY(point.getY() + (dx * sin + dy * cos));
        }

        return vertices;
    }

    public static boolean contains(List<Vertex> vertices,Vector point) {
        for (int i = 0; i < vertices.size(); i++) {
            Vertex vertice = vertices.get(i);
            Vertex nextVertice = vertices.get((i + 1) % vertices.size());
            if ((point.getX() - vertice.getX()) * (nextVertice.getY() - vertice.getY()) + (point.getY() - vertice.getY()) * (vertice.getX() - nextVertice.getX()) > 0) {
                return false;
            }
        }

        return true;
    }


    public static List<Vertex> scale(List<Vertex> vertices,double scaleX, double scaleY,Vector point) {
        if (scaleX == 1 && scaleY == 1){
            return vertices;
        }

        if (point == null) {
            point = Vertices.centre(vertices);
        }

        Vertex vertex;
        Vector delta;

        for (int i = 0; i < vertices.size(); i++) {
            vertex = vertices.get(i);
            delta = Vector.sub(vertex, point);
            vertices.get(i).setX(point.getX() + delta.getX() * scaleX);
            vertices.get(i).setY(point.getY() + delta.getY() * scaleY);
        }
        return vertices;
    }

    public static List<Vertex> clockwiseSort(List<Vertex> vertices) {
        Vector centre = Vertices.mean(vertices);
        vertices = vertices.stream().sorted((vertexA, vertexB)->{
            return (int)(Vector.angle(centre, vertexA) - Vector.angle(centre, vertexB));
        }).collect(Collectors.toList());
        return vertices;
    }

    public static Boolean isConvex(List<Vertex> vertices) {
        int flag = 0;
        int n = vertices.size();
        int i;
        int j;
        int k;
        double z;

        if (n < 3) {
            return null;
        }

        for (i = 0; i < n; i++) {
            j = (i + 1) % n;
            k = (i + 2) % n;
            z = (vertices.get(j).x - vertices.get(i).x) * (vertices.get(k).y - vertices.get(j).y);
            z -= (vertices.get(j).y - vertices.get(i).y) * (vertices.get(k).x - vertices.get(j).x);

            if (z < 0) {
                flag |= 1;
            } else if (z > 0) {
                flag |= 2;
            }

            if (flag == 3) {
                return false;
            }
        }

        if (flag != 0){
            return true;
        } else {
            return null;
        }
    }

    public static List<Vertex> hull(List<Vertex> vertices) {
        List<Vertex> upper = new ArrayList<>();
        List<Vertex> lower = new ArrayList<>();
        Vertex vertex;
        int i;

        // sort vertices on x-axis (y-axis for ties)
        vertices = vertices.subList(0, vertices.size());
       vertices = vertices.stream().sorted((vertexA, vertexB)-> {
           double dx = vertexA.x - vertexB.x;
           return (int)(dx != 0.0 ? dx : vertexA.y - vertexB.y);
       }).collect(Collectors.toList());

        // build lower hull
        for (i = 0; i < vertices.size(); i += 1) {
            vertex = vertices.get(i);

            while (lower.size() >= 2
                    && Vector.cross3(lower.get(lower.size() - 2), lower.get(lower.size() - 1), vertex) <= 0) {
                lower.remove(0);
            }

            lower.add(vertex);
        }

        // build upper hull
        for (i = vertices.size() - 1; i >= 0; i -= 1) {
            vertex = vertices.get(i);

            while (upper.size() >= 2
                    && Vector.cross3(upper.get(upper.size() - 2), upper.get(upper.size() - 1), vertex) <= 0) {
                upper.remove(0);
            }

            upper.add(vertex);
        }

        // concatenation of the lower and upper hulls gives the convex hull
        // omit last points because they are repeated at the beginning of the other list
        upper.remove(0);
        lower.remove(0);
        upper.addAll(lower);
        return upper;
    }


    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Vertex extends Vector{
        private double x;
        private double y;
        private int index;
        private Body body;
        private boolean isInternal;
    }

}
