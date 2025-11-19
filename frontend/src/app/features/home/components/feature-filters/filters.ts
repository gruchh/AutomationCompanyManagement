import { Component, output, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ProjectFilters } from '../../models/project-filters';

@Component({
  selector: 'app-filters',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './filters.html'
})
export class Filters {
  filtersChange = output<ProjectFilters>();
  sortChange = output<string>();

  currentFilters = signal<ProjectFilters>({
    searchTerm: '',
    status: 'all',
    serviceType: null,
    priority: null,
    location: '',
  });

  currentSort = signal('startDate-desc');

  statusOptions: { value: ProjectFilters['status']; label: string }[] = [
    { value: 'all', label: 'Wszystkie' },
    { value: 'in_progress', label: 'W toku' },
    { value: 'completed', label: 'Zakończone' },
    { value: 'new', label: 'Nowe' },
  ];

  serviceTypeOptions = [
    { value: null, label: 'Wszystkie' },
    { value: 'PRODUCTION_SUPPORT', label: 'Wsparcie produkcji' },
    { value: 'MACHINE_DESIGN', label: 'Projektowanie maszyn' },
    { value: 'MACHINE_REALIZATION', label: 'Realizacja maszyn' },
    { value: 'ELECTRICAL_DESIGN', label: 'Projektowanie elektryczne' },
    { value: 'ELECTRICAL_WORKS', label: 'Prace elektryczne' },
    { value: 'HYDRAULICS', label: 'Hydraulika' },
  ];

  priorityOptions = [
    { value: null, label: 'Wszystkie' },
    { value: 'LOW', label: 'Niski' },
    { value: 'MEDIUM', label: 'Średni' },
    { value: 'HIGH', label: 'Wysoki' },
    { value: 'CRITICAL', label: 'Krytyczny' },
  ];

  selectStatus = (status: ProjectFilters['status']) => {
    this.currentFilters.update((f) => ({ ...f, status }));
    this.emitFilters();
  };

  selectServiceType = (serviceType: string | null) => {
    this.currentFilters.update((f) => ({ ...f, serviceType }));
    this.emitFilters();
  };

  selectPriority = (priority: string | null) => {
    this.currentFilters.update((f) => ({ ...f, priority }));
    this.emitFilters();
  };

  onLocationChanged = () => {
    this.emitFilters();
  };

  emitFilters = () => {
    this.filtersChange.emit(this.currentFilters());
  };

  onSortChanged = () => {
    this.sortChange.emit(this.currentSort());
  };

  resetFilters = () => {
    this.currentFilters.set({
      searchTerm: '',
      status: 'all',
      serviceType: null,
      priority: null,
      location: '',
    });
    this.currentSort.set('startDate-desc');
    this.emitFilters();
    this.onSortChanged();
  };
}
