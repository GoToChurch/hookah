package hookah_sql.tabacco;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "element")
@Component("Element")
@Scope("prototype")
public class Element extends Tabacco {
    public Element() {
        this.smokingDuration = 45;
        this.heatResistance = "Средняя";
    }

}
