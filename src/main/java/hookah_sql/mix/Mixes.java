package hookah_sql.mix;

import hookah_sql.hibernate.HibernateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("mixes")
public class Mixes {
    private Mixes() {

    }

    public List<Mix> getMix (String name) {
        Session session = HibernateUtils.getSession();

        Criteria criteria = session.createCriteria(Mix.class);
        criteria.add(Restrictions.eq("names", name));
        List<Mix> mix = criteria.list();

        session.close();

        return mix;
    }

    public List<Mix> getMixList() {
        Session session = HibernateUtils.getSession();

        Criteria criteria = session.createCriteria(Mix.class);
        List<Mix> mixes = criteria.list();

        session.close();

        return mixes;
    }
}
