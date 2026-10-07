import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { DashboardActions } from '../../store/dashboard/dashboard.actions';
import { DashboardState, DashboardViewModel, Project, Stage } from '../../shared/models/dashboard.model';
import { selectDashboardViewModel } from '../../store/dashboard/dashboard.selectors';
import { StatusLabelPipe } from '../../shared/pipes/status-label.pipe';
import { DarTrackDialogService } from '../../shared/services/dartrack-dialog.service';
import { DashboardService } from '../../core/services/dashboard.service';

@Component({
  selector: 'dar-work-steps-page',
  standalone: true,
  imports: [CommonModule, StatusLabelPipe],
  templateUrl: './work-steps-page.component.html',
  styleUrl: './work-steps-page.component.scss'
})
export class WorkStepsPageComponent implements OnInit {
  private readonly store = inject(Store<{ dashboard: DashboardState }>);
  private readonly dialogService = inject(DarTrackDialogService);
  private readonly dashboardService = inject(DashboardService);
  readonly vm$: Observable<DashboardViewModel> = this.store.select(selectDashboardViewModel);

  ngOnInit(): void {
    this.store.select(selectDashboardViewModel).subscribe();
  }

  getSelectedProjectId(vm: DashboardViewModel): string {
    return vm.selectedProjectId ?? vm.projects[0]?.id ?? '';
  }

  openNewStage(vm: DashboardViewModel): void {
    this.dialogService
      .openStageForm(vm.projects, this.getSelectedProjectId(vm))
      .subscribe((result) => {
        if (!result) {
          return;
        }

        const projectId = this.getSelectedProjectId(vm);
        const stagePayload = result as {
          title: string;
          description?: string;
          plannedStart?: string | null;
          plannedEnd?: string | null;
          plannedBudget?: number | null;
        };

        this.dashboardService.createStage(projectId, stagePayload).subscribe(() => {
          this.store.dispatch(DashboardActions.loadDashboard());
        });
      });
  }
}
