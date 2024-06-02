package com.AlertFactory;

import com.alerts.Alert;
import com.alerts.BloodPressureAlert;

public class BloodPressureAlertFactory extends AlertFactory {

    
    @Override
    public Alert createAlert(String patientId,String condition, Long timeStemp) {
        Alert alert=new BloodPressureAlert(patientId, condition, timeStemp);
        super.alerts.add(alert);
        return alert;
    }
}
