package domKJSPMW1115;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class DomQueryKJSPMW {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(
                "C:\\Roland\\Egyetem\\XML\\KJSPMW_1115\\DomQueryKJSPMW\\domKJSPMW1115\\KJSPMW_kurzusfelvetel.xml");

        NodeList kurzusokNodeList = doc.getElementsByTagName("kurzusnev");
        System.out.print("Kurzusnév: [");
        for (int i = 0; i < kurzusokNodeList.getLength(); i++) {
            Node kurzusNode = kurzusokNodeList.item(i);
            System.out.print(kurzusNode.getTextContent());
            if (i < kurzusokNodeList.getLength() - 1)
                System.out.print(", ");
        }

        System.out.println("]");

        // Első hallgató adatainak kiírása konzolra
        NodeList hallgatokNodeList = doc.getElementsByTagName("hallgato");
        Node elsoHallgatoNode = hallgatokNodeList.item(0);
        Element elsoHallgatoElem = (Element) elsoHallgatoNode;

        System.out.println("Hallgató neve: " + elsoHallgatoElem.getElementsByTagName("hnev").item(0).getTextContent());
        System.out.println("Születési év: " + elsoHallgatoElem.getElementsByTagName("szulev").item(0).getTextContent());
        System.out.println("Szak: " + elsoHallgatoElem.getElementsByTagName("szak").item(0).getTextContent());
        System.out.println();

        // Első hallgató adatainak kiírása fájlba
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("KJSPMW_lekerdezes.txt"))) {
            writer.write(
                    "Hallgató neve: " + elsoHallgatoElem.getElementsByTagName("hnev").item(0).getTextContent() + "\n");
            writer.write(
                    "Születési év: " + elsoHallgatoElem.getElementsByTagName("szulev").item(0).getTextContent() + "\n");
            writer.write("Szak: " + elsoHallgatoElem.getElementsByTagName("szak").item(0).getTextContent() + "\n\n");
        }

        // Kurzusokat oktatók nevének lekérdezése
        NodeList oktatokNodeList = doc.getElementsByTagName("oktato");
        HashSet<String> oktatokNeve = new HashSet<>();

        for (int i = 0; i < oktatokNodeList.getLength(); i++) {
            Node oktatoNode = oktatokNodeList.item(i);
            oktatokNeve.add(oktatoNode.getTextContent());
        }

        System.out.println("Oktatók nevei: " + oktatokNeve);

        String oktatoNeve = "Dr. Sasvári Péter";

        NodeList kurzusokNodeList2 = doc.getElementsByTagName("kurzus");

        for (int i = 0; i < kurzusokNodeList2.getLength(); i++) {
            Node kurzusNode = kurzusokNodeList2.item(i);
            Element kurzusElem = (Element) kurzusNode;

            NodeList kurzusOktatokNodeList = kurzusElem.getElementsByTagName("oktato");

            for (int j = 0; j < kurzusOktatokNodeList.getLength(); j++) {
                Node oktatoNode = kurzusOktatokNodeList.item(j);

                if (oktatoNode.getTextContent().equals(oktatoNeve)) {
                    System.out.println("Oktató neve: " + oktatoNeve);
                    System.out.println(
                            "Kurzusnév: " + kurzusElem.getElementsByTagName("kurzusnev").item(0).getTextContent());
                    System.out.println("Kredit: " + kurzusElem.getElementsByTagName("kredit").item(0).getTextContent());
                    System.out.println("Hely: " + kurzusElem.getElementsByTagName("hely").item(0).getTextContent());
                    System.out
                            .println("Időpont: " + kurzusElem.getElementsByTagName("idopont").item(0).getTextContent());
                    System.out.println();
                    break;
                }
            }
        }
    }
}