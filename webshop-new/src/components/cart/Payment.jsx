import React from 'react'
              //({ sum })
function Payment(props) {
  
  function pay() {

    fetch("MAKSMA")
      .then(res => res.json())
      .then(json => window.location.href = "UUS_URL");
  }

  return (
    <button onClick={pay}>MAKSMA</button>
  )
}

export default Payment