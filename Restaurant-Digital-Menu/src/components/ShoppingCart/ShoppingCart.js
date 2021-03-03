import React, { useState, useEffect } from "react";
import axios from "axios";
import { Card } from "react-bootstrap";
import "../MealBrowsing/FoodCategories.css";
import { Link } from "react-router-dom";

function ShoppingCart() {
  const [cartId, setCartId] = useState(0);
  const [listOfMeals, setListOfMeals] = useState({});
  const userName = window.sessionStorage.getItem("User");
  const token = window.sessionStorage.getItem("token");

  useEffect(() => {
    async function getData() {
      try {
        const cartResponse = await axios.get(
          `http://localhost:8080/api/v1/cart/view/${userName}`,
          {
            headers: { Authorization: `Bearer ${token}` },
          }
        );
        setCartId(cartResponse.data);

        const mealResponse = await axios.get(
          `http://localhost:8080/api/v1/cart/mealsInCart/${cartResponse.data}`,
          {
            headers: { Authorization: `Bearer ${token}` },
          }
        );
        setListOfMeals(mealResponse.data);
      } catch (error) {
        console.error(error);
      }
    }
    getData();
  }, [userName, token]);

  const getListOfMeals = (mapWithMeals) => {
    let content = [];
    let meal = {};
    for (let [mealJSON, quantity] of Object.entries(mapWithMeals)) {
      meal = JSON.parse(mealJSON);
      content.push(
        <div className="card FoodCategoriesCard" key={meal.id}>
          <Card.Img
            className="card-img-top cardImg"
            variant="top"
            src={meal.image}
          />
          <Card.Body>
            <Card.Title>{meal.name}</Card.Title>
            <Card.Text>{quantity}</Card.Text>
            <Card.Text>{meal.price * quantity}$</Card.Text>
          </Card.Body>
        </div>
      );
    }

    return content;
  };

  const getTotalPrice = (mapWithMeals) => {
    let meal = {};
    let sum = 0;
    for (let [mealJSON, quantity] of Object.entries(mapWithMeals)) {
      meal = JSON.parse(mealJSON);
      sum += meal.price * quantity;
    }
    return sum;
  };

  return (
    <div
      className="container FoodCategoriesContainer"
      style={{ marginBottom: "40rem" }}
    >
      <h1 style={{ color: "white" }}>Cart</h1>
      {listOfMeals && getListOfMeals(listOfMeals)}
      <div>
        <p style={{ fontSize: "50px", color: "yellow" }}>
          TOTAL PRICE: {listOfMeals && getTotalPrice(listOfMeals)}$
        </p>

        <Link to={`/order-details/${cartId}`}>
          <button type="button" value="submit" className="btn btn-info">
            Checkout
          </button>{" "}
        </Link>
      </div>
    </div>
  );
}

export default ShoppingCart;
