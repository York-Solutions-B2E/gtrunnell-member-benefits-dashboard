import { useEffect, useState } from "react";
import axios from "axios";

export default function PrivatePage() {
    const [data, setData] = useState(null);
    const token = localStorage.getItem("token");

    useEffect(() => {
        axios.get("http://localhost:8080/api/private", {
            withCredentials: true, // ✅ send cookie automatically
        })
            .then(res => setData(res.data))
            .catch(err => console.error("Error fetching private message:", err));
    }, []);


    if (!token) return <p>Please log in first.</p>;
    if (!data) return <p>Loading...</p>;

    return (
        <div>
            <h2>{data.message}</h2>
            <p><strong>Email:</strong> {data.email}</p>
            <p><strong>Sub:</strong> {data.sub}</p>
            <p><strong>Issuer:</strong> {data.issuer}</p>
        </div>
    );
}
