package com.sxk.test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author luochong
 * @date 2017年5月12日 下午3:37:52
 * 
 * 异步执行方法，失败可重试
 */
public abstract class BaseAsyncTask implements Runnable {

private Logger log = LoggerFactory.getLogger(getClass());
	
	private static long taskId = 1;
	private final static ConcurrentMap<Long, Boolean> flagMap = new ConcurrentHashMap<>();
	
	private int retry = 0;//如果任务执行失败重试次数
	private long times = 0;//如果任务执行失败间隔时间重试
	
	private long[] timesArray = null;//数组形式表示不同的重试间隔时间
	
	public BaseAsyncTask() {
		this(0, 0);
	}
	
	public BaseAsyncTask(long[] timesArray) {
		this.timesArray = timesArray;
	}
	
	public BaseAsyncTask(int retry, long times) {
		this.retry = retry;
		this.times = times;
	}
	
	@Override
	public void run() {
		
		long tempId = taskId ++;
		Boolean flag = true;
		long startTime = System.currentTimeMillis();
		
		//如果用数组形式表示重试间隔时间不为空，则已数组长度为重试次数
		if (timesArray != null) {
			retry = timesArray.length;
		}
		
		if (retry < 0) {
			retry = 0;
		}
		
		if (times < 0) {
			times = 0;
		}
		
		long startTime1 = 0;
		long endTime1 = 0;
		long endTime = 0;
		for (int i = 0; i < retry; i++) {
			
			//如果用数组形式表示重试间隔时间不为空，则已当前重试次数，作为数组下标，获取当前重试时间
			if (timesArray != null) {
				times = timesArray[i];
			}
			
			startTime1 = System.currentTimeMillis();
			try {
				//异步执行任务，把执行结果放入map，以任务ID为主键
				/*ExecutionMethom.asyncThread(
					() -> {
						return doTask();
					}, 
					(resultFlag) -> {
						flagMap.put(taskId, resultFlag);
					}
				);*/
			} catch (Throwable e) {
				flag = false;
				e.printStackTrace();
			}
			endTime1 = System.currentTimeMillis();
			
			if (times > 0) {
				try {
					Thread.sleep(times);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			//根据任务ID获取异步执行结果
			flag = flagMap.get(taskId);
			flag = flag == null ? false : flag;
			
			//最后一次执行或者执行成功，则删除任务ID对应的结果
			if (i + 1 == retry) {
				flagMap.remove(taskId);
				if (!flag) {
					log.info("任务ID：%s 全部执行失败");
				}
			} else if (flag) {
				flagMap.remove(taskId);
			}
			
			endTime = System.currentTimeMillis();
			if (flag) {
				log.info(String.format("任务ID：%s 第%s次执行成功  此次任务耗时：%s 休眠：%s 任务总耗时：%s", tempId, i + 1, (endTime1 - startTime1), times, (endTime - startTime)));
				break;
			}
			
			if (!flag) {
				log.info(String.format("任务ID：%s 第%s次执行失败  此次任务耗时：%s 休眠：%s 任务总耗时：%s", tempId, i + 1, (endTime1 - startTime1), times, (endTime - startTime)));
			}
		}
	}

	/**
	 * 异步任务执行体
	 * @return 是否执行成功
	 */
	public abstract boolean doTask();
}
