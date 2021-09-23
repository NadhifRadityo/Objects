package io.github.NadhifRadityo.Library.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import static io.github.NadhifRadityo.Library.Utils.FileUtils.getFileString;
import static io.github.NadhifRadityo.Library.Utils.FileUtils.mkfile;
import static io.github.NadhifRadityo.Library.Utils.FileUtils.writeFileString;
import static io.github.NadhifRadityo.Library.Utils.LoggerUtils.info;
import static io.github.NadhifRadityo.Library.Utils.StreamUtils.getString;

public class XMLUtils {

	public static Document newXMLDocument(Object object) throws Exception {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		if(object instanceof InputStream) object = getString((InputStream) object, StandardCharsets.UTF_8);
		else if(object instanceof File) object = getFileString((File) object, StandardCharsets.UTF_8);
		else if(object != null) throw new IllegalArgumentException();
		return object != null ? documentBuilder.parse(new InputSource(new StringReader((String) object))) : documentBuilder.newDocument();
	}
	public static String XMLToString(Object object) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();

		DOMSource source;
		if(object instanceof Document) {
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(2));
			source = new DOMSource(((Document) object).getDocumentElement());
		} else if(object instanceof Element) {
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("omit-xml-declaration", "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(2));
			source = new DOMSource((Element) object);
		} else throw new IllegalArgumentException();

		StringWriter stringWriter = new StringWriter();
		StreamResult streamResult = new StreamResult(stringWriter);
		transformer.transform(source, streamResult);
		return stringWriter.toString();
	}
	public static String createXMLFile(Object object, File target) throws Exception {
		String stringOut = XMLToString(object);
		mkfile(target);
		writeFileString(target, stringOut, StandardCharsets.UTF_8);
		info("Configurations written to: %s", target.getPath());
		return stringOut;
	}
}
