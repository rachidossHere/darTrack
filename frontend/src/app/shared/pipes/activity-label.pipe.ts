import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'activityLabel',
  standalone: true
})
export class ActivityLabelPipe implements PipeTransform {
  transform(value: string | null | undefined): string {
    if (!value) {
      return 'Activité';
    }

    const map: Record<string, string> = {
      STAGE_CREATED: 'Étape créée',
      STAGE_UPDATED: 'Étape mise à jour',
      STAGE_APPROVED: 'Étape validée',
      STAGE_REJECTED: 'Étape refusée',
      PROJECT_CREATED: 'Projet créé',
      PROJECT_UPDATED: 'Projet mis à jour',
      EXPENSE_CREATED: 'Dépense ajoutée',
      DOCUMENT_ADDED: 'Document ajouté'
    };

    return map[value] ?? value.replace(/_/g, ' ').toLowerCase();
  }
}
