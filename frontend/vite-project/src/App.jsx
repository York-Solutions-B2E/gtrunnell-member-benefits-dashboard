import { useState } from 'react'
import './App.css'
import GoogleLoginButton from "./GoogleLoginButton/GoogleLoginButton.jsx";
import { Routes, Route } from "react-router-dom";
import DashboardPage from "./DashboardPage/DashboardPage.jsx";
import Nav from "./Nav/Nav.jsx";
import Header from "./Header/Header.jsx";
import ClaimsListPage from "./ClaimsListPage/ClaimsListPage.jsx";
import ClaimDetailPage from "./ClaimDetailPage/ClaimDetailPage.jsx";

function App() {
  const [count, setCount] = useState(0)

  return (
    <div className="min-h-screen bg-gradient-to-br from-white to-[#B7E23F]">
        <Header />
        <Nav />
        <div>
            <Routes>
                <Route path="/" element={<GoogleLoginButton />} />
                <Route path="/dashboard" element={<DashboardPage />} />
                <Route path="/claims" element={<ClaimsListPage />} />
                <Route path="/claims/:claimNumber" element={<ClaimDetailPage />} />
            </Routes>
        </div>
    </div>
  )
}

export default App
