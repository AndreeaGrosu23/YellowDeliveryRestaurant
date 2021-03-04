import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import ToastMessage from "../Toast/ToastMessage";

export default function FavoriteMeals() {
  const username = window.sessionStorage.getItem("User");
  const [favoriteMeals, setFavoriteMeals] = useState([]);
  const token = window.sessionStorage.getItem("token");

  const [toast, setToast] = useState(false);
  const [toastBody, setToastBody] = useState([]);

  useEffect(() => {
    async function getData() {
      await axios
        .get(`http://localhost:8080/api/v1/user/${username}/favorites`, {
          headers: { Authorization: `Bearer ${token}` },
        })
        .then((res) => {
          setFavoriteMeals(res.data);
        })
        .catch((error) => {
          console.log(error);
        });
    }
    getData();
  }, [token, username]);
  const handleAddMealToCart = (item) => {
    let mealData = {
      mealName: item.strMeal,
      mealImage: item.strMealThumb,
      mealPrice: 5.0,
      quantity: 1,
      idMeal: item.idMeal,
    };
    if (username) {
      addMealToUserCart({ params: mealData, token: token })
        .then((res) => {
          if (res.status === 200) {
            setToastBody(mealData.mealName + " Add to cart");
            setToast(true);
          }
        })
        .catch((error) => {
          console.error(error);
        });
    } else {
      setToastBody("ERROR try Login to order");
      setToast(true);
    }
  };
  return (
    <div
      style={{ marginBottom: "50rem" }}
      className="container FoodCategoriesContainer"
    >
      {favoriteMeals.length === 0 ? (
        <div style={{ color: "white" }}>
          <h3>No favorite meals</h3>
        </div>
      ) : (
        <div>
          <div style={{ color: "white" }}>
            <h3>Favorite meals</h3>
          </div>
          {favoriteMeals.map((item, i) => (
            <div
              key={i}
              className="card FoodCategoriesCard"
              style={{ width: "18rem", display: "inline-block" }}
            >
              <img
                className="card-img-top cardImg"
                src={item.image}
                alt="Card cap"
              />
              <div className="card-body">
                <h5 className="card-title">{item.name.substring(0, 20)}</h5>
                <Link to={`/food-details/${item.idMeal}`}>
                  <button type="button" value="submit" className="btn btn-link">
                    Info
                  </button>{" "}
                </Link>

                <button
                  onClick={() => handleAddMealToCart(item)}
                  className="btn btn-light"
                >
                  <span role="img" aria-label="cart">
                    🛒
                  </span>
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
      <ToastMessage toast={toast} setToast={setToast} toastBody={toastBody} />
    </div>
  );
}

async function addMealToUserCart({ params, token }) {
  try {
    const dataResponse = await axios.post(
      "http://localhost:8080/api/v1/cart/",
      params,
      { headers: { Authorization: `Bearer ${token}` } }
    );
    return dataResponse;
  } catch (error) {
    console.error(error);
  }
}
