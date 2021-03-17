import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {VideoGalleryComponent} from "./video-gallery/video-gallery.component";
import {VideoPlayerComponent} from "./video-player/video-player.component";
import {VideoUploadComponent} from "./video-upload/video-upload.component";

const routes: Routes = [
  {path:  "", pathMatch:  "full", redirectTo:  "gallery"},
  {path: "gallery", component: VideoGalleryComponent},
  {path: "player/:id", component: VideoPlayerComponent},
  {path: "upload", component: VideoUploadComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
