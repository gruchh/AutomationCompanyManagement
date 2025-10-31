import { CommonModule } from '@angular/common';
import { Component, computed, input } from '@angular/core';
import { Project } from '../../models/project.model';

@Component({
  selector: 'app-project-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './project-card.html'
})
export class ProjectCard {

  public project = input.required<Project>();

  public statusInfo = computed(() => {
    switch (this.project().status) {
      case 'in_progress':
        return { text: 'W toku', class: 'bg-blue-100 text-blue-800' };
      case 'completed':
        return { text: 'Zakończony', class: 'bg-green-100 text-green-800' };
      case 'new':
      default:
        return { text: 'Nowy', class: 'bg-purple-100 text-purple-800' };
    }
  });

  public timeSinceStart = computed(() => {
    const startDate = this.project().startDate;
    const now = new Date();
    const diffTime = Math.abs(now.getTime() - startDate.getTime());
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

    if (diffDays <= 1) return 'Rozpoczęty dzisiaj';
    if (diffDays <= 30) return `Rozpoczęty ${diffDays} dni temu`;
    const diffMonths = Math.floor(diffDays / 30);
    if (diffMonths < 12) return `Rozpoczęty ${diffMonths} mies. temu`;
    return `Rozpoczęty ponad rok temu`;
  });
}
