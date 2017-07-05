package com.sxk.test;

import java.util.concurrent.TimeUnit;

/**
 * @author luochong
 * @date 2017年5月12日 下午4:16:28
 * 
 * 异步通知第三方平台任务，失败有重试机制
 */
public class NotifyAsyncTask extends BaseAsyncTask {

	private static TimeUnit timeUnit = TimeUnit.MILLISECONDS;
	
	private static long[] timesArray = {
								timeUnit.toMillis(15),
								timeUnit.toMillis(15),
								timeUnit.toMillis(30),
								timeUnit.toMillis(180),
								timeUnit.toMillis(1800),
								timeUnit.toMillis(1800),
								timeUnit.toMillis(1800),
								timeUnit.toMillis(1800),
								timeUnit.toMillis(3600)
							};
	
	public NotifyAsyncTask() {
		super(timesArray);
	}
	
	public static void main(String[] args) throws InterruptedException {
		/*ExecutionMethom.print(() -> {
			new Thread(new NotifyAsyncTask()).start();
			return null;
		}, 2);*/
	}

	@Override
	public boolean doTask() {
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return true;
	}

}
