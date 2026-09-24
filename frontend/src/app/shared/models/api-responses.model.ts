import { Expense, Project, Stage } from './dashboard.model';

export interface ProjectResponse {
  id: string;
  name: string;
  description: string | null;
  type: string;
  address: string;
  city: string;
  initialBudget: number | string | null;
  startDate: string | null;
  estimatedEndDate: string | null;
  status: Project['status'];
  progress: number | null;
}

export interface StageResponse {
  id: string;
  title: string;
  description: string | null;
  plannedStartDate: string | null;
  plannedEndDate: string | null;
  plannedBudget: number | string | null;
  progress: number | null;
  status: Stage['status'];
  rejectionComment: string | null;
}

export interface ExpenseResponse {
  id: string;
  label: string;
  amount: number | string | null;
  expenseDate: string | null;
  category: string;
  provider: string | null;
  paymentStatus: Expense['status'];
}

export interface ActivityResponse {
  id: string;
  type: string;
  description: string;
  occurredAt: string | null;
}

export interface DocumentResponse {
  id: string;
  originalName: string;
  size: number;
  type: string;
  addedAt: string | null;
}
