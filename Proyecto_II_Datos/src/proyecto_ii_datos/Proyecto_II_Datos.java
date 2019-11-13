package proyecto_ii_datos;

import files.Archivo;
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

        int mx = 64;
        int my = 64;
        int sx = 72;
        int sy = 96;
        try {
//            Graph<Integer, Integer> g0 = Archivo.read(null);
            
            Graph<Integer, Integer> g0 = new Graph<>();
            
            g0.add(0, new Point2D.Float(50, 170));
            g0.add(1, new Point2D.Float(165, 185));
            g0.add(2, new Point2D.Float(286, 200));
            g0.add(3, new Point2D.Float(406, 210));
            g0.add(4, new Point2D.Float(530, 220));
            g0.add(5, new Point2D.Float(648, 235));
            g0.add(6, new Point2D.Float(762, 245));
            g0.add(7, new Point2D.Float(882, 260));
            
            g0.add(8, new Point2D.Float(50, 292));
            g0.add(9, new Point2D.Float(165, 307));
            g0.add(10, new Point2D.Float(286, 318));
            g0.add(11, new Point2D.Float(406, 332));
            g0.add(12, new Point2D.Float(530, 342));
            g0.add(13, new Point2D.Float(645, 355));
            g0.add(14, new Point2D.Float(763, 365));
            g0.add(15, new Point2D.Float(881, 374));
            
            g0.add(16, new Point2D.Float(50, 414));
            g0.add(17, new Point2D.Float(170, 429));
            g0.add(18, new Point2D.Float(294, 440));
            g0.add(19, new Point2D.Float(410, 451));
            g0.add(20, new Point2D.Float(527, 460));
            g0.add(21, new Point2D.Float(642, 468));
            g0.add(22, new Point2D.Float(760, 479));
            g0.add(23, new Point2D.Float(884, 490));
            
            //Primera fila
            g0.add(0, 1, 4);
            g0.add(0, 8, 3);
            
            g0.add(1, 2, 8);
            g0.add(1, 9, 5);

            g0.add(2, 3, 12);
            g0.add(2, 10, 4);
            
            g0.add(3, 4, 20);
            g0.add(3, 11, 15);

            g0.add(4, 5, 17);
            g0.add(4, 12, 7);

            g0.add(5, 6, 22);
            g0.add(5, 13, 30);
            
            g0.add(6, 7, 19);
            g0.add(6, 14, 9);
            
            g0.add(7, 15, 17);
            
            //Segunda fila
            g0.add(8, 9, 4);
            g0.add(8, 16, 3);
            
            g0.add(9, 10, 8);
            g0.add(9, 17, 5);

            g0.add(10, 11, 12);
            g0.add(10, 18, 4);
            
            g0.add(11, 12, 20);
            g0.add(11, 19, 15);

            g0.add(12, 13, 17);
            g0.add(12, 20, 7);

            g0.add(13, 14, 22);
            g0.add(13, 21, 30);
            
            g0.add(14, 15, 19);
            g0.add(14, 22, 9);
            
            g0.add(15, 23, 17);
            
            //Tercera fila
            g0.add(16, 17, 4);
            
            g0.add(17, 18, 8);

            g0.add(18, 19, 12);
            
            g0.add(19, 20, 20);

            g0.add(20, 21, 17);

            g0.add(21, 22, 22);
            
            g0.add(22, 23, 19);
            
            Archivo.safe(g0, null);

            System.out.printf("%s%n%n", g0);
            System.out.println();

            System.out.println("%%%%%%%%% SHORTEST PATH %%%%%%%%%%");
            for (int i = 0; i < g0.getShortestPath(g0.getVertex(0), g0.getVertex(7)).size(); i++) {
                System.out.print(g0.getShortestPath(g0.getVertex(0), g0.getVertex(7)).get(i).getInfo() + " ");
            }
            System.out.println();
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            System.out.println();

            SwingUtilities.invokeLater(() -> {
                new VentanaEjemplo("Ejemplo G0", g0).init(g0.getVertex(0), g0.getVertex(7));
            });
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

}
