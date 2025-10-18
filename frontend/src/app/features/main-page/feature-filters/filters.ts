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
    status: 'all'
  };

  public currentSort = 'startDate-desc';

  public statusOptions: { value: ProjectFilters['status'], label: string }[] = [
    { value: 'all', label: 'Wszystkie' },
    { value: 'in_progress', label: 'W toku' },
    { value: 'completed', label: 'Zakończone' },
    { value: 'new', label: 'Nowe' }
  ];

  public selectStatus(status: ProjectFilters['status']): void {
    this.currentFilters.status = status;
    this.onFiltersChanged();
  }

  public onFiltersChanged(): void {
    this.filtersChange.emit({ ...this.currentFilters });
  }

  public onSortChanged(): void {
    this.sortChange.emit(this.currentSort);
  }
}
