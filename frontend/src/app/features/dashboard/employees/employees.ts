import { Component, HostListener, signal, computed, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  LucideAngularModule,
  Upload,
  Download,
  Plus,
  Search,
  Filter,
  Eye,
  Edit,
  TrendingUp,
  Trash2,
  Users,
  UserPlus,
  Calendar,
  Building,
} from 'lucide-angular';
import {
  EmployeeReadDto,
  EmployeeReadDtoPositionLevelEnum,
  EmployeeReadDtoDepartmentEnum,
  EmployeeReadDtoEmploymentTypeEnum,
  EmployeeReadDtoStatusEnum
} from './service/generated/employee';

@Component({
  selector: 'app-employees',
  standalone: true,
  imports: [CommonModule, LucideAngularModule],
  templateUrl: './employees.html',
})
export class Employees {
  readonly UploadIcon = Upload;
  readonly DownloadIcon = Download;
  readonly PlusIcon = Plus;
  readonly SearchIcon = Search;
  readonly FilterIcon = Filter;
  readonly EyeIcon = Eye;
  readonly EditIcon = Edit;
  readonly TrendingUpIcon = TrendingUp;
  readonly Trash2Icon = Trash2;
  readonly UsersIcon = Users;
  readonly UserPlusIcon = UserPlus;
  readonly CalendarIcon = Calendar;
  readonly BuildingIcon = Building;

  openMenuId = signal<number | null>(null);

  stats = signal({
    totalEmployees: 134,
    changeFromLastMonth: 2,
    newHires: 5,
    newHiresChange: 2,
    averageTenure: 2.8,
    tenureChange: 1.2,
    activeDepartments: 7,
    departmentsChange: -1,
  });

  employees = signal<(EmployeeReadDto & { selected?: boolean })[]>([
    {
      id: 1,
      firstName: 'John',
      lastName: 'Doe',
      email: 'john.doe@company.com',
      phoneNumber: '+48 123 456 789',
      department: EmployeeReadDtoDepartmentEnum.MECHANICAL,
      positionLevel: EmployeeReadDtoPositionLevelEnum.MANAGER,
      employmentType: EmployeeReadDtoEmploymentTypeEnum.FULL_TIME,
      status: EmployeeReadDtoStatusEnum.ACTIVE,
      hireDate: '2021-03-15',
      salary: 8500,
    },
    {
      id: 2,
      firstName: 'Maria',
      lastName: 'Tan',
      email: 'maria.tan@company.com',
      phoneNumber: '+48 234 567 890',
      department: EmployeeReadDtoDepartmentEnum.FINANCE,
      positionLevel: EmployeeReadDtoPositionLevelEnum.SENIOR,
      employmentType: EmployeeReadDtoEmploymentTypeEnum.FULL_TIME,
      status: EmployeeReadDtoStatusEnum.ON_LEAVE,
      hireDate: '2020-07-22',
      salary: 7200,
      selected: true,
    },
    {
      id: 3,
      firstName: 'Charlie',
      lastName: 'Brown',
      email: 'charlie.brown@company.com',
      phoneNumber: '+48 345 678 901',
      department: EmployeeReadDtoDepartmentEnum.SOFTWARE,
      positionLevel: EmployeeReadDtoPositionLevelEnum.MID,
      employmentType: EmployeeReadDtoEmploymentTypeEnum.FULL_TIME,
      status: EmployeeReadDtoStatusEnum.ACTIVE,
      hireDate: '2022-01-10',
      salary: 6500,
    },
    {
      id: 4,
      firstName: 'Dana',
      lastName: 'White',
      email: 'dana.white@company.com',
      phoneNumber: '+48 456 789 012',
      department: EmployeeReadDtoDepartmentEnum.HR,
      positionLevel: EmployeeReadDtoPositionLevelEnum.MID,
      employmentType: EmployeeReadDtoEmploymentTypeEnum.FULL_TIME,
      status: EmployeeReadDtoStatusEnum.ACTIVE,
      hireDate: '2021-11-05',
      salary: 5800,
    },
    {
      id: 5,
      firstName: 'Ethan',
      lastName: 'Hunt',
      email: 'ethan.hunt@company.com',
      phoneNumber: '+48 567 890 123',
      department: EmployeeReadDtoDepartmentEnum.ELECTRICAL,
      positionLevel: EmployeeReadDtoPositionLevelEnum.SENIOR,
      employmentType: EmployeeReadDtoEmploymentTypeEnum.FULL_TIME,
      status: EmployeeReadDtoStatusEnum.ACTIVE,
      hireDate: '2021-06-20',
      salary: 7500,
      selected: true,
    },
    {
      id: 6,
      firstName: 'Fiona',
      lastName: 'Glenanne',
      email: 'fiona.g@company.com',
      phoneNumber: '+48 678 901 234',
      department: EmployeeReadDtoDepartmentEnum.FINANCE,
      positionLevel: EmployeeReadDtoPositionLevelEnum.JUNIOR,
      employmentType: EmployeeReadDtoEmploymentTypeEnum.FULL_TIME,
      status: EmployeeReadDtoStatusEnum.TERMINATED,
      hireDate: '2018-05-10',
      terminationDate: '2024-03-15',
      salary: 4500,
    },
    {
      id: 7,
      firstName: 'George',
      lastName: 'Lucas',
      email: 'george.lucas@company.com',
      phoneNumber: '+48 789 012 345',
      department: EmployeeReadDtoDepartmentEnum.SOFTWARE,
      positionLevel: EmployeeReadDtoPositionLevelEnum.LEAD,
      employmentType: EmployeeReadDtoEmploymentTypeEnum.CONTRACT,
      status: EmployeeReadDtoStatusEnum.ON_LEAVE,
      hireDate: '2021-02-22',
      salary: 9200,
    },
    {
      id: 8,
      firstName: 'Hannah',
      lastName: 'Montana',
      email: 'hannah.montana@company.com',
      phoneNumber: '+48 890 123 456',
      department: EmployeeReadDtoDepartmentEnum.AUTOMATION,
      positionLevel: EmployeeReadDtoPositionLevelEnum.MID,
      employmentType: EmployeeReadDtoEmploymentTypeEnum.FULL_TIME,
      status: EmployeeReadDtoStatusEnum.ACTIVE,
      hireDate: '2020-09-18',
      salary: 6800,
      selected: true,
    },
    {
      id: 9,
      firstName: 'Hannah',
      lastName: 'Montana',
      email: 'hannah.montana@company.com',
      phoneNumber: '+48 890 123 456',
      department: EmployeeReadDtoDepartmentEnum.AUTOMATION,
      positionLevel: EmployeeReadDtoPositionLevelEnum.MID,
      employmentType: EmployeeReadDtoEmploymentTypeEnum.FULL_TIME,
      status: EmployeeReadDtoStatusEnum.ACTIVE,
      hireDate: '2020-09-18',
      salary: 6800,
      selected: true,
    },
  ]);

