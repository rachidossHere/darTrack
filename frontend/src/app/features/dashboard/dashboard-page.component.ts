import { ChangeDetectionStrategy, Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { DashboardActions } from '../../store/dashboard/dashboard.actions';
import { selectDashboardViewModel } from '../../store/dashboard/dashboard.selectors';
import { DashboardState, DashboardViewModel, Project } from '../../shared/models/dashboard.model';
import { MadPipe } from '../../shared/pipes/currency.pipe';
import { StatusLabelPipe } from '../../shared/pipes/status-label.pipe';
import { ProjectDetailDialogComponent } from '../../shared/components/dialogs/project-detail-dialog.component';
import { ProjectFormDialogComponent } from '../../shared/components/dialogs/project-form-dialog.component';

@Component({
  selector: 'dar-dashboard-page',
  standalone: true,
  imports: [CommonModule, MadPipe, StatusLabelPipe],
  templateUrl: './dashboard-page.component.html',
  styleUrl: './dashboard-page.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DashboardPageComponent implements OnInit {
  private readonly store = inject(Store<{ dashboard: DashboardState }>);
  private readonly dialog = inject(MatDialog);
  readonly vm$: Observable<DashboardViewModel> = this.store.select(selectDashboardViewModel);

  ngOnInit(): void {
    this.store.dispatch(DashboardActions.loadDashboard());
  }

  onProjectChange(projectId: string): void {
    this.store.dispatch(DashboardActions.selectProject({ projectId }));
  }

  getSelectedProjectId(vm: DashboardViewModel): string {
    return vm.selectedProjectId ?? vm.projects[0]?.id ?? '';
  }

  openProjectDetail(project: Project | null): void {
    if (!project) {
      return;
    }

    this.dialog.open(ProjectDetailDialogComponent, {
      data: project,
      width: '560px'
    });
  }

  openCreateProject(): void {
    this.dialog.open(ProjectFormDialogComponent, {
      data: { title: 'Créer un projet' },
      width: '560px'
    });
  }
}
