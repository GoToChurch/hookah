package hookah_sql.commands.mix_commands;

import hookah_sql.ConsoleHelper;
import hookah_sql.commands.Command;
import hookah_sql.dao.MixDAO;
import hookah_sql.hibernate.HibernateUtils;
import hookah_sql.mix.Mix;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(AddCommand.class);

    @Override
    public void execute() {
        logger.info("User wants to add new mix");

        Session session = HibernateUtils.getSession();

        Mix mix = null;

        ConsoleHelper.writeMessage("Введите названия табаков в миксе:");
        mix.setNames(ConsoleHelper.readString());
        ConsoleHelper.writeMessage("Введите вкусы в миксе:");
        mix.setFlavors(ConsoleHelper.readString());
        ConsoleHelper.writeMessage("Введите какой микс на вкус:");
        mix.setTaste(ConsoleHelper.readString());
        ConsoleHelper.writeMessage("Введите крепость:");
        mix.setHardness(Integer.parseInt(ConsoleHelper.readString()));

        MixDAO.add(mix, session);

        logger.info("Successfully added new mix: {}", mix.getNames());

        session.close();
    }
}
