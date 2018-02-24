package pl.com.bottega.inventory.api;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.inventory.domain.Product;
import pl.com.bottega.inventory.domain.ProductManager;
import pl.com.bottega.inventory.domain.commands.AddProductCommand;
import pl.com.bottega.inventory.domain.commands.Command;
import pl.com.bottega.inventory.domain.repositories.ProductRepository;

@Component
public class AddProductHandler implements Handler<AddProductCommand, Void> {

    private ProductRepository productRepository;

    private ProductFinder productFinder;

    public AddProductHandler(ProductRepository productRepository, ProductFinder productFinder) {
        this.productRepository = productRepository;
        this.productFinder = productFinder;
    }

    @Override
    @Transactional
    public Void handle(AddProductCommand cmd) {
        ProductManager productManager = new ProductManager();
        if(!productRepository.hasProduct(cmd.getSkuCode())){
            Product product = new Product(cmd.getSkuCode(), cmd.getAmount());
            productManager.addNewProduct(cmd.getSkuCode(), cmd.getAmount());
            productRepository.save(product);
        } else{
            Product productFromDatabase = productFinder.getByCode(cmd.getSkuCode());
            productFromDatabase.increaseCount(cmd.getAmount());
            productRepository.save(productFromDatabase);
        }

        return null;
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return AddProductCommand.class;
    }
}
