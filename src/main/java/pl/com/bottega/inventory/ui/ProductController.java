package pl.com.bottega.inventory.ui;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.inventory.api.CommandGateway;
import pl.com.bottega.inventory.api.ProductDto;
import pl.com.bottega.inventory.api.ProductFinder;
import pl.com.bottega.inventory.api.PurchaseResultDto;
import pl.com.bottega.inventory.domain.commands.AddProductCommand;
import pl.com.bottega.inventory.domain.commands.PurchaseProductCommand;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class ProductController {

    private CommandGateway gateway;
    private ProductFinder productFinder;

    public ProductController(CommandGateway gateway, ProductFinder productFinder) {
        this.gateway = gateway;
        this.productFinder = productFinder;
    }

    @PostMapping("/inventory")
    public void addProduct(@RequestBody AddProductCommand cmd){
        gateway.execute(cmd);

    }

    @PostMapping("/purchase")
    public PurchaseResultDto purchaseProducts(@RequestBody Map<String, Integer> products){
        PurchaseProductCommand cmd = new PurchaseProductCommand();
        cmd.setProducts(products);
        gateway.execute(cmd);

        return new PurchaseResultDto(true, cmd);
    }

    @GetMapping("/inventory")
    public List<ProductDto> getAll(){
        return productFinder.getAll();
    }
}
