package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.alerts.AlertGenerator;
import com.cardio_generator.HealthDataSimulator;
import com.data_management.DataReader;
import com.data_management.FileDataReader;
import com.data_management.MyWebSocketClient;
import com.data_management.PatientRecord;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

class DataStorageTest {

    @Test
    void testAddAndGetRecords() {
        // TODO Perhaps you can implement a mock data reader to mock the test data?
        DataReader reader=new FileDataReader("D:\\ProgrammingProjects\\outputtest","");
        DataStorage storage = new DataStorage();
        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);
        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);
        assertEquals(2, records.size()); // Check if two records are retrieved
        assertEquals(100.0, records.get(0).getMeasurementValue()); // Validate first record
    }

    @Test
    void testDiastolicPressureAlert(){
        DataStorage storage = new DataStorage();
        AlertGenerator alertGenerator=new AlertGenerator(storage);
        storage.addPatientData(1, 100.0, "DiastolicPressure", 1714376789050L);
        storage.addPatientData(1, 70.0, "DiastolicPressure", 1714376789051L);
        storage.addPatientData(1, 85.0, "DiastolicPressure", 1714376789052L);
        storage.addPatientData(1, 100.0, "DiastolicPressure", 1714376789053L);
        storage.addPatientData(1,125.0, "DiastolicPressure", 1714376789054L);
        storage.addPatientData(1, 110.0, "DiastolicPressure", 1714376789054L);
        storage.addPatientData(1, 95.0, "DiastolicPressure", 1714376789054L);
        storage.addPatientData(1, 50.0, "DiastolicPressure", 1714376789054L);

        alertGenerator.evaluateData(storage.getAllPatients().get(0));

        // check if trigger consective increase alert
        assertEquals("Diastolic Pressure consectively increased", alertGenerator.getAlerts().get(0).getCondition());
        // check if trigger over 120 alert
        assertEquals("Diastolic Pressure over 120 mmHg", alertGenerator.getAlerts().get(2).getCondition());
        // check if trigger consective decrease alert
        assertEquals("Diastolic Pressure consectively decreased", alertGenerator.getAlerts().get(3).getCondition());
        // check if trigger low pressure alert
        assertEquals("Diastolic Pressure less than 60 mmHg", alertGenerator.getAlerts().get(5).getCondition());
    }

    @Test
    void testSystolicPressureAlert(){
        DataStorage storage = new DataStorage();
        AlertGenerator alertGenerator=new AlertGenerator(storage);
        storage.addPatientData(1, 160.0, "SystolicPressure", 1714376789050L);
        storage.addPatientData(1, 130.0, "SystolicPressure", 1714376789051L);
        storage.addPatientData(1, 145.0, "SystolicPressure", 1714376789052L);
        storage.addPatientData(1, 160.0, "SystolicPressure", 1714376789053L);
        storage.addPatientData(1,185.0, "SystolicPressure", 1714376789054L);
        storage.addPatientData(1, 170.0, "SystolicPressure", 1714376789054L);
        storage.addPatientData(1, 155.0, "SystolicPressure", 1714376789054L);
        storage.addPatientData(1, 85.0, "SystolicPressure", 1714376789054L);
        storage.addPatientData(1, 85.0, "SystolicPressure", 1714376789054L);

        alertGenerator.evaluateData(storage.getAllPatients().get(0));

        // check if trigger consective increase alert
        assertEquals("Systolic Pressure consectively increased", alertGenerator.getAlerts().get(0).getCondition());
        // check if trigger over 120 alert
        assertEquals("Systolic Pressure over 180 mmHg", alertGenerator.getAlerts().get(2).getCondition());
        // check if trigger consective decrease alert
        assertEquals("Systolic Pressure consectively decreased", alertGenerator.getAlerts().get(3).getCondition());
        // check if trigger low pressure alert
        assertEquals("Systolic Pressure less than 90 mmHg", alertGenerator.getAlerts().get(5).getCondition());
    }

    @Test
    void testSaturationAlert(){
        DataStorage storage = new DataStorage();
        AlertGenerator alertGenerator=new AlertGenerator(storage);
        storage.addPatientData(1, 91.0, "Saturation", 1714376789050L);
        storage.addPatientData(1, 99.0, "Saturation", 1714376789051L);
        storage.addPatientData(1, 93.0, "Saturation", 1714376789052L);
        storage.addPatientData(1, 99.0, "Saturation", 1714376789053L);
        storage.addPatientData(1, 93.0, "Saturation", 1714377789053L);
        storage.addPatientData(1,0.2, "ECG", 1714377789053L);
        alertGenerator.evaluateData(storage.getAllPatients().get(0));

        // check if trigger low saturation alert
        assertEquals("Low Saturation", alertGenerator.getAlerts().get(0).getCondition());
        // check if trigger over 5% drop alert
        assertEquals("Sturation Drop over 5% in 10min", alertGenerator.getAlerts().get(1).getCondition());
    }

    @Test
    void testCombinedAlert(){
        DataStorage storage = new DataStorage();
        AlertGenerator alertGenerator=new AlertGenerator(storage);
        storage.addPatientData(1, 91.0, "Saturation", 1714376789050L);
        storage.addPatientData(1, 89.0, "SystolicPressure", 1714376789050L);
        storage.addPatientData(1, 91.0, "Saturation", 1714376789050L);

        alertGenerator.evaluateData(storage.getAllPatients().get(0));

        // check if trigger the combined alert
        assertEquals("Hypotensive Hypoxemia Alert", alertGenerator.getAlerts().get(2).getCondition());
    }

    @Test
    void testManualAlert(){
        DataStorage storage = new DataStorage();
        AlertGenerator alertGenerator=new AlertGenerator(storage);
        storage.addPatientData(1, 0, "Alert", 1714376789050L);
        storage.addPatientData(1, 89.0, "SystolicPressure", 1714376789050L);

        alertGenerator.evaluateData(storage.getAllPatients().get(0));

        // check if trigger the manual alert
        assertEquals("Alert!", alertGenerator.getAlerts().get(0).getCondition());
    }

    @Test
    void testFileReader() throws IOException{
        DataStorage storage = new DataStorage();
        FileDataReader reader=new FileDataReader("D:\\ProgrammingProjects\\outputtest", "");
        reader.readData(storage);
        
        List<PatientRecord> records = storage.getRecords(20, 1700000000000L, 1800000000000L);
        assertEquals(12, records.size()); // Check if 12 records are retrieved
        assertEquals(78.0, records.get(0).getMeasurementValue()); // Validate first record
    }

    @Test
    void testWebSocketClient() throws IOException {
        DataStorage storage=new DataStorage();
        
        try{
            MyWebSocketClient reader=new MyWebSocketClient(new URI("ws://localhost:8887"),storage);
            reader.connectBlocking();
        }catch(URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    

}
