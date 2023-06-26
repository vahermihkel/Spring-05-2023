package ee.mihkel.webshop.repository;

import ee.mihkel.webshop.entity.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarouselRepository extends JpaRepository<Carousel, Long> {

    List<Carousel> findAllByOrderByIdAsc();
}
