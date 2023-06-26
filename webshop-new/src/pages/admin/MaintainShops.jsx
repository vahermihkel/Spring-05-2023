import React, { useRef } from 'react'
import { useEffect, useState } from 'react';
import config from '../../data/config.json';

function MaintainShops() {

  const nameRef = useRef();
  const latitudeRef = useRef();
  const longitudRef = useRef();
  const openTimeRef = useRef();

  const [shops, setShops] = useState([]);
  useEffect(() => {
		
    fetch(config.backendUrl + "/shop")
		.then(res => res.json())
		.then(data => setShops(data));
	}, []);

  function add() {

    const newShop = {

      "name": nameRef.current.value,
      "latitude": latitudeRef.current.value,
      "longitude": longitudRef.current.value,
      "openTime": openTimeRef.current.value,
      
    }
  fetch(config.backendUrl + "/shop/add", 
  {
    method: "POST",
    body: JSON.stringify(newShop),
    headers: { "Content-Type": "application/json" }
  })
    
}
const deleteShop = (id) => {
  fetch(config.backendUrl + "/shop/delete/" + id, {
    method: "DELETE"

  })
    .then(res => res.json())
    .then(json => setShops(json));
}

  return (
    <div>
      {shops.length === 0 && <div>No shops!</div>}
      <label>Name:</label> <br />
      <input ref={nameRef} type="text" /> <br />
      <label>Latitude:</label><br />
      <input ref={latitudeRef} type="number" /> <br />
      <label> Longitude:</label><br />
      <input ref={longitudRef} type="number" /><br />
      <label>Open times:</label><br/>
      <input ref={openTimeRef} type="text" /><br />
      <button onClick={add}>Add</button>

      {shops.map((element, i) =>
        <div key={i}>
          {element.name}
          <button onClick={() => deleteShop(element.id)}>x</button>
        </div>)}

      </div>
  )
}

export default MaintainShops