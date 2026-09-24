import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'dar-photos-page',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './photos-page.component.html',
  styleUrl: './photos-page.component.scss'
})
export class PhotosPageComponent {
  readonly photos = [
    { title: 'Mur avant travaux', date: '2025-02-18', category: 'Avant', url: 'https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?auto=format&fit=crop&w=900&q=80' },
    { title: 'Nouveau réseau', date: '2025-03-06', category: 'Plomberie', url: 'https://images.unsplash.com/photo-1484154218962-a197022b5858?auto=format&fit=crop&w=900&q=80' },
    { title: 'Finition des sols', date: '2025-03-14', category: 'Sol', url: 'https://images.unsplash.com/photo-1494526585095-c41746248156?auto=format&fit=crop&w=900&q=80' }
  ];
}
