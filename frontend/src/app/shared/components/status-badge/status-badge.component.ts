import { ChangeDetectionStrategy, Component, input } from '@angular/core';

@Component({
  selector: 'dar-status-badge',
  standalone: true,
  templateUrl: './status-badge.component.html',
  styleUrl: './status-badge.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class StatusBadgeComponent {
  readonly status = input.required<string>();
  readonly label = input.required<string>();
}
