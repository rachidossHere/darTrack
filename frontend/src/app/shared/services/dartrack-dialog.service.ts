import { Injectable, inject } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { Project, PhotoItem, Stage } from '../models/dashboard.model';
import { StageFormDialogComponent, StageFormDialogData } from '../components/dialogs/stage-form-dialog.component';
import { ExpenseFormDialogComponent, ExpenseFormDialogData } from '../components/dialogs/expense-form-dialog.component';
import { DocumentFormDialogComponent, DocumentFormDialogData } from '../components/dialogs/document-form-dialog.component';
import { PhotoGalleryDialogComponent, PhotoGalleryDialogData } from '../components/dialogs/photo-gallery-dialog.component';

@Injectable({ providedIn: 'root' })
export class DarTrackDialogService {
  private readonly dialog = inject(MatDialog);

  openStageForm(projects: Project[], selectedProjectId: string | null): Observable<unknown> {
    return this.dialog
      .open<StageFormDialogComponent, StageFormDialogData, unknown>(StageFormDialogComponent, {
        width: '560px',
        data: {
          title: 'Ajouter une étape',
          projects,
          selectedProjectId
        }
      })
      .afterClosed();
  }

  openExpenseForm(projects: Project[], selectedProjectId: string | null, stages: Stage[]): Observable<unknown> {
    return this.dialog
      .open<ExpenseFormDialogComponent, ExpenseFormDialogData, unknown>(ExpenseFormDialogComponent, {
        width: '560px',
        data: {
          title: 'Ajouter une dépense',
          projects,
          selectedProjectId,
          stages
        }
      })
      .afterClosed();
  }

  openDocumentForm(projects: Project[], selectedProjectId: string | null): Observable<unknown> {
    return this.dialog
      .open<DocumentFormDialogComponent, DocumentFormDialogData, unknown>(DocumentFormDialogComponent, {
        width: '560px',
        data: {
          title: 'Ajouter un document',
          projects,
          selectedProjectId
        }
      })
      .afterClosed();
  }

  openPhotoGallery(photos: PhotoItem[], startIndex = 0): Observable<void> {
    return this.dialog
      .open<PhotoGalleryDialogComponent, PhotoGalleryDialogData, void>(PhotoGalleryDialogComponent, {
        width: '900px',
        maxWidth: '92vw',
        panelClass: 'photo-gallery-dialog',
        data: {
          photos,
          startIndex
        }
      })
      .afterClosed();
  }
}
