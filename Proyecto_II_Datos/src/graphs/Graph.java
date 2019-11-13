package graphs;



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Queue;
import java.util.Stack;

public class Graph<V, E> {

    public Graph() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public GVertex<V> getVertex(V v) {
        GVertex<V> r = null;
        Iterator<GVertex<V>> i = vertices.iterator();
        while (i.hasNext()) {
            GVertex<V> t = i.next();
            if (t.getInfo().equals(v)) {
                r = t;
                break;
            }
        }
        return r;
    }

    public List<GVertex<V>> getAdjacent(GVertex<V> v) {
        List<GVertex<V>> r = new LinkedList<>();
        Iterator<Edge<V, E>> i = edges.iterator();
        while (i.hasNext()) {
            Edge<V, E> e = i.next();
            if (e.getHead().getInfo().equals(v.getInfo())) {
                r.add(e.getTail());
            }
            if (e.getTail().getInfo().equals(v.getInfo())) {
                r.add(e.getHead());
            }
        }
        return r;
    }

    public void add(V v, Point2D.Float position) {
        vertices.add(new GVertex<>(v, position));
    }

    public void add(V v) {
        vertices.add(new GVertex<>(v, new Point2D.Float(DX + df.x, DY + df.y)));

        if (px < MX) {
            df.x += DX;
            px++;
        } else {
            df.x = 0;
            df.y += DY;
            px = 0;
        }
    }

    public void add(GVertex<V> tail, GVertex<V> head, E w) {
        if ((tail == null) || (head == null)) {
            throw new NullPointerException("No existe el vértice.");
        }
        edges.add(new Edge<>(tail, head, w));
    }

    public void add(V t, V h, E w) {
        add(getVertex(t), getVertex(h), w);
    }

    public void add(V t, V h) {
        add(t, h, null);

    }

