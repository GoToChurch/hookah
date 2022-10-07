package hookah_sql.dao;

import hookah_sql.hibernate.HibernateUtils;
import hookah_sql.mix.Mix;
import hookah_sql.tabacco.Tabacco;
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

    public static ObservableList<Mix> getMix(String name) {
        logger.info("User wants to get particular mix");

        List<Mix> mixes = new ArrayList<>();

        Session session = HibernateUtils.getSession();

        try(session) {
            Criteria criteria = session.createCriteria(Mix.class);
            criteria.add(Restrictions.like("names", "%" + name + "%"));
            mixes.addAll(criteria.list());

            criteria = session.createCriteria(Mix.class);
            criteria.add(Restrictions.like("flavors", "%" + name + "%"));
            mixes.addAll(criteria.list());

            return getObservationListToReturn(mixes);
        } catch (Exception e) {
            logger.error("An error occured when connecting to mix data base");

            return null;
        }
    }

    public static ObservableList<Mix> getMixList() {
        logger.info("User chose mix show operation");

        Session session = HibernateUtils.getSession();

        try(session) {
            Criteria criteria = session.createCriteria(Mix.class);

            return getObservationListToReturn(criteria.list());
        } catch (Exception e) {
            logger.error("An error occured when connecting to mix data base");

            return null;
        }
    }

    public static ObservableList<Mix> getMixList(
            List<String> tabaccosNames, List<String> unchosenTastes, List<Integer> hardnesses
    ) {
        logger.info("User chose mix show operation");

        List<Mix> mixes = fillListFromDataBase(tabaccosNames, hardnesses);

        getRidFromUnChosenTastesInList(Objects.requireNonNull(mixes), unchosenTastes);

        return getObservationListToReturn(mixes);
    }

    public static ObservableList<Mix> getMixList(CheckMenuItem menuItem, ObservableList<Mix> items) {
        logger.info("User chose mix show operation");

        List<Mix> mixes;

        if (menuItem.getParentMenu().getId().equals("tabaccoFilterMenu")) {
            mixes = (tabaccoFilterHandler(menuItem, items));
        }
        else if (menuItem.getParentMenu().getId().equals("tasteFilterMenu")) {
            mixes = tasteFilterHandler(menuItem, items);
        }
        else {
            mixes = hardnessFilterHandler(menuItem, items);
        }

        return getObservationListToReturn(mixes);
    }

    private static List<Mix> tabaccoFilterHandler(CheckMenuItem menuItem, ObservableList<Mix> items) {
        Tabacco tabacco = Utils.convertStringInTobacco(menuItem.getText());

        if (menuItem.isSelected()) {
            Session session = HibernateUtils.getSession();
            try (session){
                Criteria criteria = session.createCriteria(Mix.class);
                criteria.add(Restrictions.like("names", "%" + tabacco.getTabaccoName() + "%"));
                items.addAll(criteria.list());
            } catch (Exception e) {
                logger.error("An error occured while connecting with mix data base");

                return null;
            }
        } else {
            Iterator<Mix> it = items.iterator();

            while (it.hasNext()) {
                Mix mix = it.next();
                for (String tabaccoInMix : mix.getAllTabaccosInMix()) {
                    if (tabaccoInMix.equals(tabacco.getTabaccoName())) {
                        it.remove();
                        break;
                    }
                }
            }
        }

        return items;
    }

    private static List<Mix> hardnessFilterHandler(CheckMenuItem menuItem, ObservableList<Mix> items) {
        int wantedHardness = Integer.parseInt(menuItem.getText());

        if (menuItem.isSelected()) {
            Session session = HibernateUtils.getSession();
            try (session){
                Criteria criteria = session.createCriteria(Mix.class);
                criteria.add(Restrictions.eq("hardness", wantedHardness));
                items.addAll(criteria.list());
            } catch (Exception e) {
                logger.error("An error occured while connecting with mix data base");
            }
        }

        else {
            items.removeIf(tabaccoToDelete -> tabaccoToDelete.getHardness() == wantedHardness);
        }

        return items;
    }

    private static List<Mix> tasteFilterHandler(CheckMenuItem menuItem, ObservableList<Mix> items ) {
        String wantedTaste = menuItem.getText();

        if (menuItem.isSelected()) {
            Session session = HibernateUtils.getSession();
            try (session) {
                Criteria criteria = session.createCriteria(Mix.class);
                criteria.add(Restrictions.like("taste", "%" + wantedTaste + "%"));
                items.addAll(criteria.list());
            } catch (Exception e) {
                logger.error("An error occured while connecting with mix data base");
            }
        } else {
            items.removeIf(mixToDelete -> Arrays.asList(mixToDelete.getTaste().split(", ")).contains(wantedTaste));
        }

        return items;
    }

    private static List<Mix> fillListFromDataBase(List<String> tabaccosNames, List<Integer> hardnesses) {
        Session session = HibernateUtils.getSession();
        List<Mix> mixes = new ArrayList<>();

        try (session){
            Criteria criteria = session.createCriteria(Mix.class);
            if (hardnesses.size() > 0) {
                criteria.add(Restrictions.in("hardness", hardnesses));
            }
            if (tabaccosNames.size() > 0) {
                for (String tabaccoName : tabaccosNames) {
                    criteria.add(Restrictions.like("name", "%" + tabaccoName + "%"));
                }
            }

            mixes.addAll(criteria.list());

            return mixes;
        } catch (Exception e) {
            logger.error("An error occured while connecting with mix data base");

            return null;
        }
    }

    private static void getRidFromUnChosenTastesInList(List<Mix> mixes, List<String> unchosenTastes) {
        Iterator<Mix> it = mixes.iterator();

        if (unchosenTastes.size() != 21) {
            while (it.hasNext()) {
                Mix mixToDelete = it.next();

                boolean toDelete = true;
                for (String taste : mixToDelete.getTaste().split(", ")) {
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

    private static ObservableList<Mix> getObservationListToReturn(List<Mix> mixes) {
        ObservableList<Mix> observableMixList = FXCollections.observableArrayList(mixes);

        for (Mix mix : observableMixList) {
            mix.prepareProperties();
        }

        return observableMixList;
    }
}
