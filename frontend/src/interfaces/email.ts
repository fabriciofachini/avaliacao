import { Cliente } from './cliente';

export interface Email {
  id?: number;
  email: string;
  cliente?: Cliente;
}

