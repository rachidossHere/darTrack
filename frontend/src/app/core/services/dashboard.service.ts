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

  createProject(project: {
    name: string;
    description?: string;
    propertyType: string;
    city: string;
    budget: number;
    address?: string;
    startDate?: string | null;
    estimatedEndDate?: string | null;
  }): Observable<Project> {
    const payload = {
      name: project.name,
      description: project.description ?? '',
      type: this.mapProjectType(project.propertyType),
      address: project.address ?? project.city ?? '',
      city: project.city,
      initialBudget: Number(project.budget ?? 0),
      startDate: project.startDate || null,
      estimatedEndDate: project.estimatedEndDate || null,
      status: 'DRAFT'
    };

    return this.http.post<ProjectResponse>(`${this.apiUrl}/projects`, payload).pipe(
      map((createdProject) => this.toProject(createdProject) as Project)
    );
  }

  createStage(projectId: string, stage: {
    title: string;
    description?: string;
    plannedStart?: string | null;
    plannedEnd?: string | null;
    plannedBudget?: number | null;
  }): Observable<Stage> {
    const payload = {
      title: stage.title,
      description: stage.description ?? '',
      plannedStartDate: stage.plannedStart || null,
      plannedEndDate: stage.plannedEnd || null,
      plannedBudget: Number(stage.plannedBudget ?? 0)
    };

    return this.http.post<StageResponse>(`${this.apiUrl}/projects/${projectId}/stages`, payload).pipe(
      map((createdStage) => ({
        id: createdStage.id,
        title: createdStage.title,
        description: createdStage.description ?? '',
        plannedStart: createdStage.plannedStartDate ?? '',
        plannedEnd: createdStage.plannedEndDate ?? '',
        plannedBudget: Number(createdStage.plannedBudget ?? 0),
        actualCost: 0,
        progress: createdStage.progress ?? 0,
        status: createdStage.status,
        comment: createdStage.rejectionComment ?? undefined
      }))
    );
  }

  createExpense(projectId: string, expense: {
    label: string;
    amount: number;
    date: string;
    category: string;
    vendor?: string;
    stageId?: string | null;
  }): Observable<Expense> {
    const payload = {
      label: expense.label,
      amount: Number(expense.amount ?? 0),
      expenseDate: expense.date,
      category: this.mapExpenseCategory(expense.category),
      provider: expense.vendor ?? '',
      reference: '',
      paymentStatus: 'PENDING',
      stageId: expense.stageId ?? null
    };

    return this.http.post<ExpenseResponse>(`${this.apiUrl}/projects/${projectId}/expenses`, payload).pipe(
      map((createdExpense) => ({
        id: createdExpense.id,
        label: createdExpense.label,
        amount: Number(createdExpense.amount ?? 0),
        date: createdExpense.expenseDate ?? '',
        category: createdExpense.category,
        status: createdExpense.paymentStatus,
        vendor: createdExpense.provider ?? ''
      }))
    );
  }

  uploadDocument(projectId: string, document: {
    title: string;
    type: string;
    file: File;
    stageId?: string | null;
  }): Observable<DocumentItem> {
    const formData = new FormData();
    formData.append('file', document.file, document.file.name);
    formData.append('type', this.mapDocumentType(document.type));
    if (document.stageId) {
      formData.append('stageId', document.stageId);
    }

    return this.http.post<DocumentResponse>(`${this.apiUrl}/projects/${projectId}/documents`, formData).pipe(
      map((uploadedDocument) => ({
        id: uploadedDocument.id,
        title: uploadedDocument.originalName,
        type: uploadedDocument.type,
        date: uploadedDocument.addedAt ?? '',
        size: this.formatFileSize(uploadedDocument.size)
      }))
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
      propertyType: this.formatProjectTypeLabel(project.type),
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

  private formatProjectTypeLabel(type: string | null | undefined): string {
    const normalized = (type ?? '').toUpperCase();
    const labels: Record<string, string> = {
      APARTMENT: 'Appartement',
      HOUSE: 'Maison',
      VILLA: 'Villa',
      COMMERCIAL: 'Local commercial',
      OTHER: 'Autre'
    };

    return labels[normalized] ?? 'Autre';
  }

  private mapProjectType(value: string): string {
    const normalized = (value ?? '').toLowerCase();
    const mapping: Record<string, string> = {
      appartement: 'APARTMENT',
      maison: 'HOUSE',
      villa: 'VILLA',
      'local commercial': 'COMMERCIAL',
      autre: 'OTHER',
      apartment: 'APARTMENT',
      house: 'HOUSE',
      commercial: 'COMMERCIAL',
      other: 'OTHER'
    };

    return mapping[normalized] ?? 'OTHER';
  }

  private mapExpenseCategory(value: string): string {
    const normalized = (value ?? '').toLowerCase();
    const mapping: Record<string, string> = {
      matériaux: 'MATERIALS',
      materiaux: 'MATERIALS',
      'main-d\'œuvre': 'LABOR',
      'main-doeuvre': 'LABOR',
      transport: 'TRANSPORT',
      équipement: 'EQUIPMENT',
      equipement: 'EQUIPMENT',
      'frais administratifs': 'ADMINISTRATIVE',
      'frais-administratifs': 'ADMINISTRATIVE',
      autre: 'OTHER'
    };

    return mapping[normalized] ?? 'OTHER';
  }

  private mapDocumentType(value: string): string {
    const normalized = (value ?? '').toUpperCase();
    const mapping: Record<string, string> = {
      PDF: 'QUOTE',
      JPG: 'SITE_PHOTO',
      PNG: 'SITE_PHOTO',
      DOC: 'OTHER'
    };

    return mapping[normalized] ?? 'OTHER';
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

