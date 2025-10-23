import { useEffect, useState } from "react";
//using useNavigate here so data persists when going to ClaimDetailPage
import { useNavigate} from "react-router-dom";

import axios from "axios";

export default function ClaimsListPage() {
    const [claims, setClaims] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const token = localStorage.getItem("token");
    const navigate = useNavigate();

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
        <div className="p-6">
            <header className="flex items-center justify-between mb-6">
                <h1 className="text-3xl font-bold text-sky-600">Claims</h1>
            </header>

            {claims.length === 0 ? (
                <p className="text-gray-600 italic">No claims found.</p>
            ) : (
                <div className="overflow-x-auto bg-white rounded-2xl shadow-sm border border-gray-200">
                    <table className="min-w-full text-sm text-left text-gray-700">
                        <thead className="bg-sky-100 text-gray-700 uppercase text-xs font-semibold">
                        <tr>
                            <th className="px-4 py-3">Claim #</th>
                            <th className="px-4 py-3">Service Dates</th>
                            <th className="px-4 py-3">Provider</th>
                            <th className="px-4 py-3">Status</th>
                            <th className="px-4 py-3 text-right">Member Responsibility</th>
                            <th className="px-4 py-3 text-center">Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {claims.map((claim) => {
                            const statusColors = {
                                DENIED: "text-red-500",
                                PROCESSED: "text-yellow-500",
                                IN_REVIEW: "text-yellow-500",
                                PAID: "text-green-600",
                            };

                            return (
                                <tr
                                    key={claim.claimNumber}
                                    className="border-b border-gray-200 hover:bg-sky-50 transition"
                                >
                                    <td className="px-4 py-3 font-medium text-gray-800">
                                        {claim.claimNumber}
                                    </td>
                                    <td className="px-4 py-3 text-gray-600">
                                        {claim.serviceStartDate} – {claim.serviceEndDate}
                                    </td>
                                    <td className="px-4 py-3 text-gray-700">
                                        {claim.providerName}
                                    </td>
                                    <td
                                        className={`px-4 py-3 font-semibold ${
                                            statusColors[claim.status] || "text-gray-600"
                                        }`}
                                    >
                                        {claim.status.replace("_", " ")}
                                    </td>
                                    <td className="px-4 py-3 text-right text-gray-700">
                                        $
                                        {claim.memberResponsibility != null
                                            ? claim.memberResponsibility.toFixed(2)
                                            : "—"}
                                    </td>
                                    <td className="px-4 py-3 text-center">
                                        <button
                                            onClick={() =>
                                                navigate(`/claims/${claim.claimNumber}`)
                                            }
                                            className="text-sky-600 hover:text-sky-800 font-medium hover:underline transition cursor-pointer"
                                        >
                                            View ▸
                                        </button>
                                    </td>
                                </tr>
                            );
                        })}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
}
