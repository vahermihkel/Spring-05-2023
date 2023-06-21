import React, { useEffect, useRef, useState } from 'react'
import config from "../../data/config.json";
import { Link } from 'react-router-dom';
import { Spinner } from 'react-bootstrap';

function MaintainProducts() {
  const [products, setProducts] = useState([]); 
  const [dbProducts, setDbProducts] = useState([]); 
  const searchedRef = useRef();
  const [isLoading, setLoading] = useState(true);

  useEffect(() => {
    fetch(config.productsDbUrl)
      .then(res => res.json()) 
      .then(json => {
        setProducts(json || []);
        setDbProducts(json || []);
        setLoading(false);
      }) 
  }, []);

  function deleteProduct(productId) {
    const index = dbProducts.findIndex(element => element.id === productId);
    dbProducts.splice(index, 1);
    setProducts(dbProducts.slice());
    // TODO: Backendi päring
    fetch()  
}

  const searchFromProducts = () => {
    const result = dbProducts.filter(e => 
      e.name.toLowerCase().includes(searchedRef.current.value.toLowerCase()));
    setProducts(result);
  }

  if (isLoading === true) {
    return <Spinner animation="grow" variant="success"/>
  }

  return (
    <div>
      <input onChange={searchFromProducts} ref={searchedRef} type="text" />
      <div>{products.length} tk</div>
      <table>
        <thead>
          <tr>
            <th>Image</th>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Description</th>
            <th>Category</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {products.map((element) => 
              <tr key={element.id} className={element.active === true ? "active-product" : "inactive-product"}>
                <td><img className="image" src={element.image} alt="" /></td>
                <td>{element.id}</td>
                <td>{element.name}</td>
                <td>{element.price}</td>
                <td>{element.description}</td>
                <td>{element.category}</td>
                <td>
                  <Link to={"/admin/edit-product/" + element.id}>
                    <button>Edit</button>
                  </Link>
                  <button onClick={() => deleteProduct(element.id)}>Delete</button>
                </td>
              </tr>
            )}
        </tbody>
      </table>
    </div>
  )
}

export default MaintainProducts