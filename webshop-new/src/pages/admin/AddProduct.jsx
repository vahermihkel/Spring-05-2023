import React, { useEffect, useState } from 'react'
import config from "../../data/config.json";
import { t } from 'i18next';
import { ToastContainer, toast } from 'react-toastify';
import { useRef } from 'react';

function AddProduct() {

  const idRef = useRef(); // kustutada
  const nameRef = useRef();
  const priceRef = useRef();
  const imageRef = useRef();
  const categoryRef = useRef();
  const descriptionRef = useRef();
  const activeRef = useRef();
  const [idUnique, setIdUnique] = useState(true); // kustutada
  const [dbProducts, setDbProducts] = useState([]);  // kustutada
  const [categories, setCategories] = useState([]);
  
  useEffect(() => {
    fetch("VÕTAME KATEGOORIAD")
  }, []); 

  useEffect(() => {
    fetch("VÕTAME TOOTED")
  }, []);

  function add() {
    if (idRef.current.value === "") {
      toast("Id not filled");
      return;
    }
    if (/^[0-9]+$/.test(idRef.current.value) === false) {
      toast("Id must contain only numbers");
      return;
    }
    if (nameRef.current.value === "") {
      toast("Name not filled");
      return;
    }
    if (priceRef.current.value === "") {
      toast("Price not filled");
      return;
    }
    if (categoryRef.current.value === "") {
      toast("Category not selected");
      return;
    }
    if (/^[0-9.]+$/.test(priceRef.current.value) === false) {
      toast("Price must contain only numbers");
      return;
    }
    if (descriptionRef.current.value === "") {
      toast("Description not filled");
      return;
    }
    const addProduct = {
      "id": Number(idRef.current.value),
      "name": nameRef.current.value,
      "price": Number(priceRef.current.value),
      "image": imageRef.current.value,
      "category": categoryRef.current.value,
      "description": descriptionRef.current.value,
      "active": activeRef.current.value.checked,
    }
    dbProducts.push(addProduct); // kustutada
    toast(t("product_added"));
    // TODO: BACKENDI PÄRING
    fetch()
    idRef.current.value = "";
    nameRef.current.value = "";
    priceRef.current.value = "";
    imageRef.current.value = "";
    categoryRef.current.value = "";
    descriptionRef.current.value = "";
    activeRef.current.checked = false;
  }

  const checkIdUniqueness = () => { // kustutada
    const index = dbProducts.findIndex(e => e.id === Number(idRef.current.value));
    if (index === -1) {
      setIdUnique(true)
    } else {
      setIdUnique(false)
      toast("Toote ID pole unikaalne");
    }
  }

  return (
    <div>
      <label> ID:</label><br />
      <input ref={idRef} onChange={checkIdUniqueness} type="number" /> <br />
      <label> {t("name")}:</label> <br />
      <input ref={nameRef} type="text" /> <br />
      <label> {t("price")}:</label><br />
      <input ref={priceRef} type="number" /> <br />
      <label> {t("image")}:</label><br />
      <input ref={imageRef} type="text"/><br />
      <label> {t("category")}:</label>
      <select ref={categoryRef}>
        <option value="">Vali kategooria!</option>
        {categories.map(category => <option key={category.name}>{category.name}</option>)}
      </select> <br />
      <label> {t("description")}:</label>
      <input ref={descriptionRef} type="text"/> <br />
      <label> {t("active")}:</label>
      <input ref={activeRef} type="checkbox"/> <br />
      <button disabled={idUnique === false}  onClick={add}>{t("add_product")}</button>
      {idUnique === false && <div>{t("id_error")}</div>}
      <ToastContainer position='top-center'></ToastContainer>
    </div>
  )
}
export default AddProduct