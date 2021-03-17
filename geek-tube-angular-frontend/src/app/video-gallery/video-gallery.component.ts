import { Component, OnInit } from '@angular/core';
import { DataService } from "../data.service";

@Component({
  selector: 'app-video-gallery',
  templateUrl: './video-gallery.component.html',
  styleUrls: ['./video-gallery.component.scss']
})
export class VideoGalleryComponent implements OnInit {

  previews: any;

  constructor(public dataService: DataService) { }

  ngOnInit(): void {
    this.previews = this.dataService.findAllPreviews();
  }

}
