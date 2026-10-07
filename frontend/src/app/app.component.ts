import { ChangeDetectionStrategy, Component, signal, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { Store } from '@ngrx/store';
import { map } from 'rxjs';
import { DashboardState, Project } from './shared/models/dashboard.model';
import { selectDashboardProject } from './store/dashboard/dashboard.selectors';

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
  private readonly store = inject(Store<{ dashboard: DashboardState }>);
  readonly selectedProject$ = this.store.select(selectDashboardProject).pipe(
    map((project) => project ?? null)
  );

  getProjectTitle(project: Project | null): string {
    if (!project) {
      return 'Rénovation';
    }

    const typeLabel = this.normalizePropertyType(project.propertyType);
    return `Rénovation ${typeLabel} ${project.name}`.trim();
  }

  private normalizePropertyType(type: string | null | undefined): string {
    const normalized = (type ?? '').trim().toLowerCase();

    switch (normalized) {
      case 'maison':
        return 'maison';
      case 'villa':
        return 'villa';
      case 'local commercial':
        return 'local commercial';
      case 'autre':
        return 'autre';
      case 'appartement':
      default:
        return 'appartement';
    }
  }

  toggleMenu(): void {
    this.mobileNavOpen.update((value) => !value);
  }

  closeMenu(): void {
    this.mobileNavOpen.set(false);
  }
}
