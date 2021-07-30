import { map, catchError} from 'rxjs/operators';
import { defer, Observable, throwError } from 'rxjs';
import { AxiosInstance, AxiosRequestConfig } from 'axios';
import axios from 'axios';

const initializeAxios = (config: AxiosRequestConfig): AxiosInstance => {
  const axiosInstance = axios.create(config);

  return axiosInstance;
};

const axiosRequestConfiguration: AxiosRequestConfig = {
  baseURL: 'http://localhost:8080',
  responseType: 'json',
  headers: {
    'Content-Type': 'application/json',
  },
};

const axiosInstance = initializeAxios(axiosRequestConfiguration);

const get = <T>(url: string, queryParams?: any): Observable<T> => {
  return defer(()=> axiosInstance.get<T>(url, { params: queryParams }))
    .pipe(map((response: any) => {
      return response.data.resposta;
    })).pipe(catchError(error => throwError(error)));
};

const post = <T>(url: string, body: any, config?: any): Observable<T> => {
  return defer(()=> axiosInstance.post<any>(url, body))
    .pipe(map((response: any) => {
      return response.data.resposta;
    })).pipe(catchError(error => throwError(error)));
};

const put = <T>(url: string, body: any, queryParams?: any): Observable<T> => {
  return defer(()=>axiosInstance.put<T>(url, body, { params: queryParams }))
    .pipe(map((response: any) => {
      return response.data.resposta;
    })).pipe(catchError(error => throwError(error)));
};

const patch = <T>(url: string, body: any, queryParams?: any): Observable<T> => {
  return defer(()=> axiosInstance.patch<T>(url, body, { params: queryParams }))
    .pipe(map((response: any) => {
      return response.data.resposta;
    })).pipe(catchError(error => throwError(error)));
};

const _delete = <T>(url: string, id:number): Observable<T> => {
  return defer(() => (axiosInstance.delete(`${url}/${id}` )))
    .pipe(map((response: any) => {
      return response.data.resposta;
    })).pipe(catchError(error => throwError(error)));
};

export default { get, post, put, patch, delete: _delete, axiosInstance };
