import React from "react";
import axios from "axios";
import { useHistory } from "react-router-dom";

function UserLogout() {
  const history = useHistory();

  let logoutResponse = handleLogout();
  console.log(logoutResponse);
  window.sessionStorage.clear();
  history.push("/surprise-meal");

  return (
    <div>
      <h1>Logout Page</h1>
    </div>
  );
}
async function handleLogout() {
  await axios.get("http://localhost:8080/auth/logout");
}

export default UserLogout;
