package com.wegilant.readxml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.wegilant.BE.Company;
import com.wegilant.BE.Organisation;

public class ReadXMLFromMasterFile {

	public List<Organisation> readFile(String filePath) {

		List<Organisation> returnList = new ArrayList<>();
		try {

			File fXmlFile = new File(filePath);
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();

			// Get the DOM Builder
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Load and Parse the XML document
			// document contains the complete XML as a Tree.
			Document document = builder.parse(fXmlFile);
			document.getDocumentElement().normalize();

			// Iterating through the nodes and extracting the data.
			NodeList nodeList = document.getDocumentElement().getChildNodes();

			for (int i = 0; i < nodeList.getLength(); i++) {

				// We have encountered an <employee> tag.
				Node node = nodeList.item(i);

				if (node instanceof Element) {
					Organisation orgObj = new Organisation();
					orgObj.setId(node.getAttributes().getNamedItem("id")
							.getNodeValue());
					NodeList childNodes = node.getChildNodes();
					for (int j = 0; j < childNodes.getLength(); j++) {
						Node cNode = childNodes.item(j);

						// Identifying the child tag of employee encountered.
						if (cNode instanceof Element) {
							String content = cNode.getLastChild()
									.getTextContent().trim();

							switch (cNode.getNodeName()) {
							case "ComapnyName":
								orgObj.setCompanyName(content);
								break;
							case "Salary":
								orgObj.setSalary(content);
								break;
							case "firstName":
								orgObj.setFirstName(content);
								break;
							case "lastName":
								orgObj.setLastName(content);
								break;
							case "location":
								orgObj.setLocation(content);
								break;
							}
						}
					}

					returnList.add(orgObj);
				}
			}

			/*// Printing the Employee list populated.
			for (Organisation org : returnList) {
				System.out.println(org);
			}*/
		} catch (Exception e) {
			e.printStackTrace();

		}

		return returnList;
	}
}
