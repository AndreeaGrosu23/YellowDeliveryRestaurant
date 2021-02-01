import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import "./FoodCategories.css";
import { ThemeContext } from "../../contexts/ThemeContext";

function FoodCategories() {
  const { isDarkMode, toggleTheme } = useContext(ThemeContext);
  const [foodCategories, setFoodCategories] = useState([]);
  const [MoreDetails, setMoreDetails] = useState(false);

  useEffect(() => {
    async function getData() {
      const response = await axios.get(
        "https://www.themealdb.com/api/json/v1/1/categories.php"
      );
      setFoodCategories(response.data.categories);
    }
    getData();
  }, []);

  const showMore = (e) => {
    e.preventDefault();
    let id = e.target.name;

    foodCategories.map((item) => {
      if (item.idCategory === id) {
        item.showMoreDetails = !item.showMoreDetails;
      }
      setMoreDetails(!MoreDetails);
    });
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
              : item.strCategoryDescription.substring(0, 50) + "..."
              } {" "}
              <a href="#" name={item.idCategory} onClick={showMore}>
                {textDisplay(item.showMoreDetails)}
              </a>
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
