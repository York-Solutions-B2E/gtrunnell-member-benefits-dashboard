import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";

export default function ClaimDetailPage() {
    const { claimNumber } = useParams();
    const [claim, setClaim] = useState(null);
    const token = localStorage.getItem("token");

    useEffect(() => {
        if (!token || !claimNumber) return;
        axios.get(`http://localhost:8080/api/claims/${claimNumber}`, {
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(res => setClaim(res.data))
            .catch(err => console.error("Error fetching claim detail:", err));
    }, [token, claimNumber]);

    if (!token) return <p>Please log in first.</p>;
    if (!claim) return <p>Loading claim details...</p>;

    return (
        <div>
            <h2>Claim #{claim.claimNumber}</h2>
            <p><strong>Provider:</strong> {claim.providerName}</p>
            <p><strong>Service Dates:</strong> {claim.serviceStartDate} – {claim.serviceEndDate}</p>
            <p><strong>Status:</strong> {claim.status}</p>

            <h3>Financial Summary</h3>
            <ul>
                <li>Total Billed: ${claim.totalBilled}</li>
                <li>Allowed Amount: ${claim.totalAllowed}</li>
                <li>Plan Paid: ${claim.totalPlanPaid}</li>
                <li>Member Responsibility: ${claim.totalMemberResponsibility}</li>
            </ul>

            <h3>Line Items</h3>
            <table>
                <thead>
                <tr>
                    <th>CPT</th><th>Description</th><th>Billed</th><th>Allowed</th><th>Copay</th><th>Coins</th><th>You Owe</th>
                </tr>
                </thead>
                <tbody>
                {claim.lines.map(line => (
                    <tr key={line.lineNumber}>
                        <td>{line.cptCode}</td>
                        <td>{line.description}</td>
                        <td>{line.billedAmount}</td>
                        <td>{line.allowedAmount}</td>
                        <td>{line.copayApplied}</td>
                        <td>{line.coinsuranceApplied}</td>
                        <td>{line.memberResponsibility}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}
