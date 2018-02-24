package pl.com.bottega.inventory.domain.commands;

import pl.com.bottega.inventory.api.CommandGateway;
import pl.com.bottega.inventory.domain.Product;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PurchaseProductCommand implements Command {

    Map<String, Integer> products;

    public Map<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Integer> products) {
        this.products = products;
    }


    public void validate(Validatable.ValidationErrors errors) {

        validateMinimumFields(products, errors);
        validateAmountValue(products, errors);

    }

    private void validateMinimumFields(Map<String, Integer> products, Validatable.ValidationErrors errors) {
            if (products.keySet().size() == 0)
                errors.add("skus", "are required");

    }

    private void validateAmountValue(Map<String, Integer> products, Validatable.ValidationErrors errors){
        for(Map.Entry<String, Integer> item: products.entrySet()){
            if(item.getValue() < 0 || item.getValue() > 999)
                errors.add(item.getKey(),"must be between 1 and 999");
        }
    }



}
