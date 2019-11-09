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

        try {
            Graph<Integer, Integer> g0 = Archivo.read(null);

            int mx = 64;
            int my = 64;
            int sx = 72;
            int sy = 96;

            System.out.printf("%s%n%n", g0);
            System.out.println();

            System.out.println("%%%%%%%%% SHORTEST PATH %%%%%%%%%%");
            for (int i = 0; i < g0.getShortestPath(g0.getVertex(0), g0.getVertex(7)).size(); i++) {
                System.out.print(g0.getShortestPath(g0.getVertex(0), g0.getVertex(7)).get(i).getInfo() + " ");
            }
            System.out.println();
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            System.out.println();

//        try {
//            Archivo.safe(g0, null);
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        }
            SwingUtilities.invokeLater(() -> {
                new VentanaEjemplo("Ejemplo G0", g0).init(g0.getVertex(0), g0.getVertex(7));
            });

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
