import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { Project, Stage } from '../../models/dashboard.model';

export interface ExpenseFormDialogData {
  title: string;
  projects: Project[];
  selectedProjectId: string | null;
  stages: Stage[];
}

@Component({
  selector: 'dar-expense-form-dialog',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatDialogModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatSelectModule, MatDatepickerModule, MatIconModule],
  template: `
    <h2 mat-dialog-title>{{ data.title }}</h2>

    <form [formGroup]="form" (ngSubmit)="submit()" mat-dialog-content class="dialog-form">
      <mat-form-field appearance="outline">
        <mat-label>Projet</mat-label>
        <mat-select formControlName="projectId">
          <mat-option *ngFor="let project of data.projects" [value]="project.id">
            {{ project.name }}
          </mat-option>
        </mat-select>
      </mat-form-field>

      <mat-form-field appearance="outline">
        <mat-label>Étape</mat-label>
        <mat-select formControlName="stageId">
          <mat-option *ngFor="let stage of filteredStages" [value]="stage.id">
            {{ stage.title }}
          </mat-option>
        </mat-select>
      </mat-form-field>

      <mat-form-field appearance="outline">
        <mat-label>Libellé</mat-label>
        <input matInput formControlName="label" />
      </mat-form-field>

      <mat-form-field appearance="outline">
        <mat-label>Montant (MAD)</mat-label>
        <input matInput type="number" min="0" formControlName="amount" />
      </mat-form-field>

      <mat-form-field appearance="outline">
        <mat-label>Catégorie</mat-label>
        <mat-select formControlName="category">
          <mat-option value="matériaux">Matériaux</mat-option>
          <mat-option value="main-d'œuvre">Main-d'œuvre</mat-option>
          <mat-option value="transport">Transport</mat-option>
          <mat-option value="équipement">Équipement</mat-option>
          <mat-option value="frais administratifs">Frais administratifs</mat-option>
          <mat-option value="autre">Autre</mat-option>
        </mat-select>
      </mat-form-field>

      <mat-form-field appearance="outline">
        <mat-label>Prestataire</mat-label>
        <input matInput formControlName="vendor" />
      </mat-form-field>

      <mat-form-field appearance="outline">
        <mat-label>Date</mat-label>
        <input matInput [matDatepicker]="expenseDatePicker" formControlName="date" />
        <mat-datepicker-toggle matSuffix [for]="expenseDatePicker"></mat-datepicker-toggle>
        <mat-datepicker #expenseDatePicker></mat-datepicker>
      </mat-form-field>
    </form>

    <div mat-dialog-actions align="end">
      <button mat-button type="button" (click)="dialogRef.close()">Annuler</button>
      <button mat-flat-button color="primary" type="submit" [disabled]="form.invalid" (click)="submit()">Enregistrer</button>
    </div>
  `,
  styles: [
    `
      .dialog-form {
        display: grid;
        gap: 12px;
        min-width: min(520px, 80vw);
      }

      mat-form-field {
        width: 100%;
      }

      button[mat-flat-button], button[mat-button] {
        min-width: 150px;
      }
    `
  ]
})
export class ExpenseFormDialogComponent {
  readonly form;

  constructor(
    private readonly fb: FormBuilder,
    public readonly dialogRef: MatDialogRef<ExpenseFormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public readonly data: ExpenseFormDialogData
  ) {
    this.form = this.fb.nonNullable.group({
      projectId: [this.data.selectedProjectId ?? this.data.projects[0]?.id ?? '', Validators.required],
      stageId: ['', Validators.required],
      label: ['', Validators.required],
      amount: [0, [Validators.required, Validators.min(0)]],
      category: ['matériaux', Validators.required],
      vendor: [''],
      date: [new Date(), Validators.required]
    });
  }

  get filteredStages(): Stage[] {
    return this.data.stages.filter((stage) => stage.id === this.form.controls.stageId.value || !this.form.controls.projectId.value || this.data.stages.some((item) => item.id === item.id));
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const value = this.form.getRawValue();
    this.dialogRef.close({
      ...value,
      date: this.formatDateForBackend(value.date),
      amount: Number(value.amount ?? 0),
      status: 'PENDING'
    });
  }

  private formatDateForBackend(value: Date | string | null): string | null {
    if (!value) {
      return null;
    }

    const date = typeof value === 'string' ? new Date(value) : value;
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');

    return `${year}-${month}-${day}`;
  }
}
