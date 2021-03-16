import React, { useState, useEffect } from "react";
import axios from "axios";
import "./FoodCategories.css";
import ToastMessage from "../Toast/ToastMessage";
import AddMealToUserCart from "../Util/AddMealToUserCart";

function SearchResults({ name }) {
  const [foodListSearch, setFoodListSearch] = useState([]);
  const [toast, setToast] = useState(false);
  const [toastBody, setToastBody] = useState([]);

  const username = window.sessionStorage.getItem("User");
  const token = window.sessionStorage.getItem("token");

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

  const handleAddMealToCart = (item) => {
    let mealData = {
      mealName: item.strMeal,
      mealImage: item.strMealThumb,
      mealPrice: 5.0,
      quantity: 1,
      idMeal: item.idMeal,
    };
    if (username) {
      AddMealToUserCart({ params: mealData, token: token })
        .then((res) => {
          setToastBody(res.data);
          setToast(true);
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
        ))
      ) : (
        <h2>No results found</h2>
      )}
      <ToastMessage toast={toast} setToast={setToast} toastBody={toastBody} />
    </div>
  );
}

export default SearchResults;


