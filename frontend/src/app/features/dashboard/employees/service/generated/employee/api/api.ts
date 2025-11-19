export * from './employee-management.service';
import { EmployeeManagementApi } from './employee-management.service';
export * from './employee-management.serviceInterface';
export * from './messages.service';
import { MessagesApi } from './messages.service';
export * from './messages.serviceInterface';
export const APIS = [EmployeeManagementApi, MessagesApi];
