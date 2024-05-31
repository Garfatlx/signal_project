package com.AlertFactory;

import com.alerts.Alert;

class BloodOxygenAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(String patientId,String condition, Long timeStemp) {
        return new Alert(patientId,condition,timeStemp);
    }
}
