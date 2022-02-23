import {AfterViewInit, Component, OnInit} from '@angular/core';
import {PhotoMainSiteSecondSection} from "../../../models/PhotoMainSiteSecondSection";
import {Lightbox} from "ngx-lightbox";
import {Element} from "@angular/compiler";

@Component({
  selector: 'app-photo-section',
  templateUrl: './photo-section.component.html',
  styleUrls: ['./photo-section.component.css']
})
export class PhotoSectionComponent implements OnInit {
  _albums: PhotoMainSiteSecondSection[] = [];

  constructor(private _lightbox: Lightbox) {
    for (let i = 1; i <= 4; i++) {
      const src = 'assets/photo' + i + '.png';
      let album: PhotoMainSiteSecondSection = new class implements PhotoMainSiteSecondSection {
        caption: string = '';
        src: string = src;
        thumb: string = '';
      }

      this._albums.push(album);
    }
  }


  ngOnInit(): void {
  }



  open(i: number) {
    this._lightbox.open(this._albums, i);
  }



}
