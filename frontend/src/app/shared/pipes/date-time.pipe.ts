import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dateTime',
  standalone: true
})
export class DateTimePipe implements PipeTransform {
  transform(value: string | Date | null | undefined): string {
    if (!value) {
      return '—';
    }

    const rawValue = value instanceof Date ? value.toISOString() : value;
    const match = rawValue.match(/(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2})/);

    if (!match) {
      return '—';
    }

    const [, year, month, day, hour, minute] = match;
    return `${day}/${month}/${year} ${hour}:${minute}`;
  }
}
