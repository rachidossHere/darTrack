import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'statusLabel',
  standalone: true
})
export class StatusLabelPipe implements PipeTransform {
  transform(value: string | null | undefined): string {
    if (!value) return 'Inconnu';

    const map: Record<string, string> = {
      DRAFT: 'Brouillon',
      IN_PROGRESS: 'En cours',
      ON_HOLD: 'En pause',
      COMPLETED: 'Terminé',
      CANCELLED: 'Annulé',
      TODO: 'À faire',
      PENDING_APPROVAL: 'À valider',
      APPROVED: 'Validé',
      REJECTED: 'Refusé',
      BLOCKED: 'Bloqué',
      PENDING: 'En attente',
      PAID: 'Payé'
    };

    return map[value] ?? value;
  }
}
