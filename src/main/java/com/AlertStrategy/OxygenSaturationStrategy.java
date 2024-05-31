package com.AlertStrategy;

import java.util.ArrayList;
import java.util.PriorityQueue;

import com.alerts.Alert;
import com.data_management.PatientRecord;

public class OxygenSaturationStrategy implements AlertStrategy {
    private ArrayList<Alert> alertLog=new ArrayList<>();
    public void checkAlert(PatientRecord record){
        SaturationDropChecker saturationTrackor=new SaturationDropChecker();
        if (record.getMeasurementValue()<92 ) {
            triggerAlert(new Alert(""+record.getPatientId(), "Low Saturation", record.getTimestamp()));
        }
        if (saturationTrackor.addData(record)) {
            triggerAlert(new Alert(""+record.getPatientId(), "Sturation Drop over 5% in 10min", record.getTimestamp()));
        }
    }

    private void triggerAlert(Alert alert) {
        // Implementation might involve logging the alert or notifying staff
        this.alertLog.add(alert);
        System.out.println(alert.getCondition());
    }
    private class SaturationDropChecker{
        private PriorityQueue<PatientRecord> records=new PriorityQueue<>((PatientRecord a, PatientRecord b)-> 
                Double.compare(b.getMeasurementValue(),a.getMeasurementValue()));
        /**
         * evaluate new data
         * @param record new feed in data
         * @return  true=a drop more then 5% in last 10 mins
         */
        public boolean addData(PatientRecord record){
            if (records.isEmpty()) {
                this.records.add(record);
            }else{
                // remove the maximum in the records if it older than 10 mins from the new added data
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
                    return true;
                }
            }
            return false;
        }

    }
}
