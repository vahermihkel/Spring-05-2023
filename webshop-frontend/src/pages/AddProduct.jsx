import React, { useRef } from "react";
import { ToastContainer, toast } from 'react-toastify';
import styles from '../Table.module.css';

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
       <table className={styles.layout}>
         <tbody>
            <tr className={styles.spaces}>
                <td>
                    <label>Nimi</label><br />
                    <input ref={nameRef} type='text' /><br />
                </td>
            </tr >
            <tr className={styles.spaces}>
                <td >
                    <label>Hind</label><br />
                    <input ref={priceRef} type='number' /><br />
                </td>
            </tr >
            <tr className={styles.spaces}>
                <td>
                    <label>Kirjeldus</label><br />
                    <input ref={descriptionRef} type='text' /><br />
                </td>
            </tr>
            <tr className={styles.spaces}>
                <td>
                    <label>Pilt</label><br />
                    <input ref={imageRef} type='text' /><br />
                </td>
            </tr>
            <tr className={styles.spaces}>
                <td>
                    <label>Aktiivne</label><br />
                    <input ref={activeRef} type='checkbox' /><br />
                </td>
            </tr>
            <tr className={styles.spaces}> 
                <td>
                    <label>Kogus</label><br />
                    <input ref={stockRef} type='number' /><br />
                </td>
            </tr>
            <tr className={styles.spaces}>
                <td>
                    <button onClick={add}>Lisa uus toode</button>
                </td>
            </tr>
         </tbody>
        </table>
        <ToastContainer position="bottom-left" />
    </div>
  );
}

export default AddProduct;
