package hookah_sql.tabacco;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "blackburn")
@Component("Blackburn")
@Scope("prototype")
public class Blackburn extends Tabacco {
    public Blackburn() {
        this.hardness = 6;
        this.smokingDuration = 50;
        this.heatResistance = "Средняя";
    }

}
