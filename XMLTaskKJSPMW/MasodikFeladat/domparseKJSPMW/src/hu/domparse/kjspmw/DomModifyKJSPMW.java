package hu.domparse.kjspmw;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomModifyKJSPMW {
    public static void main(String args[]) {
        try {
            // DocumentBuilder inicializálása
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Dokumentum beolvasása
            Document document = builder.parse("..\\..\\ElsoFeladat\\XMLKJSPMW.xml");

            // Dokumentum módosítása
            modifyNodes(document);

            // Dokumentum kiírása a konzolra a módosítás után
            printXML(document);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void printXML(Document document) {
        try {
            // TransformerFactory és Transformer osztályok példányosítása
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Behúzás beállítása a transformerben
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // StringWriter osztály példányosítása, amiben eltároljuk a dokumentumot
            StringWriter stringWriter = new StringWriter();

            // Dokumentum string-gé alakítása
            transformer.transform(new DOMSource(document), new StreamResult(stringWriter));

            // Dokumentum kiírása a konzolra
            System.out.println(stringWriter.toString());
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private static void modifyNodes(Document document) {
        // Lekéri az összes Alkalmazott node-ot
        NodeList alkalmazottNodeList = document.getElementsByTagName("alkalmazott");

        // Végigiterál a node-okon
        for (int i = 0; i < alkalmazottNodeList.getLength(); i++) {
            Node node = alkalmazottNodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                // Lekéri a AlkalmazottNev node-ot
                Node alkalmazottNevNode = element.getElementsByTagName("alkalmazott_nev").item(0);

                // Módosítja a node értékét
                alkalmazottNevNode.setTextContent("alkalmazott" + (i + 1));
            }
        }
        // Lekéri az összes raktarAlkalmazott node-ot
        NodeList raktarAlkalmazottNodeList = document.getElementsByTagName("raktar_alkalmazott");

        // Végigiterál a node-okon
        for (int i = 0; i < raktarAlkalmazottNodeList.getLength(); i++) {
            Node node = raktarAlkalmazottNodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                Random random = new Random();

                int randomErtek = random.nextInt(5);
                // Módosítja az attribútum értékét
                element.setAttribute("raktar_id", String.valueOf(randomErtek));
            }
        }

        // Lekéri az összes termek node-ot
        NodeList termekNodeList = document.getElementsByTagName("termek");

        // Végigiterál a node-okon
        for (int i = 0; i < termekNodeList.getLength(); i++) {
            Node node = termekNodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                // Lekéri a termekRuha node-ot
                Node termekRuhaNode = element.getElementsByTagName("termek_ruha").item(0);

                // Módosítja a node értékét
                termekRuhaNode.setTextContent("termek" + (i + 1));
            }
        }

    }
}