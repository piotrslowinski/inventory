package pl.com.bottega.inventory.domain.repositories;

import pl.com.bottega.inventory.domain.Product;

import java.time.LocalDate;
import java.util.Optional;

public interface ProductRepository {

    Product get(Integer id);

    Optional<Object> get(String code);

    void save(Product product);

    boolean hasProduct(String code);
}
