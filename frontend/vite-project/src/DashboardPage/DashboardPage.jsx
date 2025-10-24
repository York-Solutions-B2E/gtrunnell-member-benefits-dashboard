import { useEffect, useState } from "react";
import axios from "axios";

export default function DashboardPage() {
    const [data, setData] = useState(null);
    const token = localStorage.getItem("token");

    useEffect(() => {
        if (!token) return;

        axios
            .get("http://localhost:8080/api/dashboard", {
                headers: { Authorization: `Bearer ${token}` },
            })
            .then((res) => setData(res.data))
            .catch((err) => console.error("Error fetching dashboard:", err));
    }, [token]);

    if (!token) return <p>Please log in first.</p>;
    if (!data) return <p>Loading...</p>;

    const {
        fullName,
        planName,
        networkName,
        planYear,
        deductibleUsed,
        deductibleLimit,
        oopUsed,
        oopLimit,
        recentClaims,
    } = data;

    const handleSignOut = () => {
        localStorage.removeItem("token");
        window.location.href = "/";
    };

    return (
        <div className="min-h-screen bg-gradient-to-br from-white to-[#B7E23F] p-8">
            <header className="grid grid-cols-3 items-center py-4 border-b border-sky-200">
                <h1 className="text-3xl font-bold text-sky-600 justify-self-start">
                    Claims
                </h1>
                <div className="justify-self-center">
                    <span className="text-3xl font-bold text-lime-600">{fullName}</span>
                </div>
                <div className="justify-self-end">
                    <button
                        onClick={handleSignOut}
                        className="text-sm bg-sky-300 hover:bg-red-500 text-white px-3 py-1 rounded cursor-pointer transition"
                    >
                        Sign out
                    </button>
                </div>
            </header>

            <section className="bg-white/80 rounded-xl shadow p-4 mb-8">
                <h2 className="text-2xl font-semibold text-sky-700 mb-3">Active Plan</h2>
                <ul className="text-lime-600 font-semibold  space-y-1">
                    <li>• {planName}</li>
                    <li>• Network: {networkName}</li>
                    <li>• Coverage Year: {planYear}</li>
                </ul>
            </section>

            <section className="bg-white/80 rounded-xl shadow p-4 mb-8">
                <h2 className="text-xl font-semibold text-sky-700 mb-3">Accumulators</h2>
                <div className="text-lime-600 font-semibold  space-y-1">
                    <p>
                        Deductible: ${deductibleUsed} / ${deductibleLimit}
                    </p>
                    <div className="w-full bg-gray-200 rounded-full h-3">
                        <div
                            className="bg-sky-500 h-3 rounded-full"
                            style={{ width: `${(deductibleUsed / deductibleLimit) * 100}%` }}
                        ></div>
                    </div>
                    <p>
                        Out-of-Pocket Max: ${oopUsed} / ${oopLimit}
                    </p>
                    <div className="w-full bg-gray-200 rounded-full h-3">
                        <div
                            className="bg-green-500 h-3 rounded-full"
                            style={{ width: `${(oopUsed / oopLimit) * 100}%` }}
                        ></div>
                    </div>
                </div>
            </section>

            <section className="bg-white/80 rounded-xl shadow p-4">
                <h2 className="text-xl font-semibold text-sky-700 mb-3">Recent Claims</h2>
                {recentClaims && recentClaims.length > 0 ? (
                    <table className="w-full text-left border-collapse">
                        <thead>
                        <tr className="border-b-2 border-sky-100 text-sky-600">
                            <th className="py-2">Claim #</th>
                            <th className="py-2">Service Dates</th>
                            <th className="py-2">Provider</th>
                            <th className="py-2">Status</th>
                            <th className="py-2">Amount</th>
                            <th className="py-2"></th>
                        </tr>
                        </thead>
                        <tbody>
                        {recentClaims.map((claim) => (
                            <tr
                                key={claim.claimNumber}
                                className="border-b border-gray-200 hover:bg-sky-50 transition"
                            >
                                <td className="py-2">{claim.claimNumber}</td>
                                <td className="py-2">{claim.serviceDates}</td>
                                <td className="py-2">{claim.providerName}</td>
                                <td
                                    className={`py-2 font-medium ${
                                        claim.status === "DENIED"
                                            ? "text-red-500"
                                            : claim.status === "PROCESSED" || claim.status === "IN_REVIEW"
                                                ? "text-yellow-500"
                                                : claim.status === "PAID"
                                                    ? "text-green-600"
                                                    : "text-gray-600"
                                    }`}
                                >
                                    {claim.status}
                                </td>
                                <td className="py-2">${claim.memberResponsibility}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                ) : (
                    <p>No recent claims found.</p>
                )}
            </section>

            <div className="flex justify-end mt-6">
                <button
                    className="bg-sky-400 hover:bg-sky-600 text-white font-medium px-4 py-2 rounded cursor-pointer transition"
                    onClick={() => (window.location.href = "/claims")}
                >
                    View All Claims
                </button>
            </div>
        </div>
    );
}