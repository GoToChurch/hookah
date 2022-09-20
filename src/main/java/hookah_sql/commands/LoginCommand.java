package hookah_sql.commands;

import hookah_sql.ConsoleHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoginCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(LoginCommand.class);

    @Override
    public void execute() {
        logger.info("User starts using app");

        ConsoleHelper.writeMessage("Добро пожаловать!");
    }
}
