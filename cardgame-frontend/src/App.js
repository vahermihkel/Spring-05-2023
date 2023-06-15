import logo from './logo.svg';
import './App.css';
import { useRef, useState } from 'react';

// tumesinine - tag: div, input, button
// helesinine - property: placeholder, className
// punane/oranž - string: "App"   "Mängija nimi"
// valge - väljanäidatav tekst

function App() {
  const nameRef = useRef();
  const [card, setCard] = useState({}); // kandilised sulud --> täpselt 2: muutuja ja funktsioon
                                        // loogelised sulud --> dünaaika, 1,2,3,4,5,6 vms muutujat/funktsiooni
        // vasakpoolne on muutuja, mida hoitakse HTMLs
        // parempoolne on funktsioon, millega HTMLi muudetakse (setter)

  // let muutuja = 0;
  // const [muutuja, setMuutuja] = useState(0);

  // startGame();

  // function startGame() {

  // }

  const [message, setMessage] = useState("");

  const startGame = () => {
    // muutuja = 2;
    // setMuutuja(2);
    //console.log(nameRef.current.value);    // current ---> tagi kelle sees ta on, value ---> väärtus
    fetch("http://localhost:8080/start/" + nameRef.current.value)
      .then(response => response.json())
      .then(json => setCard(json))
  }

  const guess = (guessValue) => {
    fetch("http://localhost:8080/guess/" + guessValue)
      .then(response => response.text())
      .then(json => setMessage(json))
  }

  return (
    <div className="App">
      {/* {muutuja} */}
      <input ref={nameRef} placeholder="Mängija nimi" />
      <button onClick={startGame}>Alusta mängu</button>
      <div>{card.rank}</div>
      <div>{card.suit}</div>
      <button onClick={() => guess("lower")}>Väiksem</button>
      <button onClick={() => guess("equal")}>Võrdne</button>
      <button onClick={() => guess("higher")}>Suurem</button>
      <div>{message}</div>
    </div>
  );
}

export default App;
