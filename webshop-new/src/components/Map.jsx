import L from 'leaflet';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import icon from 'leaflet/dist/images/marker-icon.png';
import iconShadow from 'leaflet/dist/images/marker-shadow.png';
import ChangeView from './ChangeView';
import { useEffect, useState } from 'react';
import config from '../data/config.json';

let DefaultIcon = L.icon({
  iconUrl: icon,
  shadowUrl: iconShadow,
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [2, -40],
});
L.Marker.prototype.options.icon = DefaultIcon;

function Map(props) {
  const [shops, setShops] = useState([]);
  useEffect(() => {

    fetch(config.backendUrl + "/shop")
       .then(res => res.json())
       .then(data => setShops(data));
  }, []);

  // function coordinateSetiing(coordinates) {
  //   const lat = "";
  //   const long = "";
  //   return [lat, long];
  // }

  return (
    <div>

      <MapContainer className='map' center={props.mapCoordinaates.lngLat} zoom={props.mapCoordinaates.zoom} scrollWheelZoom={true}>
        <ChangeView center={props.mapCoordinaates.lngLat} zoom={props.mapCoordinaates.zoom} />
        <TileLayer
          attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        {/* e => .map(<Marker position={coordinateSetiing(e.coordinates)}></Marker) */}

        {shops.map(shop => (
          <Marker key={shop.id} position={[shop.latitude, shop.longitude]}>
            <Popup>
              {shop.name}<br /> Avatud {shop.openTime}
            </Popup>
          </Marker>))}

        {/* <Marker position={[59.4216, 24.7919]}>
          <Popup>
            Ülemiste keskus. <br /> Avatud 9-20
          </Popup>
        </Marker> */}
        {/* <Marker position={[59.4266, 24.7245]}>
          <Popup>
            Kristiine keskus. <br /> Avatud 10-21
          </Popup>
        </Marker> */}
        {/* <Marker position={[58.3779, 26.7308]}>
          <Popup>
            Tasku keskus. <br /> Avatud 9-21
          </Popup>
        </Marker> */}

      </MapContainer>
    </div>)
}

export default Map; 