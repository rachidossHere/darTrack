import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable, of } from 'rxjs';
import { ActivityResponse, DocumentResponse, ExpenseResponse, ProjectResponse, StageResponse } from '../../shared/models/api-responses.model';
import { Project, Stage, Expense, Activity, DocumentItem, PhotoItem } from '../../shared/models/dashboard.model';

@Injectable({ providedIn: 'root' })
export class DashboardService {
  private readonly apiUrl = '/api/v1';

  constructor(private readonly http: HttpClient) {}

  getProjects(): Observable<Project[]> {
    return this.http.get<ProjectResponse[]>(`${this.apiUrl}/projects`).pipe(
      map((projects) => projects.map((project) => this.toProject(project)).filter((project): project is Project => project !== null))
    );
  }

  getProject(): Observable<Project | null> {
    return this.getProjects().pipe(
      map((projects) => projects[0] ?? null)
    );
  }

  getStages(projectId?: string): Observable<Stage[]> {
    const id = projectId ?? '';

    return this.http.get<StageResponse[]>(`${this.apiUrl}/projects/${id}/stages`).pipe(
      map((stages) => stages.map((stage) => ({
        id: stage.id,
        title: stage.title,
        description: stage.description ?? '',
        plannedStart: stage.plannedStartDate ?? '',
        plannedEnd: stage.plannedEndDate ?? '',
        plannedBudget: Number(stage.plannedBudget ?? 0),
        actualCost: 0,
        progress: stage.progress ?? 0,
        status: stage.status,
        comment: stage.rejectionComment ?? undefined
      })))
    );
  }

  getExpenses(projectId?: string): Observable<Expense[]> {
    const id = projectId ?? '';

    return this.http.get<ExpenseResponse[]>(`${this.apiUrl}/projects/${id}/expenses`).pipe(
      map((expenses) => expenses.map((expense) => ({
        id: expense.id,
        label: expense.label,
        amount: Number(expense.amount ?? 0),
        date: expense.expenseDate ?? '',
        category: expense.category,
        status: expense.paymentStatus,
        vendor: expense.provider ?? ''
      })))
    );
  }

  getActivities(projectId?: string): Observable<Activity[]> {
    const id = projectId ?? '';

    return this.http.get<ActivityResponse[]>(`${this.apiUrl}/projects/${id}/activities`).pipe(
      map((activities) => activities.map((activity) => ({
        id: activity.id,
        label: activity.type,
        date: activity.occurredAt ?? '',
        detail: activity.description
      })))
    );
  }

  getPhotos(): Observable<PhotoItem[]> {
    return of([]);
  }

  getDocuments(projectId?: string): Observable<DocumentItem[]> {
    const id = projectId ?? '';

    return this.http.get<DocumentResponse[]>(`${this.apiUrl}/projects/${id}/documents`).pipe(
      map((documents) => documents.map((document) => ({
        id: document.id,
        title: document.originalName,
        type: document.type,
        date: document.addedAt ?? '',
        size: this.formatFileSize(document.size)
      })))
    );
  }

  private toProject(project: ProjectResponse | null): Project | null {
    if (!project) {
      return null;
    }

    return {
      id: project.id,
      name: project.name,
      description: project.description ?? '',
      propertyType: project.type,
      city: project.city,
      budget: Number(project.initialBudget ?? 0),
      spent: 0,
      startDate: project.startDate ?? '',
      estimatedEndDate: project.estimatedEndDate ?? '',
      status: project.status,
      progress: project.progress ?? 0,
      address: project.address
    };
  }

  private formatFileSize(size: number): string {
    if (size < 1024) {
      return `${size} o`;
    }

    if (size < 1024 * 1024) {
      return `${Math.round(size / 1024)} Ko`;
    }

    return `${(size / (1024 * 1024)).toFixed(1)} Mo`;
  }
}

