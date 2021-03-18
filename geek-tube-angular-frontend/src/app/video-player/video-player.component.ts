import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {DataService} from "../data.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-video-player',
  templateUrl: './video-player.component.html',
  styleUrls: ['./video-player.component.scss']
})
export class VideoPlayerComponent implements OnInit {

  videoStreamUrl: string = '';
  contentType: string = '';
  description: string = '';

  @ViewChild("videoPlayer") videoPlayerRef!: ElementRef;

  constructor(public dataService: DataService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe(param => {
      console.log(param)
      this.dataService.findById(param.id)
        .then((vmd) => {
          this.videoStreamUrl = 'http://localhost:8080/api/v1/video/stream/' + param.id;
          this.contentType = vmd.contentType;
          this.description = vmd.description;

          let videoPlayer = this.videoPlayerRef.nativeElement;
          videoPlayer.load();

          let currentTime = sessionStorage.getItem("currentTime");
          if (currentTime) {
            videoPlayer.currentTime = currentTime;
          }

          videoPlayer.ontimeupdate = () => {
            sessionStorage.setItem("currentTime", videoPlayer.currentTime);
          }
        })
    })
  }

}
