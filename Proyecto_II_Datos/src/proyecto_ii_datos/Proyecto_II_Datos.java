package proyecto_ii_datos;

import graphs.Graph;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Proyecto_II_Datos {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JFrame.setDefaultLookAndFeelDecorated(true);
        } catch (ClassNotFoundException
                | IllegalAccessException
                | InstantiationException | UnsupportedLookAndFeelException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }

        new Proyecto_II_Datos().init();
    }

    public void init() {
        Graph<Integer, Integer> g0 = new Graph<>();

        int mx = 64;
        int my = 64;
        int sx = 72;
        int sy = 96;

        g0.add(0, new Point2D.Float(100, 350));
        g0.add(1, new Point2D.Float(250, 200));
        g0.add(2, new Point2D.Float(250, 500));
        g0.add(3, new Point2D.Float(400, 350));
        g0.add(4, new Point2D.Float(450, 200));
        g0.add(5, new Point2D.Float(600, 500));
        g0.add(6, new Point2D.Float(650, 200));
        g0.add(7, new Point2D.Float(750, 350));
        

        g0.add(0, 1, 4);
        g0.add(0, 2, 3);
        
        g0.add(2, 3, 12);
        g0.add(2, 5, 4);
        
        g0.add(1, 4, 8);
        g0.add(4, 6, 17);
        
        g0.add(3, 6, 20);
        g0.add(3, 7, 15);
        g0.add(3, 5, 2);
   
        g0.add(5, 7, 22);
        g0.add(6, 7, 9);
   

        System.out.printf("%s%n%n", g0);
        System.out.println();
        
        System.out.println("%%%%%%%%% SHORTEST PATH %%%%%%%%%%");
        for (int i=0; i<g0.getShortestPath(g0.getVertex(0), g0.getVertex(7)).size(); i++){
            System.out.print(g0.getShortestPath(g0.getVertex(0), g0.getVertex(7)).get(i).getId() + " ");
        }
        System.out.println();
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println();
        
        SwingUtilities.invokeLater(() -> {
            new VentanaEjemplo("Ejemplo G0", g0).init(g0.getVertex(0), g0.getVertex(7));
        });
        
        
    }

}
