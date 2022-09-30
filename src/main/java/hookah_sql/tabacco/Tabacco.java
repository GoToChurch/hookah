package hookah_sql.tabacco;

import hookah_sql.mix.Mix;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@Component
public class Tabacco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected int id;

    @NotNull
    @Column(name = "name", nullable = false)
    protected String name;

    @NotNull
    @Column(name = "flavor", nullable = false)
    protected String flavor;

    @Column(name = "description")
    protected String description;

    @NotNull
    @Column(name = "taste", nullable = false)
    protected String taste;

    @NotNull
    @Min(0)
    @Max(10)
    @Column(name = "hardness", nullable = false)
    protected int hardness;

    @Transient
    private StringProperty tabaccoProperty;

    @Transient
    private StringProperty nameProperty;

    @Transient
    private StringProperty flavorProperty;

    @Transient
    private StringProperty descriptionProperty;

    @Transient
    private StringProperty tasteProperty;

    @Transient
    private IntegerProperty hardnessProperty;

    public Tabacco() {

    }

    @Override
    public String toString() {
        return String.format("Табак: %s%nНазвание: %s%nВкус: %s; %s%nОписание: %s%nКрепость: %s%n",
                this.getClass().getSimpleName(), name, flavor, taste, description, hardness);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHardness() {
        return hardness;
    }

    public void setHardness(int hardness) {
        this.hardness = hardness;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameProperty() {
        return nameProperty.get();
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }

    public String getFlavorProperty() {
        return flavorProperty.get();
    }

    public StringProperty flavorProperty() {
        return flavorProperty;
    }

    public String getDescriptionProperty() {
        return descriptionProperty.get();
    }

    public StringProperty descriptionProperty() {
        return descriptionProperty;
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

    public String getTabaccoProperty() {
        return tabaccoProperty.get();
    }

    public StringProperty tabaccoProperty() {
        return tabaccoProperty;
    }

    public String getTabaccoName() {
        return this.getClass().getSimpleName();
    }

    public void prepareProperties() {
        tabaccoProperty = new SimpleStringProperty(this.getClass().getSimpleName());
        nameProperty = new SimpleStringProperty(name);
        flavorProperty = new SimpleStringProperty(flavor);
        descriptionProperty = new SimpleStringProperty(description);
        tasteProperty = new SimpleStringProperty(taste);
        hardnessProperty = new SimpleIntegerProperty(hardness);
    }

    public boolean isEmpty() {
        return Objects.equals(name, null) && Objects.equals(flavor, null) && Objects.equals(taste, null)
                && Objects.equals(hardness, 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tabacco tabacco = (Tabacco) o;
        return Objects.equals(name, tabacco.name) && Objects.equals(flavor, tabacco.flavor) && Objects.equals(description, tabacco.description) && Objects.equals(taste, tabacco.taste);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, flavor, description, hardness, taste);
    }
}
