import React, { createContext, useContext, useEffect, useState } from 'react';
import api from '../services/api';
import { catchError, map, mergeMap } from 'rxjs/operators';
import { of, throwError } from 'rxjs';
import { AuthContextData } from '../interfaces/auth';
import { Usuario } from '../interfaces/usuario';

const AuthContext = createContext<AuthContextData>({} as AuthContextData);

const AuthProvider: React.FC = ({ children }) => {
  useEffect(() => {
    const stringUser = localStorage.getItem('user');
    if (stringUser) {
      const storagedUser: Usuario = JSON.parse(stringUser);
      setUser(storagedUser);
      setAdmin(storagedUser.perfil.authority === 'ROLE_ADMIN');
      api.axiosInstance.defaults.headers.Authorization = 'Bearer ' + storagedUser.access_token;
    }
    setLoading(false);
  }, []);

  const [user, setUser] = useState<null | Usuario>(null);
  const [loading, setLoading] = useState(true);
  const [admin, setAdmin] = useState(false);

  const signIn = (username: string, password: string) => {
    return api.post<Usuario>('/auth/login', {
      username,
      password
    }).pipe(
      mergeMap((response) => {
        setUser(response);
        setAdmin(response.perfil.authority === 'ROLE_ADMIN');
        api.axiosInstance.defaults.headers.Authorization = 'Bearer ' + response?.access_token;
        localStorage.setItem('user', JSON.stringify(response));
        return of(response);
      }),
      catchError(
        (error: Error) => throwError(error)
      )
    );
  };

  function signOut() {
    localStorage.removeItem('user');
    setUser(null);
  }

  return (
    <AuthContext.Provider value={{ signed: user !== null, user, loading, signIn, signOut, admin }}>
      {children}
    </AuthContext.Provider>
  );
};

function useAuth() {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider.');
  }

  return context;
}

export { AuthProvider, useAuth };
