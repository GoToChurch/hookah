package hookah_sql.dao;

import hookah_sql.hibernate.HibernateUtils;
import hookah_sql.mix.Mix;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("mixDAO")
public class MixDAO {
    private static final Logger logger = LoggerFactory.getLogger(MixDAO.class);

    public static void add(Mix mix) {
        Session session = HibernateUtils.getSession();
        Transaction tx = session.beginTransaction();

        try (session) {
            session.save(mix);
            tx.commit();
            logger.info("{} added to the table", mix.getNames());
        } catch (Exception e) {
            tx.rollback();
            logger.error("Unable to save {}", mix.getNames());
        }
    };

    public static void update(Mix mix) {
        Session session = HibernateUtils.getSession();
        Transaction tx = session.beginTransaction();

        try (session) {
            session.update(mix);
            tx.commit();
            logger.info("{} updated", mix.getNames());
        } catch (Exception e) {
            tx.rollback();
            logger.error("Unable to update {}", mix.getNames());
        }
    };

    public static void delete(Mix mix) {
        Session session = HibernateUtils.getSession();
        Transaction tx = session.beginTransaction();

        try (session) {
            session.delete(mix);
            tx.commit();
            logger.info("{} deleted", mix.getNames());
        } catch (Exception e) {
            tx.rollback();
            logger.error("Unable to delete {}", mix.getNames());
        }
    };

    public static ObservableList<Mix> getMixList() {
        logger.info("User chose mix show operation");

        Session session = HibernateUtils.getSession();

        Criteria criteria = session.createCriteria(Mix.class);
        ObservableList<Mix> observableMixList =  FXCollections.observableArrayList(criteria.list());

        session.close();

        for (Mix mix : observableMixList) {
            mix.prepareProperties();
        }

        return observableMixList;
    }

    public static ObservableList<Mix> getMix (String name) {
        Session session = HibernateUtils.getSession();

        Criteria criteria = session.createCriteria(Mix.class);
        criteria.add(Restrictions.eq("names", name));
        ObservableList<Mix> observableMixList =  FXCollections.observableArrayList(criteria.list());

        for (Mix mix : observableMixList) {
            mix.prepareProperties();
        }

        session.close();

        return observableMixList;
    }
}
