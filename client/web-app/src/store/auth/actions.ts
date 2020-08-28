import {
  UserAuth,
  CREATE_USER,
  CREATE_ERROR,
  LOGIN_USER,
  LOGIN_ERROR,
} from "./types";
import { Dispatch } from "redux";

export const registerUser = () => (dispatch: Dispatch) => {
  dispatch({ type: CREATE_USER, payload: true });
  console.log("Creating User");
  dispatch({ type: CREATE_USER, payload: false });
};
