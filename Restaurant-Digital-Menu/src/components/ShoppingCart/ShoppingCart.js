import React, { useState, useEffect } from "react";
import axios from "axios";
import { Card } from "react-bootstrap";
import "../MealBrowsing/FoodCategories.css";
import { Link } from "react-router-dom";

function ShoppingCart() {
  const [listOfMeals, setListOfMeals] = useState([]);
  const [reload, setReload] = useState(false);
  const token = window.sessionStorage.getItem("token");

  useEffect(() => {
    async function getData() {
      try {
        await axios
          .get("http://localhost:8080/api/v1/cart/", {
            headers: { Authorization: `Bearer ${token}` },
          })
          .then((res) => {
            setListOfMeals(res.data);
            setReload(false);
          });
      } catch (error) {
        console.error(error);
      }
    }
    getData();
  }, [token, reload]);

  const getTotalPrice = (data) => {
    let sum = 0;
    sum =
      data.reduce((accumulator, currentValue) => {
        return accumulator + currentValue.quantity;
      }, 0) * 5;

    return sum;
  };

  const handelChangeQty = ({ meal, modification }) => {
    let newQty = meal.quantity + modification;
    let data = {
      mealInCartId: meal.mealInCartId,
      quantity: newQty,
    };
    changeMealQty({ params: data, token: token }).then(() => {
      setReload(true);
    });
  };

  return (
    <div
      className="container FoodCategoriesContainer"
      style={{ marginBottom: "40rem" }}
    >
      <h1 style={{ color: "white" }}>Cart</h1>
      {listOfMeals ? (
        listOfMeals.map((item) => (
          <div className="card FoodCategoriesCard" key={item.mealInCartId}>
            <Card.Img
              className="card-img-top cardImg"
              variant="top"
              src={item.mealImage}
            />
            <Card.Body>
              <Card.Title>{item.mealName}</Card.Title>
              <Card.Text>{item.quantity} Qty</Card.Text>
              <button
                className="btn btn-light"
                onClick={() => handelChangeQty({ meal: item, modification: 1 })}
              >
                <span role="img" aria-label="up">
                  👍
                </span>
              </button>
              <button
                className="btn btn-light"
                onClick={() =>
                  handelChangeQty({ meal: item, modification: -1 })
                }
              >
                <span role="img" aria-label="down">
                  👎
                </span>
              </button>
              <Card.Text>{item.mealPrice * item.quantity}$</Card.Text>
            </Card.Body>
          </div>
        ))
      ) : (
        <p style={{ fontSize: "50px", color: "yellow" }}>No meals in cart</p>
      )}
      <div>
        <p style={{ fontSize: "50px", color: "yellow" }}>
          TOTAL PRICE: {getTotalPrice(listOfMeals)}$
        </p>
        <Link to={`/order-details/`}>
          <button type="button" className="btn btn-info">
            Checkout
          </button>{" "}
        </Link>
      </div>
    </div>
  );
}

export default ShoppingCart;

async function changeMealQty({ params, token }) {
  try {
    const dataResponse = await axios.put(
      "http://localhost:8080/api/v1/cart/",
      params,
      { headers: { Authorization: `Bearer ${token}` } }
    );
    return dataResponse.data;
  } catch (error) {
    console.error(error);
  }
}
