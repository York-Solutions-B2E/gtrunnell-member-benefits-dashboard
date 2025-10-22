import { useState } from 'react'
import './App.css'
import GoogleLoginButton from "./GoogleLoginButton/GoogleLoginButton.jsx";
import { Routes, Route } from "react-router-dom";
import DashboardPage from "./DashboardPage/DashboardPage.jsx";
import ClaimsPage from "./ClaimsPage/ClaimsPage.jsx";
import Nav from "./Nav/Nav.jsx";
import Header from "./Header/Header.jsx";

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
        <Header />
        <Nav />
            <Routes>
                <Route path="/" element={<GoogleLoginButton />} />
                <Route path="/dashboard" element={<DashboardPage />} />
                <Route path="/claims" element={<ClaimsPage />} />
            </Routes>
    </>
  )
}

export default App
