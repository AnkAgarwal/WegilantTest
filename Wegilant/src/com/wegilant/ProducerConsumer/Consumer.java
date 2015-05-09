package com.wegilant.ProducerConsumer;

import java.util.concurrent.BlockingQueue;

import com.wegilant.BE.Organisation;

public class Consumer implements Runnable {

	private BlockingQueue<Organisation> queue;

	public Consumer(BlockingQueue<Organisation> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Organisation org = queue.take();
				System.out.println(new StringBuffer(org.toString()).reverse());
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
