import React from 'react';
import './footerNotLoggedIn.css'; 

function FooterNotLoggedIn() {
  return (
    <footer className="main-footer">
            <div className="footer-content">
                <a href="/about">About Me</a>
                <a href="/contact">Contact Me</a>
                <p>Based in Montreal, Canada</p>
            </div>
            <p className="copy-info">
                ©Copyright 2023 All rights reserved. Powered by TheMontrealGoats
            </p>
        </footer>
  );
}

export default FooterNotLoggedIn;