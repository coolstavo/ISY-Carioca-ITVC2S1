import App.SplashScreen;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            // Create and display the splash screen
            SplashScreen splashScreen = new SplashScreen("Carioca AI");
            splashScreen.show();
        });





    }
}
