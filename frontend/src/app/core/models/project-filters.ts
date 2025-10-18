export interface ProjectFilters {
  searchTerm: string;
  status: 'all' | 'in_progress' | 'completed' | 'new';
  serviceType: string | null;
  priority: string | null;
  location: string;
}
