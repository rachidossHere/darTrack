import { ChangeDetectionStrategy, Component, output, input } from '@angular/core';

export type DashboardSection = 'dashboard' | 'stages' | 'expenses' | 'documents' | 'history';

@Component({
  selector: 'dar-dashboard-sidebar',
  standalone: true,
  templateUrl: './dashboard-sidebar.component.html',
  styleUrl: './dashboard-sidebar.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DashboardSidebarComponent {
  readonly section = input.required<DashboardSection>();
  readonly navigate = output<DashboardSection>();
  readonly navigation = [
    { section: 'dashboard' as const, label: "Vue d'ensemble" },
    { section: 'stages' as const, label: 'Étapes' },
    { section: 'expenses' as const, label: 'Dépenses' },
    { section: 'documents' as const, label: 'Documents' },
    { section: 'history' as const, label: 'Historique' }
  ];
}
