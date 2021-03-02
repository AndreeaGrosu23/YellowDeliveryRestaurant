import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { FaHeart } from "react-icons/fa";
import "./FoodCategories.css";

function MealListByCategory({ name }) {
  const [foodListCategories, setFoodListCategories] = useState([]);
  const [favoriteMeals, setFavoriteMeals] = useState([]);

  const username = window.sessionStorage.getItem("User");
  const token = window.sessionStorage.getItem("token");

  useEffect(() => {
    async function getData() {
      await axios
        .get(`https://www.themealdb.com/api/json/v1/1/filter.php?c=${name}`)
        .then((res) => {
          setFoodListCategories(res.data.meals);
        })
        .catch((error) => {
          console.log(error);
        });
    }
    getData();
  }, [name]);

  useEffect(() => {
    async function getDataFavorites() {
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
    getDataFavorites();
  }, [username, token]);

  const listIds = [];
  for (let favoriteMeal of favoriteMeals) {
    listIds.push(favoriteMeal.idMeal);
  }
  

  const faveClick = (newFavoriteMeal) => {
    console.log("add fav");
    let alreadyFave = false;
    for (let item of favoriteMeals) {
      if (item.idMeal === newFavoriteMeal.idMeal) {
        alreadyFave = true;
      }
    }

    if (alreadyFave === false) {
      const favoriteMeal={
        idMeal: newFavoriteMeal.idMeal,
        name: newFavoriteMeal.strMeal,
        image: newFavoriteMeal.strMealThumb
      };
      fetch(
        `http://localhost:8080/api/v1/user/${username}/favorites`,
        {
          method: "POST",
          headers: { "Content-Type": "application/json",
          "Authorization" : `Bearer ${token}` 
        },
          body: JSON.stringify(favoriteMeal),
        }
      ).then((response) => {
        console.log("add to db");
      });
    } else {
      fetch(
        `http://localhost:8080/api/v1/user/${username}/favorites/delete/${newFavoriteMeal.idMeal}`,
        {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );
    }
    window.location.reload();
  };

  return (
    <div className="container FoodCategoriesContainer">
      {foodListCategories.map((item) => (
        <div
          key={item.strMeal}
          className="card FoodCategoriesCard"
          style={{ width: "18rem", display: "inline-block" }}
        >
          <img
            className="card-img-top cardImg"
            src={item.strMealThumb}
            alt="Card cap"
          />
          <div className="card-body">
            <h5 className="card-title">{item.strMeal.substring(0, 20)}</h5>
            <button
              style={{ borderStyle: "none", backgroundColor: "white" }}
              type="button"
              onClick={() => faveClick(item)}
            >
              <h5>
                {listIds.includes(item.idMeal) ? (
                  <FaHeart style={{ color: "red" }} />
                ) : (
                  <FaHeart style={{ color: "blue" }} />
                )}
              </h5>
            </button>{" "}
            <Link to={`/food-details/${item.idMeal}`}>
              <button type="button" value="submit" className="btn btn-link">
                Info
              </button>{" "}
            </Link>
            <button
              onClick={() => handleClick(item)}
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
  );
}

function handleClick(mealToAddToCart) {
  const username = window.sessionStorage.getItem("User");
  const token = window.sessionStorage.getItem("token");
  const image = mealToAddToCart.strMealThumb.replace(
    "https://www.themealdb.com/images/media/meals/",
    ""
  );
  fetch(
    `http://localhost:8080/api/v1/cart/${username}/meal/${mealToAddToCart.strMeal}/tocart/${image}`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(mealToAddToCart),
    }
  ).then((response) => {
    console.log(response);
  });
}

export default MealListByCategory;
