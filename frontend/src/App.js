import './App.css';
import './AboutView.css';
import './HomeView.css';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import HomeView from './HomeView';
import AboutView from './AboutView';

function App() {
  return (
    <Router>
      <div className="App">
        {/* Navigation Links */}
        <nav>
          <ul>
            <li>
              <Link to="/">Rent</Link>
            </li>
            <li>
              <Link to="/about">Admin</Link>
            </li>
          </ul>
        </nav>

        {/* Route configuration */}
        <Routes>
          <Route path="/" element={<HomeView />} />
          <Route path="/about" element={<AboutView />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
