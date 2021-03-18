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

  constructor(public dataService: DataService) {}

  ngOnInit(): void {
    this.dataService.findAllPreviews()
      .then(res => {
        res.forEach(vmd => {
          vmd.previewUrl = '/api/v1/video/preview/' + vmd.id;
        });
        this.previews = res;
      })
  }

}
