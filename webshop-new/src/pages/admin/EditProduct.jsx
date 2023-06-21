import React, { useEffect, useRef } from 'react'
import { useNavigate, useParams } from 'react-router-dom';
import config from "../../data/config.json";
import { useState } from 'react';
import { Spinner } from 'react-bootstrap';

function EditProduct() {
  const { id } = useParams(); // admin/edit-product/:id

  const nameRef = useRef();
  const priceRef = useRef();
  const imageRef = useRef();
  const categoryRef = useRef();
  const descriptionRef = useRef();
  const activeRef = useRef();
  const navigate = useNavigate();

  const [isLoading, setLoading] = useState(false);
  const [categories, setCategories] = useState([]);
  const [product, setProduct] = useState({}); 
  
  useEffect(() => {
    fetch(config.backendUrl + "/categories")
      .then(res => res.json())
      .then(json => setCategories(json));
  }, []); 

  useEffect(() => {
    fetch(config.backendUrl + "/product/" + id)
      .then(res => res.json())
      .then(json => setProduct(json));
  }, [id]); 

  const edit = () => {

    const updatedProduct = {
      "id": id,
      "name": nameRef.current.value,
      "price": Number(priceRef.current.value),
      "image": imageRef.current.value,
      "category": categoryRef.current.value,
      "description": descriptionRef.current.value,
      "active": activeRef.current.checked,
    }

    // TODO: BACKENDI PÄRING
    fetch()
  }

  if (isLoading === true) {
    return <Spinner variant="primary"/>
  }

  return (
    <div>
      {product !== undefined && 
        <div>
          <label>Name</label> <br />
          <input ref={nameRef} type="text" defaultValue={product.name} /> <br />
          <label>Price</label> <br />
          <input ref={priceRef} type="number" defaultValue={product.price} /> <br />
          <label>Image</label> <br />
          <input ref={imageRef} type="text" defaultValue={product.image} /> <br />
          <label>Category</label> <br />
          <select ref={categoryRef} defaultValue={product.category}>
            <option value="">Vali kategooria!</option>
            {categories.map(category => <option key={category.name}>{category.name}</option>)}
          </select> <br />
          <label>Description</label> <br />
          <input ref={descriptionRef} type="text" defaultValue={product.description} /> <br />
          <label>Active</label> <br />
          <input ref={activeRef} type="checkbox" defaultChecked={product.active} /> <br />
          <button onClick={edit}>Edit</button>
        </div>}
      {product === undefined && <div>Product not found</div>}
    </div>
  )
}

export default EditProduct