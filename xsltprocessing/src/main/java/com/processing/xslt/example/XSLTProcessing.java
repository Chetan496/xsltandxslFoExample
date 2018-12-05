package com.processing.xslt.example;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

public class XSLTProcessing {

	public static void main(String[] args) {
		

		XSLTProcessing xsltProcessing = new XSLTProcessing();
		try {
			xsltProcessing.convertToFO();
			xsltProcessing.convertToPDF();
		} catch (FOPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
	public void convertToPDF()  throws IOException, FOPException, TransformerException {
        // the XSL FO file
        File xsltFile = new File("xmlresources/employeesfo.xsl");
        // the XML file which provides the input
        StreamSource xmlSource = new StreamSource(new File("xmlresources/employees.xml"));
        // create an instance of fop factory
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        // a user agent is needed for transformation
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        // Setup output
        OutputStream out = new java.io.FileOutputStream("xmlresources/employee.pdf");
    
        try {
            // Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));
            
            //as the transformation starts, sax events will be raised by transformer.
            //the sax event should be passed on to FOP 
            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Start XSLT transformation and FOP processing
            // That's where the XML is first transformed to XSL-FO and then 
            // PDF is created
            transformer.transform(xmlSource, res);
        } finally {
            out.close();
        }
    }
    
	

	/**
	 * This method will convert the given XML to XSL-FO
	 * 
	 * @throws IOException
	 * @throws FOPException
	 * @throws TransformerException
	 */
	public void convertToFO() throws IOException, FOPException, TransformerException {
		// the XSL FO file
		File xsltFile = new File("xmlresources/employeesfo.xsl");

		/*
		 * TransformerFactory factory = TransformerFactory.newInstance(); Transformer
		 * transformer = factory.newTransformer(new StreamSource
		 * ("F:\\Temp\\template.xsl"));
		 */

		// the XML file which provides the input
		StreamSource xmlSource = new StreamSource(new File("xmlresources/employees.xsl"));

		// a user agent is needed for transformation
		/* FOUserAgent foUserAgent = fopFactory.newFOUserAgent(); */
		// Setup output
		OutputStream out;

		out = new java.io.FileOutputStream("xmlresources/employees.fo");

		try {
			// Setup XSLT
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

			Result res = new StreamResult(out);

			// Start XSLT transformation and FOP processing
			transformer.transform(xmlSource, res);

		
		} finally {
			out.close();
		}
	}
	
	
	private static void xsltTransformExample() throws TransformerFactoryConfigurationError {
		// TODO Auto-generated method stub

		TransformerFactory factory = TransformerFactory.newInstance();
		Source xslt = new StreamSource(new File("xmlresources/employees.xsl"));
		Transformer transformer;
		try {
			transformer = factory.newTransformer(xslt);
			Source text = new StreamSource(new File("xmlresources/employees.xml"));
			transformer.transform(text, new StreamResult(new File("xmlresources/output.txt")));
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
