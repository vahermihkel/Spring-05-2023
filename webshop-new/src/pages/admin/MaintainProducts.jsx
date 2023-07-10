import React, { useEffect, useRef, useState } from "react";
import config from "../../data/config.json";
import { Link } from "react-router-dom";
import { Spinner } from "react-bootstrap";
import { validateHttpResponses } from "../../util/HttpResponses";

function MaintainProducts() {
  const [products, setProducts] = useState([]);
  const [dbProducts, setDbProducts] = useState([]);
  const searchedRef = useRef();
  const [isLoading, setLoading] = useState(true);
  const [message, setMessage] = useState("");

  useEffect(() => {
    fetch(config.backendUrl + "/product", {headers: {"Authorization": "Bearer " + sessionStorage.getItem("token")}})
      .then((res) => {
        if (res.ok === true) {
          return res.json();
        } else {
          setMessage("NO_ADMIN");
        }
      })
      .then((json) => {
        setProducts(json || []);
        setDbProducts(json || []);
        setLoading(false);
      });
  }, []);

  function deleteProduct(id) {
    fetch(`http://localhost:8080/product/${id}`, { method: "DELETE", headers: {"Authorization": "Bearer " + sessionStorage.getItem("token")} })
    .then((res) => {
      if (res.status === 200) {
        return res.json();
      } 

      setMessage(validateHttpResponses(res, "Toode"));
    })
    .then(data => {
      if (data) {
        setProducts(data);
        // const index = products.findIndex(p => p.id === id);
        // products.splice(index, 1);
        // setProducts(products.slice());
        setMessage("Toode kustutatud");
      }
    })
  }

  const searchFromProducts = () => {
    const result = dbProducts.filter((e) =>
      e.name?.toLowerCase().includes(searchedRef.current.value.toLowerCase())
    );
    setProducts(result);
  };

  const increaseStock = (id) => {
    fetch(`http://localhost:8080/increase-stock/${id}`, { method: "PATCH", headers: {"Authorization": "Bearer " + sessionStorage.getItem("token")} })
      .then(res => res.json())
      .then(data => setProducts(data))
  };

  const decreaseStock = (id) => {  
    fetch(`http://localhost:8080/decrease-stock/${id}`, { method: "PATCH", headers: {"Authorization": "Bearer " + sessionStorage.getItem("token")} })
      .then(res => res.json())
      .then(data => setProducts(data));
  }

  if (isLoading === true) {
    return <Spinner animation="grow" variant="success" />;
  }

  if (message === "NO_ADMIN") {
    return  <div>Sul pole piisavalt õigusi, et seda vaadet vaadata!</div>;
  }

  return (
    <div>
      <div>{message}</div>
      <input onChange={searchFromProducts} ref={searchedRef} type="text" />
      <div>{products.length} tk</div>
      <table>
        <thead>
          <tr>
            <th>Image</th>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Description</th>
            <th>Stock</th>
            <th>Category</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {products.map((element) => (
            <tr
              key={element.id}
              className={
                element.active === true ? "active-product" : "inactive-product"
              }
            >
              <td>
                <img className="image" src={element.image} alt="" />
              </td>
              <td>{element.id}</td>
              <td>{element.name}</td>
              <td>{element.price}</td>
              <td>{element.description}</td>
              <td>{element.stock}</td>
              <td>{element.category?.name}</td>
              <td>
                <Link to={"/admin/edit-product/" + element.id}>
                  <button>Edit</button>
                </Link>
                <button onClick={() => deleteProduct(element.id)}>
                  Delete
                </button>
                <button onClick={() => decreaseStock(element.id)}>Vähenda</button>
                <button onClick={() => increaseStock(element.id)}>Suurenda</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default MaintainProducts;
