import { Cliente } from './cliente';

export interface Telefone {
  id: number;
  numero: string;
  tipo: string;
  cliente: Cliente;
}
