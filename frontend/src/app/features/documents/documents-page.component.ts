import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { DashboardActions } from '../../store/dashboard/dashboard.actions';
import { DashboardState, DashboardViewModel } from '../../shared/models/dashboard.model';
import { selectDashboardViewModel } from '../../store/dashboard/dashboard.selectors';
import { DarTrackDialogService } from '../../shared/services/dartrack-dialog.service';
import { DateTimePipe } from '../../shared/pipes/date-time.pipe';
import { DashboardService } from '../../core/services/dashboard.service';

@Component({
  selector: 'dar-documents-page',
  standalone: true,
  imports: [CommonModule, DateTimePipe],
  templateUrl: './documents-page.component.html',
  styleUrl: './documents-page.component.scss'
})
export class DocumentsPageComponent {
  private readonly store = inject(Store<{ dashboard: DashboardState }>);
  private readonly dialogService = inject(DarTrackDialogService);
  private readonly dashboardService = inject(DashboardService);
  readonly vm$: Observable<DashboardViewModel> = this.store.select(selectDashboardViewModel);

  openNewDocument(vm: DashboardViewModel): void {
    const selectedProjectId = vm.selectedProjectId ?? vm.projects[0]?.id ?? null;
    this.dialogService
      .openDocumentForm(vm.projects, selectedProjectId)
      .subscribe((result) => {
        if (!result || !selectedProjectId) {
          return;
        }

        const payload = result as {
          title: string;
          type: string;
          filename?: string;
          file?: File;
          projectId?: string;
        };

        const file = payload.file ?? (payload.filename ? new File([''], payload.filename, { type: 'application/octet-stream' }) : null);
        if (!file) {
          return;
        }

        this.dashboardService.uploadDocument(selectedProjectId, {
          title: payload.title,
          type: payload.type,
          file,
          stageId: null
        }).subscribe(() => {
          this.store.dispatch(DashboardActions.loadDashboard());
        });
      });
  }
}
