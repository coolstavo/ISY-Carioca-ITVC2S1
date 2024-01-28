package Onderzoek;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CSVLogger {

    public void writeLogFile() {
        Path path = Paths.get("/Users/gustavocostaaraujo/Documents/College/2ᵉ Jaar/Intelligente systemen I & II/Project Intelligente Systemen I & II/ISY-Carioca-ITVC2S1/Onderzoek/CSVLogger.csv");
        // Use try-with-resources to auto-close the file if successfully opened.
        try (BufferedWriter writer = Files.newBufferedWriter(path)) { // Will be automatically closed if successfully opened.
            // Schrijf de headers van de kolommen
            writer.write("Naam");
            writer.write(";");
            writer.write("Winst");
            writer.write(";");
            writer.write("Column3");
            writer.write(";");
            writer.write("Column4");
            writer.newLine();

            // Write data to the rows
            writer.write("Bart");
            writer.write(";");
            writer.write("True");
            writer.write(";");
            writer.write("Data3");
            writer.write(";");
            writer.write("Data4");
            writer.newLine();

            // Add more data to other cells
            writer.write("Trab");
            writer.write(";");
            writer.write("False");
            writer.write(";");
            writer.write("Data3");
            writer.write(";");
            writer.write("Data4");
            writer.newLine();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}