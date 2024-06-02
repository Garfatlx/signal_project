package com.AlertStrategy;

import java.util.PriorityQueue;

import com.data_management.PatientRecord;

/**
 * Track if there is a Saturation drop more than 5% in last 10 mins
 */
public class OxygenSaturationStrategy implements AlertStrategy {
    
        //Use Maximum priority queue to store the data records 
        private PriorityQueue<PatientRecord> records=new PriorityQueue<>((PatientRecord a, PatientRecord b)-> 
                Double.compare(b.getMeasurementValue(),a.getMeasurementValue()));
        private String message;
        /**
         * evaluate new data
         * @param record new feed in data
         * @return  true=a drop more then 5% in last 10 mins
         */
        public boolean checkAlert(PatientRecord record){
            if (records.isEmpty()) {
                this.records.add(record);
            }else{
                // remove the maximum in the records if it older than 10 mins
                // until the maximum is within the 10min range.
                while (!records.isEmpty()) {
                        if (records.peek().getTimestamp()<record.getTimestamp()-600000L) {
                            records.poll();
                            continue;
                        }else{
                            break;
                        }
                }
                records.add(record);
                // Compare with the maximum
                if (records.peek().getMeasurementValue()-record.getMeasurementValue()>=5) {
                    this.message="Saturation drop more then 5% in 10 mins";
                    return true;
                }
            }
            return false;
        }
        @Override
        public String getMessage() {
            return this.message;
        }

    
}
