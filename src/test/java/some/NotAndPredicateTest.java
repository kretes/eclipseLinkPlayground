package some;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import some.cascade.Child;
import some.cascade.Root;
import some.domain.Phone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class NotAndPredicateTest {

    private EntityManagerFactory factory;
    private EntityManager em;
    private EntityTransaction tx;

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("eclipseLink");
//        factory = Persistence.createEntityManagerFactory("eclipseLink");
        em = factory.createEntityManager();
        tx = em.getTransaction();
        tx.begin();
    }

    @After
    public void tearDown() {
        tx.rollback();
    }

    @Test
    public void shouldNotReturnAnyResultsWhenEmptyOrIsUsed() {
        persistEntity();

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery query = criteriaBuilder.createQuery(Long.class);
        javax.persistence.criteria.Root root = query.from(Child.class);
        query.where(criteriaBuilder.and(criteriaBuilder.or()));
        query.select(criteriaBuilder.count(root.get("id")));
        Object count = em.createQuery(query).getSingleResult();

        Assertions.assertThat(count).isEqualTo(0L);
    }

    @Test
    public void shouldNotReturnAnyResultsWhenEmptyOrIsUsedTogetherWithOtherPredicates() {
        persistEntity();

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery query = criteriaBuilder.createQuery(Long.class);
        javax.persistence.criteria.Root root = query.from(Child.class);
        query.where(criteriaBuilder.and(
                criteriaBuilder.gt(root.get("id"),0),
                criteriaBuilder.or()
        ));
        query.select(criteriaBuilder.count(root.get("id")));
        Object count = em.createQuery(query).getSingleResult();

        Assertions.assertThat(count).isEqualTo(0L);
    }

    private void persistEntity() {
        Child child = new Child();
        em.persist(child);
        em.flush();
        em.clear();
    }

}
