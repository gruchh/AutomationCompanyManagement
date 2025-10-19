export * from './employeeManagement.service';
import { EmployeeManagementApi } from './employeeManagement.service';
export * from './messages.service';
import { MessagesApi } from './messages.service';
export const APIS = [EmployeeManagementApi, MessagesApi];
