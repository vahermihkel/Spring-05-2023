import React, { useContext, useRef, useState } from 'react';
import { AuthContext } from '../../store/AuthContext';
import { useNavigate } from 'react-router-dom';
import config from "../../data/config.json";

function Login() {
  const { setLoggedIn } = useContext(AuthContext);
  const navigate = useNavigate();
  const emailRef = useRef();
  const passwordRef = useRef();
  const [message, setMessage] = useState();

  const login = () => {
    const payLoad = {
      "email": emailRef.current.value,
      "password": passwordRef.current.value
    }

    fetch(config.backendUrl + "/login", {
      method: "POST",
      body: JSON.stringify(payLoad),
      headers: {
        "Content-Type": "application/json"
      },
    })
      .then(response => response.json())
      .then(data => {
      // õige kujuga, aga tühi     ei tea mis kuju peaks olema, viskas errori
      // vale parool               vale email
        if (data.token !== null && data.token !== undefined) {
          setLoggedIn(true);
          navigate('/admin');
          sessionStorage.setItem("token", data.token); // SALVESTA ÄRA SESSIONSTORAGESSE
        } else {
          setMessage(data.message);
        }
      });


  }

  return (
    <div>
      <div>{message}</div>
      <label>E-mail</label> <br />
      <input ref={emailRef} type="text" /> <br />
      <label>Parool</label> <br />
      <input ref={passwordRef} type="text" /> <br />
      <button onClick={login}>Logi sisse</button>
    </div>
  )
}

export default Login