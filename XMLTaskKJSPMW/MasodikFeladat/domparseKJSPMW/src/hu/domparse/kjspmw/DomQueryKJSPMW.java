package hu.domparse.kjspmw;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class DomQueryKJSPMW {
    public static void main(String args[]) {
        try {
            // DocumentBuilder inicializálása
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Dokumentum beolvasása
            Document document = builder.parse("..\\..\\ElsoFeladat\\XMLKJSPMW.xml");

            // Üres sor beszúrása a konzolon, a jobb olvashatóság érdekében
            System.out.println();

            System.out.println("Összes vevő nevének lekérdezése:");
            // Összes vevő nevének lekérdezése
            NodeList vevoList = document.getElementsByTagName("vevo");
            // Végigiterál az összes Aruhaz Node-on
            for (int i = 0; i < vevoList.getLength(); i++) {
                Node node = vevoList.item(i);
                // Megnézi, hogy az elem elem típusú-e
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element vevo = (Element) node;

                    // Kiírja az vevő nevét
                    System.out.println(" Vevő neve: "
                            + vevo.getElementsByTagName("nev").item(0).getTextContent());
                }
            }

            // Üres sor beszúrása a konzolon, a jobb olvashatóság érdekében
            System.out.println();

            System.out.println("Raktár-Alkalmazott kapcsolat lekérdezése:");

            // Raktár-alkalmazott kapcsolat lekérdezése
            NodeList raktarAlkalmazottKapcsolatList = document.getElementsByTagName("raktar_alkalmazott");

            // Összes alkalmazott lekérdezése
            NodeList alkalmazottList = document.getElementsByTagName("alkalmazott");

            // Összes raktár lekérdezése
            NodeList raktarList = document.getElementsByTagName("raktar");

            // Végigiterál az összes Raktar-Alkalmazott Node-on
            for (int i = 0; i < raktarAlkalmazottKapcsolatList.getLength(); i++) {
                Node node = raktarAlkalmazottKapcsolatList.item(i);

                // Inicializál egy stringet, ami a kiírandó sor lesz
                String kiirtSor = "";

                // Megnézi, hogy az elem elem típusú-e
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element raktarAlkalmazottElement = (Element) node;

                    // Végigiterál az összes alkalmazotton
                    for (int j = 0; j < alkalmazottList.getLength(); j++) {
                        Node alkalmazottNode = alkalmazottList.item(j);

                        // Megnézi, hogy az elem elem típusú-e
                        if (alkalmazottNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element alkalmazott = (Element) alkalmazottNode;

                            // Megnézi, hogy az alkalmazott ID-ja megegyezik-e a raktarAlkalmazott
                            // alkalmazott ID-jával az
                            // raktár-alkalmazott kapcsolatban
                            if (alkalmazott.getAttribute("alkalmazott_id")
                                    .equals(raktarAlkalmazottElement.getAttribute("alkalmazott_id"))) {
                                // Hozzáadja a kiírandó sorhoz az alkalmazott nevét
                                kiirtSor += "Alkalmazott neve: "
                                        + alkalmazott.getElementsByTagName("alkalmazott_nev").item(0).getTextContent()
                                        + " ";
                            }
                        }
                    }

                    // Végigiterál az összes raktáron
                    for (int j = 0; j < raktarList.getLength(); j++) {
                        Node raktarNode = raktarList.item(j);
                        // Megnézi, hogy az elem elem típusú-e
                        if (raktarNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element raktar = (Element) raktarNode;

                            // Megnézi, hogy a raktar ID-ja megegyezik-e a raktarAlkalmazott raktar ID-jával
                            // az
                            // RaktárAlkalmazott kapcsolatban
                            if (raktar.getAttribute("raktar_id")
                                    .equals(raktarAlkalmazottElement.getAttribute("raktar_id"))) {
                                // Hozzáadja a kiírandó sorhoz a raktar tipusat
                                kiirtSor += "Raktar Tipus: "
                                        + raktar.getElementsByTagName("raktar_tipus").item(0).getTextContent();
                            }
                        }
                    }

                    // Kiírja a kapcsolatban álló alkalmazottak nevét és raktárak tipusát
                    System.out.println(kiirtSor);
                }
            }

            // Üres sor beszúrása a konzolon, a jobb olvashatóság érdekében
            System.out.println();

            System.out.println("Összes Szerencsen élő vevő lekérdezése:");
            // Összes vevő lekérdezése
            vevoList = document.getElementsByTagName("vevo");
            // Végigiterál az összes vevo Node-on
            for (int i = 0; i < vevoList.getLength(); i++) {
                Node node = vevoList.item(i);
                // Megnézi, hogy az elem elem típusú-e
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element vevo = (Element) node;
                    // Lekéri a címet
                    Node cimNode = vevo.getElementsByTagName("cim").item(0);
                    Element cim = (Element) cimNode;
                    // Lekéri a települést
                    Node telepulesNode = cim.getElementsByTagName("telepules").item(0);

                    // Ha a vevő Szerencsen lakik, akkor kiírja az vevő telefonszámát, nevét és
                    // címét
                    if (telepulesNode.getTextContent().equals("Szerencs")) {
                        System.out.println(" Vevő neve: "
                                + vevo.getElementsByTagName("nev").item(0).getTextContent() + " Címe: "
                                + cim.getElementsByTagName("iranyitoszam").item(0).getTextContent() + ", "
                                + cim.getElementsByTagName("telepules").item(0).getTextContent() + ", "
                                + cim.getElementsByTagName("utca").item(0).getTextContent() + ", "
                                + cim.getElementsByTagName("hazszam").item(0).getTextContent() + " Telefonszam: " +
                                vevo.getElementsByTagName("telefonszam").item(0).getTextContent());
                    }
                }
            }

            // Üres sor beszúrása a konzolon, a jobb olvashatóság érdekében
            System.out.println();

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}