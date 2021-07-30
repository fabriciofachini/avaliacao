export interface Usuario {
    id: number;
    username: string;
    senha: string;
    nome: string;
    perfil: {codigo: string, descricao: string};
    accessToken: string;
}
