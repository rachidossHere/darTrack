import { createSelector } from '@ngrx/store';
import { DashboardState, DashboardViewModel } from '../../shared/models/dashboard.model';

export const selectDashboardState = (state: { dashboard: DashboardState }) => state.dashboard;

export const selectDashboardProjects = createSelector(
  selectDashboardState,
  (state) => state.projects
);

export const selectDashboardSelectedProjectId = createSelector(
  selectDashboardState,
  (state) => state.selectedProjectId
);

export const selectDashboardProject = createSelector(
  selectDashboardState,
  (state) => state.project
);

export const selectDashboardViewModel = createSelector(
  selectDashboardState,
  (state): DashboardViewModel => ({
    projects: state.projects,
    selectedProjectId: state.selectedProjectId,
    project: state.project,
    stages: state.stages,
    activities: state.activities,
    expenses: state.expenses,
    photos: state.photos,
    documents: state.documents,
    totalSpent: state.expenses.reduce((sum, expense) => sum + expense.amount, 0),
    remainingBudget: state.project ? state.project.budget - state.expenses.reduce((sum, expense) => sum + expense.amount, 0) : 0,
    completedStages: state.stages.filter((stage) => stage.status === 'APPROVED').length,
    pendingApproval: state.stages.filter((stage) => stage.status === 'PENDING_APPROVAL').length
  })
);
