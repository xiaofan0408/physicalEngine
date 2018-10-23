package my.geometry;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.body.Body;

/**
 * @author xuzefan  2018/10/23 14:20
 */
public class Vertices {



    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class Vertex {
        private double x;
        private double y;
        private int index;
        private Body body;
        private boolean isInternal;
    }

}
