import React, { useState, useEffect } from "react";
import axios from "axios";
import { Modal, Button } from "react-bootstrap";
import Iframe from "react-iframe";

function FoodDetails({ id }) {
  const [foodDetails, setFoodDetails] = useState([]);
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  useEffect(() => {
    async function getData() {
      const response = await axios.get(
        `https://www.themealdb.com/api/json/v1/1/lookup.php?i=${id}`
      );
      setFoodDetails(response.data.meals[0]);
    }
    getData();
  }, [id]);

  const getYoutubeUrl = (url) => {
    const embedUrl = url.replace("watch?v=", "embed/");
    return embedUrl;
  };

  return (
    <div className="container" style={{ width: "32rem", marginTop: "50px" }}>
      <div className="card">
        <div style={{ width: "30rem", margin: "0 auto", paddingTop: "10px" }}>
          <img
            className="card-img-top"
            src={foodDetails.strMealThumb}
            alt="Card image cap"
          />
        </div>
        <div className="card-body">
          <h5 className="card-title">{foodDetails.strMeal}</h5>
          <p className="card-text">
            Main ingredientes:{" "}
            <i>
              {foodDetails.strIngredient1}, {foodDetails.strIngredient2},{" "}
              {foodDetails.strIngredient3}{" "}
            </i>
          </p>
        </div>
        <ul className="list-group list-group-flush">
          <li className="list-group-item">
            Area: <i>{foodDetails.strArea}</i>
          </li>
          <li className="list-group-item">
            Category: <i>{foodDetails.strCategory}</i>
          </li>
          {foodDetails.strTags && (
            <li className="list-group-item">
              Tags: <i>{foodDetails.strTags}</i>
            </li>
          )}
        </ul>
        <>
          <Button variant="link" onClick={handleShow}>
            Recipe
          </Button>
          <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
              <Modal.Title>Recipe</Modal.Title>
            </Modal.Header>
            <Modal.Body>{foodDetails.strInstructions}</Modal.Body>
            <Modal.Footer>
              <Button variant="secondary" onClick={handleClose}>
                Close
              </Button>
            </Modal.Footer>
          </Modal>
        </>
        <div className="card-body">
          {foodDetails.strYoutube && (
            <Iframe
              url={getYoutubeUrl(foodDetails.strYoutube)}
              width="450px"
              height="450px"
              id="myId"
              className="myClassname"
              display="initial"
              position="relative"
            />
          )}
        </div>
      </div>
    </div>
  );
}

export default FoodDetails;
