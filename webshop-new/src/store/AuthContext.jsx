import React, { createContext, useEffect, useState } from 'react';
import config from "../data/config.json";

// Create the context
export const AuthContext = createContext();

// Create a provider component
export const AuthContextProvider = ({ children }) => {
  const [loggedIn, setLoggedIn] = useState(false); // pärast refreshi ka jätaks õiged nupud
  const [loggedInUser, setLoggedInUser] = useState({});

  useEffect(() => {
    fetch(config.backendUrl + "/person-account", {
      headers: { Authorization: "Bearer " + sessionStorage.getItem("token") },
    })
      .then((res) => res.json())
      .then((data) => {
        console.log(data);
        setLoggedInUser(data);
        setLoggedIn(true);
      });
  }, []);

  const emptyUser = () => {
    setLoggedInUser({});
  }

  return (
    <AuthContext.Provider value={{ loggedIn, setLoggedIn, loggedInUser, setLoggedInUser, emptyUser }}>
      {children}
    </AuthContext.Provider>
  );
};