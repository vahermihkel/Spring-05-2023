import React, { useEffect, useState } from 'react'

function Homepage() {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/public-products")
      .then(res => res.json())
      .then(data => setProducts(data));
  }, []);

  return (
    <div>
      {products.map(product =>
        <div key={product.id}>
          <div>{product.name}</div>
          <div>{product.price} €</div>
          <img src={product.image} alt="" />
          <button>Lisa ostukorvi</button>
        </div>)}
    </div>
  )
}

export default Homepage