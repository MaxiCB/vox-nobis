import {
  AuthState,
  AuthActionTypes,
  CREATE_USER,
  CREATE_ERROR,
  LOGIN_USER,
  LOGIN_ERROR,
} from "./types";

const initialState: AuthState = {
  creatingAccount: false,
  registerError: false,
  loggingIn: false,
  loginError: false,
};

export function authReducer(
  state = initialState,
  action: AuthActionTypes
): AuthState {
  switch (action.type) {
    case CREATE_USER: {
      return {
        ...state,
        creatingAccount: action.payload,
      };
    }
    case CREATE_ERROR: {
      return {
        ...state,
        registerError: action.payload,
      };
    }
    case LOGIN_USER: {
      return {
        ...state,
        loggingIn: action.payload,
      };
    }
    case LOGIN_ERROR: {
      return {
        ...state,
        loginError: action.payload,
      };
    }
  }
}
