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

return (<div>
		<Button onClick={() => setCoordinates({ lngLat: [58.94, 25.47], zoom: 7 })}>Kõik poed</Button>

		{isLoading === true ? <Spinner /> : 
			<>
				{shops.map(e =>
					<Button key={e.name} onClick={() => setCoordinates({lngLat: [e.latitude, e.longitude], zoom: 13})}>{e.name}</Button>
				)}
			</>}

    <Button onClick={() => setCoordinates({ lngLat: [58.3778125409, 26.73034625013], zoom: 13 })}>Tasku</Button><div>Shops:</div>
		<Map mapCoordinaates={coordinates} />
    

	</div>)
}

export default Shops;