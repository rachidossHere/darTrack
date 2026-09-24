export function createEmptyProjectStateMessage(): string {
  return 'Aucun projet disponible pour le moment.';
}

export function hasProjectList<T>(items: T[] | null | undefined): boolean {
  return Array.isArray(items) && items.length > 0;
}
