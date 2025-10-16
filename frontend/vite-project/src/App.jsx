import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import GoogleLoginButton from "./GoogleLoginButton/GoogleLoginButton.jsx";
import PrivatePage from "./PrivatePage/PrivatePage.jsx";
import { BrowserRouter, Routes, Route } from "react-router-dom";

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<GoogleLoginButton />} />
                <Route path="/private" element={<PrivatePage />} />
            </Routes>
        </BrowserRouter>
    </>
  )
}

export default App
