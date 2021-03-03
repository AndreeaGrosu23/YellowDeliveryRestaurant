import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faYoutube,
  faFacebook,
  faInstagram,
} from "@fortawesome/free-brands-svg-icons";
import "./Footer.css";

function Footer() {
  return (
    <footer
      className="page-footer font-small blue"
      style={{ marginTop: "35px", fontSize: "25px" }}
    >
      <div className="footer-copyright text-center text-white py-3">
        © 2020 Copyright:
        <a href="/" style={{ color: "white" }}>
          {" "}
          Yellow Team Restaurant
        </a>
        <div>
          <a href="https://www.youtube.com/" className="youtube social">
            <FontAwesomeIcon icon={faYoutube} size="1x" color="white" />
          </a>
          <a href="https://www.facebook.com/" className="facebook social">
            <FontAwesomeIcon icon={faFacebook} size="1x" color="white" />
          </a>
          <a href="https://www.instagram.com/" className="instagram social">
            <FontAwesomeIcon icon={faInstagram} size="1x" color="white" />
          </a>
        </div>
      </div>
    </footer>
  );
}

export default Footer;
