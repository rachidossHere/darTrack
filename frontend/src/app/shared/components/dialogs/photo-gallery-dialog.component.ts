import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { PhotoItem } from '../../models/dashboard.model';

export interface PhotoGalleryDialogData {
  photos: PhotoItem[];
  startIndex: number;
}

@Component({
  selector: 'dar-photo-gallery-dialog',
  standalone: true,
  imports: [CommonModule, MatDialogModule, MatButtonModule],
  template: `
    <div class="gallery-dialog" *ngIf="photos.length; else emptyState">
      <div class="gallery-header">
        <h2 mat-dialog-title>Galerie du chantier</h2>
        <button mat-button type="button" (click)="dialogRef.close()">Fermer</button>
      </div>

      <div class="viewer" *ngIf="currentPhoto as current">
        <button class="nav" type="button" (click)="previous()" aria-label="Photo précédente">‹</button>
        <img [src]="current.url" [alt]="current.title" />
        <button class="nav" type="button" (click)="next()" aria-label="Photo suivante">›</button>
      </div>

      <div class="caption">
        <strong>{{ currentPhoto?.title }}</strong>
        <span>{{ currentPhoto?.category }} • {{ currentPhoto?.date }}</span>
      </div>
    </div>

    <ng-template #emptyState>
      <div class="gallery-dialog empty-state">
        <h2 mat-dialog-title>Galerie du chantier</h2>
        <p>Aucune photo disponible pour ce projet.</p>
        <button mat-flat-button color="primary" type="button" (click)="dialogRef.close()">Fermer</button>
      </div>
    </ng-template>
  `,
  styles: [
    `
      .gallery-dialog {
        display: grid;
        gap: 16px;
        min-width: min(860px, 90vw);
      }

      .gallery-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        gap: 12px;
      }

      .viewer {
        display: grid;
        grid-template-columns: 48px minmax(0, 1fr) 48px;
        align-items: center;
        gap: 12px;
      }

      .viewer img {
        width: 100%;
        max-height: 70vh;
        object-fit: contain;
        background: #f2f5f3;
        border-radius: 16px;
      }

      .nav {
        border: 1px solid #dfe8e4;
        background: #fff;
        color: #153f38;
        width: 42px;
        height: 42px;
        border-radius: 50%;
        font-size: 1.8rem;
        cursor: pointer;
      }

      .caption {
        display: grid;
        gap: 4px;
      }

      .caption span {
        color: #6f7f7c;
      }

      .empty-state {
        padding: 12px 0;
      }
    `
  ]
})
export class PhotoGalleryDialogComponent {
  readonly photos: PhotoItem[];
  currentIndex: number;

  constructor(
    public readonly dialogRef: MatDialogRef<PhotoGalleryDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public readonly data: PhotoGalleryDialogData
  ) {
    this.photos = this.data.photos;
    this.currentIndex = this.data.startIndex ?? 0;
  }

  get currentPhoto(): PhotoItem | undefined {
    return this.photos[this.currentIndex];
  }

  previous(): void {
    this.currentIndex = (this.currentIndex - 1 + this.photos.length) % this.photos.length;
  }

  next(): void {
    this.currentIndex = (this.currentIndex + 1) % this.photos.length;
  }
}
