package nfon.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CONCRETE")
public class Phone extends AbstractPhone {



}
