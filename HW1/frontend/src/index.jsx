import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import reportWebVitals from './reportWebVitals';
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { Root, Trips, Home, Details } from "./routes";


const router = createBrowserRouter([
  {
    path: "/",
    element: <Root />,
    children:[
      { path: "/", element:<Home/>},
      { path: "/trips", element:<Trips/>},
      { path: "details", element:<Details/>}
    ]
  },
]);

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <RouterProvider router={router}>
      <Root/>
    </RouterProvider>
  </React.StrictMode>
);

reportWebVitals();