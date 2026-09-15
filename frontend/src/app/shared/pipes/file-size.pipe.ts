import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'fileSize',
  standalone: true
})
export class FileSizePipe implements PipeTransform {
  transform(bytes: number): string {
    if (bytes < 1024) {
      return `${bytes} o`;
    }

    const kilobytes = bytes / 1024;
    if (kilobytes < 1024) {
      return `${kilobytes.toFixed(0)} Ko`;
    }

    return `${(kilobytes / 1024).toFixed(1)} Mo`;
  }
}
