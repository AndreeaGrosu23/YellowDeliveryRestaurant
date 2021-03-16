import axios from "axios";

export default async function AddMealToUserCart({ params, token }) {
  try {
    const dataResponse = await axios.post(
      "http://localhost:8080/api/v1/cart/",
      params,
      { headers: { Authorization: `Bearer ${token}` } }
    );
    return dataResponse.data;
  } catch (error) {
    console.error(error);
  }
}
