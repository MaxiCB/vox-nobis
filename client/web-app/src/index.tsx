import React from "react";
import ReactDOM from "react-dom";
// Styles
import "./styles/index.css";
import "./styles/App.css";
// Components
import App from "./components/App";
// Redux
import { Provider } from "react-redux";
import configureStore from "./store";
// Service Worker
import * as serviceWorker from "./serviceWorker";

const store = configureStore();

ReactDOM.render(
  <React.StrictMode>
    <Provider store={store}>
      <App />
    </Provider>
  </React.StrictMode>,
  document.getElementById("root")
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
