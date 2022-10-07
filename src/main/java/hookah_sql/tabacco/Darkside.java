package hookah_sql.tabacco;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "darkside")
@Component("Darkside")
@Scope("prototype")
public class Darkside extends Tabacco {
    public Darkside() {

    }

}
