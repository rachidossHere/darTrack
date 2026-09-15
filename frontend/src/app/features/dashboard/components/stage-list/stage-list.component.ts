import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, input, output } from '@angular/core';
import { Stage } from '../../../../shared/models/models';
import { StatusBadgeComponent } from '../../../../shared/components/status-badge/status-badge.component';
import { StatusLabelPipe } from '../../../../shared/pipes/status-label.pipe';

@Component({
  selector: 'dar-stage-list',
  standalone: true,
  imports: [CommonModule, StatusBadgeComponent, StatusLabelPipe],
  templateUrl: './stage-list.component.html',
  styleUrl: './stage-list.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class StageListComponent {
  readonly stages = input.required<Stage[]>();
  readonly detailed = input(false);
  readonly submit = output<Stage>();
  readonly approve = output<Stage>();
  readonly reject = output<Stage>();
}
