import {Component, ElementRef, NgZone, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {MapsAPILoader} from '@agm/core';
import {} from '@types/googlemaps';

@Component({
  selector: 'app-google-maps',
  templateUrl: './google-maps.component.html',
  styleUrls: ['./google-maps.component.css']
})
export class GoogleMapsComponent implements OnInit {
  public latitude: number;
  public longitude: number;
  public searchControl: FormControl;
  public zoom: number;
  public inputAddress: string = '';
  public street: string = '';
  public house: string = '';
  public searchElementRef: ElementRef;

  constructor(public mapsAPILoader: MapsAPILoader,
              public ngZone: NgZone) {
  }

  ngOnInit() {
    this.zoom = 16;
    this.latitude = 50.449392;
    this.longitude = 30.523408;
    this.searchControl = new FormControl();

    setTimeout(() => {
      this.mapsAPILoader.load().then(() => {
        let autocomplete = new google.maps.places.Autocomplete(this.searchElementRef.nativeElement, {
          types: ['address']
        });

        autocomplete.addListener('place_changed', () => {
          this.ngZone.run(() => {
            let place: google.maps.places.PlaceResult = autocomplete.getPlace();

            if (place.geometry === undefined || place.geometry === null) {
              return;
            }

            this.latitude = place.geometry.location.lat();
            this.longitude = place.geometry.location.lng();
            this.zoom = 16;
          });
        });
      });
    }, 700);
  }

  mapReady($event, yourLocation) {
    $event.controls[google.maps.ControlPosition.RIGHT_CENTER].push(document.getElementById(yourLocation));
  }

  setSearchElement(searchElementRef: ElementRef) {
    this.searchElementRef = searchElementRef;
  }

  setCurrentPosition() {
    if ('geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition((position) => {
        this.latitude = position.coords.latitude;
        this.longitude = position.coords.longitude;
        this.zoom = 16;
        this.geocodeLatLng(new google.maps.LatLng(this.latitude, this.longitude));
      });
    }
  }

  placeMarker($event) {
    this.latitude = $event.coords.lat;
    this.longitude = $event.coords.lng;
    this.geocodeLatLng(new google.maps.LatLng(this.latitude, this.longitude));
  }

  geocodeLatLng(latLng) {
    let geocoder = new google.maps.Geocoder();
    geocoder.geocode({'location': latLng}, (results, status) => {
      if (status == google.maps.GeocoderStatus.OK) {
        if (results[1]) {
          this.inputAddress = results[0].formatted_address;
          this.street = this.inputAddress.split(',')[0].trim();
          this.house = this.inputAddress.split(',')[1].trim();
        } else {
          this.inputAddress = 'Location not found';
        }
      }
      else {
        this.inputAddress = 'Geocoder failed due to: ' + status;
      }
    });
  }

  geocodeAddress(street, house) {
    let geocoder = new google.maps.Geocoder();
    this.inputAddress = street + ', ' + house + ', Kyiv';
    geocoder.geocode({'address': this.inputAddress}, (results, status) => {
      if (status == google.maps.GeocoderStatus.OK && street!='') {
        this.latitude = results[0].geometry.location.lat();
        this.longitude = results[0].geometry.location.lng();
      }else{
        alert("Geocoding error");
      }
    });
  }

  changeHouse(house: string) {
    this.house = house;
    console.log(this.street + ' ' + this.house);
    this.geocodeAddress(this.street, this.house);
  }

  changeStreet(street: string) {
    this.street = street.split(', ')[0];
    console.log(this.street + ' ' + this.house);
    this.geocodeAddress(this.street, this.house);
  }
}
