package hookah_sql.commands.mix_commands;

import hookah_sql.ConsoleHelper;
import hookah_sql.commands.Command;
import hookah_sql.config.Context;
import hookah_sql.dao.MixDAO;
import hookah_sql.hibernate.HibernateUtils;
import hookah_sql.mix.Mix;
import hookah_sql.mix.Mixes;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DeleteCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(DeleteCommand.class);

    @Override
    public void execute() {
        logger.info("User wants to delete existing mix");

        Session session = HibernateUtils.getSession();

        List<Mix> mixes = Context.getInstance().getContext().getBean("mixes", Mixes.class)
                .getMix(ConsoleHelper.askMix());

        for (Mix mix : mixes) {
            MixDAO.delete(mix, session);
            logger.info("Successfully deleted mix: {}", mix.getNames());
        }

        session.close();
    }
}
