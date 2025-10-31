import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectCard } from '../project-card/project-card';
import { Project } from '../../models/project.model';

@Component({
  selector: 'app-project-list',
  standalone: true,
  imports: [ProjectCard, CommonModule],
  templateUrl: './project-list.html'
})
export class ProjectList {

  public projects = signal<Project[]>([
    {
      id: 1,
      title: 'Modernizacja Linii Produkcyjnej dla Sektora Automotive',
      client: { name: 'AutoCorp Poland', logoUrl: 'https://placehold.co/120x120/E9D5FF/6B21A8?text=AC' },
      location: 'Poznań, Polska',
      teamSize: 6,
      startDate: new Date('2024-08-15'),
      technologies: ['Siemens S7-1500', 'WinCC Unified', 'KUKA Robotics'],
      status: 'in_progress',
    },
    {
      id: 2,
      title: 'System Sterowania Oczyszczalnią Ścieków',
      client: { name: 'Aqua-System', logoUrl: 'https://placehold.co/120x120/DBEAFE/1E40AF?text=AS' },
      location: 'Gdańsk, Polska',
      teamSize: 4,
      startDate: new Date('2023-01-20'),
      technologies: ['Allen Bradley ControlLogix', 'FactoryTalk View', 'SCADA'],
      status: 'completed',
    },
  ]);
}
