export type ProjectStatus = 'DRAFT' | 'IN_PROGRESS' | 'ON_HOLD' | 'COMPLETED' | 'CANCELLED';
export type StageStatus = 'TODO' | 'IN_PROGRESS' | 'PENDING_APPROVAL' | 'APPROVED' | 'REJECTED' | 'BLOCKED';
export type ExpenseStatus = 'PENDING' | 'PAID' | 'CANCELLED';

export interface Project {
  id: string;
  name: string;
  description: string;
  propertyType: string;
  city: string;
  budget: number;
  spent: number;
  startDate: string;
  estimatedEndDate: string;
  status: ProjectStatus;
  progress: number;
  address: string;
}

export interface Stage {
  id: string;
  title: string;
  description: string;
  plannedStart: string;
  plannedEnd: string;
  plannedBudget: number;
  actualCost: number;
  progress: number;
  status: StageStatus;
  comment?: string;
}

export interface Expense {
  id: string;
  label: string;
  amount: number;
  date: string;
  category: string;
  status: ExpenseStatus;
  vendor: string;
}

export interface PhotoItem {
  id: string;
  title: string;
  date: string;
  category: string;
  url: string;
}

export interface DocumentItem {
  id: string;
  title: string;
  type: string;
  date: string;
  size: string;
}

export interface Activity {
  id: string;
  label: string;
  date: string;
  detail: string;
}

export interface DashboardState {
  loading: boolean;
  error: string | null;
  projects: Project[];
  selectedProjectId: string | null;
  project: Project | null;
  stages: Stage[];
  activities: Activity[];
  expenses: Expense[];
  photos: PhotoItem[];
  documents: DocumentItem[];
}

export interface DashboardViewModel {
  projects: Project[];
  selectedProjectId: string | null;
  project: Project | null;
  stages: Stage[];
  activities: Activity[];
  expenses: Expense[];
  photos: PhotoItem[];
  documents: DocumentItem[];
  totalSpent: number;
  remainingBudget: number;
  completedStages: number;
  pendingApproval: number;
}
