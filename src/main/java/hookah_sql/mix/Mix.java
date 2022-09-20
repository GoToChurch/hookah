package hookah_sql.mix;

import hookah_sql.dao.MixDAO;
import hookah_sql.hibernate.HibernateUtils;
import hookah_sql.tabacco.Tabacco;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "mix")
@Component
@Scope("prototype")
public class Mix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Transient
    private final List<Tabacco> tabaccoList = new ArrayList<>();

    @NotNull
    @Column(name = "names", nullable = false)
    private String names = "";

    @NotNull
    @Column(name = "flavors", nullable = false)
    private String flavors = "";

    @NotNull
    @Column(name = "taste", nullable = false)
    private String taste = "";

    @NotNull
    @Column(name = "hardness", nullable = false)
    private int hardness = 0;

    public void addToMix(Tabacco tabacco) {
        tabaccoList.add(tabacco);
        names += String.format("%s(%s), ",tabacco.getName(), tabacco.getClass().getSimpleName());
        flavors += tabacco.getFlavor() + ", ";
        for (String tobaccoTaste : tabacco.getTaste().split(" ")) {
            String tobaacooTaste = tobaccoTaste.replace(",", "");
            taste += taste.contains(tobaacooTaste) ? "" : tobaacooTaste + ", ";
        }

        hardness += tabacco.getHardness();
    }

    public void addToMix(List<Tabacco> tabaccos) {
        for (Tabacco tabacco : tabaccos) {
           addToMix(tabacco);
        }
    }

    public void clearMix() {
        tabaccoList.clear();
        names = "";
        flavors = "";
        taste = "";
        hardness = 0;
    }

    public void countHardness() {
        hardness /= tabaccoList.size();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Tabacco> getTabaccoList() {
        return tabaccoList;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getFlavors() {
        return flavors;
    }

    public void setFlavors(String flavors) {
        this.flavors = flavors;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public int getHardness() {
        return hardness;
    }

    public void setHardness(int hardness) {
        this.hardness = hardness;
    }

    public void save() {
        Session session = HibernateUtils.getSession();

        MixDAO.add(this, session);

        session.close();
    }

    public void finilizeMix() {
        names = names.substring(0, names.length() - 2);
        flavors = flavors.substring(0, flavors.length() - 2);
        taste = taste.substring(0, taste.length() - 2);
    }

    @Override
    public String toString() {
        return String.format("Табаки: %s%nВкус: %s; %s%nКрепость: %d%n", names, flavors, taste, hardness);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mix mix = (Mix) o;
        return Objects.equals(tabaccoList, mix.tabaccoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tabaccoList);
    }
}
