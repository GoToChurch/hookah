package hookah_sql.commands;

import hookah_sql.config.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExitCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ExitCommand.class);

    @Override
    public void execute() {
        logger.info("User exits the app");
        Context.getInstance().close();
    }
}
