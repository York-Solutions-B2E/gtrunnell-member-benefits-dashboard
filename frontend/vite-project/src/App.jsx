import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import GoogleLoginButton from "./GoogleLoginButton/GoogleLoginButton.jsx";
// import PrivatePage from "./PrivatePage/PrivatePage.jsx";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import DashboardPage from "./DashboardPage/DashboardPage.jsx";
import ClaimsPage from "./ClaimsPage/ClaimsPage.jsx";

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<GoogleLoginButton />} />
                {/*<Route path="/private" element={<PrivatePage />} />*/}
                <Route path="/dashboard" element={<DashboardPage />} />
                <Route path="/claims" element={<ClaimsPage />} />
            </Routes>
        </BrowserRouter>
    </>
  )
}

export default App
