export interface AuthState {
  creatingAccount: boolean;
  registerError: boolean;
  loggingIn: boolean;
  loginError: boolean;
}

export interface UserAuth {
  username: string;
  email: string;
  password: string;
}

export const CREATE_USER = "CREATE_USER";
export const CREATE_ERROR = "CREATE_ERROR";
export const LOGIN_USER = "LOGIN_USER";
export const LOGIN_ERROR = "LOGIN_ERROR";

interface CreateUserAction {
  type: typeof CREATE_USER;
  payload: boolean;
}

interface CreateErrorAction {
  type: typeof CREATE_ERROR;
  payload: boolean;
}

interface LoginUserAction {
  type: typeof LOGIN_USER;
  payload: boolean;
}

interface LoginUserError {
  type: typeof LOGIN_ERROR;
  payload: boolean;
}

export type AuthActionTypes =
  | CreateUserAction
  | CreateErrorAction
  | LoginUserAction
  | LoginUserError;
