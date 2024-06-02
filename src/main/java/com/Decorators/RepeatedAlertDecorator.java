package com.Decorators;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RepeatedAlertDecorator extends AlertDecorator{
    private ScheduledExecutorService scheduler;

    public RepeatedAlertDecorator(String patientId, String condition, long timestamp) {
        super(patientId,condition,timestamp);
        scheduler.scheduleAtFixedRate(() -> sendAlert(), 5, 5, TimeUnit.MINUTES);
    }
    @Override
    public void sendAlert(){
        super.sendAlert();
    }
    public void stopRepeat(){
        try{
            this.scheduler.shutdown();
        }catch(Exception e){
        }
    }

}
