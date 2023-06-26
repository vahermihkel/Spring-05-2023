import React, { useEffect, useRef } from 'react'
import { json, useNavigate, useParams } from 'react-router-dom';
import config from "../../data/config.json";
import { useState } from 'react';
import { Spinner } from 'react-bootstrap';

function EditProduct() {
  const { id } = useParams();

  const nameRef = useRef();
  const priceRef = useRef();
  const imageRef = useRef();
  const categoryRef = useRef();
  const descriptionRef = useRef();
  const activeRef = useRef();
  const navigate = useNavigate();

  const [isLoading, setLoading] = useState(false);
  const [categories, setCategories] = useState([]);
  const [product, setProduct] = useState();

  useEffect(() => {
    // TODO: BACKENDI PÄRING
    fetch(config.backendUrl + "/categories")
      .then(res => res.json())
      .then(json => {
        console.log(json);
        setCategories(json);
      });
  }, []);

  useEffect(() => {
    // TODO: BACKENDI PÄRING
    fetch(config.backendUrl + "/product/" + id)
      .then(res => res.json())
      .then(json => setProduct(json));
  }, [id]);

  const edit = () => {
    //const selectedCategory = categories.find(category => category.name === categoryRef.current.value);
    //"Kali"
    // {"id": 2, "name": "Kali"}

    const updatedProduct = {
      "id": id,
      "name": nameRef.current.value,
      "description": descriptionRef.current.value,
      "price": Number(priceRef.current.value),
      "image": imageRef.current.value,
      "active": activeRef.current.checked,
      "category": {"id": categoryRef.current.value},
      "stock": product.stock,


    }

    // TODO: BACKENDI PÄRING
    fetch(config.backendUrl + "/product/edit", {
      method: "PUT",
      body: JSON.stringify(updatedProduct),
      headers: {
        "Content-Type": "application/json",},
    })
      .then(res => res.json())
      .then((json) => {
        console.log(json);
        setProduct(json);
        
      })

  }
 
  if (isLoading === true) {
    return <Spinner variant="primary" />
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
            {categories.map(category => <option key={category.name} value={category.id}>{category.name}</option>)} 
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