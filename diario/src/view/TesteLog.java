package view;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TesteLog {

    private static final Logger logger = LogManager.getLogger(TesteLog.class);

    public static void main(String[] args) {
        logger.info("Teste de log INFO");
        logger.error("Teste de log ERROR");
    }
}
