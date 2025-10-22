import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import { GoogleOAuthProvider } from "@react-oauth/google";
import {BrowserRouter} from "react-router-dom";

ReactDOM.createRoot(document.getElementById("root")).render(
    <BrowserRouter>
    <React.StrictMode>
        <GoogleOAuthProvider clientId="491839525697-1ad0pir6k4fmvss8dn5m2qbjkecvso1e.apps.googleusercontent.com">
            <App />
        </GoogleOAuthProvider>
    </React.StrictMode>
    </BrowserRouter>
);
