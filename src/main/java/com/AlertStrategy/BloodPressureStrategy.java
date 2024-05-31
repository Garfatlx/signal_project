package com.AlertStrategy;

import java.util.ArrayList;

import com.alerts.Alert;
import com.data_management.PatientRecord;

public class BloodPressureStrategy implements AlertStrategy{
    private ArrayList<Alert> alertLog=new ArrayList<>();
    public void checkAlert(PatientRecord record){
        SuccessiveChangeChecker diastolicTrackor=new SuccessiveChangeChecker(3, 10);
        SuccessiveChangeChecker systolicTrackor=new SuccessiveChangeChecker(3, 10);
        if (record.getRecordType().equals("DiastolicPressure")) {
            if(diastolicTrackor.addData(record.getMeasurementValue())){
                String condition=String.join(" ", "Diastolic Pressure",diastolicTrackor.getMessage());
                triggerAlert(new Alert(""+record.getPatientId(), condition, record.getTimestamp()));
            }
            if (record.getMeasurementValue()>120 ) {
                triggerAlert(new Alert(""+record.getPatientId(), "Diastolic Pressure over 120 mmHg", record.getTimestamp()));
            }
            if (record.getMeasurementValue()<60 ) {
                triggerAlert(new Alert(""+record.getPatientId(), "Diastolic Pressure less than 60 mmHg", record.getTimestamp()));
            }
        }
        
        if (record.getRecordType().equals("SystolicPressure")) {
            if(systolicTrackor.addData(record.getMeasurementValue())){
                String condition=String.join(" ", "Systolic Pressure",systolicTrackor.getMessage());
                triggerAlert(new Alert(""+record.getPatientId(), condition, record.getTimestamp()));
            }
            if (record.getMeasurementValue()>180 ) {
                triggerAlert(new Alert(""+record.getPatientId(), "Systolic Pressure over 180 mmHg", record.getTimestamp()));
            }
            if (record.getMeasurementValue()<90 ) {
                triggerAlert(new Alert(""+record.getPatientId(), "Systolic Pressure less than 90 mmHg", record.getTimestamp()));
            }
        }
        
        
    }
    private void triggerAlert(Alert alert) {
        // Implementation might involve logging the alert or notifying staff
        alertLog.add(alert);
        System.out.println(alert.getCondition());
    }

    private class SuccessiveChangeChecker{
        private Double currentData;
        private double stepThreshold;
        private int countWindow;
        private int consectiveIncrestedCount;
        private int consectiveDecrestedCount;
        private String message;
        public SuccessiveChangeChecker(int count, double stepThreshold){
            this.countWindow=count-1;
            this.currentData=null;
            this.consectiveDecrestedCount=0;
            this.consectiveIncrestedCount=0;
            this.stepThreshold=stepThreshold;
        }
        /**
         * evaluate the new data in. 
         * @param record newly added data
         * @return  true=consective {stepThreshold} changes in a roll
         */
        public boolean addData(double record){
            if (this.currentData==null) {
                this.currentData=record;
            }else{
                if (record-this.currentData>this.stepThreshold) {
                    this.consectiveIncrestedCount++;
                    this.consectiveDecrestedCount=0;
                }else if (record-this.currentData<-this.stepThreshold) {
                    this.consectiveIncrestedCount=0;
                    this.consectiveDecrestedCount++;
                }else{
                    this.consectiveIncrestedCount=0;
                    this.consectiveDecrestedCount=0;
                }
                this.currentData=record;
            }
            if (this.consectiveDecrestedCount>=this.countWindow) {
                this.message="consectively decreased";
                return true;
            }
            if (this.consectiveIncrestedCount>=this.countWindow) {
                this.message="consectively increased";
                return true;
            }
            return false;
        }
        public String getMessage(){
            return this.message;
        }
    }
}
