import React, { useContext } from "react";
import { ThemeContext } from "./contexts/ThemeContext";
import Background1 from "./images/texture-wooden-boards_1249-132.jpg";
import Background2 from "./images/img2.jpg";

export default function PageContent(props) {
  const { isDarkMode } = useContext(ThemeContext);
  const styles = {
    backgroundImage: isDarkMode ? `url(${Background1})` : `url(${Background2}`,
    backgroundPosition: "left",
    backgroundSize: "cover",
    backgroundRepeat: "no-repeat",
    backgroundAttachment: "fixed",
  };
  return <div style={styles}>{props.children}</div>;
}
