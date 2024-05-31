package com.AlertStrategy;

import com.data_management.Patient;
import com.data_management.PatientRecord;

public interface AlertStrategy {
    public void checkAlert(PatientRecord record);
}


