import { useEffect, useState } from 'react';
import Map from '../../components/Map';
import Button from '@mui/material/Button';
import config from '../../data/config.json'
import { Spinner } from 'react-bootstrap';


function Shops() {
  const [coordinates, setCoordinates] = useState({lngLat: [59.4378, 24.7574], zoom: 11});
	const [shops, setShops] = useState([]);
	const [isLoading, setLoading] = useState(true);

	useEffect(() => {
		// TODO: Backendi päring
    fetch()
	}, []);

  function coordinateSetiing(coordinatesClicked) {
      const latitude = coordinatesClicked.split(",")[0]
      const longitude = coordinatesClicked.split(",")[1]
      setCoordinates({ lngLat: [latitude, longitude], zoom: 13 })
	}

return (<div>
		<Button onClick={() => setCoordinates({ lngLat: [58.94, 25.47], zoom: 7 })}>Kõik poed</Button>

		{isLoading === true ? <Spinner /> : 
			<>
				{shops.map(e =>
					<Button key={e.name} onClick={() => coordinateSetiing(e.coordinates)}>{e.name}</Button>
				)}
			</>}

    <Button onClick={() => setCoordinates({ lngLat: [58.3778125409, 26.73034625013], zoom: 13 })}>Tasku</Button><div>Shops:</div>
		<Map mapCoordinaates={coordinates} />
    

	</div>)
}

export default Shops;