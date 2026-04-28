import { CommonModule } from '@angular/common';
import { Component, computed, HostListener, inject, OnInit, signal } from '@angular/core';
import { finalize } from 'rxjs/operators';
import { AlertCircle, Briefcase, CheckCircle, Clock, Edit, Eye, LucideAngularModule, Plus, Trash2, TrendingUp, Users } from 'lucide-angular';
import { ProjectCardDto, ProjectFilterDto, ProjectControllerApi } from './service/generated';

const STATUS_MAP: Record<string, string> = {
  PLANNING: 'Planowanie',
  IN_PROGRESS: 'W trakcie',
  ON_HOLD: 'Wstrzymany',
  COMPLETED: 'Zakończony',
  CANCELLED: 'Anulowany',
};

const STATUS_BADGE: Record<string, string> = {
  PLANNING: 'bg-yellow-100 text-yellow-800',
  IN_PROGRESS: 'bg-blue-100 text-blue-800',
  ON_HOLD: 'bg-gray-100 text-gray-800',
  COMPLETED: 'bg-green-100 text-green-800',
  CANCELLED: 'bg-red-100 text-red-800',
};

@Component({
  selector: 'app-projects',
  standalone: true,
  imports: [CommonModule, LucideAngularModule],
  templateUrl: './projects.html',
})
export class Projects implements OnInit {
  private api = inject(ProjectControllerApi);

  readonly PlusIcon = Plus;
  readonly EyeIcon = Eye;
  readonly EditIcon = Edit;
  readonly TrendingUpIcon = TrendingUp;
  readonly Trash2Icon = Trash2;
  readonly BriefcaseIcon = Briefcase;
  readonly CheckCircleIcon = CheckCircle;
  readonly ClockIcon = Clock;
  readonly AlertCircleIcon = AlertCircle;
  readonly UsersIcon = Users;

  openMenuId = signal<number | null>(null);
  isLoading = signal(false);
  projects = signal<(ProjectCardDto & { selected?: boolean })[]>([]);

  stats = computed(() => ({
    total: this.projects().length,
    active: this.projects().filter(p => p.status === 'IN_PROGRESS').length,
    completed: this.projects().filter(p => p.status === 'COMPLETED').length,
  }));

  ngOnInit(): void {
    this.isLoading.set(true);
    this.api
      .searchProjectCards({ projectFilterDto: { sortBy: 'startDate' } as ProjectFilterDto })
      .pipe(finalize(() => this.isLoading.set(false)))
      .subscribe({
        next: (projects) => this.projects.set(projects.map(p => ({ ...p, selected: false }))),
        error: (err) => console.error('Load projects error', err),
      });
  }

  toggleProject(p: ProjectCardDto & { selected?: boolean }): void {
    this.projects.update(list =>
      list.map(x => x.id === p.id ? { ...x, selected: !x.selected } : x)
    );
  }

  toggleMenu(id?: number, e?: Event): void {
    e?.stopPropagation();
    this.openMenuId.set(this.openMenuId() === id ? null : (id ?? null));
  }

  @HostListener('document:click')
  closeMenu(): void {
    this.openMenuId.set(null);
  }

  getInitials(name = ''): string {
    const words = name.trim().split(/\s+/);
    return ((words[0]?.[0] ?? '') + (words[1]?.[0] ?? '')).toUpperCase();
  }

  getStatusLabel(status?: string): string {
    return status ? (STATUS_MAP[status] ?? status) : '-';
  }

  getStatusBadge(status?: string): string {
    return status ? (STATUS_BADGE[status] ?? 'bg-gray-100 text-gray-800') : 'bg-gray-100 text-gray-800';
  }

  formatTech(tech?: string[]): string {
    if (!tech?.length) return '-';
    if (tech.length <= 3) return tech.join(', ');
    return `${tech.slice(0, 3).join(', ')} +${tech.length - 3}`;
  }
}