package Onderzoek;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class CSVLogger {

    public void writeLogFile(int aantalSpellen, int roundCount, String name) {
        Path path = Paths.get("C:/ISY-Carioca-ITVC2S1/Onderzoek/CSVLogger.csv");
        // Use try-with-resources to auto-close the file if successfully opened.
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
            // If the file doesn't exist, create it.
            if (Files.size(path) == 0) {
                // Write the headers if the file is empty
                writer.write("GameWinner;GameNumber;RoundCount");
                writer.newLine();
            }

            // Write data to the rows
            writer.write(String.valueOf(name));
            writer.write(";");
            writer.write(String.valueOf(aantalSpellen));
            writer.write(";");
            writer.write(String.valueOf(roundCount));
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
