package hu.domparse.kjspmw;

import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomReadKJSPMW {
    public static void main(String args[]) {
        try {
            // DocumentFactory inicializálása
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // DocumentBuilder inicializálása
            DocumentBuilder builder = factory.newDocumentBuilder();

            File file = new File("..\\..\\ElsoFeladat\\XMLKJSPMW.xml");

            // Dokumentum betöltése
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            // Kimeneti fájl inicializálása
            PrintWriter outfile = new PrintWriter(new File("..\\XMLKJSPMW2.xml"), "UTF-8");

            // XML adatok kiírása
            printToFileAndConsole("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", System.out, outfile);
            printToFileAndConsole(
                    "<Futobolt_KJSPMW xmlns:xs=\"http://www.w3.org/2001/XMLSchema-instance\" xs:noNamespaceSchemaLocation=\"XMLSchemaKJSPMW.xsd\">",
                    System.out, outfile);

            // Rendeles beolvasása
            readRendeles(doc, outfile);

            // Raktárak beolvasása
            readRaktarak(doc, outfile);

            // RaktarAlkalmazott kapcsolatok beolvasása
            readRaktarAlkalmazott(doc, outfile);

            // Alkalmazottak beolvasása
            readAlkalmazott(doc, outfile);

            // Termékek beolvasása
            readTermekek(doc, outfile);

            // Vevők beolvasása
            readVevok(doc, outfile);

            // XML gyökérelem lezárása
            printToFileAndConsole("</Futobolt_KJSPMW>", System.out, outfile);

            // Kimeneti fájl lezárása
            outfile.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Kiíró metódus
    private static void printToFileAndConsole(final String msg, PrintStream console, PrintWriter file) {
        console.println(msg);
        file.println(msg);
    }

    // Eleme kiírás formázó metódus
    private static void printElement(String elementName, String content, PrintWriter file) {
        printToFileAndConsole("        <" + elementName + ">" + content + "</" + elementName + ">", System.out,
                file);
    }

    // Cím kiírás formázó metódus
    private static void printCim(Element cimElement, PrintWriter file) {
        printToFileAndConsole("            <" + cimElement.getNodeName() + ">", System.out, file);
        printToFileAndConsole(
                "                <" + cimElement.getElementsByTagName("iranyitoszam").item(0).getNodeName()
                        + ">" + cimElement.getElementsByTagName("iranyitoszam").item(0).getTextContent() + "</"
                        + cimElement.getElementsByTagName("iranyitoszam").item(0).getNodeName() + ">",
                System.out, file);
        printToFileAndConsole(
                "                <" + cimElement.getElementsByTagName("telepules").item(0).getNodeName()
                        + ">" + cimElement.getElementsByTagName("telepules").item(0).getTextContent() + "</"
                        + cimElement.getElementsByTagName("telepules").item(0).getNodeName() + ">",
                System.out, file);
        printToFileAndConsole(
                "                <" + cimElement.getElementsByTagName("utca").item(0).getNodeName() + ">"
                        + cimElement.getElementsByTagName("utca").item(0).getTextContent() + "</"
                        + cimElement.getElementsByTagName("utca").item(0).getNodeName() + ">",
                System.out, file);
        printToFileAndConsole("                <" + cimElement.getElementsByTagName("hazszam").item(0).getNodeName()
                + ">" + cimElement.getElementsByTagName("hazszam").item(0).getTextContent() + "</"
                + cimElement.getElementsByTagName("hazszam").item(0).getNodeName() + ">", System.out, file);
        printToFileAndConsole("            </" + cimElement.getNodeName() + ">", System.out, file);
    }

    // Rendeléseket beolvasó metódus
    private static void readRendeles(Document document, PrintWriter file) {
        NodeList rendelesList = document.getElementsByTagName("rendeles");
        for (int temp = 0; temp < rendelesList.getLength(); temp++) {
            Node node = rendelesList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element rendelesElement = (Element) node;
                String datum = rendelesElement.getElementsByTagName("datum").item(0).getTextContent();
                String rendelesId = rendelesElement.getAttribute("rendeles_id");
                String fizetes = rendelesElement.getElementsByTagName("rendeles_fizetes").item(0).getTextContent();
                String ar = rendelesElement.getElementsByTagName("rendeles_ar").item(0).getTextContent();

                printToFileAndConsole("    <rendeles rendeles_id=\"" + rendelesId + "\">", System.out, file);
                printElement("datum", datum, file);
                printElement("rendeles_fizetes", fizetes, file);
                printElement("rendeles_ar", ar, file);
                printToFileAndConsole("    </rendeles>", System.out, file);
            }
        }
    }

    // Raktárakat beolvasó metódus
    private static void readRaktarak(Document document, PrintWriter file) {
        NodeList raktarList = document.getElementsByTagName("raktar");
        for (int temp = 0; temp < raktarList.getLength(); temp++) {
            Node node = raktarList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element raktarElement = (Element) node;
                String raktar_id = raktarElement.getAttribute("raktar_id");
                String rendeles_id = raktarElement.getAttribute("rendeles_id");
                String ra_darabszam = raktarElement.getElementsByTagName("ra_darabszam").item(0).getTextContent();
                String raktar_ar = raktarElement.getElementsByTagName("raktar_ar").item(0).getTextContent();
                String raktar_tipus = raktarElement.getElementsByTagName("raktar_tipus").item(0).getTextContent();

                printToFileAndConsole("    <raktar raktar_id=\"" + raktar_id + "\" rendeles_id=\""
                        + rendeles_id + "\">", System.out, file);
                printElement("ra_darabszam", ra_darabszam, file);
                printElement("raktar_ar", raktar_ar, file);
                printElement("raktar_tipus", raktar_tipus, file);
                printToFileAndConsole("    </raktar>", System.out, file);
            }

        }
    }

    // Raktar-Alkalmazott kapcsolatokat beolvasó metódus
    private static void readRaktarAlkalmazott(Document document, PrintWriter file) {
        NodeList raktarAlkalmazottList = document.getElementsByTagName("raktar_alkalmazott");
        for (int temp = 0; temp < raktarAlkalmazottList.getLength(); temp++) {
            Node node = raktarAlkalmazottList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element raktarAlkalmazottElement = (Element) node;
                String elerheto = raktarAlkalmazottElement
                        .getElementsByTagName("elerheto").item(0).getTextContent();
                String raktar_id = raktarAlkalmazottElement.getAttribute("raktar_id");
                String alkalmazott_id = raktarAlkalmazottElement.getAttribute("alkalmazott_id");

                printToFileAndConsole("    <raktar_alkalmazott raktar_id=\"" + raktar_id + "\" alkalmazott_id=\""
                        + alkalmazott_id + "\">", System.out, file);
                printToFileAndConsole("          <elerheto>" + elerheto
                        + "</elerheto>", System.out, file);
                printToFileAndConsole("    </raktar_alkalmazott>", System.out, file);
            }
        }
    }

    // Alkalmazott beolvasó metódus
    private static void readAlkalmazott(Document document, PrintWriter file) {
        NodeList alkalmazottList = document.getElementsByTagName("alkalmazott");
        for (int temp = 0; temp < alkalmazottList.getLength(); temp++) {
            Node node = alkalmazottList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element alkalmazottElement = (Element) node;
                String alkalmazott_nev = alkalmazottElement.getElementsByTagName("alkalmazott_nev").item(0)
                        .getTextContent();
                String alkalmazott_id = alkalmazottElement.getAttribute("alkalmazott_id");
                String alkalmazott_fizetes = alkalmazottElement.getElementsByTagName("alkalmazott_fizetes").item(0)
                        .getTextContent();
                String alkalmazott_beosztas = alkalmazottElement.getElementsByTagName("alkalmazott_beosztas").item(0)
                        .getTextContent();
                String alkalmazott_csatlakozas = alkalmazottElement.getElementsByTagName("alkalmazott_csatlakozas")
                        .item(0).getTextContent();

                printToFileAndConsole("    <alkalmazott alkalmazott_id=\"" + alkalmazott_id + "\">", System.out,
                        file);
                printElement("alkalmazott_nev", alkalmazott_nev, file);
                printElement("alkalmazott_fizetes", alkalmazott_fizetes, file);
                printElement("alkalmazott_beosztas", alkalmazott_beosztas, file);
                printElement("alkalmazott_csatlakozas", alkalmazott_csatlakozas, file);
                printToFileAndConsole("    </alkalmazott>", System.out, file);
            }
        }
    }

    // Termékeket beolvasó metódus
    private static void readTermekek(Document document, PrintWriter file) {
        NodeList TermekekList = document.getElementsByTagName("termek");
        for (int temp = 0; temp < TermekekList.getLength(); temp++) {
            Node node = TermekekList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element TermekElement = (Element) node;
                String termek_id = TermekElement.getAttribute("termek_id");
                String rendeles_id = TermekElement.getAttribute("rendeles_id");
                String termek_cipo = TermekElement.getElementsByTagName("termek_cipo").item(0).getTextContent();
                String termek_ruha = TermekElement.getElementsByTagName("termek_ruha").item(0).getTextContent();
                String termek_kiegeszito = TermekElement.getElementsByTagName("termek_kiegeszito").item(0)
                        .getTextContent();

                printToFileAndConsole("    <termek termek_id=\"" + termek_id + "\" rendeles_id=\""
                        + rendeles_id + "\">", System.out, file);
                printElement("termek_cipo", termek_cipo, file);
                printElement("termek_ruha", termek_ruha, file);
                printElement("termek_kiegeszito", termek_kiegeszito, file);
                printToFileAndConsole("    </termek>", System.out, file);

            }
        }
    }

    // Vevőket beolvasó metódus
    private static void readVevok(Document document, PrintWriter file) {
        NodeList vevokList = document.getElementsByTagName("vevo");
        for (int temp = 0; temp < vevokList.getLength(); temp++) {
            Node node = vevokList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element vevoElement = (Element) node;
                String vevo_id = vevoElement.getAttribute("id");
                String rendeles_id = vevoElement.getAttribute("rendeles_id");
                String vevo_nev = vevoElement.getElementsByTagName("nev").item(0).getTextContent();
                String vevo_email = vevoElement.getElementsByTagName("email").item(0).getTextContent();
                NodeList vevo_telefon = vevoElement.getElementsByTagName("telefonszam");
                Element vevo_cim = (Element) vevoElement.getElementsByTagName("cim").item(0);
                String vevo_szemelyi = vevoElement.getElementsByTagName("szemelyigazolvany_szam").item(0)
                        .getTextContent();
                printToFileAndConsole("    <vevo id=\"" + vevo_id + "\" rendeles_id=\""
                        + rendeles_id + "\">", System.out, file);
                printElement("nev", vevo_nev, file);
                for (int i = 0; i < vevo_telefon.getLength(); i++) {
                    printElement("telefonszam", vevo_telefon.item(i).getTextContent(), file);
                }
                printCim(vevo_cim, file);
                printElement("email", vevo_email, file);
                printElement("szemelyigazolvany_szam", vevo_szemelyi, file);
                printToFileAndConsole("    </vevo>", System.out, file);

            }
        }
    }
}