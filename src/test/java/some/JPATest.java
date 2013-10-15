package some;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import junit.framework.Assert;
import some.domain.Extension;
import some.domain.Phone;
import org.junit.Before;
import org.junit.Test;

public class JPATest {

    private EntityManagerFactory factory;
    private EntityManager em;

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("persistenceUnit");
        em = factory.createEntityManager();
    }

    @Test
    public void shouldWork() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            //composite pu
            Extension extension = new Extension();
            em.persist(extension);
            Phone phone = new Phone();
            em.persist(phone);
            extension.abstractEntities.add(phone);
            phone.extension = extension;

            em.merge(extension);
            em.flush();
            em.clear();

            Extension refetched = em.find(Extension.class, extension.getId());
            em.remove(refetched);
            em.flush();

            Assert.assertNotNull(em.find(Phone.class, phone.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
    }
}
