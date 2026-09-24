import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { Observable, of } from 'rxjs';
import { Project } from '../../shared/models/dashboard.model';
import { DashboardService } from '../services/dashboard.service';

@Injectable({ providedIn: 'root' })
export class ProjectResolver implements Resolve<Project | null> {
  constructor(private readonly dashboardService: DashboardService) {}

  resolve(): Observable<Project | null> {
    return this.dashboardService.getProject();
  }
}
