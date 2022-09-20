package hookah_sql.commands.tabacco_commands;

import hookah_sql.ConsoleHelper;
import hookah_sql.commands.Command;
import hookah_sql.config.Context;
import hookah_sql.dao.TabaccoDAO;
import hookah_sql.hibernate.HibernateUtils;
import hookah_sql.tabacco.*;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(AddCommand.class);

    @Override
    public void execute() {
        logger.info("User wants to add new tabacco");

        Session session = HibernateUtils.getSession();

        Tabacco tabacco = null;

        ConsoleHelper.writeMessage("Введите марку табака:");
        String tabaccoName = ConsoleHelper.readString();

        switch (tabaccoName) {
            case "Darkside" -> tabacco = Context.getInstance().getContext().getBean("Darkside", Darkside.class);
            case "Black Burn" -> tabacco = Context.getInstance().getContext().getBean("Blackburn", Blackburn.class);
            case "Must Have" -> tabacco = Context.getInstance().getContext().getBean("Musthave", Musthave.class);
            case "Satyr" -> tabacco = Context.getInstance().getContext().getBean("Satyr", Satyr.class);
            case "Duft" -> tabacco = Context.getInstance().getContext().getBean("Duft", Duft.class);
            case "Element" -> tabacco = Context.getInstance().getContext().getBean("Element", Element.class);
            case "Tangiers" -> tabacco = Context.getInstance().getContext().getBean("Tangiers", Tangiers.class);
        }

        ConsoleHelper.writeMessage("Введите название:");
        tabacco.setName(ConsoleHelper.readString());
        ConsoleHelper.writeMessage("Введите вкус:");
        tabacco.setFlavor(ConsoleHelper.readString());
        ConsoleHelper.writeMessage("Введите описание:");
        tabacco.setDescription(ConsoleHelper.readString());
        ConsoleHelper.writeMessage("Введите вкусы:");
        tabacco.setTaste(ConsoleHelper.readString());
        ConsoleHelper.writeMessage("Введите крепость:");
        tabacco.setHardness(Integer.parseInt(ConsoleHelper.readString()));

        TabaccoDAO.add(tabacco, session);

        session.close();
    }
}
