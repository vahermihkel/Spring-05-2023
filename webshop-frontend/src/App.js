import { Link, Route, Routes } from 'react-router-dom';
import './App.css';
import Homepage from './pages/Homepage';
import AddProduct from './pages/AddProduct';
import MaintainProducts from './pages/MaintainProducts';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';

function App() {
  return (
    <div className="App">
      <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
        <Container>
          <Navbar.Brand as={Link} to="/"><img className="logo" src="https://estonia.ee/wp-content/uploads/nobe_netist_4.jpg" alt="" /></Navbar.Brand>
          <Navbar.Toggle aria-controls="responsive-navbar-nav" />
          <Navbar.Collapse id="responsive-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link as={Link} to="/">Meist</Nav.Link>
              <Nav.Link as={Link} to="/">Meie poed</Nav.Link>
              <NavDropdown title="Admin" id="collasible-nav-dropdown">
                <NavDropdown.Item as={Link} to="/lisa">Lisa toode</NavDropdown.Item>
                <NavDropdown.Item as={Link} to="/halda">Halda tooteid</NavDropdown.Item>
                <NavDropdown.Item href="#action/3.3">Something</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item href="#action/3.4">
                  Separated link
                </NavDropdown.Item>
              </NavDropdown>
            </Nav>
            <Nav>
              <Nav.Link href="#deets">More deets</Nav.Link>
              <Nav.Link eventKey={2} href="#memes">
                Dank memes
              </Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      <Routes>
        <Route path="" element={ <Homepage /> } />
        <Route path="lisa" element={ <AddProduct /> } />
        <Route path="halda" element={ <MaintainProducts /> } />
      </Routes>
    </div>
  );
}

export default App;
