package my.body;

import my.geometry.Vector;

import java.util.List;

/**
 * @author xuzefan  2018/10/23 14:19
 */
public class Body {

    Integer _inertiaScale = 4;
    Integer _nextCollidingGroupId = 1;
    Integer _nextNonCollidingGroupId = -1;
    Integer _nextCategory = 0x0001;

    public Integer id;

    public String type;

    public String label;

    public List<Body> parts;

    public Body parent;

    public Double angle = 0.0;

    public List<Vector> vertices;

    public Vector position;

    public Vector force;



}
