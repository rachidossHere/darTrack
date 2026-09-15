export type ProjectStatus = 'DRAFT' | 'IN_PROGRESS' | 'ON_HOLD' | 'COMPLETED' | 'CANCELLED';
export type ProjectType = 'APARTMENT' | 'HOUSE' | 'VILLA' | 'COMMERCIAL' | 'OTHER';
export type StageStatus = 'TODO' | 'IN_PROGRESS' | 'PENDING_APPROVAL' | 'APPROVED' | 'REJECTED' | 'BLOCKED';
export type ExpenseCategory = 'MATERIALS' | 'LABOR' | 'TRANSPORT' | 'EQUIPMENT' | 'ADMINISTRATIVE' | 'OTHER';
export type PaymentStatus = 'PENDING' | 'PAID' | 'CANCELLED';
export type DocumentType = 'SITE_PHOTO' | 'QUOTE' | 'INVOICE' | 'OTHER';
export interface Project { id: string; name: string; description?: string; type: ProjectType; address: string; city: string; initialBudget: number; startDate?: string; estimatedEndDate?: string; status: ProjectStatus; progress: number; createdAt: string; updatedAt: string; }
export interface Stage { id: string; projectId: string; title: string; description?: string; displayOrder: number; plannedStartDate?: string; plannedEndDate?: string; plannedBudget: number; progress: number; status: StageStatus; rejectionComment?: string; createdAt: string; updatedAt: string; }
export interface Expense { id: string; projectId: string; stageId?: string; label: string; amount: number; expenseDate: string; category: ExpenseCategory; provider?: string; reference?: string; paymentStatus: PaymentStatus; createdAt: string; updatedAt: string; }
export interface DocumentItem { id: string; projectId: string; stageId?: string; originalName: string; mimeType: string; size: number; type: DocumentType; addedAt: string; }
export interface Activity { id: string; projectId: string; stageId?: string; type: string; description: string; occurredAt: string; }

export interface CreateProjectRequest {
	name: string;
	description?: string;
	type: ProjectType;
	address: string;
	city: string;
	initialBudget: number;
	status: ProjectStatus;
}

export interface CreateStageRequest {
	title: string;
	description: string;
	displayOrder: number;
	plannedBudget: number;
	progress: number;
}

export interface CreateExpenseRequest {
	label: string;
	amount: number;
	expenseDate: string;
	category: ExpenseCategory;
	provider: string;
	paymentStatus: PaymentStatus;
}