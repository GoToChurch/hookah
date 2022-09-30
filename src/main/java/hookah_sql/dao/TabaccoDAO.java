package hookah_sql.dao;

import hookah_sql.hibernate.HibernateUtils;
import hookah_sql.tabacco.Tabacco;
import hookah_sql.tabacco.TabaccoEnum;
import hookah_sql.tabacco.TabaccoFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("tabaccoDAO")
public class TabaccoDAO {

    private static final Logger logger = LoggerFactory.getLogger(TabaccoDAO.class);

    public static void add(Tabacco tabacco) {
        Session session = HibernateUtils.getSession();
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

    public static void update(Tabacco tabacco) {
        Session session = HibernateUtils.getSession();
        Transaction tx = session.beginTransaction();

        try(session) {
            session.update(tabacco);
            tx.commit();
            logger.info("{} updated", tabacco.getName());
        } catch (Exception e) {
            tx.rollback();
            logger.error("Unable to update {}", tabacco.getName());
        }
    };

    public static void delete(Tabacco tabacco) {
        Session session = HibernateUtils.getSession();
        Transaction tx = session.beginTransaction();

        try(session) {
            session.delete(tabacco);
            tx.commit();
            logger.info("{} deleted", tabacco.getName());
        } catch (Exception e) {
            tx.rollback();
            logger.error("Unable to delete {}", tabacco.getName());
        }
    };

    public static ObservableList<Tabacco> getTobacco (String name) {
        Session session = HibernateUtils.getSession();

        Criteria criteria = session.createCriteria(Tabacco.class);
        criteria.add(Restrictions.eq("name", name));
        ObservableList<Tabacco> observableTabaccoList = FXCollections.observableArrayList(criteria.list());

        session.close();

        for (Tabacco tabacco : observableTabaccoList) {
            tabacco.prepareProperties();
        }

        return observableTabaccoList;
    }

    public static ObservableList<Tabacco> getTabaccoList(TabaccoEnum tabaccoEnum) {
        Session session = HibernateUtils.getSession();

        List<Tabacco> tabaccos = new ArrayList<>();

        if(tabaccoEnum == TabaccoEnum.ALL) {

            for (int i = 1; i < 8; i++) {
                Criteria criteria = session.createCriteria(
                        TabaccoFactory.tabaccoFactory(TabaccoEnum.getTobaccosByOrdinal(i)).getClass()
                );
                tabaccos.addAll(criteria.list());
            }
        }
        else {
            Criteria criteria = session.createCriteria(TabaccoFactory.tabaccoFactory(tabaccoEnum).getClass());
            tabaccos.addAll(criteria.list());
        }

        session.close();
        ObservableList<Tabacco> observableTabaccoList = FXCollections.observableArrayList(tabaccos);

        for (Tabacco tabacco : observableTabaccoList) {
            tabacco.prepareProperties();
        }

        return observableTabaccoList;
    }
}