    public void init(GVertex<V> pathStart, GVertex<V> pathEnd) {
        setActive(true);
        new Thread() {
            @Override
            public void run() {
                boolean hasStarted = false;
                ArrayList<GVertex<V>> shortestPath = getShortestPath(pathStart, pathEnd);
                Stack<GVertex<V>> pila = new Stack<>();
                for (int i = shortestPath.size() - 1; i >= 0; i--) {
                    pila.push(shortestPath.get(i));
                }
                GVertex<V> v0 = pathStart;
                while (isActive()) {

                    try {
                        p0 = v0.getPosition();

                        // Se define el criterio para seleccionar
                        // el siguiente vértice.
                        GVertex<V> v1 = pila.pop();

                        p1 = v1.getPosition();

                        if (!hasStarted) {
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException ex) {
                            }
                            hasStarted = true;
                        }

                        System.out.printf("v(%s): %s%n", v0.getInfo(), p0);
                        System.out.printf("v(%s): %s%n", v1.getInfo(), p1);

                        t = 0.0;
                        while (t <= 1.0) {
                            t += DT;
                            try {
                                /*la velocidad en una arista (calle) es MAX_WAIT multiplicado por el peso de
                                la arista en ese momento*/
                                Thread.sleep(MAX_WAIT * thereRelation(v1.getPredecessor(), v1).getInfo());
                            } catch (InterruptedException ex) {
                            }
                        }
                        v0 = v1;
                    } catch (NullPointerException ex) {
                        System.err.println("Hay que eliminar el thread but idk");
                        if (pila.isEmpty()) {
                            break;
                        }
                    }
                }
            }

        }.start();
    }

    @Override
    public String toString() {
        return String.format("G: (%n   V: %s,%n   E: %s%n)",
                vertices, edges);
    }

    public Rectangle getBounds() {
        float x0, x1, y0, y1;
        x0 = x1 = y0 = y1 = 0f;
        boolean f = false;

        Iterator<GVertex<V>> i = vertices.iterator();
        while (i.hasNext()) {
            GVertex<V> v = i.next();

            if (!f) {
                x0 = x1 = v.getPosition().x;
                y0 = y1 = v.getPosition().y;
            }
            f = true;

            x0 = Math.min(x0, v.getPosition().x);
            x1 = Math.max(x1, v.getPosition().x);
            y0 = Math.min(y0, v.getPosition().y);
            y1 = Math.max(y1, v.getPosition().y);
        }

        if (!f) {
            throw new IllegalArgumentException();
        }

        Rectangle r = new Rectangle(
                (int) (x0), (int) (y0),
                (int) (x1 - x0), (int) (y1 - y0)
        );
        r.grow(S0 / 2, S0 / 2);
        return r;
    }

    public void paint(Graphics bg, Rectangle bounds) {
        Graphics2D g = (Graphics2D) bg;

        Iterator<Edge<V, E>> i = edges.iterator();
        while (i.hasNext()) {
            Edge<V, E> e = i.next();

            g.setStroke(TRAZO_BASE);
            g.setColor(Color.WHITE);

            /*LO QUE MAS PUEDE ASEMEJARSE(SIMULAR) EL CAMINO (LA CALLE)*/
            g.drawLine(
                    (int) e.getTail().getPosition().x,
                    (int) e.getTail().getPosition().y,
                    (int) e.getHead().getPosition().x,
                    (int) e.getHead().getPosition().y
            );
        }

        g.setFont(TIPO_BASE);
        FontMetrics fm = g.getFontMetrics();

        g.setStroke(TRAZO_VERTICE);
        Iterator<GVertex<V>> j = vertices.iterator();
        while (j.hasNext()) {
            GVertex<V> v = j.next();

            String t = String.format("%s", v.getInfo());
            g.setColor(Color.BLACK);
            g.drawString(t,
                    v.getPosition().x - fm.stringWidth(t) / 2,
                    v.getPosition().y + fm.getAscent() / 2);
        }
        if (p0 != null) {

            g.setStroke(TRAZO_MARCADOR);

            g.setColor(Color.ORANGE);
            g.drawLine(
                    (int) p0.x,
                    (int) p0.y,
                    (int) p1.x,
                    (int) p1.y
            );

            g.setColor(Color.BLACK);
            g.drawOval(
                    (int) ((p0.x + t * (p1.x - p0.x)) - S1 / 2),
                    (int) ((p0.y + t * (p1.y - p0.y)) - S1 / 2),
                    S1, S1);
            g.setColor(Color.ORANGE);
            g.drawOval(
                    (int) ((p0.x + t * (p1.x - p0.x)) - S2 / 2),
                    (int) ((p0.y + t * (p1.y - p0.y)) - S2 / 2),
                    S2, S2);
        }
    }

    public void update(Observable obs, Object evt) {
        throw new UnsupportedOperationException();
    }

    public String getAdjacencyInfo() {
        StringBuilder r = new StringBuilder();
        Iterator<GVertex<V>> i = vertices.iterator();
        while (i.hasNext()) {
            GVertex<V> v = i.next();
            r.append(String.format("%s: ", v.getInfo()));
            Iterator<GVertex<V>> j = getAdjacent(v).iterator();
            while (j.hasNext()) {
                r.append(String.format("%s, ", j.next().getInfo()));
            }
            r.append("\n");
        }
        return r.toString();
    }

    //////////////////////////////////////////
    public void dijkstraShortestPathAlgorithm(GVertex<V> origen) {

        Queue<GVertex<Integer>> toEvaluate = new LinkedList<>();
        ArrayList<GVertex<Integer>> closedList = new ArrayList<>();

        vertices.stream().map((v) -> (GVertex<Integer>)v).forEachOrdered((aux) -> {
            if (aux.equals(origen)) {
                aux.setDistance(0);
                aux.setPredecessor(null);
                toEvaluate.add(aux);
            } else {
                aux.setDistance(Integer.MAX_VALUE);
                aux.setPredecessor(null);
            }
        });

        while (!toEvaluate.isEmpty()) {
            GVertex<Integer> evaluating = toEvaluate.remove();

            getAdjacent((GVertex<V>) evaluating).stream().map((v) -> (GVertex<Integer>)v).map((aux) -> {
                if (thereRelation((GVertex<V>) evaluating, (GVertex<V>) aux) != null) {
                    Edge<GVertex<V>, Integer> edge = thereRelation((GVertex<V>) evaluating, (GVertex<V>) aux);
                    if (aux.getDistance() > evaluating.getDistance() + (int) edge.getInfo()) {
                        aux.setDistance(evaluating.getDistance() + (int) edge.getInfo());
                        aux.setPredecessor(evaluating);
                    }
                }
                /*Agregar a toEvaluate si no esta en closedList*/
                return aux;
            }).filter((aux) -> (!isInThere(aux, closedList))).forEachOrdered((aux) -> {
                toEvaluate.add((GVertex<Integer>) aux);
            });
            closedList.add(evaluating);

        }

    }

    public ArrayList<GVertex<V>> getAllVertices() {
        return vertices;
    }

    public ArrayList<Edge<V, E>> getAllEdges() {
        return edges;
    }

    public ArrayList<GVertex<V>> getShortestPath(GVertex<V> origen, GVertex<V> destino) {

        ArrayList<GVertex<V>> shortestPath = new ArrayList<>();

        dijkstraShortestPathAlgorithm(origen);

        while (destino != null) {

            shortestPath.add(destino);
            destino = destino.getPredecessor();
        }

        return shortestPath;
    }

    public Edge<GVertex<V>, Integer> thereRelation(GVertex<V> tail, GVertex<V> head) {
        Edge<GVertex<V>, Integer> res = null;
        for (Edge<V, E> v : edges) {
            if (v.getTail().equals(tail) && v.getHead().equals(head)) {
                res = (Edge<GVertex<V>, Integer>)v;
                break;
            }
        }
        return res;
    }

    private boolean isInThere(GVertex<Integer> node, ArrayList<GVertex<Integer>> array) {
        boolean res = false;
        for (GVertex<Integer> v : array) {
            if (v.getInfo().equals(node.getInfo())) {
                res = true;
                break;
            }
        }
        return res;
    }

    //////////////////////////////////////////
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    private static final float[] DASHES = {4f, 4f};
    private static final Stroke TRAZO_BASE
            = new BasicStroke(5f,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0f, null, 0f);
    private static final Stroke TRAZO_VERTICE = new BasicStroke(2f);
    private static final Stroke TRAZO_MARCADOR
            = new BasicStroke(8f,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0f, null, 0f);

    private static final Stroke TRAZO_GUIA
            = new BasicStroke(1.0f,
                    BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                    0f, DASHES, 0f);
    private static final Font TIPO_BASE
            = new Font(Font.SANS_SERIF, Font.PLAIN, 24);

    private static final int S0 = 5; //tamano de los nodos
    private static final int S1 = 20; //tamano del circulo rojo que se mueve
    private static final int S2 = S1 - S1 / 2; //tamano del circulo rojo que se mueve

    private static final int DX = 72;
    private static final int DY = 64;
    private static final int MX = 6;
    private int px = 0;
    private final Point2D.Float df = new Point2D.Float(0, 0);

    private final ArrayList<GVertex<V>> vertices;
    private final ArrayList<Edge<V, E>> edges;

    private static final int MAX_WAIT = 40; //velocidad del circulo rojo que se mueve
    private boolean active = false;
    private Point2D.Float p0;
    private Point2D.Float p1;
    private static final double DT = 0.045; //velocidad del circulo rojo que se mueve
    private double t = 0.0;
}
