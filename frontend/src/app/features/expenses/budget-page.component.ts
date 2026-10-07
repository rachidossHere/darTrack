import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { DashboardActions } from '../../store/dashboard/dashboard.actions';
import { DashboardState, DashboardViewModel } from '../../shared/models/dashboard.model';
import { selectDashboardViewModel } from '../../store/dashboard/dashboard.selectors';
import { MadPipe } from '../../shared/pipes/currency.pipe';
import { StatusLabelPipe } from '../../shared/pipes/status-label.pipe';
import { DarTrackDialogService } from '../../shared/services/dartrack-dialog.service';
import { DashboardService } from '../../core/services/dashboard.service';

@Component({
  selector: 'dar-budget-page',
  standalone: true,
  imports: [CommonModule, MadPipe, StatusLabelPipe],
  templateUrl: './budget-page.component.html',
  styleUrl: './budget-page.component.scss'
})
export class BudgetPageComponent {
  private readonly store = inject(Store<{ dashboard: DashboardState }>);
  private readonly dialogService = inject(DarTrackDialogService);
  private readonly dashboardService = inject(DashboardService);
  readonly vm$: Observable<DashboardViewModel> = this.store.select(selectDashboardViewModel);

  openNewExpense(vm: DashboardViewModel): void {
    const selectedProjectId = vm.selectedProjectId ?? vm.projects[0]?.id ?? null;
    this.dialogService
      .openExpenseForm(vm.projects, selectedProjectId, vm.stages)
      .subscribe((result) => {
        if (!result || !selectedProjectId) {
          return;
        }

        const expensePayload = result as {
          label: string;
          amount: number;
          date: string;
          category: string;
          vendor?: string;
          stageId?: string | null;
        };

        this.dashboardService.createExpense(selectedProjectId, expensePayload).subscribe(() => {
          this.store.dispatch(DashboardActions.loadDashboard());
        });
      });
  }
}
