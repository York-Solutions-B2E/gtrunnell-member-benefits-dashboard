import { NavLink } from 'react-router-dom'

function Nav() {

    return (
        <nav className="Nav">
            <div style={{ display: "flex", gap: "1rem" }}>
            <NavLink to="/">Login</NavLink>
            <NavLink to="/dashboard">Dashboard</NavLink>
            <NavLink to="/claims">Claims</NavLink>
            </div>
        </nav>
    );
}

export default Nav;
