export interface Usuario {
  access_token: string;
  accountNonExpired: boolean;
  accountNonLocked: boolean;
  authorities: Array<{id: number, authority: string}>
  credentialsNonExpired: boolean;
  enabled: boolean;
  expires_in: number;
  idUsuario: number;
  password: string;
  perfil: {idPerfil: number, authority: string}
  authority: string;
  username: string;
}
