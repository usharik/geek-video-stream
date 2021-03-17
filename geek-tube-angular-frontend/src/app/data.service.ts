import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private http: HttpClient) {
  }

  public findAllPreviews() {
    return [
      {id: 1, description: `First video`, imageUrl: `https://loremflickr.com/213/106`},
      {id: 2, description: `Second video`, imageUrl: `https://loremflickr.com/213/106`},
      {id: 3, description: `Third video`, imageUrl: `https://loremflickr.com/213/106`},
      {id: 5, description: `Fifth video`, imageUrl: `https://loremflickr.com/213/106`},
      {id: 6, description: `Sixth video`, imageUrl: `https://loremflickr.com/213/106`},
      {id: 7, description: `Seventh video`, imageUrl: `https://loremflickr.com/213/106`},
      {id: 8, description: `Eighth video`, imageUrl: `https://loremflickr.com/213/106`},
    ]
  }

  public uploadNewVideo(formData: FormData, onSuccess: Function) {
    this.http.post("http://localhost:8080/upload", formData).subscribe({
      next: data => {
        console.log("New video submitted");
        onSuccess();
      },
      error: error => {
        console.error(error.message)
      }
    });
  }
}
