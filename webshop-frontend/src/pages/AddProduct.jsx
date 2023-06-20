import React, { useRef } from "react";
import { ToastContainer, toast } from 'react-toastify';

function AddProduct() {
  const nameRef = useRef();
  const priceRef = useRef();
  const descriptionRef = useRef();
  const imageRef = useRef();
  const activeRef = useRef();
  const stockRef = useRef();

  const add = () => {
    const newProduct = {
      name: nameRef.current.value,
      price: priceRef.current.value,
      description: descriptionRef.current.value,
      image: imageRef.current.value,
      active: activeRef.current.checked,
      stock: stockRef.current.value,
    };
    fetch("http://localhost:8080/product", {
      method: "POST",
      body: JSON.stringify(newProduct),
      headers: {"Content-Type": "application/json"},
    });
    toast.success("Wow so easy!");
  };

  return (
    <div>
      <label>Nimi</label> <br />
      <input ref={nameRef} type="text" /> <br />
      <label>Hind</label> <br />
      <input ref={priceRef} type="number" /> <br />
      <label>Kirjeldus</label> <br />
      <input ref={descriptionRef} type="text" /> <br />
      <label>Pilt</label> <br />
      <input ref={imageRef} type="text" /> <br />
      <label>Aktiivne</label> <br />
      <input ref={activeRef} type="checkbox" /> <br />
      <label>Kogus</label> <br />
      <input ref={stockRef} type="number" /> <br />
      <button onClick={add}>Lisa uus toode</button>
      <ToastContainer position="bottom-right" />
    </div>
  );
}

export default AddProduct;
