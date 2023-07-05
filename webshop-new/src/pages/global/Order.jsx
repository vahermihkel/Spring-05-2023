import React, { useEffect, useState } from "react";
import config from "../../data/config.json";

function Orders() {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    fetch(config.backendUrl + "/person-order", {
      headers: { Authorization: "Bearer " + sessionStorage.getItem("token") },
    })
      .then((response) => response.json())
      .then((data) => setOrders(data));
  }, []);

  return (
    <div>
      <table>
        <thead>
          <tr style={{ borderBottom: "3px solid black" }}>
            <td>Id</td>
            <td>Sum</td>
          </tr>
        </thead>
        <tbody>
          {orders.map((order, index) => (
            <tr key={index}>
              <td>{order.id}</td>
              <td>{order.totalSum}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Orders;
