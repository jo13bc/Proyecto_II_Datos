/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import graphs.Graph;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

enum VARIABLES {
    VERTEX("vertex"),
    INFO("info"),
    X("x"),
    Y("y"),
    DISTANCIA("distancia"),
    PREDECESOR("predecesor"),
    EDGE("edge"),
    TAIL("tail"),
    HEAD("head"),
    FILE_NAME("datos"),
    FILE_TITLE("datos"),
    XML_EXTENSION(".xml");

    private final String value;

    VARIABLES(String envUrl) {
        this.value = envUrl;
    }

    public String getValue() {
        return value;
    }
}

public class Archivo {

    public static Graph<Integer, Integer> read(String ubicacion) throws Exception{
        Graph<Integer, Integer> graph = new Graph();
        ubicacion = (ubicacion == null) ? VARIABLES.FILE_NAME.getValue() + VARIABLES.XML_EXTENSION.getValue() : ubicacion;
        try {
            File archivo = new File(ubicacion);

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(archivo);
            doc.getDocumentElement().normalize();

            NodeList as = doc.getElementsByTagName(VARIABLES.VERTEX.getValue());

            for (int i = 0; i < as.getLength(); i++) {
                Node n = as.item(i);

                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    Element a = (Element) n;
                    graph.add(
                            Integer.parseInt(a.getAttribute(VARIABLES.INFO.getValue())),
                            new Point2D.Float(
                                    Float.parseFloat(a.getAttribute(VARIABLES.X.getValue())),
                                    Float.parseFloat(a.getAttribute(VARIABLES.Y.getValue()))
                            )
                    );
                }
            }
            as = doc.getElementsByTagName(VARIABLES.EDGE.getValue());
            for (int i = 0; i < as.getLength(); i++) {
                Node n = as.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    Element a = (Element) n;
                    graph.add(
                            Integer.parseInt(a.getAttribute(VARIABLES.TAIL.getValue())),
                            Integer.parseInt(a.getAttribute(VARIABLES.HEAD.getValue())),
                            Integer.parseInt(a.getAttribute(VARIABLES.INFO.getValue()))
                    );
                }
            }

        } catch (IOException | NumberFormatException | ParserConfigurationException | SAXException ex) {
            throw new Exception(ex.getMessage());
        }

        return graph;
    }

    public static <V, W> void safe(Graph<V, W> graph, String ruta) throws Exception {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement(VARIABLES.FILE_TITLE.getValue());
            doc.appendChild(rootElement);

            //Para recorrer el HashMap
            graph.getAllVertices().forEach((vertex) -> {
                Element nodo = doc.createElement(VARIABLES.VERTEX.getValue());
                rootElement.appendChild(nodo);

                nodo.setAttributeNode(createAtribute(doc, VARIABLES.INFO.getValue(), vertex.getInfo()));
                //Position{
                nodo.setAttributeNode(createAtribute(doc, VARIABLES.X.getValue(), vertex.getPosition().getX()));
                nodo.setAttributeNode(createAtribute(doc, VARIABLES.Y.getValue(), vertex.getPosition().getY()));
                //}
            });

            graph.getAllEdges().forEach((edge) -> {
                Element nodo = doc.createElement(VARIABLES.EDGE.getValue());
                rootElement.appendChild(nodo);

                nodo.setAttributeNode(createAtribute(doc, VARIABLES.TAIL.getValue(), edge.getTail().getInfo()));
                nodo.setAttributeNode(createAtribute(doc, VARIABLES.HEAD.getValue(), edge.getHead().getInfo()));
                nodo.setAttributeNode(createAtribute(doc, VARIABLES.INFO.getValue(), edge.getInfo()));
            });

            //Para escribir el contenido en un archivo .xml
            Source source = new DOMSource(doc);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();

            String r = (ruta == null || ruta.isEmpty()) ? VARIABLES.FILE_NAME.getValue() : ruta;
            r = (!r.contains(VARIABLES.XML_EXTENSION.getValue())) ? r + VARIABLES.XML_EXTENSION.getValue() : r;

            transformer.transform(source, new StreamResult(new java.io.File(r)));
        } catch (ParserConfigurationException | TransformerException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public static Attr createAtribute(Document doc, String name, Object value) {
        Attr attr = doc.createAttribute(name);
        attr.setValue(String.valueOf(value));
        return attr;
    }

}
