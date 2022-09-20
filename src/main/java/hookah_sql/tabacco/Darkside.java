package hookah_sql.tabacco;

import hookah_sql.mix.Mix;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "darkside")
@Component("Darkside")
@Scope("prototype")
public class Darkside extends Tabacco {
    public Darkside() {

    }

}
