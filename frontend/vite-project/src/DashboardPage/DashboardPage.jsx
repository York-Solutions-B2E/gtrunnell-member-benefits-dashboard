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

    const progressBar = (used, limit) => {
        const percent = Math.min(used / limit, 1);
        const filled = Math.round(percent * 10);
        const empty = 10 - filled;
        return `[${"=".repeat(filled)}${"-".repeat(empty)}]`;
    };

    const handleSignOut = () => {
        localStorage.removeItem("token");
        window.location.href = "/"; // Forces a fresh page load
    };

    return (
        <div>
            <h1>Dashboard</h1>
            <div>
                <span>{fullName}</span> | <button onClick={handleSignOut}>Sign out</button>
            </div>
            <hr />
            <section>
                <h2>Active Plan</h2>
                <ul>
                    <li>• {planName}</li>
                    <li>• Network: {networkName}</li>
                    <li>• Coverage {planYear}</li>
                </ul>
            </section>
            <section>
                <h2>Accumulators</h2>
                <p>
                    Deductible: ${deductibleUsed} / ${deductibleLimit} {progressBar(deductibleUsed, deductibleLimit)}
                </p>
                <p>
                    OOP Max: ${oopUsed} / ${oopLimit} {progressBar(oopUsed, oopLimit)}
                </p>
            </section>
            <section>
                <h2>Recent Claims</h2>
                {recentClaims && recentClaims.length > 0 ? (
                    <ul>
                        {recentClaims.map((claim) => (
                            <li key={claim.claimNumber}>
                                #{claim.claimNumber} | {claim.status} | ${claim.memberResponsibility} |{" "}
                                {claim.providerName}
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>No recent claims found.</p>
                )}
            </section>
            <hr />
            <button onClick={() => (window.location.href = "/claims")}>View All Claims</button>
        </div>
    );
}
