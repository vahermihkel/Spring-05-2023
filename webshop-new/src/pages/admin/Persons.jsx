import React, { useEffect, useState } from "react";
import config from "../../data/config.json";
// import { use } from "i18next";

function Persons() {
  const [persons, setPerson] = useState([]);

  const deletePerson = (id) => {
    fetch(config.backendUrl + "/person/" + id, {
      method: "DELETE",
      headers: { Authorization: "Bearer " + sessionStorage.getItem("token") },
    })
      .then((res) => res.json())
      .then((json) => setPerson(json));
  };

  useEffect(() => {
    fetch(config.backendUrl + "/person", {
      headers: { Authorization: "Bearer " + sessionStorage.getItem("token") },
    })
      .then((res) => res.json())
      .then((json) => setPerson(json));
  }, []);
  return (
    <div>
      {/* {persons.map((person) => <div key={person.id}>{person.id}</div>)} */}
      <table>
        <thead>
          <tr>
            <th>Id</th>
            <th>First name</th>
            <th>Last name</th>
            <th>Phone</th>
            <th>Email</th>
            <th>Personal code</th>
            <th>Date created</th>
            <th>Password</th>
          </tr>
        </thead>
        <tbody>
          {persons.map((person) => (
            <tr key={person.id}>
              <td>{person.id}</td>
              <td>{person.firstName}</td>
              <td>{person.lastName}</td>
              <td>{person.phone}</td>
              <td>{person.email}</td>
              <td>{person.personalCode}</td>
              <td>{person.creationDate}</td>
              <td>{person.password}</td>
              <td>
                <button onClick={() => deletePerson(person.id)}>X</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Persons;
