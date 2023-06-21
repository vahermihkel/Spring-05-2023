import React, { useContext } from 'react'
import { Link } from 'react-router-dom'
import styles from "../../css/HomePage.module.css";  
import { Button } from '@mui/material';
import { useTranslation } from 'react-i18next';
import { toast } from 'react-toastify';
import { CartSumContext } from '../../store/CartSumContext';

function Product({element}) {
  const { t } = useTranslation(); 
  const { setCartSum } = useContext(CartSumContext);

  function addToCart(clickedProduct) {
    const cartLS = JSON.parse(localStorage.getItem("cart")) || [];
    const index = cartLS.findIndex(e => e.product.id === clickedProduct.id);
    if (index >= 0) { // index !== -1
      // KUI ON JUBA OLEMAS, suurendan kogust (quantity <-- ise välja mõeldud)
      cartLS[index].quantity = cartLS[index].quantity + 1;
      // uuendaKogus(kogus + 1);
    } else {
      // KUI EI OLE OLEMAS, pushin kogusega 1 (selle pärast, et kogus peab olema igaühel ja 
        // kui teda varasemalt polnud, on kogus 1)
      cartLS.push({"product": clickedProduct, "quantity": 1});
    }
    
    let sum = 0;
    cartLS.forEach(cartLine => sum = sum + cartLine.product.price * cartLine.quantity);
    
    setCartSum(sum.toFixed(2));
    localStorage.setItem("cart", JSON.stringify(cartLS));
    // localStorage.setItem("language", "ee"); // i18n
    // localStorage.setItem("keel", "est");
    // localStorage.setItem("theme", "dark-mode");
    // localStorage.setItem("telefon", "51232131");
    // localStorage.setItem("aadress", "Tammsaare 111");
    // localStorage.setItem("email", "adsd@dasda.ee");

    // updateCart(cartFromFile.slice());
    toast(clickedProduct.name + " lisatud")
  }

  return (
    <div className={styles["home-product"]}>
      <Link to={"/product/" + element.id}>
        <img src={element.image} alt="" />
        <div>{element.name}</div>
        <div>{element.price}</div>
      </Link>
      <Button variant="contained" onClick={() => addToCart(element)}>
        {t("add_to_cart")}
      </Button>
    </div>
  )
}

export default Product