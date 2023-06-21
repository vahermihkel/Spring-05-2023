import React, { createContext, useEffect, useState } from 'react';

// Create the context
export const AuthContext = createContext();

// Create a provider component
export const AuthContextProvider = ({ children }) => {
  const [loggedIn, setLoggedIn] = useState(false);
  const [loggedInUser, setLoggedInUser] = useState({});

  // TODO: Backendi päring
  // fetch()

  const emptyUser = () => {
    setLoggedInUser({});
  }

  return (
    <AuthContext.Provider value={{ loggedIn, setLoggedIn, loggedInUser, emptyUser }}>
      {children}
    </AuthContext.Provider>
  );
};