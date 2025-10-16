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
[[GoogleLoginButtonExplanation]]

This component handles:

- Rendering the “Continue with Google” button.
    
- Receiving the token from Google.
    
- Sending that token to your backend for verification.
    

```
window.google.accounts.id.initialize({
  client_id: "YOUR_CLIENT_ID.apps.googleusercontent.com",
  callback: handleCredentialResponse,
});

```

#### 🔍 What’s happening:

1. The SDK injects a sign-in button into your DOM.
    
2. When the user logs in, Google returns a **JWT ID token** (in `response.credential`).
    
3. You decode it locally (for learning/debug).
    
4. You send that token in a request to your backend using Axios:
    

```
axios.get("http://localhost:8080/api/private/hello", {
  headers: { Authorization: `Bearer ${response.credential}` },
});

```
## `SecurityConfig.java`
[[code-templates/SecurityConfig.md]]
[[SpringOIDCNotes/SecurityConfigExplanation.md]]```
http
  .authorizeHttpRequests(auth -> auth
      .requestMatchers("/api/public/**").permitAll()
      .anyRequest().authenticated()
  )
  .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

```

**Why:**

- `oauth2ResourceServer` tells Spring that every request must include a JWT.
    
- `jwt(Customizer.withDefaults())` configures Spring to verify that token.
    
- Spring fetches Google’s public keys automatically from `https://www.googleapis.com/oauth2/v3/certs`.
  ```
## `application.properties`

`spring.security.oauth2.resourceserver.jwt.issuer-uri=https://accounts.google.com`

**Why:**  
Spring needs to know **who issued** the JWT.  
It uses this URI to download Google’s signing keys and verify your token signature automatically.

## `CorsConfig.java`
[[code-templates/CorsConfig.md]]
```
registry.addMapping("/**")
    .allowedOrigins("http://localhost:5173")
    .allowedMethods("GET", "POST", "PUT", "DELETE")
    .allowedHeaders("*")
    .allowCredentials(true);

```

**Why:**  
CORS (Cross-Origin Resource Sharing) allows your frontend (on port 5173) to call the backend (port 8080).  
Without this, browsers would block the request.

## `HelloController.java`
[[code-templates/HelloTestController.md]]
```
@GetMapping("/api/private/hello")
public Map<String, Object> privateHello(@AuthenticationPrincipal Jwt jwt) {
    return Map.of(
        "message", "Hello from PRIVATE endpoint!",
        "email", jwt.getClaimAsString("email"),
        "sub", jwt.getClaimAsString("sub"),
        "issuer", jwt.getIssuer().toString()
    );
}

```

**Why:**

- We use `@AuthenticationPrincipal Jwt jwt` to inject the **decoded token** directly from Spring Security.
    
- This verifies that the JWT was signed by Google and hasn’t been tampered with.
    
- The endpoint only works if a valid `Bearer` token is sent.
# Step 4: Verification and Testing

**Process Flow:**

1. React loads → Google button renders.
    
2. User clicks → logs in → Google sends ID token.
    
3. React decodes token, sends to backend.
    
4. Spring validates token signature and issuer.
    
5. Backend returns secure JSON response.
    

If you saw:

`✅ Backend says: { email: 'your@email.com', message: 'Hello from PRIVATE endpoint!' }`

Then authentication is fully working.