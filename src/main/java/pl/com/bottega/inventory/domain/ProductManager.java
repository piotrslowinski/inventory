package pl.com.bottega.inventory.domain;

import pl.com.bottega.inventory.domain.commands.PurchaseProductCommand;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ProductManager {

    private Set<Product> products = new HashSet<>();


    public void addProductAmount(Product product){
        for(Product item: products){
            if (item.getCode().equals(product.getCode()))
                    item.increaseCount(product.getCount());
        }
    }

    public void addNewProduct(String code, Integer amount) {
        products.add(new Product(code, amount));
    }

    private void sellProduct(String code, Integer amount){
        for (Product item: products){
            if (item.getCode().equals(code))
                item.decreaseCount(amount);
        }
    }

    public void calculateProducts(Set<Product> products) {
        for (Product product: products){
            product.increaseCount(product.getCount());
        }

    }
}
