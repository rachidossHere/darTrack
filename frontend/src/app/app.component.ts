import { ChangeDetectionStrategy, Component } from '@angular/core';
import { DashboardComponent } from '../features/dashboard/dashboard/dashboard.component';

@Component({
  selector: 'dar-root',
  standalone: true,
  imports: [DashboardComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent {
}
