import React, { useEffect, useState } from 'react'
import style from '../Table.module.css';

function Homepage() {

  const [products, setProducts] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/public-products")
      .then(res => res.json())
      .then(data => setProducts(data));
  }, []);

  return (
    <div>

      <table className={style.layout}>
        <thead>
          <tr >
            <th className={style.td}>Nimetus</th>
            <th className={style.td}>Hind</th>
            <th className={style.td}>Kirjeldus</th>
          </tr>
        </thead>
        <tbody className={style.td}>

          {products.map(product =>
            <tr key={product.id}>
              <td className={style.td}>{product.name}</td>
              <td className={style.td}>{product.price}$</td>
              <td className={style.td}>{product.description}</td>

              <td className={style.td}><img src={product.image} alt='' className={style.custom_size} /></td>

              <td className={style.td}><button>Lisa ostukorvi</button></td>
            </tr>)}

        </tbody>

      </table>
    </div>
  )
}

export default Homepage