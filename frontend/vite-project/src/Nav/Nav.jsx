import { NavLink } from 'react-router-dom'

function Nav() {

    return (
        <nav className="flex justify-center items-center py-4 border-b-2 border-sky-300">
            <div className="flex gap-4">
                <NavLink className="bg-sky-300 hover:bg-sky-500 text-white px-4 py-2 rounded transition" to="/">
                    Login
                </NavLink>
                <NavLink className="bg-sky-300 hover:bg-sky-500 text-white px-4 py-2 rounded transition" to="/dashboard">
                    Dashboard
                </NavLink>
                <NavLink className="bg-sky-300 hover:bg-sky-500 text-white px-4 py-2 rounded transition" to="/claims">
                    Claims
                </NavLink>
            </div>
        </nav>

    );
}

export default Nav;
