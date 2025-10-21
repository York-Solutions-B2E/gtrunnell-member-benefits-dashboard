import { useEffect, useState } from "react";
import axios from "axios";

export default function ClaimsPage() {
    const [data, setData] = useState(null);
    const token = localStorage.getItem("token");

    useEffect(() => {
        if (!token) return;

        axios.get("http://localhost:8080/api/claims", {
            params: { provider: "River Clinic", page: 0, size: 10 },
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(res => setData(res.data))
            .catch(err => console.error("Error fetching claims:", err));
    }, [token]);

    if (!token) return <p>Please log in first.</p>;
    if (!data) return <p>Loading...</p>;

    return (
        <div>
            <p>{JSON.stringify(data, null, 2)}</p>
        </div>
    );
}
