package com.AlertFactory;

import com.alerts.Alert;
import com.alerts.BloodOxygenAlert;

public class BloodOxygenAlertFactory extends AlertFactory{
    
   
    @Override
    public Alert createAlert(String patientId,String condition, Long timeStemp) {
        Alert alert=new BloodOxygenAlert(patientId, condition, timeStemp);
        super.alerts.add(alert);
        return alert;

    }
}
