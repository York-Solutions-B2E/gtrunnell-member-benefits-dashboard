### 🧩 where the token comes from

when a user clicks the google button and signs in, google’s **identity services sdk** (the script you loaded) creates a special, short-lived token called an **ID token**.

this token:

- is a long string of random-looking letters (a **JWT**, or _json web token_),
    
- contains information about the user (email, name, google user id),
    
- is **digitally signed** by google so no one can fake it.
    

---

### ⚙️ where the token lives in your code

after the user signs in, google runs your callback:

`callback: handleCredentialResponse`

and passes in an object like:

`{   credential: "eyJhbGciOiJSUzI1NiIsInR5cCI6..." }`

so, right inside your callback, this happens:

`const handleCredentialResponse = async (response) => {   console.log("Encoded JWT ID token:", response.credential); }`

👉 `response.credential` **is the token**.

it’s temporarily stored:

- in memory (the `response` variable inside your callback),
    
- visible in the console log (for debugging),
    
- **not** stored permanently in local storage or cookies (that’s good — more secure).
    

---

### 🔍 what you do with it on the frontend

1. you decode it locally (with `jwtDecode`) just to _see_ what’s inside.
    
2. you send it to your backend in the **Authorization header**:
    

``headers: { Authorization: `Bearer ${response.credential}` }``

that’s the same pattern used by nearly every API that uses tokens.

---

### ☕ what happens next on the backend

your spring boot server gets that header:

`Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR...`

because you configured it as an **oauth2 resource server**, spring:

1. reads your `issuer-uri=https://accounts.google.com`.
    
2. downloads google’s public signing keys.
    
3. verifies that the token’s signature and expiration are valid.
    
4. extracts the claims (`email`, `sub`, etc.).
    
5. if everything checks out, it lets the request through and injects the decoded jwt into your controller as the `@AuthenticationPrincipal Jwt jwt` object.
    

---

### 🧠 quick analogy

|Step|Analogy|
|---|---|
|google creates token|the school office prints an id card with your name and photo|
|token given to your callback|you hold the id card in your hand (response.credential)|
|axios sends it to backend|you show the id card to the hall monitor|
|backend verifies signature|the monitor calls the office to make sure it’s real|
|jwt injected|the monitor writes down your name from the card and lets you pass|