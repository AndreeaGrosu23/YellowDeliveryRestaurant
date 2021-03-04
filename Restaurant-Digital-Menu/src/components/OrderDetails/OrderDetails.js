import React, { useState, useEffect } from "react";
import axios from "axios";
import { Table } from "react-bootstrap";
import { Link } from "react-router-dom";
import { useForm } from "react-hook-form";

import "../MealBrowsing/FoodCategories.css";

function OrderDetails({ name }) {
  const [listOfMeals, setListOfMeals] = useState([]);
  const { handleSubmit } = useForm();
  const token = window.sessionStorage.getItem("token");
  const [orderDetails, setOrderDetails] = useState([]);

  useEffect(() => {
    async function getData() {
      await axios
        .get("http://localhost:8080/api/v1/cart/order-details", {
          headers: { Authorization: `Bearer ${token}` },
        })
        .then((res) => {
          setOrderDetails(res.data);
          setListOfMeals(res.data.meals);
        });
    }
    getData();
  }, [token]);

  const getTotalPrice = (data) => {
    let sum = 0;
    sum =
      data.reduce((accumulator, currentValue) => {
        return accumulator + currentValue.quantity;
      }, 0) * 5;

    return sum;
  };

  const onSubmit = () => {
    const paymentRequest = {
      totalAmount: `${getTotalPrice(listOfMeals)}`,
      description: `OrderId${orderDetails.userFirstName}`,
      linkPaypal: null,
    };
    doPayment(paymentRequest);
  };

  return (
    <div
      className="container FoodCategoriesContainer"
      style={{ marginBottom: "40rem" }}
    >
      <h1 style={{ color: "white" }}>Order Details</h1>

      <Table striped bordered hover style={{ color: "white" }}>
        <thead>
          <tr>
            <th>Item Name</th>
            <th>Qty</th>
            <th>Price</th>
          </tr>
        </thead>
        <tbody>
          {listOfMeals.map((item) => (
            <tr key={item.mealInCartId}>
              <td>{item.mealName}</td>
              <td>{item.quantity}</td>
              <td>{item.mealPrice * item.quantity} $</td>
            </tr>
          ))}

          <tr style={{ color: "yellow" }}>
            <td>TOTAL PRICE:</td>
            <td colSpan="2">{listOfMeals && getTotalPrice(listOfMeals)} $</td>
          </tr>
          <tr>
            <td>NAME</td>
            <td colSpan="2">
              {orderDetails.userFirstName} {orderDetails.userLastName}
            </td>
          </tr>
          <tr>
            <td>PHONE NUMBER</td>
            <td colSpan="2">
              {orderDetails.userPhoneNumber ? (
                orderDetails.userPhoneNumber
              ) : (
                <Link to={`/user-profile`}>Please add Phone Number</Link>
              )}
            </td>
          </tr>
          <tr>
            <td>EMAIL ADDRESS</td>
            <td colSpan="2">{orderDetails.userEmailAddress}</td>
          </tr>
          <tr>
            <td>SHIPPING ADDRESS</td>
            <td colSpan="2">
              {orderDetails.userDeliveryAddress ? (
                orderDetails.userDeliveryAddress
              ) : (
                <Link to={`/user-profile`}>Please add Shipping Address</Link>
              )}
            </td>
          </tr>
        </tbody>
      </Table>
      <form onSubmit={handleSubmit(onSubmit)}>
        <button className="btn btn-primary" type="submit">
          Proceeded with Paypal payment
        </button>
      </form>
    </div>
  );
}

export default OrderDetails;

async function doPayment(params) {
  axios
    .post("http://localhost:8080/payment/request-payment", params)
    .then((response) => {
      if (response.status === 200) {
        window.location.href = response.data;
      }
    })
    .catch((error) => {
      console.log(error);
    });
}
