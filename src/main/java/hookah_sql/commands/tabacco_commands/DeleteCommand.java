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

import java.util.List;

public class DeleteCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(DeleteCommand.class);

    @Override
    public void execute() {
        logger.info("User wants to delete existing tabacco");
        Session session = HibernateUtils.getSession();

        List<Tabacco> tabaccos = Context.getInstance().getContext().getBean("tabaccos", Tabaccos.class)
                .getTobacco(ConsoleHelper.askTabaccoDelete(), ConsoleHelper.askFlavor());

        for (Tabacco tabacco : tabaccos) {
                TabaccoDAO.delete(tabacco, session);
        }

        session.close();
    }
}
