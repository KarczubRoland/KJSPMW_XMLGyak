package hu.KJSPMW;

import java.io.File;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

public class XsdValidation {

    /**
     * Validates the XML against the provided XSD schema.
     * 
     * @param xmlFilePath Path to the XML file.
     * @param xsdFilePath Path to the XSD file.
     * @return true if XML is valid, false otherwise.
     */
    public static boolean validateXML(String xmlFilePath, String xsdFilePath) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

            File schemaLocation = new File(xsdFilePath);
            Schema schema = factory.newSchema(schemaLocation);

            Validator validator = schema.newValidator();

            Source source = new StreamSource(new File(xmlFilePath));
            validator.validate(source);

            return true;
        } catch (SAXException ex) {
            System.out.println("XML is not valid. Reason: " + ex.getLocalizedMessage());
            return false;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getLocalizedMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        String xmlPath;
        String xsdPath;

        if (args.length != 2) {
            xmlPath = "C:\\Roland\\Egyetem\\XML\\KJSPMW_1025\\hu\\KJSPMW\\KJSPMW_kurzusfelvetel.xml";
            xsdPath = "C:\\Roland\\Egyetem\\XML\\KJSPMW_1025\\hu\\KJSPMW\\KJSPMW_kurzusfelvetel.xsd";
        } else {
            xmlPath = args[0];
            xsdPath = args[1];
        }

        boolean isValid = validateXML(xmlPath, xsdPath);
        if (isValid)
            System.out.println("XML is valid against the XSD.");
        else
            System.out.println("XML is NOT valid against the XSD.");
    }
}
