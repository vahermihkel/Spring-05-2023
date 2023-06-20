import React, { useEffect, useState } from 'react'

function MaintainProducts() {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/product")
      .then(res => res.json())
      .then(data => setProducts(data));
  }, []);

  return (
    <div>
      {products.map(product =>
        <div key={product.id}>
          <div>{product.id}</div>
          <div>{product.name}</div>
          <div>{product.description}</div>
          <div>{product.price} €</div>
          <img src={product.image} alt="" />
          <div>{product.stock} tk</div>
          <button>Kustuta</button>
          <button>Vähenda kogust</button>
          <button>Suurenda kogust</button>
        </div>)}
    </div>
  )
}

export default MaintainProducts