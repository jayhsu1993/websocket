package com.haidong.business.contrller.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import com.huixin.framework.utils.HttpClientUtil;
import com.huixin.framework.wx.util.HttpUtils;

public class Test {
	private static int thread_num = 5;
    private static int client_num = 5;
    private static String url = "http://121.40.194.154:8081/haidong-chat/gamePeople/5e47c2a18faa49b8a489d8fd9a5e5bbb";
    
    
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        // thread_num个线程可以同时访问
        final Semaphore semp = new Semaphore(thread_num);
        // 模拟client_num个客户端访问
        for (int index = 0; index < client_num; index++) {
            final int NO = index;
            Runnable run = new Runnable() {
                public void run() {
                    try {
                        // 获取许可
                        semp.acquire();
                        
                        System.out.println("Thread并发事情>>>"+ NO);
                        
                        
                        try {
                        	String result = HttpUtils.get(url);
                            System.err.println("接口调用返回结果：" + result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        
                        semp.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            exec.execute(run);
        }
        // 退出线程池
        exec.shutdown();
    }

}
