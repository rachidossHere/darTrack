import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { Project } from '../../models/dashboard.model';

export interface DocumentFormDialogData {
  title: string;
  projects: Project[];
  selectedProjectId: string | null;
}

@Component({
  selector: 'dar-document-form-dialog',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatDialogModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatSelectModule],
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
        <mat-label>Titre du document</mat-label>
        <input matInput formControlName="title" />
      </mat-form-field>

      <mat-form-field appearance="outline">
        <mat-label>Type</mat-label>
        <mat-select formControlName="type">
          <mat-option value="PDF">PDF</mat-option>
          <mat-option value="JPG">JPG</mat-option>
          <mat-option value="PNG">PNG</mat-option>
          <mat-option value="DOC">DOC</mat-option>
        </mat-select>
      </mat-form-field>

      <mat-form-field appearance="outline">
        <mat-label>Fichier</mat-label>
        <input matInput type="text" formControlName="filename" placeholder="Nom du document" />
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
export class DocumentFormDialogComponent {
  readonly form;

  constructor(
    private readonly fb: FormBuilder,
    public readonly dialogRef: MatDialogRef<DocumentFormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public readonly data: DocumentFormDialogData
  ) {
    this.form = this.fb.nonNullable.group({
      projectId: [this.data.selectedProjectId ?? this.data.projects[0]?.id ?? '', Validators.required],
      title: ['', Validators.required],
      type: ['PDF', Validators.required],
      filename: ['', Validators.required]
    });
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.dialogRef.close(this.form.getRawValue());
  }
}
