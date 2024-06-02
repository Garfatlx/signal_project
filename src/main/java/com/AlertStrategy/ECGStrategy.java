package com.AlertStrategy;

import com.data_management.PatientRecord;

public class ECGStrategy implements AlertStrategy {

    @Override
    public boolean checkAlert(PatientRecord record) {
        throw new UnsupportedOperationException("Unimplemented method 'checkAlert'");
    }

    @Override
    public String getMessage() {
        throw new UnsupportedOperationException("Unimplemented method 'getMessage'");
    }
    
}
