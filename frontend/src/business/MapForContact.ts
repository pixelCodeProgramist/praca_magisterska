import * as L from "leaflet";
import {LatLngExpression} from "leaflet";

export class MapForContact {

  private centroid: L.LatLngExpression;
  private elementName: string;
  private description: string;

  constructor(centroid: LatLngExpression, elementName: string, description: string) {
    this.centroid = centroid;
    this.elementName = elementName;
    this.description = description;
  }

  initMap(): void {
    let map: L.Map = L.map(this.elementName, {
      center: this.centroid,
      zoom: 12
    });


    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 10,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });
    const iconUrl = 'assets/marker-icon.png';
    const shadowUrl = 'assets/marker-shadow.png';
    const iconRetinaUrl = 'assets/marker-icon-2x.png';
    const iconDefault = L.icon({
      iconRetinaUrl,
      iconUrl,
      shadowUrl,
      iconSize: [25, 41],
      iconAnchor: [12, 41],
      popupAnchor: [1, -34],
      tooltipAnchor: [16, -28],
      shadowSize: [41, 41]
    });
    L.Marker.prototype.options.icon = iconDefault;

    L.marker(this.centroid).addTo(map).bindPopup(this.description);

    tiles.addTo(map);
  }
}
