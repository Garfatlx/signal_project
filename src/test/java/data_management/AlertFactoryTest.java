package data_management;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


import com.AlertFactory.BloodPressureAlertFactory;

import com.data_management.PatientRecord;

public class AlertFactoryTest {
    @Test
    void bloodPressureFactoryTest(){
        PatientRecord record;
        BloodPressureAlertFactory factory=new BloodPressureAlertFactory();

        record=new PatientRecord(1, 100.0, "DiastolicPressure", 1714376789050L);
        factory.evaluateData(record);
        record=new PatientRecord(1, 70.0, "DiastolicPressure", 1714376789051L);
        factory.evaluateData(record);
        record=new PatientRecord(1, 85.0, "DiastolicPressure", 1714376789052L);
        factory.evaluateData(record);
        record=new PatientRecord(1, 100.0, "DiastolicPressure", 1714376789053L);
        factory.evaluateData(record);
        record=new PatientRecord(1,125.0, "DiastolicPressure", 1714376789054L);
        factory.evaluateData(record);
        record=new PatientRecord(1, 110.0, "DiastolicPressure", 1714376789054L);
        factory.evaluateData(record);
        record=new PatientRecord(1, 95.0, "DiastolicPressure", 1714376789054L);
        factory.evaluateData(record);
        record=new PatientRecord(1, 50.0, "DiastolicPressure", 1714376789054L);
        factory.evaluateData(record);

        // check if trigger consective increase alert
        assertEquals("Diastolic Pressure consectively increased", factory.getAlerts().get(0).getCondition());
        // check if trigger over 120 alert
        assertEquals("Diastolic Pressure over 120 mmHg", factory.getAlerts().get(2).getCondition());
        // check if trigger consective decrease alert
        assertEquals("Diastolic Pressure consectively decreased", factory.getAlerts().get(3).getCondition());
        // check if trigger low pressure alert
        assertEquals("Diastolic Pressure less than 60 mmHg", factory.getAlerts().get(5).getCondition());
    } 

    @Test
    void SystolicPressureAlertFactoryTest(){
        PatientRecord record;
        BloodPressureAlertFactory factory=new BloodPressureAlertFactory();

        record=new PatientRecord(1, 160.0, "SystolicPressure", 1714376789050L);
        factory.evaluateData(record);
        record=new PatientRecord(1, 130.0, "SystolicPressure", 1714376789051L);
        factory.evaluateData(record);
        record=new PatientRecord(1, 145.0, "SystolicPressure", 1714376789052L);
        factory.evaluateData(record);
        record=new PatientRecord(1, 160.0, "SystolicPressure", 1714376789053L);
        factory.evaluateData(record);
        record=new PatientRecord(1,185.0, "SystolicPressure", 1714376789054L);
        factory.evaluateData(record);
        record=new PatientRecord(1, 170.0, "SystolicPressure", 1714376789054L);
        factory.evaluateData(record);
        record=new PatientRecord(1, 155.0, "SystolicPressure", 1714376789054L);
        factory.evaluateData(record);
        record=new PatientRecord(1, 85.0, "SystolicPressure", 1714376789054L);
        factory.evaluateData(record);
        record=new PatientRecord(1, 85.0, "SystolicPressure", 1714376789054L);
        factory.evaluateData(record);


        // check if trigger consective increase alert
        assertEquals("Systolic Pressure consectively increased", factory.getAlerts().get(0).getCondition());
        // check if trigger over 120 alert
        assertEquals("Systolic Pressure over 180 mmHg", factory.getAlerts().get(2).getCondition());
        // check if trigger consective decrease alert
        assertEquals("Systolic Pressure consectively decreased", factory.getAlerts().get(3).getCondition());
        // check if trigger low pressure alert
        assertEquals("Systolic Pressure less than 90 mmHg", factory.getAlerts().get(5).getCondition());
    }

    @Test
    void SaturationAlertFactoryTest(){
        PatientRecord record;
        BloodPressureAlertFactory factory=new BloodPressureAlertFactory();
        record=new PatientRecord(1, 91.0, "Saturation", 1714376789050L);
        factory.evaluateData(record);
        record=new PatientRecord(1, 99.0, "Saturation", 1714376789051L);
        factory.evaluateData(record);
        record=new PatientRecord(1, 93.0, "Saturation", 1714376789052L);
        factory.evaluateData(record);
        record=new PatientRecord(1, 99.0, "Saturation", 1714376789053L);
        factory.evaluateData(record);
        record=new PatientRecord(1, 93.0, "Saturation", 1714377789053L);
        factory.evaluateData(record);
        

        // check if trigger low saturation alert
        assertEquals("Low Saturation", factory.getAlerts().get(0).getCondition());
        // check if trigger over 5% drop alert
        assertEquals("Sturation Drop over 5% in 10min", factory.getAlerts().get(1).getCondition());
    }
}
