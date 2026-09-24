import { createReducer, on } from '@ngrx/store';
import { DashboardActions } from './dashboard.actions';
import { DashboardState } from '../../shared/models/dashboard.model';

const initialState: DashboardState = {
  loading: false,
  error: null,
  projects: [],
  selectedProjectId: null,
  project: null,
  stages: [],
  activities: [],
  expenses: [],
  photos: [],
  documents: []
};

export const dashboardReducer = createReducer(
  initialState,
  on(DashboardActions.loadDashboard, (state) => ({ ...state, loading: true, error: null })),
  on(DashboardActions.selectProject, (state, { projectId }) => ({
    ...state,
    selectedProjectId: projectId
  })),
  on(DashboardActions.loadDashboardSuccess, (state, { projects, project, stages, activities, expenses, photos, documents }) => ({
    ...state,
    loading: false,
    projects,
    selectedProjectId: project?.id ?? state.selectedProjectId,
    project,
    stages,
    activities,
    expenses,
    photos,
    documents,
    error: null
  })),
  on(DashboardActions.loadDashboardFailure, (state, { error }) => ({
    ...state,
    loading: false,
    error
  })),
  on(DashboardActions.setLoading, (state, { loading }) => ({
    ...state,
    loading
  }))
);
