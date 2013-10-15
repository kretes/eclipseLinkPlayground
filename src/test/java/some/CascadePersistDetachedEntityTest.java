package some;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import some.cascade.Child;
import some.cascade.Root;
import some.domain.Extension;

public class CascadePersistDetachedEntityTest {

    private EntityManagerFactory factory;
    private EntityManager em;

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("persistenceUnit");
        em = factory.createEntityManager();
    }

    //this shows that when you have a cascade persist to an entity that is detached - EM will try to re-persist it, which is strange
    @Test
    public void shouldNotCascadePersistToPersistedEntity() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            //composite pu
            Child child = new Child();
            child.name = "A";
            em.persist(child);
            em.flush();
            em.clear();

            Root root = new Root();
            root.child=child;
            em.persist(root);
            em.flush();
            em.clear();

            Assertions.assertThat(em.createQuery("select c from Child c").getResultList()).hasSize(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
    }
}
