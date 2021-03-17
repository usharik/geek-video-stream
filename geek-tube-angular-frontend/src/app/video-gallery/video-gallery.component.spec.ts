import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VideoGalleryComponent } from './video-gallery.component';

describe('VideoGalleryComponent', () => {
  let component: VideoGalleryComponent;
  let fixture: ComponentFixture<VideoGalleryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VideoGalleryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VideoGalleryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
