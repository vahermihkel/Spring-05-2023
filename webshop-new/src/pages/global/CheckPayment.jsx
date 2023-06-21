import React, { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";

function CheckPayment() {
  const [searchParams] = useSearchParams();
  const paymentReference = searchParams.get("payment_reference");
  const [message, setMessage] = useState("");

  useEffect(() => {
    // TODO: Backendi päring
    fetch()
  }, [paymentReference]);

  return (
    <div>
      <div>{message}</div>
    </div>
    );
}

export default CheckPayment;
