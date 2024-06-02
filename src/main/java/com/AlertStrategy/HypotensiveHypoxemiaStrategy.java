package com.AlertStrategy;

import java.util.HashMap;

import com.data_management.PatientRecord;

/**
 * Check whether Systolic Pressure lower than 90 and Saturation lower than 92% at the same time
 */
public class HypotensiveHypoxemiaStrategy implements AlertStrategy{
    private HashMap<Long,Double> systolicMap=new HashMap<>();
    private HashMap<Long,Double> saturationMap=new HashMap<>();
    private String message;
    /**
     * Evaluate newly added data
     * @param record
     * @return  true=meet the threshold
     */
    public boolean checkAlert(PatientRecord record){
        Long timestamp=record.getTimestamp();
        if (record.getRecordType().equals("SystolicPressure")) {
            this.systolicMap.put(timestamp, record.getMeasurementValue());
            //Check if it meet the two requirements at the same time
            if (record.getMeasurementValue()<90) {
                if (saturationMap.containsKey(timestamp)) {
                    if (saturationMap.get(timestamp)<92) {
                        this.message="Hypotensive Hypoxemia Detected";
                        return true;
                    }
                }
            }
        }
        if (record.getRecordType().equals("Saturation")) {
            this.saturationMap.put(timestamp, record.getMeasurementValue());
            //Check if it meet the two requirements at the same time
            if (record.getMeasurementValue()<92) {
                if (systolicMap.containsKey(timestamp)) {
                    if (systolicMap.get(timestamp)<90) {
                        this.message="Hypotensive Hypoxemia Detected";
                        return true;
                    }
                }
            }
        }
        return false;
    }
    @Override
    public String getMessage() {
        return this.message;
    }
}
