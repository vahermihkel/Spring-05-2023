import React from 'react'
import config from "../../data/config.json";

              //({ sum })
function Payment(props) {

  // const {sum, products} = props;
  
  function pay() {

    console.log(props.products);

    fetch(config.backendUrl + "/payment/1", {
      method: "POST",
      body: JSON.stringify(props.products),
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then(response => response.json())
      .then(data => window.location.href = data.link);

    // fetch("MAKSMA")
    //   .then(res => res.json())
    //   .then(json => window.location.href = "UUS_URL");
  }

  return (
    <button onClick={pay}>MAKSMA</button>
  )
}

export default Payment