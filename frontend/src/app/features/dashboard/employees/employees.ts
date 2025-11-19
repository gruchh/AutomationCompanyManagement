import { Component, HostListener, signal, computed, effect, OnInit, inject } from '@angular/core';
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
  EmployeeReadDtoStatusEnum,
  EmployeeManagementApi,
} from './service/generated/employee';

@Component({
  selector: 'app-employees',
  standalone: true,
  imports: [CommonModule, LucideAngularModule],
  templateUrl: './employees.html',
})
export class Employees implements OnInit {
  private employeeApi = inject(EmployeeManagementApi);

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
  employees = signal<(EmployeeReadDto & { selected?: boolean })[]>([]);
  isLoading = signal<boolean>(true);

  stats = computed(() => {
    const emps = this.employees();
    const now = new Date();
    const currentYear = now.getFullYear();
    const currentMonth = now.getMonth();

    const newHires = emps.filter((emp) => {
      const hireDate = new Date(emp.hireDate!);
      return hireDate.getFullYear() === currentYear && hireDate.getMonth() === currentMonth;
    }).length;

    const totalTenure = emps.reduce((sum, emp) => {
      const hireDate = new Date(emp.hireDate!);
      const years = (now.getTime() - hireDate.getTime()) / (1000 * 60 * 60 * 24 * 365.25);
      return sum + years;
    }, 0);
    const averageTenure = emps.length > 0 ? (totalTenure / emps.length).toFixed(1) : '0';

    const departments = new Set(emps.map((emp) => emp.department));

    return {
      totalEmployees: emps.length,
      changeFromLastMonth: 2,
      newHires,
      newHiresChange: 2,
      averageTenure,
      tenureChange: 1.2,
      activeDepartments: departments.size,
      departmentsChange: -1,
    };
  });

  constructor() {
    effect(() => {
      console.log('Employees updated:', this.employees());
    });
  }

  ngOnInit(): void {
    this.loadEmployees();
  }

  private loadEmployees(): void {
    this.isLoading.set(true);
    this.employeeApi.getAll().subscribe({
      next: (employees) => {
        this.employees.set(employees.map((emp) => ({ ...emp, selected: false })));
        this.isLoading.set(false);
      },
      error: (error) => {
        console.error('Error loading employees:', error);
        this.isLoading.set(false);
      },
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
    if (employee.id) {
      this.employeeApi._delete(employee.id).subscribe({
        next: () => {
          console.log('Employee deleted:', employee);
          this.loadEmployees();
          this.closeMenu();
        },
        error: (error) => {
          console.error('Error deleting employee:', error);
          this.closeMenu();
        },
      });
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
