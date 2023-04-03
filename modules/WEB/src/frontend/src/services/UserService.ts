import {AxiosError} from "axios";
import {ErrorResponse} from "@/services/ErrorResponse";
import {Api} from "@/services/ApiService";

export interface UserData {
  login: string,
  email: string,
  phones: string[],
  posts: string[]
}

export interface TokenUserData {
  token: string,
  user: UserData
}

export interface LoginForm {
  login: string,
  password: string
}

export class UserService {
  public async signIn(form: LoginForm): Promise<ErrorResponse | TokenUserData> {
    try {
      const api = new Api();
      return await api.post<TokenUserData, LoginForm>('/api/v1/users/login', form);
    } catch (e) {
      const error = e as AxiosError;
      if (error.response != null)
        return error.response.data as ErrorResponse;
      throw e;
    }
  }

  public static async signUp(login: string, email: string, password: string): Promise<ErrorResponse | boolean> {
    try {
      const api = new Api();
      await api.post('/api/v1/users', {
        login: login,
        password: password,
        email: email
      });
      return true;
    } catch (e) {
      const error = e as AxiosError;
      if (error.response != null)
        return error.response.data as ErrorResponse;
      throw e;
    }
  }

  public async logout(): Promise<ErrorResponse | boolean> {
    try {
      const api = new Api();
      await api.post("/api/v1/users/logout");
      return true;
    } catch (e) {
      const error = e as AxiosError;
      if (error.response != null)
        return error.response.data as ErrorResponse;
      throw e;
    }
  }

  public async changeUserContacts(field: string, op: string, value: string): Promise<ErrorResponse | UserData> {
    try {
      const api = new Api();
      const data = await api.patch<UserData, any>("/api/v1/users", {
        field: field,
        op: op,
        value: value
      });
      return data;
    } catch (e) {
      const error = e as AxiosError;
      if (error.response != null)
        return error.response.data as ErrorResponse;
      throw e;
    }
  }
}