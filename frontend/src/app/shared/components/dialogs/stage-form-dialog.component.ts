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
import { Project } from '../../models/dashboard.model';

export interface StageFormDialogData {
  title: string;
  projects: Project[];
  selectedProjectId: string | null;
}

@Component({
  selector: 'dar-stage-form-dialog',
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
        <mat-label>Titre de l’étape</mat-label>
        <input matInput formControlName="title" />
      </mat-form-field>

      <mat-form-field appearance="outline">
        <mat-label>Description</mat-label>
        <textarea matInput rows="3" formControlName="description"></textarea>
      </mat-form-field>

      <div class="two-columns">
        <mat-form-field appearance="outline">
          <mat-label>Date de début</mat-label>
          <input matInput [matDatepicker]="startPicker" formControlName="plannedStart" />
          <mat-datepicker-toggle matSuffix [for]="startPicker"></mat-datepicker-toggle>
          <mat-datepicker #startPicker></mat-datepicker>
        </mat-form-field>

        <mat-form-field appearance="outline">
          <mat-label>Date de fin</mat-label>
          <input matInput [matDatepicker]="endPicker" formControlName="plannedEnd" />
          <mat-datepicker-toggle matSuffix [for]="endPicker"></mat-datepicker-toggle>
          <mat-datepicker #endPicker></mat-datepicker>
        </mat-form-field>
      </div>

      <mat-form-field appearance="outline">
        <mat-label>Budget prévu (MAD)</mat-label>
        <input matInput type="number" min="0" formControlName="plannedBudget" />
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

      .two-columns {
        display: grid;
        grid-template-columns: repeat(2, minmax(0, 1fr));
        gap: 12px;
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
export class StageFormDialogComponent {
  readonly form;

  constructor(
    private readonly fb: FormBuilder,
    public readonly dialogRef: MatDialogRef<StageFormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public readonly data: StageFormDialogData
  ) {
    this.form = this.fb.nonNullable.group({
      projectId: [this.data.selectedProjectId ?? this.data.projects[0]?.id ?? '', Validators.required],
      title: ['', Validators.required],
      description: [''],
      plannedStart: [''],
      plannedEnd: [''],
      plannedBudget: [0, [Validators.min(0)]]
    });
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const { projectId, plannedBudget, plannedStart, plannedEnd, ...rest } = this.form.getRawValue();
    this.dialogRef.close({
      ...rest,
      projectId,
      plannedStart: this.formatDateForBackend(plannedStart),
      plannedEnd: this.formatDateForBackend(plannedEnd),
      plannedBudget: Number(plannedBudget ?? 0),
      status: 'TODO'
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
