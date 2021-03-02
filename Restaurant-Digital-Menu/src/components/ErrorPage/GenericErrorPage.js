import React from 'react'

import { useLocation } from "react-router-dom";

function GenericErrorPage() {
  const location = useLocation();

  const errorMessage = () => {
    if (location.state === undefined) {
      return "Page not found !";
    } else {
      return location.state.detail;
    }
  };

  return (
    <div style={{ color: "white" }} classNam="App-header">
      <div></div>
      <h2>Error:</h2>
      <h3>{errorMessage()}</h3>
    </div>
  );
}

export default GenericErrorPage
