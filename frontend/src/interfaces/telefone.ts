import { Cliente } from './cliente';

export interface Telefone {
  id: number;
  telefone: string;
  tipo: string;
  cliente: Cliente;
}
