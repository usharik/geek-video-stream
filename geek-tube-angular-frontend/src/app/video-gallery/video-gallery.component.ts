import { Component, OnInit } from '@angular/core';
import { DataService } from "../data.service";
import {VideoMetadata} from "../video-metadata";

@Component({
  selector: 'app-video-gallery',
  templateUrl: './video-gallery.component.html',
  styleUrls: ['./video-gallery.component.scss']
})
export class VideoGalleryComponent implements OnInit {

  previews: VideoMetadata[] = [];
  isError: boolean = false;

  constructor(public dataService: DataService) {}

  ngOnInit(): void {
    this.dataService.findAllPreviews()
      .then(res => {
        this.isError = false;
        this.previews = res;
      })
      .catch(err => {
        this.isError = true;
      });
  }

}
