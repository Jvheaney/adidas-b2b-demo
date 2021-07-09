import './css/Navbar.css';

function Navbar() {
  return (
    <div className="Navbar">
      <a rel="noreferrer" className="StripAnchor" href="https://jvheaney.com">
        <span className="LogoText">adidas<span className="Bold">B2B</span></span>
      </a>
      <a rel="noreferrer" target="_blank" className="StripAnchor" href="https://github.com/Jvheaney">
        <span className="LogoText">View on <span className="Bold">GitHub</span></span>
      </a>
    </div>
  );
}

export default Navbar;
