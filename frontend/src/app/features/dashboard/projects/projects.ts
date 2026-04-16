import { CommonModule } from '@angular/common';
import { Component, computed, effect, HostListener, inject, OnInit, signal } from '@angular/core';
import { finalize } from 'rxjs/operators';
import {
  AlertCircle,
  Briefcase,
  CheckCircle,
  Clock,
  Edit,
  Eye,
  LucideAngularModule,
  Plus,
  Trash2,
  TrendingUp,
  Users,
} from 'lucide-angular';

import { ProjectCardDto, ProjectFilterDto, ProjectControllerApi } from './service/generated';

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
    active: this.projects().filter((p) => p.status === 'IN_PROGRESS').length,
    completed: this.projects().filter((p) => p.status === 'COMPLETED').length,
  }));

  ngOnInit(): void {
    this.loadProjects();
  }

  private loadProjects(): void {
    this.isLoading.set(true);

    const filter: ProjectFilterDto = {
      sortBy: 'startDate',
    };

    this.api
      .searchProjectCards({ projectFilterDto: filter })
      .pipe(finalize(() => this.isLoading.set(false)))
      .subscribe({
        next: (projects) => {
          this.projects.set(projects.map((p) => ({ ...p, selected: false })));
        },
        error: (err) => console.error('Load projects error', err),
      });
  }

  toggleProject(p: ProjectCardDto & { selected?: boolean }): void {
    this.projects.update((list) =>
      list.map((x) => (x.id === p.id ? { ...x, selected: !x.selected } : x)),
    );
  }

  toggleMenu(id?: number, e?: Event): void {
    e?.stopPropagation();
    this.openMenuId.set(this.openMenuId() === id ? null : (id ?? null));
  }

  closeMenu(): void {
    this.openMenuId.set(null);
  }

  @HostListener('document:click')
  onClick(): void {
    this.closeMenu();
  }

  getInitials(p: ProjectCardDto): string {
    if (!p.name) return '??';
    const w = p.name.split(' ');
    return (w[0]?.[0] ?? '') + (w[1]?.[0] ?? '');
  }

  formatStatus(s?: any): string {
    if (!s) return '-';

    const map: Record<any, string> = {
      PLANNING: 'Planowanie',
      IN_PROGRESS: 'W trakcie',
      ON_HOLD: 'Wstrzymany',
      COMPLETED: 'Zakończony',
      CANCELLED: 'Anulowany',
    };

    return map[s] ?? s;
  }

  formatTech(t?: string[]): string {
    if (!t?.length) return '-';
    if (t.length <= 3) return t.join(', ');
    return `${t.slice(0, 3).join(', ')} +${t.length - 3}`;
  }
}
