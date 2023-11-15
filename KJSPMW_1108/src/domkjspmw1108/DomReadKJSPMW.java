package domkjspmw1108;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomReadKJSPMW {
    public static void main(String[] args) {
        try {
            File file = new File("KJSPMW_kurzusfelvetel.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }
            writeXmlContentToFile(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeXmlContentToFile(File inputFile) {
        File outputFile = new File("output.xml");
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             FileWriter writer = new FileWriter(outputFile)) {
            String line;
            while ((line = br.readLine()) != null) {
                writer.write(line + System.lineSeparator());
            }
            System.out.println("XML tartalom kiírva a 'output.xml' fájlba.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

