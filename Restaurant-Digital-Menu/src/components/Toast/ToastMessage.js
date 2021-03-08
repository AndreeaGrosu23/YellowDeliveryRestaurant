import React from "react";
import Toast from "react-bootstrap/Toast";

function ToastMessage({ toast, setToast, toastBody }) {
  return (
    <Toast
      style={{
        position: "fixed",
        top: 0,
        right: "45%",
      }}
      onClose={() => setToast(false)}
      show={toast}
      delay={3000}
      autohide
    >
      <Toast.Header>
        <img src="holder.js/20x20?text=%20" className="rounded mr-2" alt="" />
        <strong className="mr-auto">Message</strong>
      </Toast.Header>
      <Toast.Body>{toastBody}</Toast.Body>
    </Toast>
  );
}

export default ToastMessage;
