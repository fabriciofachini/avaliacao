import { Observable } from 'rxjs';
import { Usuario } from './usuario';

export interface AuthContextData {
  signed: boolean;
  user: Usuario| null;
  loading: boolean;
  signIn(username: string, senha: string): Observable<Usuario | null>;
  signOut(): void;
  admin: boolean;
}
