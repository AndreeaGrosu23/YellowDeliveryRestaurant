import React, { useState, useRef } from "react";
import { useForm } from "react-hook-form";
import { Redirect } from "react-router";
import { Button, Modal } from "react-bootstrap";
import "../MealBrowsing/FoodCategories.css";

export default function UserSignUp() {
  const { register, handleSubmit, watch, errors } = useForm();
  const [toHome, setToHome] = useState();
  const [message, setMessage] = useState();
  const [show, setShow] = useState(false);
  const password = useRef({});
  password.current = watch("password", "");

  const onSubmit = (data) => {
    fetch(" http://localhost:8080/api/v1/user", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    })
      .then((data) => {
        if (data.status === 200) {
          setToHome(true);
        } else if (data.status === 400) {
          data.json().then((res) => {
            setMessage(res.error);
            handleShow();
          });
        }
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  return (
    <div className="RegisterContainer">
      {toHome ? <Redirect to="/login" /> : null}
      <div
        className="container-register"
        style={{
          width: "38rem",
          marginTop: "50px",
          justifyContent: "center",
          display: "flex",
        }}
      >
        <form onSubmit={handleSubmit(onSubmit)}>
          <h5 style={{ color: "white" }}>Register account</h5>
          <div className="form-group">
            <input
              name="firstName"
              placeholder="First name"
              ref={register}
              type="text"
              className="form-control"
              id="first-name"
              required="required"
            />
          </div>
          <div className="form-group">
            <input
              name="lastName"
              placeholder="Last name"
              ref={register}
              type="text"
              className="form-control"
              id="last-name"
              required="required"
            />
          </div>
          <div className="form-group">
            <input
              name="userName"
              placeholder="Username"
              ref={register({ required: true, maxLength: 20 })}
              type="text"
              className="form-control"
              id="user-name"
              required="required"
            />
            {errors.userName && "Username is required"}
          </div>
          <div className="form-group">
            <input
              name="emailAddress"
              placeholder="Email Address"
              ref={register({ required: true, maxLength: 30 })}
              type="email"
              className="form-control"
              id="email-address"
              required="required"
            />
            {errors.emailAddress && "E-mail is required"}
          </div>
          <div className="form-group">
            <input
              type="password"
              name="password"
              placeholder="Password"
              ref={register}
              className="form-control"
              id="password"
              required="required"
            />
            {errors.password && "Password is required"}
          </div>
          <div className="form-group">
            <input
              type="password"
              name="confirmPassword"
              placeholder="Confirm password"
              ref={register({
                validate: (value) => {
                  return value === password.current || "Passwords do not match";
                },
              })}
              className="form-control"
              id="confirm-password"
              required="required"
            />
            {errors.confirmPassword && (
              <small className="text-danger">
                {errors.confirmPassword.message}
              </small>
            )}
          </div>
          <input type="submit" className="btn btn-primary" />
        </form>
      </div>
      <>
        <Modal show={show} onHide={handleClose} message={message}>
          <Modal.Header closeButton>
            <Modal.Title>Message</Modal.Title>
          </Modal.Header>
          <Modal.Body>{message}</Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
              Close
            </Button>
          </Modal.Footer>
        </Modal>
      </>
    </div>
  );
}
