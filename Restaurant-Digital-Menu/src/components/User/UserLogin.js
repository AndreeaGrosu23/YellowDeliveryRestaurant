import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { Redirect, useHistory } from "react-router";
import { Button, Modal } from "react-bootstrap";
import "./UserLogin.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye } from "@fortawesome/free-solid-svg-icons";

export default function UserLogin() {
  const { register, handleSubmit } = useForm();
  const [userLogin, setUserLogin] = useState(false);
  // Pop-up Alert
  const [show, setShow] = useState(false);
  const [modalMessage, setModalMessage] = useState("");

  //Show Password
  const eye = <FontAwesomeIcon icon={faEye} />;
  const [passwordShown, setPasswordShown] = useState(false);

  //Redirect
  const history = useHistory();

  const togglePasswordVisiblity = () => {
    setPasswordShown(passwordShown ? false : true);
  };

  const onSubmit = (data) => {
    let promiseA = loginUser(data);
    promiseA.then(function (result) {
      if (result.username) {
        window.sessionStorage.setItem("User", result.username);
        window.sessionStorage.setItem("token", result.token);
        setUserLogin(true);
        history.push("/categories");
      } else {
        setModalMessage("Username or Password is incorrect");
        handleShow();
      }
    });
  };

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  return (
    <div className="LoginContainer">
      <div>
        <>
          <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
              <Modal.Title>Message</Modal.Title>
            </Modal.Header>
            <Modal.Body>{modalMessage}</Modal.Body>
            <Modal.Footer>
              <Button variant="secondary" onClick={handleClose}>
                Close
              </Button>
            </Modal.Footer>
          </Modal>
        </>
      </div>
      {userLogin && <Redirect to="/" />}
      <div
        className="   LoginContainer "
        style={{
          width: "38rem",
          marginTop: "50px",
          justifyContent: "center",
          display: "flex",
        }}
      >
        <form onSubmit={handleSubmit(onSubmit)}>
          <h5 style={{ color: "white" }}>Login</h5>
          <div className="form-group">
            <input
              name="username"
              placeholder="username"
              ref={register({ required: "This is required." })}
              type="text"
              class="form-control"
              id="user-name"
              required="required"
            />
          </div>
          <div className="form-group">
            <input
              type={passwordShown ? "text" : "password"}
              name="password"
              ref={register({ required: "This is required." })}
              placeholder="Password"
              class="form-control"
              id="password"
              required="required"
            />
            <br></br>
            <h6 style={{ color: "white" }}>
              <i style={{ color: "white" }} onClick={togglePasswordVisiblity}>
                {eye}
              </i>
              Show password
            </h6>
          </div>
          <input type="submit" className="btn btn-primary" />
        </form>
      </div>
    </div>
  );
}

async function loginUser(data) {
  let responseLogin = await fetch("http://localhost:8080/auth/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data),
  }).then((response) => response.json());
  return responseLogin;
}
