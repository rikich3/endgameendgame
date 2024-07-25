package com.unsa.eda.service.Reader;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Service;

import List.List;
import List.MyArrayList;

@Service
public class Reader {
    private MyArrayList<Music> musics ;
    public Reader(){
        this.musics = new MyArrayList<>();
        readData();
    }
    private void readData() {
        String filePath = "src/main/resources/spotify_data.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine();
            // while ((line = reader.readLine()) != null) {
                for(int i = 0; i < 1000; i++){
                //mejorar para que no divida cuando encuentre comas dentro de comillas
                line = reader.readLine();
                List<String> parts = parseCSVLine(line);

                if (parts.size() >= 20) {
                    Music music = new Music(parts.get(0), parts.get(1), parts.get(2), parts.get(3), parts.get(4), parts.get(5),
                    parts.get(6), parts.get(7), parts.get(8), parts.get(9), parts.get(10), parts.get(11),
                    parts.get(12), parts.get(13), parts.get(14), parts.get(15), parts.get(16), parts.get(17),
                    parts.get(18), parts.get(19));
                    this.musics.add(music);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private List<String> parseCSVLine(String line) {
        List<String> result = new MyArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes; // Toggle the inQuotes flag
            } else if (c == ',' && !inQuotes) {
                result.add(currentField.toString());
                currentField.setLength(0); // Reset the StringBuilder
            } else {
                currentField.append(c);
            }
        }
        result.add(currentField.toString()); // Add the last field

        // Remove quotes surrounding the fields
        for (int i = 0; i < result.size(); i++) {
            result.set(i, result.get(i).replaceAll("^\"|\"$", ""));
        }
        return result;
    }
    public MyArrayList<Music> getData(){
        return this.musics;
    }
}

