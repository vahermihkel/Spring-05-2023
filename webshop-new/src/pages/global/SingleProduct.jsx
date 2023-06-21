import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import config from "../../data/config.json";
import { Spinner } from 'react-bootstrap';

function SingleProduct() {
  const { id } = useParams();
  const [dbProducts, setDbProducts] = useState([]); // 240tk
  const found = dbProducts.find(e => e.id === Number(id));
  const [isLoading, setLoading] = useState(true);
  
  useEffect(() => {
    // TODO: Backendi päring
    fetch()
  }, []);

  if (isLoading === true) {
    return <Spinner />
  }

  return (
    <div>
      {found !== undefined && 
        <div>
          <img  src={found.image} alt="" />
          <div>ID: {id}</div>
          <div> Name: {found.name} </div>
          <div> Price: {found.price} </div>
          <div> Description: {found.description} </div>
          <div> Category: {found.category} </div>
        </div>}
      {found === undefined && <div>Not found</div>}
    </div>
  )
}

export default SingleProduct