import { createStore, combineReducers, applyMiddleware } from "redux";
// Middleware
import thunk from "redux-thunk";
import logger from "redux-logger";

// Reducers
import { authReducer } from "./auth/reducers";
const rootReducer = authReducer;

export type AppState = ReturnType<typeof rootReducer>;

export default function configureStore() {
  const store = createStore(rootReducer, applyMiddleware(thunk, logger));
  return store;
}
