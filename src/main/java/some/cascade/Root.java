package some.cascade;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Root {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    public Child child;


}
