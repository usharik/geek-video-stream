import { Component, OnInit } from '@angular/core';
import { DataService } from "../data.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-video-upload',
  templateUrl: './video-upload.component.html',
  styleUrls: ['./video-upload.component.scss']
})
export class VideoUploadComponent implements OnInit {

  isError: boolean = false;

  constructor(private dataService: DataService, private router: Router) { }

  ngOnInit(): void {
  }

  submit() {
    console.log("Submit button")
    let form:HTMLFormElement | null = document.forms.namedItem('uploadForm');
    if (form) {
      let fd = new FormData(form);
      this.dataService.uploadNewVideo(fd)
        .then(() => {
          this.isError = false;
          this.router.navigate(['player/1']);
        })
        .catch((err) => {
          this.isError = true;
          console.error(err);
        });
    }
  }
}
