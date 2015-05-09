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
		
		ReadXMLFromFile parseFromFile = new ReadXMLFromFile();
		List<Company> companyList = parseFromFile.readFile("Sample.xml");
		
		ReadXMLFromRemoteLocation parseFromRemote = new ReadXMLFromRemoteLocation();
		List<Employee> employeeList = parseFromRemote.readRemoteServerFile("https://file.ac/x_C6gIsigeU/Sample.xml?download=true");
		
		MergeXML mergeObject = new MergeXML();
		mergeObject.mergeXML(companyList, employeeList, "Master.xml");
		
		ReadXMLFromMasterFile parseFromMasterFile = new ReadXMLFromMasterFile();
		List<Organisation> orgList = parseFromMasterFile.readFile("Master.xml");
		
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
