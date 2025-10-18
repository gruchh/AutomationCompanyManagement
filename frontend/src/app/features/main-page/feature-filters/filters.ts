  import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ProjectFilters } from '../../../core/models/project-filters';

@Component({
  selector: 'app-filters',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './filters.html'
})
export class Filters {

  @Output() filtersChange = new EventEmitter<ProjectFilters>();
  @Output() sortChange = new EventEmitter<string>();

  public currentFilters: ProjectFilters = {
    searchTerm: '',
    status: 'all',
    serviceType: null,
    priority: null,
    location: ''
  };

  public currentSort = 'startDate-desc';

  public statusOptions: { value: ProjectFilters['status'], label: string }[] = [
    { value: 'all', label: 'Wszystkie' },
    { value: 'in_progress', label: 'W toku' },
    { value: 'completed', label: 'Zakończone' },
    { value: 'new', label: 'Nowe' }
  ];

  public serviceTypeOptions = [
    { value: null, label: 'Wszystkie' },
    { value: 'PRODUCTION_SUPPORT', label: 'Wsparcie produkcji' },
    { value: 'MACHINE_DESIGN', label: 'Projektowanie maszyn' },
    { value: 'MACHINE_REALIZATION', label: 'Realizacja maszyn' },
    { value: 'ELECTRICAL_DESIGN', label: 'Projektowanie elektryczne' },
    { value: 'ELECTRICAL_WORKS', label: 'Prace elektryczne' },
    { value: 'HYDRAULICS', label: 'Hydraulika' }
  ];

  public priorityOptions = [
    { value: null, label: 'Wszystkie' },
    { value: 'LOW', label: 'Niski' },
    { value: 'MEDIUM', label: 'Średni' },
    { value: 'HIGH', label: 'Wysoki' },
    { value: 'CRITICAL', label: 'Krytyczny' }
  ];

  public selectStatus(status: ProjectFilters['status']): void {
    this.currentFilters.status = status;
    this.onFiltersChanged();
  }

  public selectServiceType(serviceType: string | null): void {
    this.currentFilters.serviceType = serviceType;
    this.onFiltersChanged();
  }

  public selectPriority(priority: string | null): void {
    this.currentFilters.priority = priority;
    this.onFiltersChanged();
  }

  public onLocationChanged(): void {
    this.onFiltersChanged();
  }

  public onFiltersChanged(): void {
    this.filtersChange.emit({ ...this.currentFilters });
  }

  public onSortChanged(): void {
    this.sortChange.emit(this.currentSort);
  }

  public resetFilters(): void {
    this.currentFilters = {
      searchTerm: '',
      status: 'all',
      serviceType: null,
      priority: null,
      location: ''
    };
    this.currentSort = 'startDate-desc';
    this.onFiltersChanged();
    this.onSortChanged();
  }
}
