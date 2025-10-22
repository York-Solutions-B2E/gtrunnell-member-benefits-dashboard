import { useEffect, useState } from "react";
import axios from "axios";

export default function ClaimsListPage() {
    const [claims, setClaims] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const token = localStorage.getItem("token");

    useEffect(() => {
        if (!token) return;

        axios
            .get("http://localhost:8080/api/claims", {
                params: { page: 0, size: 10 },
                headers: { Authorization: `Bearer ${token}` },
            })
            .then((res) => {
                setClaims(res.data.content || res.data); // handle paged or plain arrays
                setLoading(false);
            })
            .catch((err) => {
                console.error("Error fetching claims:", err);
                setError(err);
                setLoading(false);
            });
    }, [token]);

    if (!token) return <p>Please log in first.</p>;
    if (loading) return <p>Loading claims...</p>;
    if (error) return <p>Error loading claims.</p>;

    return (
        <div>
            <h1>Claims</h1>
            <hr />

            {/* Filters section (placeholder for now) */}
            <div>
                <p>Filters: [Status] [Date Range] [Provider] [Claim #] (Search)</p>
            </div>

            <hr />

            {/* Claims Table */}
            {claims.length === 0 ? (
                <p>No claims found.</p>
            ) : (
                <table border="1" cellPadding="4" cellSpacing="0">
                    <thead>
                    <tr>
                        <th>Claim #</th>
                        <th>Service Dates</th>
                        <th>Provider</th>
                        <th>Status</th>
                        <th>Member Responsibility</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {claims.map((claim) => (
                        <tr key={claim.claimNumber}>
                            <td>{claim.claimNumber}</td>
                            <td>
                                {claim.serviceStartDate} – {claim.serviceEndDate}
                            </td>
                            <td>{claim.providerName}</td>
                            <td>{claim.status}</td>
                            <td>${claim.memberResponsibility?.toFixed(2) ?? "—"}</td>
                            <td>
                                <button
                                    onClick={() =>
                                        (window.location.href = `/claims/${claim.claimNumber}`)
                                    }
                                >
                                    View ▸
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}

            <hr />

            {/* Pagination placeholder */}
            <div>
                <p>Page 1 of X [10 v] per page ◂ Prev 1 2 3 ... Next ▸</p>
            </div>
        </div>
    );
}
