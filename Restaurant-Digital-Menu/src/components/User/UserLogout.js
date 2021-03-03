import React from "react";
import axios from "axios";
import { useHistory } from "react-router-dom";

function UserLogout() {
  const history = useHistory();
  const doLogout = () => {
    handleLogout()
      .then((res) => {
        
        window.sessionStorage.clear();
        history.push("/surprise-meal");
      })
      .catch((error) => {
        history.push({
          pathname: "/error",
          state: { detail: error.message },
        });
      });
  };
  return <>{doLogout()}</>;
}
async function handleLogout() {
  const responseData = await axios.get("http://localhost:8080/auth/logout");
  return responseData.data;
}

export default UserLogout;
