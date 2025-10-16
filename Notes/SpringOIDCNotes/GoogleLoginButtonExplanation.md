### imports

`import { useEffect } from "react"; import axios from "axios"; import { jwtDecode } from "jwt-decode";`

- **`useEffect`**: a React tool that lets you run code _after_ the page shows up. We’ll use it to set up Google’s login button.
    
- **`axios`**: a helper to make web requests to your backend (like sending a message to your server).
    
- **`jwtDecode`**: a helper to _read_ what’s inside a Google login token (like reading what’s on your student ID card). This is just for learning—**the server** still double-checks that the ID is real.
### component start

`function GoogleLoginButton() {`

### set up the Google button after the page loads

  `useEffect(() => {     // Wait until Google SDK is loaded     const initializeGoogle = () => {       if (window.google && window.google.accounts && window.google.accounts.id) {`

- **`useEffect(..., [])`** runs once when the component appears on the screen.
    
- **`window.google`** is something the Google script puts on the page. If it’s not there yet, we wait. (The script tag must be in your `index.html`:  
    `<script src="https://accounts.google.com/gsi/client" async defer></script>`)
### tell Google who we are and what to do after login

        window.google.accounts.id.initialize({
          client_id: "4918...apps.googleusercontent.com",
          callback: handleCredentialResponse,
        });


- **`client_id`**: your app’s public ID from Google. It’s how Google recognizes your site.
    
- **`callback`**: the function Google will call after the user signs in. Google will give you a **token** there.
- [[SpringOIDCNotes/Token routes.md]]    
### draw the actual Google button

        `window.google.accounts.id.renderButton(           document.getElementById("googleSignInDiv"),           { theme: "outline", size: "large" }         );`

- This tells Google where to put the button (inside the `<div id="googleSignInDiv">`) and how it should look.

### if the Google script isn’t ready yet, try again soon

      `} else {         setTimeout(initializeGoogle, 100);       }     };      initializeGoogle();   }, []);`

- If `window.google` isn’t there yet, we try again in **100 milliseconds**.
    
- The empty `[]` means “run this setup only once.”
### what happens when Google says “the user is signed in”

  `const handleCredentialResponse = async (response) => {     console.log("Encoded JWT ID token:", response.credential);     const decoded = jwtDecode(response.credential);     console.log("Decoded token:", decoded);`

- **Google calls this** and gives you `response.credential`, which is a **JWT** (a signed token that proves who the user is).
    
- We **log** the raw token and also **decode** it so you can see things like the user’s email. (Again: decoding is just for learning; verifying happens on the server.)
    

---

### ask our server to greet the user (and verify the token)

```
    try {
      const res = await axios.get("http://localhost:8080/api/private/hello", {
        headers: { Authorization: `Bearer ${response.credential}` },
      });
      console.log("✅ Backend says:", res.data);
      alert(res.data.message);
    } catch (err) {
      console.error("❌ Error calling backend:", err);
    }

```


- We call your backend’s protected route `/api/private/hello`.
    
- We attach the token in the **Authorization header** like this: `Bearer <token>`.  
    That’s the official way to send tokens.
    
- Your **Spring Boot backend** checks the token with Google (verifies the signature, issuer, expiration). If it’s legit, it lets you in and returns a friendly message.
    
- We show the message in an alert.
### what shows up on the page

 ```
   return (
    <div style={{ marginTop: "2rem", textAlign: "center" }}>
      <h2>Login with Google</h2>
      <div id="googleSignInDiv"></div>
    </div>
  );
}

 ```

- We render a title and an **empty div**. Google’s script fills that div with the real button.
### make this component usable elsewhere

`export default GoogleLoginButton;`

- This lets you import and use `<GoogleLoginButton />` inside your `App.jsx` or any other screen.

