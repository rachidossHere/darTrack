import { createActionGroup, emptyProps, props } from '@ngrx/store';
import { Activity, DashboardState, Expense, PhotoItem, Project, Stage, DocumentItem } from '../../shared/models/dashboard.model';

export const DashboardActions = createActionGroup({
  source: 'Dashboard',
  events: {
    'Load dashboard': emptyProps(),
    'Select project': props<{ projectId: string }>(),
    'Load dashboard success': props<{ projects: Project[]; project: Project | null; stages: Stage[]; activities: Activity[]; expenses: Expense[]; photos: PhotoItem[]; documents: DocumentItem[] }>(),
    'Load dashboard failure': props<{ error: string }>(),
    'Set loading': props<{ loading: boolean }>()
  }
});

export type DashboardStateSlice = DashboardState;
