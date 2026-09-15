import { ChangeDetectionStrategy, Component, input } from '@angular/core';

@Component({
  selector: 'dar-metric-card',
  standalone: true,
  templateUrl: './metric-card.component.html',
  styleUrl: './metric-card.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class MetricCardComponent {
  readonly label = input.required<string>();
  readonly value = input.required<string | number | null>();
  readonly unit = input('');
  readonly detail = input('');
}
