import React, { useContext, useEffect, useRef, useState } from "react";
// import { AuthContext } from '../../store/AuthContext';
import config from "../../data/config.json";

function Profile() {
  const nameRef = useRef();
  const emailRef = useRef();
  const [credentsials, setCredentsials] = useState([]);

  // const { loggedInUser } = useContext(AuthContext);

  useEffect(() => {
    fetch(config.backendUrl + "/person-account", {
      headers: { Authorization: "Bearer " + sessionStorage.getItem("token") },
    })
      .then((res) => res.json())
      .then((data) => {
        console.log(data);
        setCredentsials(data);
      });
  }, []);

  const changeProfile = () => {
    const payLoad = {
      id: credentsials.id, //?????
      firstName: nameRef.current.value,
      lastName: credentsials.lastName,
      phone: credentsials.phone,
      email: emailRef.current.value,
      personalCode: credentsials.personalCode,
      creationDate: credentsials.creationDate,
      password: credentsials.password,
      admin: credentsials.admin,
    };

    fetch(config.backendUrl + "/person", {
      method: "PUT",
      body: JSON.stringify(payLoad),
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + sessionStorage.getItem("token"),
      },
    })
      .then((res) => res.json())
      .then((data) => setMessage("Uuendatud!"));
  };

  const [message, setMessage] = useState("");

  return (
    <div>
      {/* {loggedInUser.users !== undefined && */}
      <div>{message}</div>
      <div>
        <label>Name</label> <br />
        <input
          ref={nameRef}
          type="text"
          defaultValue={credentsials.firstName}
        />{" "}
        <br />
        <label>E-Mail</label> <br />
        <input
          ref={emailRef}
          type="text"
          defaultValue={credentsials.email}
        />{" "}
        <br />
        <button onClick={changeProfile}>Change</button>
      </div>
      {/* } */}
    </div>
  );
}

export default Profile;
