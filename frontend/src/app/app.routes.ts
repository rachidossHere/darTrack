import { Routes } from '@angular/router';
import { ProjectResolver } from './core/resolvers/project.resolver';
import { DashboardPageComponent } from './features/dashboard/dashboard-page.component';
import { WorkStepsPageComponent } from './features/work-steps/work-steps-page.component';
import { PhotosPageComponent } from './features/media/photos-page.component';
import { BudgetPageComponent } from './features/expenses/budget-page.component';
import { DocumentsPageComponent } from './features/documents/documents-page.component';

export const routes: Routes = [
  { path: '', redirectTo: 'overview', pathMatch: 'full' },
  {
    path: 'overview',
    component: DashboardPageComponent,
    resolve: { project: ProjectResolver }
  },
  {
    path: 'steps',
    component: WorkStepsPageComponent,
    resolve: { project: ProjectResolver }
  },
  {
    path: 'photos',
    component: PhotosPageComponent,
    resolve: { project: ProjectResolver }
  },
  {
    path: 'budget',
    component: BudgetPageComponent,
    resolve: { project: ProjectResolver }
  },
  {
    path: 'documents',
    component: DocumentsPageComponent,
    resolve: { project: ProjectResolver }
  },
  { path: '**', redirectTo: 'overview' }
];
