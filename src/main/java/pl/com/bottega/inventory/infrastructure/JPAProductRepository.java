package pl.com.bottega.inventory.infrastructure;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.inventory.domain.Product;
import pl.com.bottega.inventory.domain.repositories.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.Optional;

@Component
public class JPAProductRepository implements ProductRepository {

    private EntityManager entityManager;

    public JPAProductRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Product get(Integer id) {
        Product product = entityManager.find(Product.class, id);
        return product;
    }

    @Override
    public void save(Product product) {
        entityManager.persist(product);
    }

    @Override
    public boolean hasProduct(String code) {
        return get(code).isPresent();
    }

    @Transactional
    public Optional<Object> get(String skuCode) {
        try {
            Product product = (Product) entityManager.createQuery("FROM Product p WHERE p.skuCode = :skuCode").
                    setParameter("skuCode", skuCode)
                    .getSingleResult();
            return Optional.of(product);
        } catch (NoResultException ex){
            return Optional.empty();
        }
    }
}
