export interface ProjectFilters {
  searchTerm: string;
  status: 'all' | 'in_progress' | 'completed' | 'new';
}
