package hookah_sql.tabacco;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "duft")
@Component("Duft")
@Scope("prototype")
public class Duft extends Tabacco {
    public Duft() {
        this.hardness = 6;
        this.smokingDuration = 60;
        this.heatResistance = "Высокая";
    }

}
