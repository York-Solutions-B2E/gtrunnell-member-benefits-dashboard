
import { useEffect } from "react";
import { jwtDecode } from "jwt-decode";

function GoogleLoginButton() {

    useEffect(() => {
        let isMounted = true;

        const initializeGoogle = () => {
            if (!isMounted) return;
            const buttonDiv = document.getElementById("googleSignInDiv");
            if (!buttonDiv) return setTimeout(initializeGoogle, 100);
            if (window.google?.accounts?.id) {
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

        return () => { isMounted = false; };
    }, []);

    const handleCredentialResponse = async (response) => {
        console.log("Encoded JWT ID token:", response.credential);

        await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ credential: response.credential }),
            credentials: "include", // ✅ store the cookie
        });

        window.location.href = "/private";
    };



    return (
        <div style={{ marginTop: "2rem", textAlign: "center" }}>
            <h2>Login with Google</h2>
            <div id="googleSignInDiv"></div>
        </div>
    );
}

export default GoogleLoginButton;
