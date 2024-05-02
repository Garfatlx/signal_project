package com.data_management;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileDataReader implements DataReader {

    private String pathString;

    public FileDataReader(String pathString, String label){
        this.pathString=pathString;
    }

    public void readData(DataStorage dataStorage) throws IOException{
        File fileDir = new File(pathString);
        String line;
        for (File file : fileDir.listFiles()) {
            try(BufferedReader reader=Files.newBufferedReader(file.toPath())){
                while ((line=reader.readLine())!=null) {
                    String[] tokens=line.split(":|,|%");
                    if (tokens[5].trim().equals("Alert")) {
                        continue;
                    }
                    dataStorage.addPatientData(Integer.parseInt(tokens[2].trim()), Double.parseDouble(tokens[7].trim()), tokens[5].trim(), Long.parseLong(tokens[3].trim()));
                }
            }

        }
    }
}
