package com.alerts;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        // Implementation goes here
        List<PatientRecord> evaluateRecords = patient.getRecords(1700000000000L, 1800000000000L);
        
        SuccessiveChangeChecker diastolicTrackor=new SuccessiveChangeChecker(3, 10);
        SuccessiveChangeChecker systolicTrackor=new SuccessiveChangeChecker(3, 10);
        SaturationDropChecker saturationTrackor=new SaturationDropChecker();
        for (PatientRecord record : evaluateRecords) {
            switch (record.getRecordType()) {
                case "DiastolicPressure":
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
                    break;
                case "SystolicPressure":
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
                    break;
                case "Saturation":
                    if (record.getMeasurementValue()<92 ) {
                        triggerAlert(new Alert(""+record.getPatientId(), "Low Saturation", record.getTimestamp()));
                    }
                    if (saturationTrackor.addData(record)) {
                        triggerAlert(new Alert(""+record.getPatientId(), "Sturation Drop over 5% in 10min", record.getTimestamp()));
                    }
                    break;
                case "ECG":
                    
                    break;
                case "Alert":
                    
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        // Implementation might involve logging the alert or notifying staff

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
            }
            if (this.consectiveDecrestedCount>=this.countWindow) {
                this.message="consective decreased";
                return true;
            }
            if (this.consectiveIncrestedCount>=this.countWindow) {
                this.message="consective increased";
                return true;
            }
            return false;
        }
        public String getMessage(){
            return this.message;
        }
    }

    private class SaturationDropChecker{
        private PriorityQueue<PatientRecord> records=new PriorityQueue<>((PatientRecord a, PatientRecord b)-> 
                Double.compare(a.getMeasurementValue(),b.getMeasurementValue()));
        public boolean addData(PatientRecord record){
            if (records.isEmpty()) {
                this.records.add(record);
            }else{
                while (records.peek().getTimestamp()<record.getTimestamp()-600000L && !records.isEmpty()) {
                    records.poll();
                }
                records.add(record);
                if (records.peek().getMeasurementValue()-record.getMeasurementValue()>=5) {
                    return true;
                }
            }
            return false;
        }
    }

    private class HypotensiveHypoxemiaChecker{
        
    }

}
