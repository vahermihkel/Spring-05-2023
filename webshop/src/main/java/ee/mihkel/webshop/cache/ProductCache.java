package ee.mihkel.webshop.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ee.mihkel.webshop.entity.Product;
import ee.mihkel.webshop.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component // kõige üldisem
@Log4j2
public class ProductCache {

    @Autowired
    ProductRepository productRepository;

    private LoadingCache<Long, Product> productLoadingCache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build(new CacheLoader<>() {
                @Override
                public Product load(Long aLong) {
                    log.info("Võtsin cache-st andmebaasist");
                    return productRepository.findById(aLong).get();
                }
            });

    public Product getProduct(Long id) throws ExecutionException {
        log.info("võtan toodet");
        return productLoadingCache.get(id);
    }

    public void updateProduct(Long id) {
        productLoadingCache.refresh(id);
    }

    public void emptyCache() {
        productLoadingCache.invalidateAll();
    }
}
