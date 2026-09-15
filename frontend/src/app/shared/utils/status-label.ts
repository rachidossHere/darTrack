export const STATUS_LABELS: Readonly<Record<string, string>> = {
  IN_PROGRESS: 'En cours',
  PENDING_APPROVAL: 'À valider',
  APPROVED: 'Validée',
  REJECTED: 'Refusée',
  BLOCKED: 'Bloquée',
  TODO: 'À faire',
  PAID: 'Payée',
  PENDING: 'En attente',
  CANCELLED: 'Annulée',
  MATERIALS: 'Matériaux',
  LABOR: "Main-d'œuvre",
  TRANSPORT: 'Transport',
  EQUIPMENT: 'Équipement',
  ADMINISTRATIVE: 'Administratif',
  SITE_PHOTO: 'Photo de chantier',
  QUOTE: 'Devis',
  INVOICE: 'Facture',
  OTHER: 'Autre'
};

export function statusLabel(value: string): string {
  return STATUS_LABELS[value] ?? value;
}