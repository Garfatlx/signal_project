package com.AlertFactory;

import java.util.ArrayList;
import java.util.List;

import com.AlertStrategy.AlertStrategy;
import com.AlertStrategy.BloodPressureStrategy;
import com.AlertStrategy.ECGStrategy;
import com.AlertStrategy.HypotensiveHypoxemiaStrategy;
import com.AlertStrategy.OxygenSaturationStrategy;
import com.alerts.Alert;
import com.data_management.PatientRecord;

public class AlertFactory {
    protected List<Alert> alerts=new ArrayList<>();
    
    final AlertStrategy diastolicsStrategy=new BloodPressureStrategy(3,10);
    final AlertStrategy systolicsStrategy=new BloodPressureStrategy(3,10);
    final AlertStrategy combinedStrategy=new HypotensiveHypoxemiaStrategy();
    final AlertStrategy saturationStrategy=new OxygenSaturationStrategy();
    final AlertStrategy ecgStrategy=new ECGStrategy();

    public void evaluateData(PatientRecord record){
        
        switch (record.getRecordType()) {
            case "DiastolicPressure":
                if(diastolicsStrategy.checkAlert(record)){
                    String condition=String.join(" ", "Diastolic Pressure",diastolicsStrategy.getMessage());
                    createAlert(""+record.getPatientId(), condition, record.getTimestamp());
                }
                if (record.getMeasurementValue()>120 ) {
                    createAlert(""+record.getPatientId(), "Diastolic Pressure over 120 mmHg", record.getTimestamp());
                }
                if (record.getMeasurementValue()<60 ) {
                    createAlert(""+record.getPatientId(), "Diastolic Pressure less than 60 mmHg", record.getTimestamp());
                }
                break;
            case "SystolicPressure":
                if(systolicsStrategy.checkAlert(record)){
                    String condition=String.join(" ", "Systolic Pressure",systolicsStrategy.getMessage());
                    createAlert(""+record.getPatientId(), condition, record.getTimestamp());
                }
                if (record.getMeasurementValue()>180 ) {
                    createAlert(""+record.getPatientId(), "Systolic Pressure over 180 mmHg", record.getTimestamp());
                }
                if (record.getMeasurementValue()<90 ) {
                    createAlert(""+record.getPatientId(), "Systolic Pressure less than 90 mmHg", record.getTimestamp());
                }
                if (combinedStrategy.checkAlert(record)) {
                    createAlert(""+record.getPatientId(), "Hypotensive Hypoxemia Alert", record.getTimestamp());
                }
                break;
            case "Saturation":
                if (record.getMeasurementValue()<92 ) {
                    createAlert(""+record.getPatientId(), "Low Saturation", record.getTimestamp());
                }
                if (saturationStrategy.checkAlert(record)) {
                    createAlert(""+record.getPatientId(), "Sturation Drop over 5% in 10min", record.getTimestamp());
                }
                if (combinedStrategy.checkAlert(record)) {
                    createAlert(""+record.getPatientId(), "Hypotensive Hypoxemia Alert", record.getTimestamp());
                }
                
                break;
            case "ECG":
                
                break;
            case "Alert":
                createAlert(""+record.getPatientId(), "Alert!", record.getTimestamp());
                
                break;
            default:
                break;
        }
    }

    public Alert createAlert(String patientId,String condition, Long timeStemp){
        Alert alert=new Alert(patientId, condition, timeStemp);
        this.alerts.add(alert);
        return alert;
    }
    public List<Alert> getAlerts(){
        return this.alerts;
    }


}


