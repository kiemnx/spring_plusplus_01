package vn.plusplus.spring.springbootdemo.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.plusplus.spring.springbootdemo.services.UserService;

@Component
public class JobScheduler {

    @Autowired
    UserService userService;

    @Scheduled(fixedDelayString = "${fix_delay_default}")
    public void fixDelay(){
        System.out.println("FIX DELAY: " + System.currentTimeMillis());
    }

    @Scheduled(fixedRateString = "${fix_rate_default}")
    public void fixRate(){
        System.out.println("FIX RATE: " + System.currentTimeMillis());
    }

    @Scheduled(cron = "*/10 * * * * ?")
    public void cron(){
        System.out.println("CRON: " + System.currentTimeMillis());
    }
}
