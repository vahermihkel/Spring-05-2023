import React, { useContext, useRef } from 'react'
import { AuthContext } from '../../store/AuthContext';

function Profile() {
  const nameRef = useRef();
  const emailRef = useRef();
  const url = "";
  const { loggedInUser } = useContext(AuthContext);

  const changeProfile = () => {
    const payLoad = {
      "????": true
    }

    // TODO: Backendi päring
    fetch()
  }

  return (
    <div>
      {loggedInUser.users !== undefined &&
        <div>
          <label>Display Name</label> <br />
          <input ref={nameRef} type="text" defaultValue={"VANA NIMI"} /> <br />
          <label>Photo URL</label> <br />
          <input ref={emailRef} type="text" defaultValue={"VANA EMAIL"} /> <br />
          <button onClick={changeProfile}>Change</button>
        </div>}
    </div>
  )
}

export default Profile