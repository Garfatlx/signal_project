package com.Decorators;

import com.alerts.Alert;

public class AlertDecorator extends Alert{
    public AlertDecorator(String patientId, String condition, long timestamp) {
        super(patientId,condition,timestamp);
    }
    @Override
    public void sendAlert(){
        super.sendAlert();
    }
}
