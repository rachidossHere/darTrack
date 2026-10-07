import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { DashboardState, DashboardViewModel, PhotoItem } from '../../shared/models/dashboard.model';
import { selectDashboardViewModel } from '../../store/dashboard/dashboard.selectors';
import { DarTrackDialogService } from '../../shared/services/dartrack-dialog.service';

@Component({
  selector: 'dar-photos-page',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './photos-page.component.html',
  styleUrl: './photos-page.component.scss'
})
export class PhotosPageComponent {
  private readonly store = inject(Store<{ dashboard: DashboardState }>);
  private readonly dialogService = inject(DarTrackDialogService);
  readonly vm$: Observable<DashboardViewModel> = this.store.select(selectDashboardViewModel);

  openGallery(photos: PhotoItem[], startIndex = 0): void {
    this.dialogService.openPhotoGallery(photos, startIndex).subscribe();
  }

  openAddPhotos(vm: DashboardViewModel): void {
    const selectedProjectId = vm.selectedProjectId ?? vm.projects[0]?.id ?? null;
    this.dialogService.openDocumentForm(vm.projects, selectedProjectId).subscribe();
  }
}
