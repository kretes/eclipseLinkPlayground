package nfon.domain;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractDevice extends AbstractIdEntity {

    public String additionalColumn;
}

