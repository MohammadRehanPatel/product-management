package telusko.productspringweb;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    ProductService service ;

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return service.getAllProducts();
    }

    @GetMapping("/product/{name}")
    public Product getProduct(@PathVariable String name){
        return service.getProduct(name);
    }

    @PostMapping("/product")
    public void addProduct(@RequestBody Product p){
        service.addProduct(p);
    }

    @GetMapping("/product/outOfWarranty")
    public List<Product> outOfWarranty(){
        return service.getOutOfWarranty();
    }

    @GetMapping("/product/search/{text}")
    public List<Product> searchProduct(@PathVariable String text){
        return service.searchProduct(text);
    }

    @DeleteMapping("/product/{id}")
    public  void deleteProduct(@PathVariable int id){
        service.deleteProduct(id);
    }

    @GetMapping("/product/samePlace/{place}")
    public List<Product> productOnSamePlace(@PathVariable String place){
        return service.getProductsOnSamePlace(place);
    }

    @PutMapping("/product/{id}")
    public Product updateProduct(@RequestBody Product product,@PathVariable int id) throws ChangeSetPersister.NotFoundException {
        return service.updateProduct(product,id);
    }
}
