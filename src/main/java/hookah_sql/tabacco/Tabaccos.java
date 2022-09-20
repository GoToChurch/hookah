package hookah_sql.tabacco;

import hookah_sql.hibernate.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("tabaccos")
public class Tabaccos {
    private Tabaccos() {
    }

    public List<Tabacco> getTobacco (TabaccoEnum tabaccoEnum, String name) {
        Session session = HibernateUtils.getSession();

        Criteria criteria = session.createCriteria(TabaccoFactory.tabaccoFactory(tabaccoEnum).getClass());
        criteria.add(Restrictions.eq("name", name));
        List<Tabacco> tabacco = criteria.list();

        session.close();

        return tabacco;
    }

    public List<Tabacco> getTabaccoList(TabaccoEnum tabaccoEnum) {
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

        return tabaccos;
    }
}
