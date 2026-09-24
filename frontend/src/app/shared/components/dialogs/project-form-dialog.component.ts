import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';

export interface ProjectFormDialogData {
  title: string;
}

@Component({
  selector: 'dar-project-form-dialog',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatDialogModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatSelectModule],
  template: `
    <h2 mat-dialog-title>{{ data.title }}</h2>

    <form [formGroup]="form" (ngSubmit)="submit()" mat-dialog-content class="dialog-form">
      <mat-form-field appearance="outline">
        <mat-label>Nom du projet</mat-label>
        <input matInput formControlName="name" />
      </mat-form-field>

      <mat-form-field appearance="outline">
        <mat-label>Type de bien</mat-label>
        <mat-select formControlName="propertyType">
          <mat-option value="appartement">Appartement</mat-option>
          <mat-option value="maison">Maison</mat-option>
          <mat-option value="villa">Villa</mat-option>
          <mat-option value="local commercial">Local commercial</mat-option>
          <mat-option value="autre">Autre</mat-option>
        </mat-select>
      </mat-form-field>

      <mat-form-field appearance="outline">
        <mat-label>Ville</mat-label>
        <input matInput formControlName="city" />
      </mat-form-field>

      <mat-form-field appearance="outline">
        <mat-label>Budget initial (MAD)</mat-label>
        <input matInput type="number" formControlName="budget" />
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
        min-width: min(500px, 80vw);
      }

      mat-form-field {
        width: 100%;
      }
    `
  ]
})
export class ProjectFormDialogComponent {
  readonly form;

  constructor(
    private readonly fb: FormBuilder,
    public readonly dialogRef: MatDialogRef<ProjectFormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public readonly data: ProjectFormDialogData
  ) {
    this.form = this.fb.nonNullable.group({
      name: ['', Validators.required],
      propertyType: ['appartement', Validators.required],
      city: ['', Validators.required],
      budget: [0, [Validators.required, Validators.min(0)]]
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
