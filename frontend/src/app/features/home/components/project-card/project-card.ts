import { CommonModule } from '@angular/common';
import { Component, computed, input } from '@angular/core';
import { Project } from '../../models/project.model';
import { ProjectCardDto } from '../../../dashboard/projects/generated/employee';

@Component({
  selector: 'app-project-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './project-card.html'
})
export class ProjectCard {

  public project = input.required<ProjectCardDto>();

  public statusInfo = computed(() => {
    switch (this.project().status) {
      case 'PLANNING':
        return { text: 'Planowanie', class: 'bg-yellow-100 text-yellow-800' };
      case 'IN_PROGRESS':
        return { text: 'W toku', class: 'bg-blue-100 text-blue-800' };
      case 'ON_HOLD':
        return { text: 'Wstrzymany', class: 'bg-orange-100 text-orange-800' };
      case 'COMPLETED':
        return { text: 'Zakończony', class: 'bg-green-100 text-green-800' };
      case 'CANCELLED':
        return { text: 'Anulowany', class: 'bg-red-100 text-red-800' };
      default:
        return { text: 'Nieznany', class: 'bg-gray-100 text-gray-800' };
    }
  });

  public companyLogo = computed(() => {
    const companyName = this.project().name;
    if (!companyName) return 'https://placehold.co/120x120/E5E7EB/6B7280?text=?';

    const colors = [
      { bg: 'E9D5FF', text: '6B21A8' },
      { bg: 'DBEAFE', text: '1E40AF' },
      { bg: 'FEE2E2', text: 'B91C1C' },
      { bg: 'D1FAE5', text: '065F46' },
      { bg: 'FEF3C7', text: '92400E' }
    ];

    const initials = companyName
      .split(' ')
      .map(word => word.charAt(0))
      .join('')
      .substring(0, 2)
      .toUpperCase();

    const colorIndex = companyName.length % colors.length;
    const color = colors[colorIndex];

    return `https://placehold.co/120x120/${color.bg}/${color.text}?text=${initials}`;
  });

  public formattedTechnologies = computed(() => {
    return this.project().technologies?.map(tech =>
      tech.replace(/_/g, ' ')
        .split(' ')
        .map(word => word.charAt(0) + word.slice(1).toLowerCase())
        .join(' ')
    ) ?? [];
  });

  public timeSinceStart = computed(() => {
    const startDateStr = this.project().startDate;
    if (!startDateStr) return 'Brak daty';

    const startDate = new Date(startDateStr);
    const now = new Date();
    const diffTime = Math.abs(now.getTime() - startDate.getTime());
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

    if (diffDays <= 1) return 'Rozpoczęty dzisiaj';
    if (diffDays <= 30) return `Rozpoczęty ${diffDays} dni temu`;
    const diffMonths = Math.floor(diffDays / 30);
    if (diffMonths < 12) return `Rozpoczęty ${diffMonths} mies. temu`;
    const diffYears = Math.floor(diffMonths / 12);
    return diffYears === 1 ? 'Rozpoczęty rok temu' : `Rozpoczęty ${diffYears} lata temu`;
  });
}
