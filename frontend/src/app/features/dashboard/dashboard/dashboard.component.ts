import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ApiService } from '../../../core/services/api.service';
import { Activity, CreateExpenseRequest, CreateProjectRequest, CreateStageRequest, DocumentItem, DocumentType, Expense, ExpenseCategory, Project, Stage } from '../../../shared/models/models';
import { FileSizePipe } from '../../../shared/pipes/file-size.pipe';
import { StatusLabelPipe } from '../../../shared/pipes/status-label.pipe';
import { DashboardSection, DashboardSidebarComponent } from '../components/dashboard-sidebar/dashboard-sidebar.component';
import { MetricCardComponent } from '../components/metric-card/metric-card.component';
import { StageListComponent } from '../components/stage-list/stage-list.component';

type Section = DashboardSection;

@Component({
  selector: 'dar-dashboard',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, DashboardSidebarComponent, MetricCardComponent, StageListComponent, FileSizePipe, StatusLabelPipe],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DashboardComponent {
  readonly api = inject(ApiService);
  private readonly fb = inject(FormBuilder);
  section: Section = 'dashboard';
  project?: Project;
  stages: Stage[] = [];
  expenses: Expense[] = [];
  documents: DocumentItem[] = [];
  activities: Activity[] = [];
  totalExpenses = 0;
  error = '';
  notice = '';
  showStageForm = false;
  showExpenseForm = false;
  showProjectForm = false;
  readonly projectForm = this.fb.nonNullable.group({ name: ['', Validators.required], type: ['APARTMENT', Validators.required], address: ['', Validators.required], city: ['Rabat', Validators.required], initialBudget: [0, [Validators.required, Validators.min(0)]], status: ['DRAFT', Validators.required] });
  readonly stageForm = this.fb.nonNullable.group({ title: ['', Validators.required], description: [''], displayOrder: [0, [Validators.required, Validators.min(0)]], plannedBudget: [0, [Validators.required, Validators.min(0)]], progress: [0, [Validators.required, Validators.min(0), Validators.max(100)]] });
  readonly expenseForm = this.fb.nonNullable.group({ label: ['', Validators.required], amount: [0, [Validators.required, Validators.min(0.01)]], expenseDate: [new Date().toISOString().slice(0, 10), Validators.required], category: ['MATERIALS', Validators.required], provider: [''] });

  constructor() { this.loadProjects(); }

  get pendingCount(): number { return this.stages.filter(stage => stage.status === 'PENDING_APPROVAL').length; }
  createProject(): void { if (this.projectForm.invalid) return; this.api.createProject(this.projectForm.getRawValue() as CreateProjectRequest).subscribe({ next: project => { this.project = project; this.showProjectForm = false; this.flash('Projet créé.'); this.loadProjectData(); }, error: this.showError }); }
  loadProjects(): void { this.api.projects().subscribe({ next: projects => { this.project = projects[0]; if (this.project) this.loadProjectData(); }, error: () => this.error = 'Le backend est indisponible. Démarrez Spring Boot et PostgreSQL pour charger vos données.' }); }
  loadProjectData(): void { if (!this.project) return; const id = this.project.id; this.api.stages(id).subscribe({ next: data => this.stages = data, error: this.showError }); this.api.expenses(id).subscribe({ next: data => { this.expenses = data; this.totalExpenses = data.filter(item => item.paymentStatus !== 'CANCELLED').reduce((sum, item) => sum + item.amount, 0); }, error: this.showError }); this.api.documents(id).subscribe({ next: data => this.documents = data, error: this.showError }); this.api.activities(id).subscribe({ next: data => this.activities = data, error: this.showError }); }
  createStage(): void { if (!this.project || this.stageForm.invalid) return; this.api.createStage(this.project.id, this.stageForm.getRawValue() as CreateStageRequest).subscribe({ next: () => { this.stageForm.reset({ title: '', description: '', displayOrder: this.stages.length, plannedBudget: 0, progress: 0 }); this.showStageForm = false; this.flash('Étape ajoutée.'); this.loadProjectData(); }, error: this.showError }); }
  stageAction(stage: Stage, action: 'submit' | 'approve'): void { this.api.stageAction(stage.id, action).subscribe({ next: () => { this.flash(action === 'approve' ? 'Étape validée.' : 'Étape envoyée en validation.'); this.loadProjectData(); }, error: this.showError }); }
  rejectStage(stage: Stage): void { const comment = window.prompt('Pourquoi cette étape est-elle refusée ?'); if (!comment?.trim()) return; this.api.rejectStage(stage.id, comment.trim()).subscribe({ next: () => { this.flash('Étape refusée et commentaire enregistré.'); this.loadProjectData(); }, error: this.showError }); }
  createExpense(): void { if (!this.project || this.expenseForm.invalid) return; const formValue = this.expenseForm.getRawValue(); const payload: CreateExpenseRequest = { ...formValue, category: formValue.category as ExpenseCategory, paymentStatus: 'PAID' }; this.api.createExpense(this.project.id, payload).subscribe({ next: () => { this.showExpenseForm = false; this.expenseForm.reset({ label: '', amount: 0, expenseDate: new Date().toISOString().slice(0, 10), category: 'MATERIALS', provider: '' }); this.flash('Dépense enregistrée.'); this.loadProjectData(); }, error: this.showError }); }
  upload(event: Event): void { const file = (event.target as HTMLInputElement).files?.[0]; if (!this.project || !file) return; const type: DocumentType = file.type === 'application/pdf' ? 'OTHER' : 'SITE_PHOTO'; this.api.uploadDocument(this.project.id, file, type).subscribe({ next: () => { this.flash('Document ajouté.'); this.loadProjectData(); }, error: this.showError }); }
  private readonly showError = (): void => { this.error = 'Une erreur est survenue. Vérifiez les données et la connexion au backend.'; };
  private flash(message: string): void { this.error = ''; this.notice = message; window.setTimeout(() => this.notice = '', 3500); }
}
