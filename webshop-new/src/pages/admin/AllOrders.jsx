import React, { useEffect, useState } from "react";
import config from "../../data/config.json";

function AllOrders() {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    fetch(config.backendUrl + "/order", {
      headers: { Authorization: "Bearer " + sessionStorage.getItem("token") },
    })
      .then((res) => res.json())
      .then((data) => setOrders(data));
  }, []);

  return (
    <div>
      <table>
        <thead>
          <tr style={{ borderBottom: "3px solid black" }}>
            <td>Order ID</td>
            <td>Customer ID</td>
            <td>Order SUM </td>
            <td>Status</td>
            <td>Date Created</td>
          </tr>
        </thead>
        <tbody>
          {orders.map((order) => (
            <tr key={order.id}>
              <td>{order.id}</td>
              <td>{order.person.email}</td>
              <td>{order.totalSum}</td>
              <td>{order.paid}</td>
              <td>{order.creationDate}</td>
              <td>{order.orderRows.map(row => <span>{row.product.name}:{row.quantity} | </span>)}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default AllOrders;
