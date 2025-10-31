export interface Project {
    id: string | number;
    title: string;
    client: {
      name: string;
      logoUrl: string;
    };
    location: string;
    teamSize: number;
    startDate: Date;
    technologies: string[];
    status: 'new' | 'in_progress' | 'completed';
  }