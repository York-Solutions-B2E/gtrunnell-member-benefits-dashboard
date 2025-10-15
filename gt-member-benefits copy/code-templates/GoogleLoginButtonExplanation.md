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
    
