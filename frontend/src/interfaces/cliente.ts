import { Telefone } from './telefone';
import { Email } from './email';

export interface Cliente {
  id: number;
  nome: string;
  cpf: string;
  cep: string;
  bairro: string;
  logradouro: string;
  cidade: string;
  uf: string;
  complemento: string;
  emails: Array<Email>,
  telefones: Array<Telefone>
}
