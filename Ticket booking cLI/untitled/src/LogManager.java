import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogManager {
    public static void configureLogger(Logger logger, String logFileName) {
        try {
            FileHandler fileHandler = new FileHandler(logFileName, true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false); // Prevent logs from appearing in the terminal
        } catch (IOException e) {
            System.err.println("Failed to configure logger: " + e.getMessage());
        }
    }
}