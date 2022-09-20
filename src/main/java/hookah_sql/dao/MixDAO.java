package hookah_sql.dao;

import hookah_sql.mix.Mix;
import hookah_sql.tabacco.Tabacco;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MixDAO {
    private static final Logger logger = LoggerFactory.getLogger(MixDAO.class);

    public static void add(Mix mix, Session session) {
        Transaction tx = session.beginTransaction();

        try {
            session.save(mix);
            tx.commit();
            logger.info("{} added to the table", mix.getNames());
        } catch (Exception e) {
            tx.rollback();
            logger.error("Unable to save {}", mix.getNames());
        }
    };

    public static void update(Mix mix, Session session) {
        Transaction tx = session.beginTransaction();

        try {
            session.update(mix);
            tx.commit();
            logger.info("{} updated", mix.getNames());
        } catch (Exception e) {
            tx.rollback();
            logger.error("Unable to update {}", mix.getNames());
        }
    };

    public static void delete(Mix mix, Session session) {
        Transaction tx = session.beginTransaction();

        try {
            session.delete(mix);
            tx.commit();
            logger.info("{} deleted", mix.getNames());
        } catch (Exception e) {
            tx.rollback();
            logger.error("Unable to delete {}", mix.getNames());
        }
    };
}
