package proyecto_ii_datos;

import graphs.GVertex;
import graphs.Graph;
import graphs.view.GraphPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

public class VentanaEjemplo extends JFrame {
    
    public VentanaEjemplo(String titulo, Graph<Integer, Integer> g)
            throws HeadlessException {
        super(titulo);
        this.g = g;
        
        configurar();
    }
    
    private void configurar() {
        ajustarComponentes(getContentPane());
        setResizable(true);
        setSize(800, 600);
        setMinimumSize(new Dimension(640, 480));
        setLocationRelativeTo(null);
        
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent e) {
                g.setActive(false);
                System.exit(0);
            }
            
        });
    }
    
    private void ajustarComponentes(Container c) {
        c.setLayout(new BorderLayout());
        c.add(BorderLayout.CENTER, panelPrincipal = new GraphPanel(g));
    }
    
    public void init(GVertex<Integer> origen, GVertex<Integer> destino) {
        setVisible(true);
        panelPrincipal.init();
        g.init(origen,destino);
    }
    
    private GraphPanel panelPrincipal;
    private final Graph<Integer, Integer> g;
    /* Graph<Object, Object> */
}
