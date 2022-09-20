package hookah_sql.commands.mix_commands;

import hookah_sql.commands.Command;
import hookah_sql.config.Context;
import hookah_sql.mix.Mixes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShowCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ShowCommand.class);

    @Override
    public void execute() {
        logger.info("User chose mix show operation");

        System.out.println(
                Context.getInstance().getContext().getBean("mixes", Mixes.class)
                        .getMixList()
        );
    }
}
