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

    L.marker(this.centroid).addTo(map).bindPopup(this.description);

    tiles.addTo(map);
  }
}
