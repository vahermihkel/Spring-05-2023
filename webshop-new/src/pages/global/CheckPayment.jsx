import React, { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import config from "../../data/config.json";

function CheckPayment() {
  const [searchParams] = useSearchParams();
  const paymentReference = searchParams.get("payment_reference");
  const [message, setMessage] = useState("");

  useEffect(() => {
    fetch(config.backendUrl + "/check-payment/" + paymentReference, {
      headers: {
        "Authorization": "Bearer " + sessionStorage.getItem("token")
      },
    })
      .then(response => response.json())
      .then(data => setMessage(data.paid));
  }, [paymentReference]);

  return (
    <div>
      <div>{message}</div>
    </div>
    );
}

export default CheckPayment;
