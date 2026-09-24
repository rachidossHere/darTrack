import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { Project } from '../../models/dashboard.model';

@Component({
  selector: 'dar-project-detail-dialog',
  standalone: true,
  imports: [CommonModule, MatDialogModule, MatButtonModule],
  template: `
    <h2 mat-dialog-title>Détail du projet</h2>

    <div mat-dialog-content class="dialog-content">
      <div class="field-row">
        <span class="label">Nom</span>
        <strong>{{ data.name }}</strong>
      </div>
      <div class="field-row">
        <span class="label">Type</span>
        <strong>{{ data.propertyType }}</strong>
      </div>
      <div class="field-row">
        <span class="label">Ville</span>
        <strong>{{ data.city }}</strong>
      </div>
      <div class="field-row">
        <span class="label">Budget</span>
        <strong>{{ data.budget }} MAD</strong>
      </div>
      <div class="field-row">
        <span class="label">Progression</span>
        <strong>{{ data.progress }}%</strong>
      </div>
      <div class="field-row">
        <span class="label">Description</span>
        <p>{{ data.description || 'Aucune description.' }}</p>
      </div>
    </div>

    <div mat-dialog-actions align="end">
      <button mat-button mat-dialog-close type="button">Fermer</button>
    </div>
  `,
  styles: [
    `
      .dialog-content {
        display: grid;
        gap: 14px;
        min-width: min(500px, 80vw);
      }

      .field-row {
        display: grid;
        gap: 6px;
        padding-bottom: 12px;
        border-bottom: 1px solid #e2e9e6;
      }

      .label {
        color: #6f7f7c;
        font-size: 0.75rem;
        text-transform: uppercase;
        letter-spacing: 0.08em;
      }

      p {
        margin: 0;
        line-height: 1.5;
      }
    `
  ]
})
export class ProjectDetailDialogComponent {
  constructor(
    public readonly dialogRef: MatDialogRef<ProjectDetailDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public readonly data: Project
  ) {}
}
