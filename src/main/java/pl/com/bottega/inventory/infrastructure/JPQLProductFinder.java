package pl.com.bottega.inventory.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.inventory.api.ProductDto;
import pl.com.bottega.inventory.api.ProductFinder;
import pl.com.bottega.inventory.domain.Product;

import javax.persistence.EntityManager;
import java.util.List;


@Component
public class JPQLProductFinder implements ProductFinder {

    private EntityManager entityManager;

    public JPQLProductFinder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Product getByCode(String skuCode) {
        Product product = (Product) entityManager.createQuery("FROM Product p WHERE p.skuCode = :skuCode").
                setParameter("skuCode", skuCode)
                .getSingleResult();
        return product;
    }

    @Override
    public List<ProductDto> getAll() {
        List<ProductDto> results = entityManager.createQuery("SELECT NEW " +
                "pl.com.bottega.inventory.api.ProductDto(p.id, p.skuCode, p.amount) FROM Product p")
                .getResultList();
        return results;
    }
}
