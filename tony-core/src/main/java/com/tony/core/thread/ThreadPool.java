package com.tony.core.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 线程池-业务公用。
 * @author jahe.me
 *
 */
public class ThreadPool {
	
	public static final int CORES = Runtime.getRuntime().availableProcessors();
	private static Logger logger = LoggerFactory.getLogger(ThreadPool.class);
	
	private final static ExecutorService pools = Executors.newFixedThreadPool((CORES * 2), 
			new ThreadFactoryBuilder().setNameFormat("worker-%d").build());
	
	public static void run(Runnable task) {
		try {
			pools.submit(task);
		} catch (Exception e) {
			logger.warn("ThreadPool - run error ", e);
		}
	}
	
	
}
