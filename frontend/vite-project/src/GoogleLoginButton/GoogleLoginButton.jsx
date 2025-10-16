import { useEffect } from "react";
import { jwtDecode } from "jwt-decode";

function GoogleLoginButton() {
    console.log("googleloginbutton");

    useEffect(() => {
        const initializeGoogle = () => {
            const buttonDiv = document.getElementById("googleSignInDiv");
            if (!buttonDiv) {
                // Element not yet in DOM — try again shortly
                setTimeout(initializeGoogle, 100);
                return;
            }

            if (window.google && window.google.accounts && window.google.accounts.id) {
                window.google.accounts.id.initialize({
                    client_id: "491839525697-1ad0pir6k4fmvss8dn5m2qbjkecvso1e.apps.googleusercontent.com",
                    callback: handleCredentialResponse,
                });
                window.google.accounts.id.renderButton(buttonDiv, {
                    theme: "outline",
                    size: "large",
                });
            } else {
                setTimeout(initializeGoogle, 100);
            }
        };

        initializeGoogle();
    }, []);

    const handleCredentialResponse = (response) => {
        console.log("Encoded JWT ID token:", response.credential);
        const decoded = jwtDecode(response.credential);
        console.log("Decoded token:", decoded);
        localStorage.setItem("token", response.credential); // ✅ save token
        window.location.href = "/private"; // ✅ redirect
    };

    return (
        <div style={{ marginTop: "2rem", textAlign: "center" }}>
            <h2>Login with Google</h2>
            <div id="googleSignInDiv"></div>
        </div>
    );
}

export default GoogleLoginButton;
