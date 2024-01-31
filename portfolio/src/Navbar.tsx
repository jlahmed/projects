import './Navbar.css'; 

const Navbar = () => {
    return (
        <nav className="navbar">
            <div className="navbar-left">
                Jubayer Ahmed
            </div>
            <div className="navbar-right">
                <a href="#home">Home</a>
                <a href="#about">About</a>
                <a href="#projects">Projects</a>
                <a href="#contact">Contact</a>
            </div>
        </nav>
    );
};

export default Navbar;