import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";

export default function ClaimDetailPage() {
    const { claimNumber } = useParams();
    const [claim, setClaim] = useState(null);
    const token = localStorage.getItem("token");

    useEffect(() => {
        if (!token || !claimNumber) return;
        axios
            .get(`http://localhost:8080/api/claims/${claimNumber}`, {
                headers: { Authorization: `Bearer ${token}` },
            })
            .then((res) => setClaim(res.data))
            .catch((err) => console.error("Error fetching claim detail:", err));
    }, [token, claimNumber]);

    if (!token) return <p className="text-gray-600 p-6">Please log in first.</p>;
    if (!claim) return <p className="text-gray-600 p-6">Loading claim details...</p>;

    const statusColors = {
        DENIED: "text-red-500",
        PROCESSED: "text-yellow-500",
        IN_REVIEW: "text-yellow-500",
        PAID: "text-green-600",
    };

    return (
        <div className="p-6 space-y-6">
            <div>
                <h2 className="text-3xl font-bold text-sky-600 mb-2">
                    Claim #{claim.claimNumber}
                </h2>
                <div className="space-y-1 text-gray-700">
                    <p>
                        <strong>Provider:</strong> {claim.providerName}
                    </p>
                    <p>
                        <strong>Service Dates:</strong> {claim.serviceStartDate} –{" "}
                        {claim.serviceEndDate}
                    </p>
                    <p
                        className={`font-semibold ${
                            statusColors[claim.status] || "text-gray-700"
                        }`}
                    >
                        <strong>Status:</strong> {claim.status}
                    </p>
                </div>
            </div>

            <div className="bg-white border border-gray-200 rounded-2xl shadow-sm p-5">
                <h3 className="text-xl font-semibold text-gray-800 mb-3">
                    Financial Summary
                </h3>
                <ul className="grid grid-cols-2 gap-y-1 text-gray-700">
                    <li>
                        <strong>Total Billed:</strong> ${claim.totalBilled}
                    </li>
                    <li>
                        <strong>Allowed Amount:</strong> ${claim.totalAllowed}
                    </li>
                    <li>
                        <strong>Plan Paid:</strong> ${claim.totalPlanPaid}
                    </li>
                    <li>
                        <strong>Member Responsibility:</strong> ${claim.totalMemberResponsibility}
                    </li>
                </ul>
            </div>

            <div className="bg-white border border-gray-200 rounded-2xl shadow-sm overflow-x-auto">
                <h3 className="text-xl font-semibold text-gray-800 p-5 pb-2">
                    Line Items
                </h3>
                <table className="min-w-full text-sm text-left text-gray-700">
                    <thead className="bg-sky-100 uppercase text-xs font-semibold text-gray-700">
                    <tr>
                        <th className="px-4 py-3">CPT</th>
                        <th className="px-4 py-3">Description</th>
                        <th className="px-4 py-3 text-right">Billed</th>
                        <th className="px-4 py-3 text-right">Allowed</th>
                        <th className="px-4 py-3 text-right">Copay</th>
                        <th className="px-4 py-3 text-right">Coins</th>
                        <th className="px-4 py-3 text-right">You Owe</th>
                    </tr>
                    </thead>
                    <tbody>
                    {claim.lines?.length > 0 ? (
                        claim.lines.map((line) => (
                            <tr
                                key={line.lineNumber}
                                className="border-b border-gray-200 hover:bg-sky-50 transition"
                            >
                                <td className="px-4 py-3 font-medium text-gray-800">
                                    {line.cptCode}
                                </td>
                                <td className="px-4 py-3">{line.description}</td>
                                <td className="px-4 py-3 text-right">${line.billedAmount}</td>
                                <td className="px-4 py-3 text-right">${line.allowedAmount}</td>
                                <td className="px-4 py-3 text-right">${line.copayApplied}</td>
                                <td className="px-4 py-3 text-right">${line.coinsuranceApplied}</td>
                                <td className="px-4 py-3 text-right font-semibold text-gray-800">
                                    ${line.memberResponsibility}
                                </td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan="7" className="px-4 py-4 text-center text-gray-500 italic">
                                No line items found
                            </td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </div>

            <div>
                <button
                    onClick={() => window.history.back()}
                    className="text-sky-600 hover:text-sky-800 font-medium transition"
                >
                    ← Back to Claims
                </button>
            </div>
        </div>
    );
}
