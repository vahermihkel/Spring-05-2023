package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.entity.Carousel;
import ee.mihkel.webshop.repository.CarouselRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController()
public class CarouselController {
    @Autowired
    CarouselRepository carouselRepository;
    @GetMapping("carousel")
    public List<Carousel> getCarouselPictures(){
        return carouselRepository.findAllByOrderByIdAsc();
    }
    @GetMapping("carousel/{id}")
    public Carousel getCarousel(@PathVariable Long id)  {
        return carouselRepository.findById(id).get();
    }
    @PostMapping("carousel/add")
    public List<Carousel> addCarousel(@RequestBody Carousel carousel){
        carouselRepository.save(carousel);
        return carouselRepository.findAll();
    }
    @PutMapping("carousel/edit")
    public List<Carousel> editCarousel(@RequestBody Carousel carousel){
        if (carouselRepository.existsById(carousel.getId())){
            carouselRepository.save(carousel);
        }
        return carouselRepository.findAll();
    }
    @DeleteMapping("carousel/delete/{id}")
    public List<Carousel> deleteById(@PathVariable Long id){
        carouselRepository.deleteById(id);
        return carouselRepository.findAll();
    }
}
