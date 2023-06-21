import React, { useContext } from 'react'
import { useState } from 'react'
import { t } from 'i18next';
import { ToastContainer, toast } from 'react-toastify';
import Button from '@mui/material/Button';
import styles from "../../css/Cart.module.css";
import ParcelMachines from '../../components/cart/ParcelMachines';
import Payment from '../../components/cart/Payment';
import { CartSumContext } from '../../store/CartSumContext';

function Cart() {
  const [cart, updateCart] = useState(JSON.parse(localStorage.getItem("cart")) || []);
  const { setCartSum } = useContext(CartSumContext);

  function clearCart() {
    updateCart([]);
    localStorage.setItem("cart", JSON.stringify([]));
    toast.success("Cleared");
    setCartSum("0.00");
  }

  function decreaseQuantity(qnr) {
    cart[qnr].quantity = cart[qnr].quantity - 1;
    if (cart[qnr].quantity <= 0) {
      deleteItem(qnr);
    }
    updateCart(cart.slice());
    localStorage.setItem("cart", JSON.stringify(cart));
    setCartSum(calcSum());
  }

  function increaseQuantity(qnr) {
    cart[qnr].quantity = cart[qnr].quantity + 1;
    updateCart(cart.slice());
    localStorage.setItem("cart", JSON.stringify(cart));
    setCartSum(calcSum());
  }

  function deleteItem(qnr) {
    cart.splice(qnr, 1);
    updateCart(cart.slice());
    localStorage.setItem("cart", JSON.stringify(cart));
    toast.success("Item removed");
    setCartSum(calcSum());
  }

  function calcSum() {
    let sum = 0;
    cart.forEach(cartLine => sum = sum + cartLine.product.price * cartLine.quantity);
    return sum.toFixed(2);
  }

  return (
    <div>
      {cart.length === 0 && <div>Cart is empty!</div>}

      {cart.length > 0 && <Button variant="contained" onClick={clearCart}>{t("clearcart")}</Button>}
      
      {cart.length > 0 && 
        <div className={styles.totalsection}>
          {cart.length > 0 && <div>{t("total")}: {calcSum()} € </div>}
        </div>}

      <br /><br /><br /><br />
      {cart.map((element, qnr) =>
      <div className={styles.product_wrapper}>
        <div className={styles.product} key={element.product.id}>
          <img className={styles.image} src={element.product.image} alt=""></img>
          <div className={styles.name}>{element.product.name}</div>
          <div className={styles.price}>{element.product.price} €</div>
          <div className={styles.quantity}>
            <img className={styles.button} onClick={() => decreaseQuantity(qnr)} src="/minus.png" alt="" />
            <div>{element.quantity}</div>
            <img className={styles.button} onClick={() => increaseQuantity(qnr)} src="/plus.png" alt="" />
          </div>
          <div className={styles.total}>{(element.product.price * element.quantity).toFixed(2)} €</div>
          <img className={styles.button} variant="contained" onClick={() => deleteItem(qnr)} src="/remove.png" alt="" />
        </div>
        <div className={styles.mobile_row}>
          <img className={styles.button} onClick={() => decreaseQuantity(qnr)} src="/minus.png" alt="" />
          <div>{element.quantity}</div>
          <img className={styles.button} onClick={() => increaseQuantity(qnr)} src="/plus.png" alt="" />
        </div>
      </div>
      )}

      {cart.length > 0 && 
      <React.Fragment>
        <ParcelMachines />

        <Payment sum={calcSum()} />

      </React.Fragment>}

      <ToastContainer position='bottom-center'></ToastContainer>
    </div>
  )
}


export default Cart