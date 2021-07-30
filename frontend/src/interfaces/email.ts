import { Cliente } from './cliente';

export interface Email {
  id?: number;
  dsEmail: string;
  cliente?: Cliente;
}

