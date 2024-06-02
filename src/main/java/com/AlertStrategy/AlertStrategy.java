package com.AlertStrategy;

import com.data_management.PatientRecord;

public interface AlertStrategy {
    public boolean checkAlert(PatientRecord record);
    public String getMessage();
}


