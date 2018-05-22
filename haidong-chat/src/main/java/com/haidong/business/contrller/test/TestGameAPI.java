package com.haidong.business.contrller.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import com.huixin.framework.wx.util.HttpUtils;

public class TestGameAPI {
	private static int thread_num = 5;
    private static int client_num = 10;
    private static String game_url = "http://hiapiuat.hidongtv.com/gameIntercept/shakeScore?USER_ID=5e47c2a18faa49b8a489d8fd9a5e5bbb&roomId=5ac20f85e70c4a0eb19802ea28ae84e0&score=188";
//    private static String game_url = "http://192.168.1.94:8080/haidong-api/gameIntercept/shakeScore?roomId=5ac20f85e70c4a0eb19802ea28ae84e0&score=188";
    private static String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiI1ZTQ3YzJhMThmYWE0OWI4YTQ4OWQ4ZmQ5YTVlNWJiYiIsImlhdCI6MTQ4Nzc1NTk4MDgxMiwiZXh0IjoxNDg4MzYwNzgwODEyfQ.snTtHbCGzslJTkbHt5GACJcFFw1fG0QbM5q2fzHZbhg";
    
    public static void main(String[] args) throws InterruptedException {
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
                        	String result = HttpUtils.get(game_url);
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
            Thread.sleep(2000);
        }
        // 退出线程池
        exec.shutdown();
    }
}
