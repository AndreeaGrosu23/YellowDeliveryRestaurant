import React, { useState, useContext, useEffect } from "react";
import { Navbar, Nav, Form, Button, FormControl } from "react-bootstrap";
import { Link } from "react-router-dom";
import { ThemeContext } from "../../contexts/ThemeContext";
import "./Navbar.css";

export default function MenuBar() {
  const { isDarkMode, toggleTheme } = useContext(ThemeContext);
  const [dataInput, setDataInput] = useState("");
  const [userIsLogin, setIsLogin] = useState("");

  useEffect(() => {
    if (window.sessionStorage.getItem("User")) {
      setIsLogin(true);
    } else {
      setIsLogin(false);
    }
  }, [userIsLogin]);

  const handleChange = (e) => {
    setDataInput(e.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    setDataInput("");
  };

  return (
    <Navbar
      className="color-nav"
      bg={isDarkMode ? "light" : "dark"}
      variant={isDarkMode ? "light" : "dark"}
    >
      <Navbar.Brand href="#home">Yellow Restaurant</Navbar.Brand>
      <Nav className="mr-auto">
        <Nav.Link href="/">Home</Nav.Link>
        <Nav.Link href="/categories">Food categories</Nav.Link>
        <Nav.Link href="/surprise-meal">Surprise Meal</Nav.Link>
        {userIsLogin ? null : <Nav.Link href="/signup">SignUp</Nav.Link>}
        {userIsLogin ? null : <Nav.Link href="/login">Login</Nav.Link>}
        {userIsLogin ? (
          <Nav.Link href="/user-profile">User Profile</Nav.Link>
        ) : null}
        {userIsLogin ? <Nav.Link href="/logout">Logout</Nav.Link> : null}
        <Nav.Link href="#">
          {userIsLogin
            ? `Logged in as ${window.sessionStorage.getItem("User")}`
            : "Not logged in"}
        </Nav.Link>

        <Nav.Link href="/cart">Cart</Nav.Link>
      </Nav>
      <Form inline onSubmit={handleSubmit}>
        <FormControl
          type="text"
          placeholder="Search by ingredient"
          className="mr-sm-2"
          value={dataInput}
          onChange={handleChange}
        />

        <Link to={`/search/${dataInput}`}>
          <Button type="submit" variant="outline-success">
            <span role="img" aria-label="search">
              🔍
            </span>
          </Button>
        </Link>
      </Form>

      <Button type="submit" variant="outline-success" onClick={toggleTheme}>
        <span aria-label="paint" role="img">
          {" "}
          🎨
        </span>
      </Button>
    </Navbar>
  );
}
