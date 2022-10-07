package hookah_sql.mix;

import hookah_sql.tabacco.Tabacco;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Transient
    private StringProperty namesProperty;

    @Transient
    private StringProperty flavorsProperty;

    @Transient
    private StringProperty tasteProperty;

    @Transient
    private IntegerProperty hardnessProperty;

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

    public List<Tabacco> getTabaccoList() {
        return tabaccoList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNamesProperty() {
        return namesProperty.get();
    }

    public StringProperty namesProperty() {
        return namesProperty;
    }

    public String getFlavorsProperty() {
        return flavorsProperty.get();
    }

    public StringProperty flavorsProperty() {
        return flavorsProperty;
    }

    public String getTasteProperty() {
        return tasteProperty.get();
    }

    public StringProperty tasteProperty() {
        return tasteProperty;
    }

    public int getHardnessProperty() {
        return hardnessProperty.get();
    }

    public IntegerProperty hardnessProperty() {
        return hardnessProperty;
    }


    public void finilizeMix() {
        names = names.substring(0, names.length() - 2);
        flavors = flavors.substring(0, flavors.length() - 2);
        taste = taste.substring(0, taste.length() - 2);
    }

    public void prepareProperties() {
        namesProperty = new SimpleStringProperty(names);
        flavorsProperty = new SimpleStringProperty(flavors);
        tasteProperty = new SimpleStringProperty(taste);
        hardnessProperty = new SimpleIntegerProperty(hardness);
    }

    public List<String> getAllTabaccosInMix() {
        String[] tabaccos = names.split(", ");

        for (int i = 0; i < tabaccos.length; i++) {
            tabaccos[i] = tabaccos[i].replaceAll(".+\\(", "").replaceAll("\\)", "");
        }

        return Arrays.asList(tabaccos);
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
