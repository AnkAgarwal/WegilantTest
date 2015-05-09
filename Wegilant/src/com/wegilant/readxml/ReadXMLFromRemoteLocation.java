package com.wegilant.readxml;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.wegilant.BE.Employee;

public class ReadXMLFromRemoteLocation {

	public List<Employee> readRemoteServerFile(String uri) {
		List<Employee> returnList = new ArrayList<>();
		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();

			// Get the DOM Builder
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Load and Parse the XML document
			// document contains the complete XML as a Tree.
			//Document document = builder.parse(new URL("https://file.ac/x_C6gIsigeU/Sample.xml?download=true").openStream());
			Document document = builder.parse(new URL(uri).openStream());
			document.getDocumentElement().normalize();

			

			// Iterating through the nodes and extracting the data.
			NodeList nodeList = document.getDocumentElement().getChildNodes();

			for (int i = 0; i < nodeList.getLength(); i++) {

				// We have encountered an <employee> tag.
				Node node = nodeList.item(i);

				if (node instanceof Element) {
					Employee emp = new Employee();
					emp.setId(node.getAttributes().getNamedItem("id")
							.getNodeValue());
					NodeList childNodes = node.getChildNodes();
					for (int j = 0; j < childNodes.getLength(); j++) {
						Node cNode = childNodes.item(j);

						// Identifying the child tag of employee encountered.
						if (cNode instanceof Element) {
							String content = cNode.getLastChild()
									.getTextContent().trim();

							switch (cNode.getNodeName()) {
							case "firstName":
								emp.setFirstName(content);
								break;
							case "lastName":
								emp.setLastName(content);
								break;
							case "location":
								emp.setLocation(content);
								break;
							}
						}
					}

					returnList.add(emp);
				}
			}

			/*// Printing the Employee list populated.
			for (Employee emp : returnList) {
				System.out.println(emp);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnList;
	}
}
