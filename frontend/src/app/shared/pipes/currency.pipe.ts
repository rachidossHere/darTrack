import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'mad',
  standalone: true
})
export class MadPipe implements PipeTransform {
  transform(value: number | null | undefined, digits = 0): string {
    const safeValue = Number(value ?? 0);
    return `${new Intl.NumberFormat('fr-FR', { minimumFractionDigits: digits, maximumFractionDigits: digits }).format(safeValue)} MAD`;
  }
}
