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

public class DomWriteKJSPMW {
    public static void main(String args[]) {
        try {
            // DocumentFactory inicializálása
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // DocumentBuilder inicializálása
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Létrehozza a dokumentumot
            Document doc = builder.newDocument();

            // Gyökér elem létrehozása
            Element rootElement = doc.createElement("Futobolt_KJSPMW");

            rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttribute("xsi:noNamespaceSchemaLocation", "XMLSchemaKJSPMW.xsd");

            // Gyökér elem hozzáadása a dokumentumhoz
            doc.appendChild(rootElement);

            // Rendelések hozzáadása
            addRendeles(doc, rootElement, "1", "2020-11-13", "Utánvét", "11500");
            addRendeles(doc, rootElement, "2", "2022-12-10", "Utánvét", "5000");
            addRendeles(doc, rootElement, "3", "2021-01-02", "Kártya", "7810");
            addRendeles(doc, rootElement, "4", "2021-05-09", "Utánvét", "6788");
            addRendeles(doc, rootElement, "5", "2023-09-10", "Kártya", "23000");

            // Raktárak hozzáadása
            addRaktar(doc, rootElement, "1", "1", "34", "1500", "új");
            addRaktar(doc, rootElement, "1", "2", "12", "500", "használt");
            addRaktar(doc, rootElement, "3", "3", "45", "60540", "új");
            addRaktar(doc, rootElement, "4", "4", "23", "12300", "régi");
            addRaktar(doc, rootElement, "5", "5", "67", "21000", "új");

            // Rakatár-Alkalmazott kapcsolatok hozzáadása
            addRaktarAlkalmazott(doc, rootElement, "1", "1", "igen");
            addRaktarAlkalmazott(doc, rootElement, "1", "2", "nem");
            addRaktarAlkalmazott(doc, rootElement, "2", "3", "igen");
            addRaktarAlkalmazott(doc, rootElement, "3", "4", "igen");
            addRaktarAlkalmazott(doc, rootElement, "3", "5", "nem");

            // Alkalmazottak hozzáadása
            addAlkalmazott(doc, rootElement, "1", "Kiss József", "350000", "vezető", "2019-01-01");
            addAlkalmazott(doc, rootElement, "2", "Nagy Béla", "100000", "segédmunkás", "2016-03-23");
            addAlkalmazott(doc, rootElement, "3", "Kovács Péter", "80000", "segédmunkás", "2013-12-11");
            addAlkalmazott(doc, rootElement, "4", "Horváth Tibor", "760000", "tulajdonos", "2010-05-30");
            addAlkalmazott(doc, rootElement, "5", "Kun Erika", "450000", "adminisztrátor", "2012-10-05");

            // Termékek hozzáadása
            addTermek(doc, rootElement, "1", "1", "félcipő", "póló", "pulzuspánt");
            addTermek(doc, rootElement, "2", "2", "cipő", "pulóver", "sportóra");
            addTermek(doc, rootElement, "3", "3", "félcipő", "kabát", "csősál");
            addTermek(doc, rootElement, "4", "4", "bakancs", "nadrág", "kesztyű");
            addTermek(doc, rootElement, "1", "1", "félcipő", "aláfelső", "compress szár");

            // Vevők hozzáadása
            addVevo(doc, rootElement, "1", "1", "Pista", "06-30-540-3344", 1106, "Budapest", "Kossuth Lajos utca", "12",
                    "kedvespista@gmail.com", "12312445");
            addVevo(doc, rootElement, "2", "2", "Józsi", "06-30-567-3455", 3900, "Szerencs", "Rózsa utca",
                    "34",
                    "jozsivagyok@gmail.com", "25468356");
            addVevo(doc, rootElement, "3", "3", "Attila", "06-20-345-3455", 3900, "Szerencs", "Damjanich utca",
                    "56",
                    "attila112@gmail.com", "34957543");
            addVevo(doc, rootElement, "4", "4", "Péter", "06-20-324-6788", 3903, "Bekecs", "Honvéd út", "23",
                    "nagypeter@gmail.com", "23497532");
            addVevo(doc, rootElement, "5", "5", "János", "06-70-566-3435", 3905, "Monok", "Szabadság utca", "78",
                    "janoskiss@gmail.com", "12343253");

            // Dokumentum kiírása, mentése
            File outputFile = new File("..\\XMLKJSPMW4.xml");

            PrintWriter file = new PrintWriter(outputFile, "UTF-8");

            printHeader(doc, file);

            readRendeles(doc, file);

            readRaktarak(doc, file);

            readRaktarAlkalmazott(doc, file);

            readAlkalmazott(doc, file);

            readTermekek(doc, file);

            readVevok(doc, file);

            printToFileAndConsole("</Futobolt_KJSPMW>", System.out, file);

            file.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Kiíró, adatfeldolgozó rész kezdete */

    // Kiíró metódus
    private static void printToFileAndConsole(final String msg, PrintStream console, PrintWriter file) {
        console.println(msg);
        file.println(msg);
    }

    // Fejrész elkészítő metódus
    private static void printHeader(Document doc, PrintWriter file) {
        printToFileAndConsole("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", System.out, file);
        printToFileAndConsole(
                "<Futobolt_KJSPMW xmlns:xs=\"http://www.w3.org/2001/XMLSchema-instance\" xs:noNamespaceSchemaLocation=\"XMLSchemaKJSPMW.xsd\">",
                System.out, file);
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
    /* Hozzáadó rész kezdete */

    // Rendelések hozzáadása

    private static void addRendeles(Document doc, Element rootElement, String rendeles_id, String datum,
            String rendeles_fizetes, String rendeles_ar) {
        Element rendeles = doc.createElement("rendeles");
        rendeles.setAttribute("rendeles_id", rendeles_id);

        Element datumElement = createElementAndAddToDoc(doc, "datum", datum);
        Element fizetesElement = createElementAndAddToDoc(doc, "rendeles_fizetes", rendeles_fizetes);
        Element arElement = createElementAndAddToDoc(doc, "rendeles_ar", rendeles_ar);

        rendeles.appendChild(datumElement);
        rendeles.appendChild(fizetesElement);
        rendeles.appendChild(arElement);

        rootElement.appendChild(rendeles);
    }

    // Raktár hozzáadása
    private static void addRaktar(Document doc, Element rootElement, String raktar_id, String rendeles_id,
            String ra_darabszam, String raktar_ar, String raktar_tipus) {
        Element raktar = doc.createElement("raktar");
        raktar.setAttribute("raktar_id", raktar_id);
        raktar.setAttribute("rendeles_id", rendeles_id);

        Element raDarabszamElement = createElementAndAddToDoc(doc, "ra_darabszam", ra_darabszam);
        Element raktarArElement = createElementAndAddToDoc(doc, "raktar_ar", raktar_ar);
        Element raktarTipusElement = createElementAndAddToDoc(doc, "raktar_tipus", raktar_tipus);

        raktar.appendChild(raDarabszamElement);
        raktar.appendChild(raktarArElement);
        raktar.appendChild(raktarTipusElement);

        rootElement.appendChild(raktar);
    }

    // Raktár-Alkalmazott kapcsolat hozzáadása
    private static void addRaktarAlkalmazott(Document doc, Element rootElement, String raktar_id,
            String alkalmazott_id, String elerheto) {
        Element raktarAlkalmazott = doc.createElement("raktar_alkalmazott");
        raktarAlkalmazott.setAttribute("raktar_id", raktar_id);
        raktarAlkalmazott.setAttribute("alkalmazott_id", alkalmazott_id);

        Element elerhetoElement = createElementAndAddToDoc(doc, "elerheto", elerheto);

        raktarAlkalmazott.appendChild(elerhetoElement);

        rootElement.appendChild(raktarAlkalmazott);
    }

    // Alkalmazott hozzáadása
    private static void addAlkalmazott(Document doc, Element rootElement, String alkalmazott_id, String alkalmazott_nev,
            String alkalmazott_fizetes, String alkalmazott_beosztas, String alkalmazott_csatlakozas) {
        Element alkalmazott = doc.createElement("alkalmazott");
        alkalmazott.setAttribute("alkalmazott_id", alkalmazott_id);

        Element alkalmazottNevElement = createElementAndAddToDoc(doc, "alkalmazott_nev", alkalmazott_nev);
        Element alkalmazottFizetesElement = createElementAndAddToDoc(doc, "alkalmazott_fizetes", alkalmazott_fizetes);
        Element alkalmazottBeosztasElement = createElementAndAddToDoc(doc, "alkalmazott_beosztas",
                alkalmazott_beosztas);
        Element alkalmazottCsatlakozasElement = createElementAndAddToDoc(doc, "alkalmazott_csatlakozas",
                alkalmazott_csatlakozas);

        alkalmazott.appendChild(alkalmazottNevElement);
        alkalmazott.appendChild(alkalmazottFizetesElement);
        alkalmazott.appendChild(alkalmazottBeosztasElement);
        alkalmazott.appendChild(alkalmazottCsatlakozasElement);

        rootElement.appendChild(alkalmazott);
    }

    // Termék hozzáadása
    private static void addTermek(Document doc, Element rootElement, String termek_id, String rendeles_id,
            String termek_cipo, String termek_ruha, String termek_kiegeszito) {
        Element termek = doc.createElement("termek");
        termek.setAttribute("termek_id", termek_id);
        termek.setAttribute("rendeles_id", rendeles_id);

        Element termekCipoElement = createElementAndAddToDoc(doc, "termek_cipo", termek_cipo);
        Element termekRuhaElement = createElementAndAddToDoc(doc, "termek_ruha", termek_ruha);
        Element termekKiegeszitoElement = createElementAndAddToDoc(doc, "termek_kiegeszito", termek_kiegeszito);

        termek.appendChild(termekCipoElement);
        termek.appendChild(termekRuhaElement);
        termek.appendChild(termekKiegeszitoElement);

        rootElement.appendChild(termek);
    }

    // Vevő hozzáadása
    private static void addVevo(Document doc, Element rootElement, String vevo_id, String rendeles_id, String nev,
            String telefon, int iranyitoszam, String telepules, String utca, String hazszam,
            String email,
            String szemelyi) {
        Element vevo = doc.createElement("vevo");
        vevo.setAttribute("id", vevo_id);
        vevo.setAttribute("rendeles_id", rendeles_id);

        Element nevElement = createElementAndAddToDoc(doc, "nev", nev);
        Element telefonElement = createElementAndAddToDoc(doc, "telefonszam", telefon);
        Element cimElement = doc.createElement("cim");
        Element iranyitoszamElement = createElementAndAddToDoc(doc, "iranyitoszam", String.valueOf(iranyitoszam));
        Element telepulesElement = createElementAndAddToDoc(doc, "telepules", telepules);
        Element utcaElement = createElementAndAddToDoc(doc, "utca", utca);
        Element hazszamElement = createElementAndAddToDoc(doc, "hazszam", hazszam);
        Element emailElement = createElementAndAddToDoc(doc, "email", email);
        Element szemelyiElement = createElementAndAddToDoc(doc, "szemelyigazolvany_szam", szemelyi);

        vevo.appendChild(nevElement);
        vevo.appendChild(telefonElement);
        vevo.appendChild(cimElement);
        cimElement.appendChild(iranyitoszamElement);
        cimElement.appendChild(telepulesElement);
        cimElement.appendChild(utcaElement);
        cimElement.appendChild(hazszamElement);
        vevo.appendChild(emailElement);
        vevo.appendChild(szemelyiElement);

        rootElement.appendChild(vevo);
    }

    // Elem létrehozása és dokumentumhoz adása
    private static Element createElementAndAddToDoc(Document doc, String name, String value) {
        Element element = doc.createElement(name);
        element.appendChild(doc.createTextNode(value));
        return element;
    }

}