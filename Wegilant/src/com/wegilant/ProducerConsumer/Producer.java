package com.wegilant.ProducerConsumer;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import com.wegilant.BE.Message;
import com.wegilant.BE.Organisation;

public class Producer implements Runnable {

	private BlockingQueue<Organisation> queue;
	private List<Organisation> orgList;
	AtomicInteger count;

	public Producer(BlockingQueue<Organisation> queue,
			List<Organisation> orgList) {
		this.queue = queue;
		this.orgList = orgList;
		count = new AtomicInteger(0);
	}

	@Override
	public void run() {
		try {
			while (count.get() < orgList.size())
				queue.put(produceElement(count));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Organisation produceElement(AtomicInteger count) {
		// TODO Auto-generated method stub
		return orgList.get(count.getAndIncrement());
	}
}