  constructor() {
    effect(() => {
      console.log('Employees updated:', this.employees());
    });
  }

  @HostListener('document:click')
  onDocumentClick(): void {
    this.closeMenu();
  }

  getInitials(employee: EmployeeReadDto): string {
    const first = employee.firstName?.charAt(0) ?? '';
    const last = employee.lastName?.charAt(0) ?? '';
    return (first + last).toUpperCase();
  }

  private formatLabel(value?: string): string {
    if (!value) return '-';
    return value
      .replace(/_/g, ' ')
      .toLowerCase()
      .replace(/\b\w/g, (l) => l.toUpperCase());
  }

  formatDepartment = this.formatLabel;
  formatPositionLevel = this.formatLabel;
  formatEmploymentType = this.formatLabel;
  formatStatus = this.formatLabel;

  toggleMenu(employeeId?: number, event?: Event): void {
    event?.stopPropagation();
    this.openMenuId.set(this.openMenuId() === employeeId ? null : employeeId ?? null);
  }

  closeMenu(): void {
    this.openMenuId.set(null);
  }

  viewProfile(employee: EmployeeReadDto): void {
    console.log('View profile:', employee);
    this.closeMenu();
  }

  editDetails(employee: EmployeeReadDto): void {
    console.log('Edit details:', employee);
    this.closeMenu();
  }

  viewPerformance(employee: EmployeeReadDto): void {
    console.log('View performance:', employee);
    this.closeMenu();
  }

  deleteEmployee(employee: EmployeeReadDto): void {
    if (confirm(`Are you sure you want to delete ${employee.firstName} ${employee.lastName}?`)) {
      console.log('Delete employee:', employee);
      this.closeMenu();
    }
  }

  toggleEmployee(employee: EmployeeReadDto & { selected?: boolean }): void {
    employee.selected = !employee.selected;
    this.employees.update((list) =>
      list.map((e) => (e.id === employee.id ? { ...e, selected: employee.selected } : e))
    );
  }

  addNewEmployee(): void {
    console.log('Add new employee');
  }
}
