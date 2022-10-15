package hookah_sql.tabacco;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "musthave")
@Component("Musthave")
@Scope("prototype")
public class Musthave extends Tabacco {
    public Musthave() {
        this.hardness = 5;
        this.smokingDuration = 50;
        this.heatResistance = "Высокая";
    }

}
