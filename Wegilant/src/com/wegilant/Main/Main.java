package com.wegilant.Main;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.wegilant.BE.Company;
import com.wegilant.BE.Employee;
import com.wegilant.BE.Message;
import com.wegilant.BE.Organisation;
import com.wegilant.ProducerConsumer.Consumer;
import com.wegilant.ProducerConsumer.Producer;
import com.wegilant.merge.MergeXML;
import com.wegilant.readxml.ReadXMLFromFile;
import com.wegilant.readxml.ReadXMLFromMasterFile;
import com.wegilant.readxml.ReadXMLFromRemoteLocation;

public class Main {

	public static void main(String[] args){
		
		String filePath = "Sample.xml";
		String mergeFilePath = "Master.xml";
		String remoteFileURI = "https://file.ac/x_C6gIsigeU/Sample.xml?download=true";
		
		//ReadXMLFromFile is to read the file from particular path directory
		ReadXMLFromFile parseFromFile = new ReadXMLFromFile();
		List<Company> companyList = parseFromFile.readFile(filePath);
		
		//ReadXMLFromRemoteLocation is to read the file from particular remote server
		ReadXMLFromRemoteLocation parseFromRemote = new ReadXMLFromRemoteLocation();
		List<Employee> employeeList = parseFromRemote.readRemoteServerFile(remoteFileURI);
		
		//MergeXML will merge the files based on the collection retrived by parsing the uper files
		MergeXML mergeObject = new MergeXML();
		mergeObject.mergeXML(companyList, employeeList, mergeFilePath);
		
		//Merge file is parsed to obtain the collection
		ReadXMLFromMasterFile parseFromMasterFile = new ReadXMLFromMasterFile();
		List<Organisation> orgList = parseFromMasterFile.readFile(mergeFilePath);
		
		//blocking queue is created and producer consumer thread are initialsed
		BlockingQueue<Organisation> queue = new ArrayBlockingQueue<>(3);
        Producer producer = new Producer(queue,orgList);
        Consumer consumer = new Consumer(queue);
        
        //starting producer to produce messages in queue
        new Thread(producer).start();
        //starting consumer to consume messages from queue
        new Thread(consumer).start();
        System.out.println("Producer and Consumer has been started");
	}
}
