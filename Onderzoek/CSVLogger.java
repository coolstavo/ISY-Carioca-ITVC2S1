package Onderzoek;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CSVLogger {

    public void writeLogFile(int aantalSpellen, int roundCount) {
        Path path = Paths.get("C:/ISY-Carioca-ITVC2S1/Onderzoek/CSVLogger.csv");
        // Use try-with-resources to auto-close the file if successfully opened.
        try (BufferedWriter writer = Files.newBufferedWriter(path)) { // Will be automatically closed if successfully opened.
            // Schrijf de headers van de kolommen
            writer.write("GameNumber");
            writer.write(";");
            writer.write("RoundCount");
            writer.write(";");

            writer.newLine();

            // Write data to the rows
            writer.write(String.valueOf(aantalSpellen));
            writer.write(";");
            writer.write(String.valueOf(roundCount));
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}