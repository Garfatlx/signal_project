package com.AlertStrategy;


import com.data_management.PatientRecord;

/**
 * Track successive changes in blood pressure
 */
public class BloodPressureStrategy implements AlertStrategy{
    
        private Double currentData;
        private double stepThreshold;
        private int countWindow;
        private int consectiveIncrestedCount;
        private int consectiveDecrestedCount;
        private String message;
        public BloodPressureStrategy(int count, double stepThreshold){
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
        public boolean checkAlert(PatientRecord patientRrecord){
            double record=patientRrecord.getMeasurementValue();
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
