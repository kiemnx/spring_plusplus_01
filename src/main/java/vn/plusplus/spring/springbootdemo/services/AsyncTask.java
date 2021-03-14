package vn.plusplus.spring.springbootdemo.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncTask {
    @Async
    public void doBackGroundTask(){
        int i = 100;
        while (i>0){
            System.out.println("Do background task: " + System.currentTimeMillis());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i--;
        }
    }
}
