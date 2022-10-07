package hookah_sql.tabacco;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tangiers")
@Component("Tangiers")
@Scope("prototype")
public class Tangiers extends Tabacco {
    public Tangiers() {
    }
}
