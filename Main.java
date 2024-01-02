import App.ConnectScreen;
import App.SelectScreen;
import App.SplashScreen;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. Create and display the splash screen
            SplashScreen splashScreen = new SplashScreen("Splash Screen");
            splashScreen.show();

            // 2. Set a timer to close the splash screen after 3 seconds and open the ConnectScreen
            Timer splashTimer = new Timer(3000, e -> {
                splashScreen.close();

                // 3. Create and display the ConnectScreen
                ConnectScreen connectScreen = new ConnectScreen("Server Connection");
                connectScreen.show();

                // 4. Set a timer to close the ConnectScreen after a certain time and open the SelectScreen
                Timer connectTimer = new Timer(3000, e2 -> {
                    connectScreen.close();

                    // 5. Create and display the SelectScreen
                    SelectScreen selectScreen = new SelectScreen();
                    selectScreen.show();
                });

                connectTimer.setRepeats(false); // Execute only once
                connectTimer.start();
            });

            splashTimer.setRepeats(false); // Execute only once
            splashTimer.start();
        });
    }

}
