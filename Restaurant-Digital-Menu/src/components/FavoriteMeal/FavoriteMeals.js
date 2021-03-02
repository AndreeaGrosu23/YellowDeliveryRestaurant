import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

export default function FavoriteMeals() {
  const username = window.sessionStorage.getItem("User");
  const [favoriteMeals, setFavoriteMeals] = useState([]);
  const token = window.sessionStorage.getItem("token");

  useEffect(() => {
    async function getData() {
      const response = await axios.get(
        `http://localhost:8080/api/v1/user/${username}/favorites`,
        {
          headers: { "Authorization" : `Bearer ${token}` }
        }
      );
      setFavoriteMeals(response.data);
      console.log(response.data);
    }
    getData();
  }, []);

  return (
    <div
      style={{ marginBottom: "50rem" }}
      className="container FoodCategoriesContainer"
    >

      { favoriteMeals.length === 0 ? 
        <div style={{ color: "white" }}>
          <h3>No favorite meals</h3> 
        </div> 
      : 
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
      }

    </div>
  );
}

function handleClick(mealToAddToCart) {
  const username = window.sessionStorage.getItem("User");
  const token = window.sessionStorage.getItem("token");
  const image = mealToAddToCart.image.replace(
    "https://www.themealdb.com/images/media/meals/",
    ""
  );
  fetch(
    `http://localhost:8080/api/v1/cart/${username}/meal/${mealToAddToCart.name}/tocart/${image}`,
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
