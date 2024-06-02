package com.AlertFactory;

import com.alerts.Alert;
import com.alerts.ECGAlert;

public class  ECGAlertFactory extends AlertFactory {
    
    @Override
    public Alert createAlert(String patientId,String condition, Long timeStemp) {
        Alert alert=new ECGAlert(patientId, condition, timeStemp);
        super.alerts.add(alert);
        return alert;
    }
}
