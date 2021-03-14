package vn.plusplus.spring.springbootdemo.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobScheduler {

    @Scheduled(fixedDelay = 1000)
    public void fixDelay(){
        System.out.println("FIX DELAY: " + System.currentTimeMillis());
    }

    @Scheduled(fixedRate = 1500)
    public void fixRate(){
        System.out.println("FIX RATE: " + System.currentTimeMillis());
    }

    @Scheduled(cron = "*/10 * * * * ?")
    public void cron(){
        System.out.println("CRON: " + System.currentTimeMillis());
    }
}
