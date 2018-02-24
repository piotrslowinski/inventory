package pl.com.bottega.inventory.api;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.inventory.domain.Product;
import pl.com.bottega.inventory.domain.ProductManager;
import pl.com.bottega.inventory.domain.commands.Command;
import pl.com.bottega.inventory.domain.commands.InvalidCommandException;
import pl.com.bottega.inventory.domain.commands.Validatable;
import pl.com.bottega.inventory.domain.repositories.ProductRepository;
import pl.com.bottega.inventory.domain.commands.PurchaseProductCommand;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        Validatable.ValidationErrors errors = new Validatable.ValidationErrors();
        ProductManager productManager = new ProductManager();
        Map<String, Integer> productMap = cmd.getProducts();
        Set<String> productCodes = new HashSet<>(productMap.keySet());
        Set<Product> productSet = new HashSet<>();
        for (String code : productCodes) {
            Product p = (Product) productRepository.get(code).get();
            productSet.add(p);
            validatePresenceInDb(p);
            validateIfAmountIsEnough(p, cmd.getProducts().get(code));

            p.decreaseCount(cmd.getProducts().get(p.getCode()));
            productRepository.save(p);
        }

        if(errors.isValid())
            return new PurchaseResultDto(true, cmd);
        else
            return new PurchaseNegativeResult(false, cmd);
    }

    private void validateIfAmountIsEnough(Product product, Integer amount){
        if (product.isEnough(amount)) {
            Validatable.ValidationErrors errors = new Validatable.ValidationErrors();

        }
    }

    private void validatePresenceInDb(Product product) {

        if (productRepository.hasProduct(product.getCode())) {
            Validatable.ValidationErrors errors = new Validatable.ValidationErrors();
            errors.add(product.getCode(), "no such sku");
        }
    }


    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return PurchaseProductCommand.class;
    }

}
