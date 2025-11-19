import { Component, HostListener, signal, computed, effect, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  LucideAngularModule,
  Plus,
  Eye,
  Edit,
  TrendingUp,
  Trash2,
  Briefcase,
  CheckCircle,
  Clock,
  AlertCircle,
  Users,
} from 'lucide-angular';
import { ProjectCardDto, ProjectCardDtoStatusEnum, ProjectManagementApi, ProjectSummaryDto } from './generated/employee';

@Component({
  selector: 'app-projects',
  standalone: true,
  imports: [CommonModule, LucideAngularModule],
  templateUrl: './projects.html',
})
export class Projects implements OnInit {
  private projectApi = inject(ProjectManagementApi);

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
  projects = signal<(ProjectCardDto & { selected?: boolean })[]>([]);
  isLoading = signal<boolean>(true);

  // Statystyki z API
  summary = signal<ProjectSummaryDto>({
    totalProjects: 0,
    activeProjects: 0,
    completedProjects: 0,
    projectsPastDeadline: 0,
    averageProjectDurationDays: 0
  });

  // Statystyki obliczane lokalnie
  stats = computed(() => {
    const projs = this.projects();
    const sum = this.summary();

    return {
      totalProjects: sum.totalProjects || 0,
      activeProjects: sum.activeProjects || 0,
      completedProjects: sum.completedProjects || 0,
      projectsPastDeadline: sum.projectsPastDeadline || 0,
      averageProjectDurationDays: sum.averageProjectDurationDays
        ? Math.round(sum.averageProjectDurationDays)
        : 0
    };
  });

  constructor() {
    effect(() => {
      console.log('Projects updated:', this.projects());
    });
  }

  ngOnInit(): void {
    this.loadProjects();
    this.loadSummary();
  }

  private loadProjects(): void {
    this.isLoading.set(true);

    this.projectApi.filterPublicProjectCardsGet().subscribe({
      next: (projects) => {
        this.projects.set(projects.map(proj => ({ ...proj, selected: false })));
        this.isLoading.set(false);
      },
      error: (error) => {
        console.error('Error loading projects:', error);
        this.isLoading.set(false);
      }
    });
  }

  private loadSummary(): void {
    this.projectApi.getOverallProjectSummary().subscribe({
      next: (summary) => {
        this.summary.set(summary);
      },
      error: (error) => {
        console.error('Error loading summary:', error);
      }
    });
  }

  @HostListener('document:click')
  onDocumentClick(): void {
    this.closeMenu();
  }

  getProjectInitials(project: ProjectCardDto): string {
    if (!project.name) return '??';
    const words = project.name.split(' ');
    if (words.length >= 2) {
      return (words[0].charAt(0) + words[1].charAt(0)).toUpperCase();
    }
    return project.name.substring(0, 2).toUpperCase();
  }

  formatStatus(status?: ProjectCardDtoStatusEnum): string {
    if (!status) return '-';
    const statusMap: Record<ProjectCardDtoStatusEnum, string> = {
      [ProjectCardDtoStatusEnum.PLANNING]: 'Planowanie',
      [ProjectCardDtoStatusEnum.IN_PROGRESS]: 'W trakcie',
      [ProjectCardDtoStatusEnum.ON_HOLD]: 'Wstrzymany',
      [ProjectCardDtoStatusEnum.COMPLETED]: 'Zakończony',
      [ProjectCardDtoStatusEnum.CANCELLED]: 'Anulowany'
    };
    return statusMap[status] || status;
  }

  formatTechnologies(technologies?: Array<string>): string {
    if (!technologies || technologies.length === 0) return '-';
    if (technologies.length <= 3) {
      return technologies.join(', ');
    }
    return `${technologies.slice(0, 3).join(', ')} +${technologies.length - 3}`;
  }

  toggleMenu(projectId?: number, event?: Event): void {
    event?.stopPropagation();
    this.openMenuId.set(this.openMenuId() === projectId ? null : projectId ?? null);
  }

  closeMenu(): void {
    this.openMenuId.set(null);
  }

  viewProject(project: ProjectCardDto): void {
    console.log('View project:', project);
    this.closeMenu();
  }

  editProject(project: ProjectCardDto): void {
    console.log('Edit project:', project);
    this.closeMenu();
  }

  viewDetails(project: ProjectCardDto): void {
    console.log('View details:', project);
    this.closeMenu();
  }

  deleteProject(project: ProjectCardDto): void {
    if (confirm(`Czy na pewno chcesz usunąć projekt "${project.name}"?`)) {
      if (project.id) {
        this.projectApi.deleteProject(project.id).subscribe({
          next: () => {
            console.log('Project deleted:', project);
            this.loadProjects();
            this.loadSummary();
            this.closeMenu();
          },
          error: (error) => {
            console.error('Error deleting project:', error);
            this.closeMenu();
          }
        });
      }
    }
  }

  toggleProject(project: ProjectCardDto & { selected?: boolean }): void {
    project.selected = !project.selected;
    this.projects.update((list) =>
      list.map((p) => (p.id === project.id ? { ...p, selected: project.selected } : p))
    );
  }

  addNewProject(): void {
    console.log('Add new project');
  }
}
