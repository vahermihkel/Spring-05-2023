import React, { createContext, useState } from 'react';

// Create the context
export const CartSumContext = createContext();

// Create a provider component
export const CartSumContextProvider = ({ children }) => {
  const [cartSum, setCartSum] = useState(calcSum());

  function calcSum() {
    const cart = JSON.parse(localStorage.getItem("cart")) || [];

    let sum = 0;
    cart.forEach(cartLine => sum = sum + cartLine.product.price * cartLine.quantity);
    return sum.toFixed(2);
  }

  return (
    <CartSumContext.Provider value={{ cartSum, setCartSum }}>
      {children}
    </CartSumContext.Provider>
  );
};