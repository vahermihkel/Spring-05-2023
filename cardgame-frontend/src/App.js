import './App.css';
import { useEffect, useRef, useState } from 'react';

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
      .then(text => setMessage(text))
  }

  const newRound = () => {
    setMessage("");

    fetch("http://localhost:8080/start/NEW_ROUND")
      .then(response => response.json())
      .then(json => setCard(json))
  }

  const newGame = () => {
    setMessage("");
    setCard({});
  }

  const [games, setGames] = useState([]); // nordpool ajad

  // uef + enter
  useEffect(() => { // iga kord kui lehele tulles tehakse kohe fetch
    fetch("http://localhost:8080/games-by-score")
      .then(response => response.json())
      .then(json => setGames(json));
  }, [card]);

  const deleteGame = (id) => {
    fetch(`http://localhost:8080/game/delete/${id}`, {"method": "DELETE"})
      .then(response => response.json())
      .then((json) => setGames(json))
    };

  const [players, setPlayers] = useState([]);
  //ief+enter
  useEffect(() => { 
    fetch("http://localhost:8080/players-by-score")
      .then(response => response.json())
      .then((json) => setPlayers(json) )
  }, []);

  const [showTable, setShowTable] = useState(false);

  const handleButtonOnClick = () => {
    setShowTable(!showTable);
  };

  return (
    <div className="App">
      {/* {muutuja} */}
      {card.value === undefined &&
        <div>
          <input ref={nameRef} placeholder="Mängija nimi" />
          <button onClick={startGame}>Alusta mängu</button>
        </div>}

       {/* <div *ngIf="card.value === undefined">
            <input ref={nameRef} placeholder="Mängija nimi" />
            <button onClick={startGame}>Alusta mängu</button>
          </div> */}

      <div>{card.rank}</div>
      <div>{card.suit}</div>
      {card.value !== undefined && message === "" &&
        <div>
          <button onClick={() => guess("lower")}>Väiksem</button>
          <button onClick={() => guess("equal")}>Võrdne</button>
          <button onClick={() => guess("higher")}>Suurem</button>
        </div>}
      <div>{message}</div>
      {message !== "" && message !== "Mäng läbi!" && <button onClick={newRound}>Alusta uut raundi</button>}
      {message === "Mäng läbi!" && <button onClick={newGame}>Alusta uut mängu</button>}
      <br /> <br></br>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Correct answers</th>
            <th>Duration</th>
            <th>Player name</th>
          </tr>
        </thead>
        <tbody>
          {games.map(game => 
            <tr key={game.id}>
              <td>{game.id}</td>
              <td>{game.correctAnswers}</td>
              <td>{game.duration}</td>
              <td>{game.player.name}</td>
              <td><button onClick={() => deleteGame(game.id)}>Delete</button></td>
            </tr>)}
        </tbody>
      </table>
      <br/><br/>
      <button onClick={handleButtonOnClick}>{showTable ? "Hide Table" : "Show players by high score"}</button>
      {showTable &&(<table>
      <thead>
        Players by highest score
      <tr>
      <th>Name</th>
        <th>First Created</th>
        <th>High Score</th>
        </tr>
      </thead>
        <tbody>
        {players.map(players =>
          <tr key={players.id}> 
            <td>{players.name}</td>
            <td>{players.firstCreated}</td>
            <td>{players.highScore}</td>
          </tr>)}
        </tbody>
        </table>)}
    </div>
  );
}

export default App;

// seal kus tegin "npm start" on kompileerimise errorid
//     leht läheb halli taustaga ja punane tekst ütleb mulle vea

// runtime errorid


// 