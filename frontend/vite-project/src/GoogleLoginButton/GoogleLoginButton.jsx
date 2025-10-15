import { useEffect } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode";

function GoogleLoginButton() {
    useEffect(() => {
        // Wait until Google SDK is loaded
        const initializeGoogle = () => {
            if (window.google && window.google.accounts && window.google.accounts.id) {
                window.google.accounts.id.initialize({
                    client_id: "491839525697-1ad0pir6k4fmvss8dn5m2qbjkecvso1e.apps.googleusercontent.com", // <-- your Google client ID
                    callback: handleCredentialResponse,
                });
                window.google.accounts.id.renderButton(
                    document.getElementById("googleSignInDiv"),
                    { theme: "outline", size: "large" }
                );
            } else {
                setTimeout(initializeGoogle, 100);
            }
        };

        initializeGoogle();
    }, []);

    const handleCredentialResponse = async (response) => {
        console.log("Encoded JWT ID token:", response.credential);
        const decoded = jwtDecode(response.credential);
        console.log("Decoded token:", decoded);

        try {
            const res = await axios.get("http://localhost:8080/api/private/hello", {
                headers: { Authorization: `Bearer ${response.credential}` },
            });
            console.log("✅ Backend says:", res.data);
            alert(res.data);
        } catch (err) {
            console.error("❌ Error calling backend:", err);
        }
    };

    return (
        <div style={{ marginTop: "2rem", textAlign: "center" }}>
            <h2>Login with Google</h2>
            <div id="googleSignInDiv"></div>
        </div>
    );
}

export default GoogleLoginButton;
