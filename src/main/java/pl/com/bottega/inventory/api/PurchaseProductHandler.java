package pl.com.bottega.inventory.api;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.inventory.domain.Product;
import pl.com.bottega.inventory.domain.commands.Command;
import pl.com.bottega.inventory.domain.commands.InvalidCommandException;
import pl.com.bottega.inventory.domain.commands.Validatable;
import pl.com.bottega.inventory.domain.repositories.ProductRepository;
import pl.com.bottega.inventory.domain.commands.PurchaseProductCommand;

import java.util.*;

@Component
public class PurchaseProductHandler implements Handler<PurchaseProductCommand, PurchaseResult> {

    private ProductRepository productRepository;
    private CommandGateway gateway;

    public PurchaseProductHandler(ProductRepository productRepository, CommandGateway gateway) {
        this.productRepository = productRepository;
        this.gateway = gateway;
    }

    @Override
    @Transactional
    public PurchaseResult handle(PurchaseProductCommand cmd) {

        validateSkuCodesAvailability(cmd.getProducts());
        Map<String, Integer> productMap = cmd.getProducts();
        Map<String, Integer> rejectedProducts = getProductsToReject(productMap);

        if (!(rejectedProducts.size() == 0)){
            return new PurchaseNegativeResult(false,rejectedProducts);
        }
        else
            purchaseProducts(productMap);
        return new PurchaseSuccessfulResult(true, productMap);

    }

    private void purchaseProducts(Map<String, Integer> productMap) {
        Set<String> productCodes = new HashSet<>(productMap.keySet());
        Set<Product> productSet = new HashSet<>();
        for (String code : productCodes) {
            Product p = productRepository.get(code).get();
            productSet.add(p);
            p.decreaseCount(productMap.get(p.getCode()));
            productRepository.save(p);
        }
    }

    private Map<String, Integer> getProductsToReject(Map<String, Integer> productMap) {
        Map<String, Integer> rejectedProducts = new HashMap<>();
        for (Map.Entry<String, Integer> item : productMap.entrySet()) {
            Optional<Product> product = productRepository.getProductList(item.getKey()).stream().findFirst();

            if (item.getValue() > product.get().getCount())
                rejectedProducts.put(item.getKey(), item.getValue());

        }
        return rejectedProducts;
    }


    private void validateSkuCodesAvailability(Map<String, Integer> products){
        Validatable.ValidationErrors errors = new Validatable.ValidationErrors();
        for (Map.Entry<String, Integer> product : products.entrySet()) {
            if (!(isPresentInDb(product.getKey()))) {
                errors.add(product.getKey(), "no such sku");
            }
        }
        if (!errors.isValid())
            throw new InvalidCommandException(errors);
    }

    private boolean isPresentInDb(String code) {
        return productRepository.hasProduct(code);

    }


    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return PurchaseProductCommand.class;
    }

}
