package telusko.productspringweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductService {

    @Autowired
    ProductDB db;


    public List<Product> getAllProducts() {

        return db.findAll();
    }

    public Product getProduct(String name ) {
        return db.findAllByName(name);
    }

    public void addProduct(Product p) {
        db.save(p);
    }

    public Product getProductById(int id){
        return db.getById(id);
    }

        public List<Product> getOutOfWarranty(){
        List<Product> wp= new ArrayList<>();
            List<Product> allProducts = getAllProducts();
            LocalDate date = LocalDate.now();
            var currentYear = date.getYear();

        List<Product> outOfWarranty = allProducts.stream().filter(
                p -> p.getWarranty() < currentYear).map(p -> p

        ).collect(Collectors.toList());

        return outOfWarranty;
    }

    public List<Product> searchProduct(String text){
    String str = text.toLowerCase();
        List<Product> allProducts = getAllProducts();
    List<Product> products;
        products = allProducts.stream().filter(p -> p.getName().toLowerCase().contains(str) || p.getPlace().toLowerCase().contains(str) || p.getType().toLowerCase().contains(str)).collect(Collectors.toList());
        return products;
    }

    public void deleteProduct(int id){
        db.deleteById(id);
    }

        public List<Product> getProductsOnSamePlace(String place){
        List<Product> sp= new ArrayList<>();
            List<Product> allProducts = getAllProducts();
        String placeLowerCase =place.toLowerCase();
        for (Product p:allProducts){
            if( p.getPlace().toLowerCase().equals(placeLowerCase)){

                sp.add(p);
            }

        }
        return sp;
    }

    public Product updateProduct(Product product,int id) throws ChangeSetPersister.NotFoundException {

        Optional<Product> theProduct = Optional.ofNullable(this.getProductById(id));

        if(theProduct.isPresent()){
        Product updateProduct =theProduct.get();

        updateProduct.setName(product.getName()==null?updateProduct.getName():product.getName());
        updateProduct.setType(product.getType()==null? updateProduct.getType(): product.getType());
        updateProduct.setPlace(product.getPlace()==null? updateProduct.getPlace() : product.getPlace());
        updateProduct.setWarranty(product.getWarranty()==0? updateProduct.getWarranty() : product.getWarranty());
        return db.save(updateProduct);
        }else {
            throw new ChangeSetPersister.NotFoundException();
        }

    }
}
