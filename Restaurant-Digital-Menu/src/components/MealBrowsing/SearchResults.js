import React, { useState, useEffect } from "react";
import axios from "axios";
import "./FoodCategories.css";

function SearchResults({ name }) {
  const [foodListSearch, setFoodListSearch] = useState([]);

  useEffect(() => {
    async function getData() {
      await axios
        .get(`https://www.themealdb.com/api/json/v1/1/filter.php?i=${name}`)
        .then((res) => {
          setFoodListSearch(res.data.meals);
        })
        .catch((error) => {
          console.log(error);
        });
    }
    getData();
  }, [name]);

  return (
    <div className="container FoodCategoriesContainer">
      {foodListSearch !== null ? (
        foodListSearch.map((item) => (
          <div
            key={item.idMeal}
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

              <span
                onClick={() => handleClick(item)}
                role="img"
                aria-label="cart"
              >
                Add to 🛒
              </span>
            </div>
          </div>
        ))
      ) : (
        <h2>No results found</h2>
      )}
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

export default SearchResults;
