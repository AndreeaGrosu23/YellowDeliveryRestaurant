import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import "./FoodCategories.css";
import { ThemeContext } from "../../contexts/ThemeContext";
import { useHistory } from "react-router";

function FoodCategories() {
  const { isDarkMode } = useContext(ThemeContext);
  const [foodCategories, setFoodCategories] = useState([]);
  const [MoreDetails, setMoreDetails] = useState(false);
  const history = useHistory();

  useEffect(() => {
    async function getData() {
      await axios
        .get("https://www.themealdb.com/api/json/v1/1/categories.php")
        .then((res) => {
          setFoodCategories(res.data.categories);
        })
        .catch((error) => {
          history.push({
            pathname: "/error",
            state: { detail: error.message },
          });
        });
    }
    getData();
  }, [history]);

  const showMore = (e) => {
    e.preventDefault();
    let id = e.target.name;

    for (let index = 0; index < foodCategories.length; index++) {
      if (foodCategories[index].idCategory === id) {
        foodCategories[index].showMoreDetails = !foodCategories[index]
          .showMoreDetails;
      }
      setMoreDetails(!MoreDetails);
    }
  };

  const textDisplay = (show) => {
    return show ? "show less" : "show more";
  };

  return (
    <div className="container FoodCategoriesContainer">
      <h1 className={isDarkMode ? "blackText" : "whiteText"}>
        Food Categories
      </h1>
      {foodCategories.map((item, i) => (
        <div className="card FoodCategoriesCard" key={i}>
          <div className="cardImg">
            <img
              className="card-img-top"
              src={item.strCategoryThumb}
              alt={item.strCategory}
            />
          </div>
          <div className="card-body">
            <h5 className="card-title">{item.strCategory}</h5>

            <p className="card-text FoodCategoriesCardText">
              {item.showMoreDetails
                ? item.strCategoryDescription
                : item.strCategoryDescription.substring(0, 50) + "..."}
              <button
                name={item.idCategory}
                onClick={showMore}
                className=" btn btn-link btn-sm shadow-none"
              >
                {textDisplay(item.showMoreDetails)}
              </button>
            </p>

            <Link
              className="btn btn-primary"
              to={`/categories/${item.strCategory}`}
            >
              Go to food
            </Link>
          </div>
        </div>
      ))}
    </div>
  );
}

export default FoodCategories;
