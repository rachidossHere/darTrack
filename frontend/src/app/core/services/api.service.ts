import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Activity, CreateExpenseRequest, CreateProjectRequest, CreateStageRequest, DocumentItem, DocumentType, Expense, Project, Stage } from '../../shared/models/models';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api/v1';
  projects(): Observable<Project[]> { return this.http.get<Project[]>(`${this.baseUrl}/projects`); }
  createProject(payload: CreateProjectRequest): Observable<Project> { return this.http.post<Project>(`${this.baseUrl}/projects`, payload); }
  stages(id: string): Observable<Stage[]> { return this.http.get<Stage[]>(`${this.baseUrl}/projects/${id}/stages`); }
  createStage(id: string, payload: CreateStageRequest): Observable<Stage> { return this.http.post<Stage>(`${this.baseUrl}/projects/${id}/stages`, payload); }
  stageAction(id: string, action: 'submit' | 'approve'): Observable<Stage> { return this.http.post<Stage>(`${this.baseUrl}/stages/${id}/${action}`, {}); }
  rejectStage(id: string, comment: string): Observable<Stage> { return this.http.post<Stage>(`${this.baseUrl}/stages/${id}/reject`, { comment }); }
  expenses(id: string): Observable<Expense[]> { return this.http.get<Expense[]>(`${this.baseUrl}/projects/${id}/expenses`); }
  createExpense(id: string, payload: CreateExpenseRequest): Observable<Expense> { return this.http.post<Expense>(`${this.baseUrl}/projects/${id}/expenses`, payload); }
  documents(id: string): Observable<DocumentItem[]> { return this.http.get<DocumentItem[]>(`${this.baseUrl}/projects/${id}/documents`); }
  uploadDocument(id: string, file: File, type: DocumentType): Observable<DocumentItem> { const data = new FormData(); data.append('file', file); data.append('type', type); return this.http.post<DocumentItem>(`${this.baseUrl}/projects/${id}/documents`, data); }
  downloadUrl(id: string): string { return `${this.baseUrl}/documents/${id}/download`; }
  activities(id: string): Observable<Activity[]> { return this.http.get<Activity[]>(`${this.baseUrl}/projects/${id}/activities`); }
}