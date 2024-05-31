package com.AlertFactory;

import com.alerts.Alert;

public abstract class AlertFactory {
    public abstract Alert createAlert(String patientId,String condition, Long timeStemp);
}


