package pl.com.bottega.inventory.api;

import pl.com.bottega.inventory.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductFinder {

    Product getByCode(String code);

    List<ProductDto> getAll();
}
