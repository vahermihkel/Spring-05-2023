import { Link, useNavigate } from 'react-router-dom';
import React, { useContext } from 'react'
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { useTranslation } from 'react-i18next';
import { CartSumContext } from '../store/CartSumContext';
import { AuthContext } from '../store/AuthContext';

function NavigationBar() {
  const { t, i18n } = useTranslation();
  const { cartSum } = useContext(CartSumContext);
  const { loggedIn, setLoggedIn, loggedInUser, emptyUser } = useContext(AuthContext);
  const navigate = useNavigate();

  const updateLanguage = (newLang) => {
    i18n.changeLanguage(newLang);
    localStorage.setItem("language", newLang);
  }

  const logout = () => {
    setLoggedIn(false);
    sessionStorage.removeItem("token");
    navigate("/");
    emptyUser();
  }

  return (
    <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
      <Container>
        <Navbar.Brand as={Link} to="/">Webshop</Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="me-auto">
            {loggedInUser.admin === true && <Nav.Link as={Link} to="/admin">{t("nav.admin")}</Nav.Link>}
            <Nav.Link as={Link} to="/shops">{t("nav.shops")}</Nav.Link>
            <Nav.Link as={Link} to="/cart">{t("nav.cart")}</Nav.Link>
            <Nav.Link as={Link} to="/order">{t("nav.orders")}</Nav.Link>
            {loggedIn === false ? 
              <>
                <Nav.Link as={Link} to="/login">Login</Nav.Link>
                <Nav.Link as={Link} to="/signup">Signup</Nav.Link>
              </>
                : 
              <>
              <Nav.Link as={Link} to="/profile">Profile</Nav.Link>
              {loggedInUser.email !== undefined && <div>Oled sise logitud: {loggedInUser.email} {loggedInUser.firstName}</div>}
                <button onClick={logout}>Logi välja</button>
              </>
              }
          </Nav>
          
          {/* {loggedIn === false && <button onClick={() => setLoggedIn(true)}>Logi sisse</button>}
          {loggedIn === true && <button onClick={() => setLoggedIn(false)}>Logi välja</button>} */}
          
          <div>{cartSum} €</div>
          <img className="lang" src="/estonian.png" alt="" onClick={() => updateLanguage("en")} />
          <img className="lang" src="/english.png" alt="" onClick={() => updateLanguage("ee")} />
        </Navbar.Collapse>
      </Container>
    </Navbar>
  )
}

export default NavigationBar