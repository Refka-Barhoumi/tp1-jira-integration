import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { JuryComponent } from './components/jury/jury.component'; // <--- 1. ADD THIS IMPORT

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, JuryComponent], // <--- 2. ADD IT HERE
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'jury-frontend';
}