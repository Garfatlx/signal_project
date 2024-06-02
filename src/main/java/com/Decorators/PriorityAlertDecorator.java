package com.Decorators;


public class PriorityAlertDecorator extends AlertDecorator{
    private String priority;
    public PriorityAlertDecorator(String patientId, String condition, long timestamp, String priority) {
        super(patientId,condition,timestamp);
        this.priority=priority;
    }
    @Override
    public void sendAlert(){
        super.sendAlert(); 
        System.out.println(priority);
    }
}
