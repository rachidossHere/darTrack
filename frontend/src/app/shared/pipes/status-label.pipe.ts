import { Pipe, PipeTransform } from '@angular/core';
import { statusLabel } from '../utils/status-label';

@Pipe({
  name: 'statusLabel',
  standalone: true
})
export class StatusLabelPipe implements PipeTransform {
  transform(value: string): string {
    return statusLabel(value);
  }
}
