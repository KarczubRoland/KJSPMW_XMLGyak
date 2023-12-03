package XsltKJSPMW;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XslTransform {


	    public static void main(String[] args) {
	        try {
	        	//1. feladat
	            String xmlInput = "hallgatoKJSPMW.xml";
	            String xsltInputHTML = "hallgatoKJSPMW.xsl";
	            String xsltInputXML = "hallgatoKJSPMWxml.xsl";
	            String outputResult = "hallgatoKJSPMW.html";
	            String outputResultXML = "hallgatoKJSPMW.out.xml";

	            TransformerFactory transformerFactory = TransformerFactory.newInstance();

	            Transformer transformer = transformerFactory.newTransformer(new StreamSource(xsltInputHTML));
	            transformer.transform(new StreamSource(xmlInput), new StreamResult(outputResult));
	            
	            transformer = transformerFactory.newTransformer(new StreamSource(xsltInputXML));
	            transformer.transform(new StreamSource(xmlInput), new StreamResult(outputResultXML));
	            
	            //2. feladat
	            xmlInput = "orarendKJSPMW.xml";
	            xsltInputHTML = "orarendKJSPMW.xsl";
	            xsltInputXML = "orarendKJSPMWxml.xsl";
	            outputResult = "orarendKJSPMW.html";
	            outputResultXML = "orarendKJSPMW.out.xml";

	            transformer = transformerFactory.newTransformer(new StreamSource(xsltInputHTML));
	            transformer.transform(new StreamSource(xmlInput), new StreamResult(outputResult));
	            
	            transformer = transformerFactory.newTransformer(new StreamSource(xsltInputXML));
	            transformer.transform(new StreamSource(xmlInput), new StreamResult(outputResultXML));

	            System.out.println("Sikeres XSLT transzformáció, eredmény mentve.");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}

