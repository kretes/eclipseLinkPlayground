package nfon.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Referencing")
public class Extension extends AbstractIdEntity {

	private String name;

    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "ENTITY_ID")
    public List<AbstractPhone> abstractEntities = new ArrayList<AbstractPhone>();

	public Extension() {
		super();
	}
	public Extension(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
