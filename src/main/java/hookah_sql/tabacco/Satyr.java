package hookah_sql.tabacco;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "satyr")
@Component("Satyr")
@Scope("prototype")
public class Satyr extends Tabacco {
    public Satyr() {
        this.hardness = 10;
        this.smokingDuration = 60;
        this.heatResistance = "Высокая";
    }

}
