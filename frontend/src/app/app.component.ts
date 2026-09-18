import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'dar-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent {
}
