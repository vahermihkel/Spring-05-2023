import React from 'react'
import { useEffect, useState } from 'react'
import config from '../../data/config.json';
import { Spinner } from 'react-bootstrap';

function ParcelMachines() {
  const [parcelMachines, setParcelMachines] = useState({});
  const [isLoading, setLoading] = useState(true);

  useEffect(() => {
    // PAKIAUTOMAADID BACK-ENDIST
    fetch(config.backendUrl + "/parcel-machines/EE")
      .then(res => res.json())
      .then(json => {
        setParcelMachines(json);
        // console.log(typeof parcelMachines);
        setLoading(false);
      })
  }, []);

  if (isLoading === true) {
    return <Spinner animation="grow" variant="success" />;
  }

  return (
    <div>
        <select style={{ "font-size": "10px" }}>
          <option value="">Omniva pakiautomaat</option>
        {parcelMachines.omnivaPMs.map(parcel => (
          <option key={parcel.NAME}>{parcel.NAME}</option>
        ))}
      </select>
        <select style={{ "font-size": "10px" }}>
        <option value="">Smartpost pakiautomaat</option>
        {parcelMachines.smartpostPMs.map(parcel => (
        <option key={parcel.name}>{parcel.name}</option>
      ))}
      </select>
    </div>
  )
}

export default ParcelMachines