import Carousel from 'react-bootstrap/Carousel';

function CarouselGallery() {

  // alguses võib siit panna useEffectiga tööle, aga ta võtab aega
  // pigem võtta useEffectiga HomePagest ja saata läbi sulgude need pildid siia

  return (
    <Carousel>
      <Carousel.Item>
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
      </Carousel.Item>
    </Carousel>
  );
}

export default CarouselGallery