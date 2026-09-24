import { inject, Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { Store } from '@ngrx/store';
import { catchError, forkJoin, map, of, switchMap, take } from 'rxjs';
import { DashboardActions } from './dashboard.actions';
import { DashboardService } from '../../core/services/dashboard.service';
import { DashboardState, Project } from '../../shared/models/dashboard.model';

@Injectable()
export class DashboardEffects {
  private readonly actions$ = inject(Actions);
  private readonly store = inject(Store<{ dashboard: DashboardState }>);
  private readonly dashboardService = inject(DashboardService);

  readonly loadDashboard$ = createEffect(() =>
    this.actions$.pipe(
      ofType(DashboardActions.loadDashboard, DashboardActions.selectProject),
      switchMap((action) => {
        const selectedProjectId = 'projectId' in action ? action.projectId : null;

        return this.store.select((state) => state.dashboard.selectedProjectId).pipe(
          take(1),
          switchMap((currentSelectedProjectId) => {
            const projectId = selectedProjectId ?? currentSelectedProjectId;

            return this.dashboardService.getProjects().pipe(
              switchMap((projects) => {
                const availableProjects = projects ?? [];
                const project = projectId ? availableProjects.find((item) => item.id === projectId) ?? availableProjects[0] ?? null : availableProjects[0] ?? null;

                if (!project) {
                  return of(
                    DashboardActions.loadDashboardFailure({ error: 'Aucun projet disponible.' })
                  );
                }

                return forkJoin({
                  project: of(project),
                  stages: this.dashboardService.getStages(project.id),
                  expenses: this.dashboardService.getExpenses(project.id),
                  activities: this.dashboardService.getActivities(project.id),
                  photos: this.dashboardService.getPhotos(),
                  documents: this.dashboardService.getDocuments(project.id)
                }).pipe(
                  map(({ project, stages, expenses, activities, photos, documents }) =>
                    DashboardActions.loadDashboardSuccess({
                      projects: availableProjects,
                      project,
                      stages: stages ?? [],
                      expenses: expenses ?? [],
                      activities: activities ?? [],
                      photos: photos ?? [],
                      documents: documents ?? []
                    })
                  )
                );
              }),
              catchError(() => of(DashboardActions.loadDashboardFailure({ error: 'Erreur de chargement du dashboard.' })))
            );
          })
        );
      })
    )
  );
}
