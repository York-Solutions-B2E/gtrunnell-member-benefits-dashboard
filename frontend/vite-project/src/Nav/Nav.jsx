import { NavLink } from 'react-router-dom'

function Nav() {

    return (
        <nav className="Nav">
            <div style={{ display: "flex", gap: "1rem" }}>
            <NavLink className="bg-sky-300 hover:bg-sky-500 text-white p-1 rounded" to="/">Login</NavLink>
            <NavLink className="bg-sky-300 hover:bg-sky-500 text-white p-1 rounded" to="/dashboard">Dashboard</NavLink>
            <NavLink className="bg-sky-300 hover:bg-sky-500 text-white p-1 rounded" to="/claims">Claims</NavLink>
            </div>
        </nav>
    );
}

export default Nav;
