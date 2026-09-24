import { ChangeDetectionStrategy, Component, signal, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { ProjectFormDialogComponent } from './shared/components/dialogs/project-form-dialog.component';

@Component({
  selector: 'dar-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent {
  readonly mobileNavOpen = signal(false);
  private readonly dialog = inject(MatDialog);

  toggleMenu(): void {
    this.mobileNavOpen.update((value) => !value);
  }

  closeMenu(): void {
    this.mobileNavOpen.set(false);
  }

  openNewStep(): void {
    this.dialog.open(ProjectFormDialogComponent, {
      data: { title: 'Créer une étape' },
      width: '560px'
    });
  }
}
