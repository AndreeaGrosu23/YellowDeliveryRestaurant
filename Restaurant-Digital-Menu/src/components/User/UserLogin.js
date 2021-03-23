import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { Redirect, useHistory } from "react-router";
import { Button, Modal } from "react-bootstrap";
import "./UserLogin.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye } from "@fortawesome/free-solid-svg-icons";
import axios from "axios";

export default function UserLogin() {
  const { register, handleSubmit, errors } = useForm();
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

  async function handleLogin(data) {
    try {
      await axios
        .post("http://localhost:8080/auth/login", data)
        .then((result) => {
          window.sessionStorage.setItem("User", result.data.username);
          window.sessionStorage.setItem("token", result.data.token);
          setUserLogin(true);
        });
    } catch (error) {
      if (error.message === "Request failed with status code 403") {
        setModalMessage("Username or Password is incorrect");
        handleShow();
        console.log(error);
      } else {
        history.push({ pathname: "/error", state: { detail: error.message } });
      }
    }
  }

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
      {userLogin && <Redirect to="/categories" />}
      <div
        className="   LoginContainer "
        style={{
          width: "38rem",
          marginTop: "50px",
          justifyContent: "center",
          display: "flex",
        }}
      >
        <form onSubmit={handleSubmit(handleLogin)}>
          <h5 style={{ color: "white" }}>Login</h5>
          <div className="form-group">
            <input
              name="username"
              placeholder="username"
              ref={register({
                required: { message: "This field is mandatory", value: true },
                minLength: { message: "Minim 1 characters", value: 1 },
              })}
              type="text"
              className="form-control"
              id="user-name"
            />
            {errors.username && (
              <small className="text-danger">{errors.username.message}</small>
            )}
          </div>
          <div className="form-group">
            <input
              type={passwordShown ? "text" : "password"}
              name="password"
              ref={register({
                required: { message: "This field is mandatory", value: true },
                minLength: { message: "Minim 4 characters", value: 4 },
              })}
              placeholder="Password"
              className="form-control"
              id="password"
            />
            {errors.password && (
              <small className="text-danger">{errors.password.message}</small>
            )}
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
