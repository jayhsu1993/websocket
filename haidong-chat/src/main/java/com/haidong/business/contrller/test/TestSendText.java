package com.haidong.business.contrller.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import com.huixin.framework.utils.HttpClientUtil;
import com.huixin.framework.wx.util.HttpUtils;

/**
 * 测试发消息
 * @author Administrator
 *
 */
public class TestSendText {
	private static int thread_num = 5;
    private static int client_num = 300;
    private static String url = "http://121.40.194.154:8081/haidong-chat/chat?msg='hello_kitty!'";
    private static String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiJmYjY2OWY0YzQyMDc0OTMyYTRhNjljZmIzZDgxMDBhNCIsImlhdCI6MTQ4OTQ3NDc1MzE3MiwiZXh0IjoxNDkwMDc5NTUzMTcyfQ.bt6E4LyWmHdQlf3dyq_v0vZNrQ9wufn38INXR3BmWPU";
    
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
