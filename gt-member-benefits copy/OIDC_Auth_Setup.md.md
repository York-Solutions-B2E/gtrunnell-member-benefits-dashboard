# ☕ Step 1: Set Up Google OAuth 2.0 Credentials

## 🎯 Goal

Before any frontend or backend code can authenticate a user, Google needs to **know about your app** — where it runs and what URLs it’s allowed to communicate from.  
This is why we first set up an **OAuth 2.0 Client ID** in the **Google Cloud Console**.

---

## 🧩 Step 1A — Create OAuth 2.0 Client ID

### Where:

**Google Cloud Console → APIs & Services → Credentials → Create Credentials → OAuth Client ID**

### What you chose:

- **Application type:** `Web application`
    
- **Authorized JavaScript origin:**
    
    `http://localhost:5173`
    
    This tells Google that your app running on this address (your React dev server) can send sign-in requests.
    
- **Authorized redirect URI:**
    
    `http://localhost:5173`
    
    This is where Google can safely send the user _back_ after authenticating.
    

### Result:

You got two important values:

- `Client ID`
    
- `Client Secret` (used only on backend or later server flows)
    

### Why this matters:

The Client ID is how Google recognizes your app.  
If this step isn’t done correctly, Google rejects your login attempt with:

> "The given origin is not allowed for the given client ID"


# 🧱 Step 2: Frontend Setup (React + Vite)

## 📁 Files Created or Edited

### 1. `index.html`

We added this inside the `<head>`:
[[index.html]]
`<script src="https://accounts.google.com/gsi/client" async defer></script>`

**Why:**  
This script loads **Google Identity Services (GSI)** — the SDK that renders the Google Sign-In button and issues the ID token when a user logs in.  
Without it, `window.google.accounts.id` would be `undefined`.

### 2. `GoogleLoginButton.jsx`
[[code-templates/GoogleLoginButtonTemp.md]]
[[code-templates/GoogleLoginButtonExplanation.md]]

This component handles:

- Rendering the “Continue with Google” button.
    
- Receiving the token from Google.
    
- Sending that token to your backend for verification.
    

`window.google.accounts.id.initialize({   client_id: "YOUR_CLIENT_ID.apps.googleusercontent.com",   callback: handleCredentialResponse, });`

#### 🔍 What’s happening:

1. The SDK injects a sign-in button into your DOM.
    
2. When the user logs in, Google returns a **JWT ID token** (in `response.credential`).
    
3. You decode it locally (for learning/debug).
    
4. You send that token in a request to your backend using Axios:
    

``axios.get("http://localhost:8080/api/private/hello", {   headers: { Authorization: `Bearer ${response.credential}` }, });``
