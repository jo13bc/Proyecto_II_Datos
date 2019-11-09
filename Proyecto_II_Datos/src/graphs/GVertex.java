package graphs;

import java.awt.geom.Point2D;

public class GVertex<V> {

    public GVertex(V info, Point2D.Float position) {
        this.info = info; 
        this.position = position;  
        this.distance = 0;
        this.predecessor = null;
    }

    public GVertex(V info) {
        this(info, new Point2D.Float(0f, 0f));
    }

    public void setInfo(V info){
        this.info = info;
    }
    
    public V getInfo() {
        return info;
    }

    public Point2D.Float getPosition() {
        return position;
    }

    public void setPosition(Point2D.Float position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return String.format("{%s, (%4.2f, %4.2f), %d}",
                getInfo(), getPosition().getX(), getPosition().getY(), getDistance());
    }

    public GVertex<V> getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(GVertex<V> predecessor) {
        this.predecessor = predecessor;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
    
    private V info;
    private Point2D.Float position;
    private int distance;
    private GVertex<V> predecessor;
}
