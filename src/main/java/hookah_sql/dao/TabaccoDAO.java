package hookah_sql.dao;

import hookah_sql.tabacco.Tabacco;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

public class TabaccoDAO {

    private static final Logger logger = LoggerFactory.getLogger(TabaccoDAO.class);

    public static void add(Tabacco tabacco, Session session) {
        Transaction tx = session.beginTransaction();

        try {
            session.save(tabacco);
            tx.commit();
            logger.info("{} added to the table", tabacco.getName());
        } catch (Exception e) {
            tx.rollback();
            logger.error("Unable to save {}", tabacco.getName());
        }
    };

    public static void update(Tabacco tabacco, Session session) {
        Transaction tx = session.beginTransaction();

        try {
            session.update(tabacco);
            tx.commit();
            logger.info("{} updated", tabacco.getName());
        } catch (Exception e) {
            tx.rollback();
            logger.error("Unable to update {}", tabacco.getName());
        }
    };

    public static void delete(Tabacco tabacco, Session session) {
        Transaction tx = session.beginTransaction();

        try {
            session.delete(tabacco);
            tx.commit();
            logger.info("{} deleted", tabacco.getName());
        } catch (Exception e) {
            tx.rollback();
            logger.error("Unable to delete {}", tabacco.getName());
        }
    };
}
