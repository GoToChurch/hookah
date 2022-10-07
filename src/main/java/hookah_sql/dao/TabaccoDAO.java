package hookah_sql.dao;

import hookah_sql.hibernate.HibernateUtils;
import hookah_sql.tabacco.Tabacco;
import hookah_sql.tabacco.TabaccoEnum;
import hookah_sql.tabacco.TabaccoFactory;
import hookah_sql.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckMenuItem;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("tabaccoDAO")
public class TabaccoDAO {

    private static final Logger logger = LoggerFactory.getLogger(TabaccoDAO.class);

    public static void add(Tabacco tabacco) {
        Session session = HibernateUtils.getSession();
        Transaction tx = session.beginTransaction();

        try (session) {
            session.save(tabacco);
            tx.commit();
            logger.info("{} added to the table", tabacco.getName());
        } catch (Exception e) {
            tx.rollback();
            logger.error("Unable to save {}", tabacco.getName());
        }
    }

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
    }

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
    }

    public static ObservableList<Tabacco> getTabacco(String name) {
        Session session = HibernateUtils.getSession();
        List<Tabacco> tabaccos = new ArrayList<>();

        try(session) {
            Criteria criteria = session.createCriteria(Tabacco.class);
            criteria.add(Restrictions.like("name", "%" + name + "%"));
            tabaccos.addAll(criteria.list());

            criteria = session.createCriteria(Tabacco.class);
            criteria.add(Restrictions.like("flavor", "%" + name + "%"));
            tabaccos.addAll(criteria.list());

            return getObservationListToReturn(tabaccos);
        } catch (Exception e) {
            logger.error("An error occured while connecting with data base");
            return null;
        }
    }

    public static ObservableList<Tabacco> getTabaccoList(TabaccoEnum tabaccoEnum) {
        List<Tabacco> tabaccos = fillListFromDataBase(tabaccoEnum);

        return getObservationListToReturn(tabaccos);
    }

    public static ObservableList<Tabacco> getTabaccoList(
            List<String> tabaccosNames, List<String> unchosenTastes, List<Integer> hardnesses
    ) {
        List<Tabacco> tabaccos = fillListFromDataBase(tabaccosNames, hardnesses);

        getRidFromUnChosenTastesInList(Objects.requireNonNull(tabaccos), unchosenTastes);

        return getObservationListToReturn(tabaccos);
    }

    public static ObservableList<Tabacco> getTabaccoList(CheckMenuItem menuItem, ObservableList<Tabacco> items
    ) {

        List<Tabacco> tabaccos;

        if (menuItem.getParentMenu().getId().equals("tabaccoFilterMenu")) {
            tabaccos = (tabaccoFilterHandler(menuItem, items));
        }
        else if (menuItem.getParentMenu().getId().equals("tasteFilterMenu")) {
            tabaccos = tasteFilterHandler(menuItem, items);
        }
        else {
            tabaccos = hardnessFilterHandler(menuItem, items);
        }

        return getObservationListToReturn(tabaccos);
    }

    private static List<Tabacco> tabaccoFilterHandler(CheckMenuItem menuItem, ObservableList<Tabacco> items) {
        Tabacco tabacco = Utils.convertStringInTobacco(menuItem.getText());

        if (menuItem.isSelected()) {
            Session session = HibernateUtils.getSession();
            try (session){

                Criteria criteria = session.createCriteria(tabacco.getClass());
                items.addAll(criteria.list());
            } catch (Exception e) {
                logger.error("An error occured while connecting with data base");
            }
        } else {
            items.removeIf(tabaccoToDelete -> tabaccoToDelete.getTabaccoName().equals(tabacco.getTabaccoName()));
        }

        return items;
    }

    private static List<Tabacco> hardnessFilterHandler(CheckMenuItem menuItem, ObservableList<Tabacco> items) {
        int wantedHardness = Integer.parseInt(menuItem.getText());

        if (menuItem.isSelected()) {
            Session session = HibernateUtils.getSession();
            try (session){
                Criteria criteria = session.createCriteria(Tabacco.class);
                criteria.add(Restrictions.eq("hardness", wantedHardness));
                items.addAll(criteria.list());
            } catch (Exception e) {
                logger.error("An error occured while connecting with data base");
            }
        }

        else {
            items.removeIf(tabaccoToDelete -> tabaccoToDelete.getHardness() == wantedHardness);
        }

        return items;
    }

    private static List<Tabacco> tasteFilterHandler(CheckMenuItem menuItem, ObservableList<Tabacco> items ) {
        String wantedTaste = menuItem.getText();

        if (menuItem.isSelected()) {
            Session session = HibernateUtils.getSession();
            try (session) {
                Criteria criteria = session.createCriteria(Tabacco.class);
                criteria.add(Restrictions.like("taste", "%" + wantedTaste + "%"));
                items.addAll(criteria.list());
            } catch (Exception e) {
                logger.error("An error occured while connecting with data base");
            }
        } else {
            items.removeIf(tabaccoToDelete -> Arrays.asList(tabaccoToDelete.getTaste().split(", ")).contains(wantedTaste));
        }

        return items;
    }

    private static List<Tabacco> fillListFromDataBase(List<String> tabaccosNames, List<Integer> hardnesses) {
        Session session = HibernateUtils.getSession();
        List<Tabacco> tabaccos = new ArrayList<>();

        try (session){
            for (int i = 1; i < 8; i++) {
                TabaccoEnum tabacco = TabaccoEnum.getTobaccosByOrdinal(i);
                Criteria criteria = session.createCriteria(
                        Objects.requireNonNull(TabaccoFactory.tabaccoFactory(tabacco)).getClass()
                );
                if (hardnesses.size() > 0) {
                    criteria.add(Restrictions.in("hardness", hardnesses));
                }
                if (tabaccosNames.size() == 0 || tabaccosNames.contains(Utils.getStringByTabaccoEnum(tabacco))) {
                    tabaccos.addAll(criteria.list());
                }
            }

            return tabaccos;
        } catch (Exception e) {
            logger.error("An error occured while connecting with data base");

            return null;
        }
    }

    private static List<Tabacco> fillListFromDataBase(TabaccoEnum tabaccoEnum) {
        Session session = HibernateUtils.getSession();
        List<Tabacco> tabaccos = new ArrayList<>();

        try (session){
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
            return tabaccos;
        } catch (Exception e) {
            logger.error("An error occured while connecting with data base");

            return null;
        }
    }

    private static void getRidFromUnChosenTastesInList(List<Tabacco> tabaccos, List<String> unchosenTastes) {
        Iterator<Tabacco> it = tabaccos.iterator();

        if (unchosenTastes.size() != 21) {
            while (it.hasNext()) {
                Tabacco tabaccoToDelete = it.next();

                boolean toDelete = true;
                for (String taste : tabaccoToDelete.getTaste().split(", ")) {
                    if (!unchosenTastes.contains(taste)) {
                        toDelete = false;
                        break;
                    }
                }
                if (toDelete) {
                    it.remove();
                }
            }
        }
    }

    private static ObservableList<Tabacco> getObservationListToReturn(List<Tabacco> tabaccos) {
        ObservableList<Tabacco> observableTabaccoList = FXCollections.observableArrayList(tabaccos);

        for (Tabacco tabacco : observableTabaccoList) {
            tabacco.prepareProperties();
        }

        return observableTabaccoList;
    }
}
