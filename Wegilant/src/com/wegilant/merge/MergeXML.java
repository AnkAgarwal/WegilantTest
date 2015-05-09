package com.wegilant.merge;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.wegilant.BE.Company;
import com.wegilant.BE.Employee;

public class MergeXML {

	public void mergeXML(List<Company> companyList,
			List<Employee> employeeList, String fileName) {

		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("employees");
			doc.appendChild(rootElement);

			for (int iterator = 0; iterator < companyList.size(); iterator++) {

				Company compObj = companyList.get(iterator);

				int index = employeeList.indexOf(new Employee(compObj.getId()));

				Employee empObj = null;

				// add element in result XML only if id exist on both XML
				if (index != -1) {
					empObj = employeeList.get(index);

					// employee elements
					Element employee = doc.createElement("employee");
					rootElement.appendChild(employee);

					// set attribute to staff element
					Attr attr = doc.createAttribute("id");
					attr.setValue(compObj.getId());
					employee.setAttributeNode(attr);

					Element firstname = doc.createElement("firstname");
					firstname.appendChild(doc.createTextNode(empObj
							.getFirstName()));
					employee.appendChild(firstname);

					// lastname elements
					Element lastname = doc.createElement("lastname");
					lastname.appendChild(doc.createTextNode(empObj
							.getLastName()));
					employee.appendChild(lastname);

					// location elements
					Element location = doc.createElement("location");
					location.appendChild(doc.createTextNode(empObj
							.getLocation()));
					employee.appendChild(location);

					// ComapnyName elements
					Element ComapnyName = doc.createElement("ComapnyName");
					ComapnyName.appendChild(doc.createTextNode(compObj
							.getCompanyName()));
					employee.appendChild(ComapnyName);

					// salary elements
					Element salary = doc.createElement("salary");
					salary.appendChild(doc.createTextNode(compObj.getSalary()));
					employee.appendChild(salary);
				}
			}
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(fileName));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}
