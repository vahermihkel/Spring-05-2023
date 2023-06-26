import React, { useEffect, useState } from 'react';
import Carousel from 'react-bootstrap/Carousel';
import config from "../../data/config.json";


function CarouselGallery() {
  const [carouselItems, setCarouselItems] = useState([]);

  useEffect(() => {
    fetch(config.backendUrl + "/carousel")
      .then(res => res.json())
      .then(data => setCarouselItems(data));
  }, []);

  // alguses võib siit panna useEffectiga tööle, aga ta võtab aega
  // pigem võtta useEffectiga HomePagest ja saata läbi sulgude need pildid siia

  return (
    <Carousel>
     {carouselItems.map(carouselItem => (
        <Carousel.Item key={carouselItem.id}>
          <img className='home-product img' src={carouselItem.src} alt={carouselItem.alt} />
          <Carousel.Caption>
            <h3>{carouselItem.header}</h3>
            <p>{carouselItem.description}</p>
          </Carousel.Caption>
        </Carousel.Item> ))}
      {/* <Carousel.Item>
        <img
          src="https://picsum.photos/id/237/500/200"
          alt="First slide"
        />
        <Carousel.Caption>
          <h3>First slide label</h3>
          <p>Nulla vitae elit libero, a pharetra augue mollis interdum.</p>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item>
        <img
          src="https://picsum.photos/id/257/500/200"
          alt="Second slide"
        />
        <Carousel.Caption>
          <h3>Second slide label</h3>
          <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item>
        <img
          src="https://picsum.photos/id/277/500/200"
          alt="Third slide"
        />
        <Carousel.Caption>
          <h3>Third slide label</h3>
          <p>
            Praesent commodo cursus magna, vel scelerisque nisl consectetur.
          </p>
        </Carousel.Caption>
      </Carousel.Item> */}
    </Carousel>
  );
}

export default CarouselGallery