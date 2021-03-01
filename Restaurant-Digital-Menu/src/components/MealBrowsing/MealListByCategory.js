import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { FaHeart } from "react-icons/fa";
import "./FoodCategories.css";

function MealListByCategory({ name }) {
  const [foodListCategories, setFoodListCategories] = useState([]);
  const [favoriteMeals, setFavoriteMeals] = useState([]);
  // const [updated, setUpdated] = useState(false);

  const username = window.sessionStorage.getItem("User");
  const token = window.sessionStorage.getItem("token");

  useEffect(() => {
    async function getData() {
      const response = await axios.get(
        `https://www.themealdb.com/api/json/v1/1/filter.php?c=${name}`
      );
      setFoodListCategories(response.data.meals);
    }
    getData();
  }, [name]);

  useEffect(() => {
    async function getDataFavorites() {
      const responseFavorites = await axios.get(
        `http://localhost:8080/api/v1/user/${username}/favorites`,
        {
          headers: { "Authorization" : `Bearer ${token}` }
        }
      );
      setFavoriteMeals(responseFavorites.data);   
    }
    getDataFavorites();
  }, []);

  const listIds = []
  for (let favoriteMeal of favoriteMeals) {
    listIds.push(favoriteMeal.idMeal);
  }
  console.log(listIds);

  const faveClick = (newFavoriteMeal) => {
    let alreadyFave = false;
    for (let item of favoriteMeals) {
      if (item.idMeal === newFavoriteMeal.idMeal) {
        alreadyFave = true;
      }
    }

    if (alreadyFave === false) {
      fetch(
        `http://localhost:8080/api/v1/user/${username}/favorites/${newFavoriteMeal.idMeal}`,
        {
          method: "PUT",
          headers: { "Content-Type": "application/json",
          "Authorization" : `Bearer ${token}` 
        },
          body: JSON.stringify(newFavoriteMeal),
        }
      ).then((response) => {
        console.log(response);
      });
    } else {
      fetch(
        `http://localhost:8080/api/v1/user/${username}/favorites/delete/${newFavoriteMeal.idMeal}`,
        {
          method: "DELETE",
          headers: { 
            "Content-Type": "application/json",
            "Authorization" : `Bearer ${token}` 
          }
        }
      )
    }
    window.location.reload();
    // setUpdated(true);
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
                { listIds.includes(item.idMeal) ? <FaHeart style={{ color: "red" }} /> : <FaHeart style={{ color: "blue" }} /> }
              </h5>
            </button>{" "}
            <Link to={`/food-details/${item.idMeal}`}>
              <button type="button" value="submit" className="btn btn-info">
                Info
              </button>{" "}
            </Link>
            <a
              href="#"
              onClick={() => handleClick(item)}
              className="btn btn-primary"
            >
              🛒
            </a>
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
        "Authorization" : `Bearer ${token}`
      },
      body: JSON.stringify(mealToAddToCart),
    }
  ).then((response) => {
    console.log(response);
  });
}

export default MealListByCategory;
