import React from 'react'
import { useEffect, useState } from 'react'

function ParcelMachines() {
  const [parcelMachines, setParcelMachines] = useState([]);

  useEffect(() => {
    // PAKIAUTOMAADID BACK-ENDIST
  }, []);

  return (
    <select style={{"font-size": "10px"}}>
      <option key={"VÕTI"}>1</option>
    </select>
  )
}

export default ParcelMachines