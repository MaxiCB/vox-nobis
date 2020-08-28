import React from "react";
import { AppState } from "../store";
import { registerUser } from "../store/auth/actions";
import { connect } from "react-redux";
import { AuthState } from "../store/auth/types";

interface TestProps {
  auth: AuthState;
  registerUser: typeof registerUser;
}

const TestButton: React.FC<TestProps> = (props) => {
  return <button onClick={() => props.registerUser()}>Click Me</button>;
};

const mapStateToProps = (state: AppState) => ({
  auth: state,
  registerUser: registerUser,
});

export default connect(mapStateToProps, { registerUser })(TestButton);
