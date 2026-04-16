import { CommonModule } from '@angular/common';
import { Component, computed, input } from '@angular/core';
import { ProjectCardDto } from '../../../dashboard/projects/service/generated';

type Status = ProjectCardDto.StatusEnum;
type Tech = ProjectCardDto.TechnologiesEnum;

@Component({
  selector: 'app-project-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './project-card.html',
})
export class ProjectCard {

  public project = input.required<ProjectCardDto>();
  public statusInfo = computed(() => {
    const status = this.project().status as Status;

    switch (status) {
      case ProjectCardDto.StatusEnum.Planning:
        return { text: 'Planowanie', class: 'bg-yellow-100 text-yellow-800' };

      case ProjectCardDto.StatusEnum.InProgress:
        return { text: 'W toku', class: 'bg-blue-100 text-blue-800' };

      case ProjectCardDto.StatusEnum.OnHold:
        return { text: 'Wstrzymany', class: 'bg-orange-100 text-orange-800' };

      case ProjectCardDto.StatusEnum.Completed:
        return { text: 'Zakończony', class: 'bg-green-100 text-green-800' };

      case ProjectCardDto.StatusEnum.Cancelled:
        return { text: 'Anulowany', class: 'bg-red-100 text-red-800' };

      default:
        return { text: 'Nieznany', class: 'bg-gray-100 text-gray-800' };
    }
  });

  // ======================================================
  // LOGO / AVATAR
  // ======================================================
  public companyLogo = computed(() => {
    const name = this.project().name ?? 'PR';

    const colors = [
      { bg: 'E9D5FF', text: '6B21A8' },
      { bg: 'DBEAFE', text: '1E40AF' },
      { bg: 'FEE2E2', text: 'B91C1C' },
      { bg: 'D1FAE5', text: '065F46' },
      { bg: 'FEF3C7', text: '92400E' },
    ];

    const initials = name
      .split(' ')
      .map(w => w.charAt(0))
      .join('')
      .substring(0, 2)
      .toUpperCase();

    const color = colors[name.length % colors.length];

    return `https://placehold.co/120x120/${color.bg}/${color.text}?text=${initials}`;
  });

  public formattedTechnologies = computed(() => {
    const techs = this.project().technologies ?? [];

    return techs.map((tech: Tech) =>
      tech
        .replace(/_/g, ' ')
        .split(' ')
        .map(w => w.charAt(0) + w.slice(1).toLowerCase())
        .join(' ')
    );
  });

  public timeSinceStart = computed(() => {
    const startDateStr = this.project().startDate;
    if (!startDateStr) return 'Brak daty';

    const startDate = new Date(startDateStr + 'T00:00:00');
    const now = new Date();

    const diffDays = Math.ceil(
      Math.abs(now.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24)
    );

    if (diffDays <= 1) return 'Rozpoczęty dzisiaj';
    if (diffDays <= 30) return `Rozpoczęty ${diffDays} dni temu`;

    const months = Math.floor(diffDays / 30);
    if (months < 12) return `Rozpoczęty ${months} mies. temu`;

    const years = Math.floor(months / 12);
    return years === 1
      ? 'Rozpoczęty rok temu'
      : `Rozpoczęty ${years} lata temu`;
  });
}