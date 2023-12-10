USE `fitwsarah-db`;

create table if not exists accounts(
                                       id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       account_id VARCHAR(36) NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL
    );

create table if not exists admin_accounts(
                                             id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                             admin_id VARCHAR(35) NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL
    );

create table if not exists invoices(
                                       id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       invoice_id VARCHAR(36) NOT NULL,
    account_id VARCHAR(36) NOT NULL,
    amount DECIMAL NOT NULL,
    content VARCHAR(120) NOT NULL
    );

create table if not exists feeback_threads(
                                              id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                              feedback_id VARCHAR(36) NOT NULL,
    account_id VARCHAR(36) NOT NULL,
    stars INTEGER NOT NULL,
    content VARCHAR(120) NOT NULL
    );

create table if not exists appointments(
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    appointment_id VARCHAR(36) NOT NULL,
    admin_id VARCHAR(36) NOT NULL,
    account_id VARCHAR(36) NOT NULL,
    service_id VARCHAR(36) NOT NULL,
    status VARCHAR(50) NOT NULL,
    location VARCHAR(50) NOT NULL,
    available_status VARCHAR (50) NOT NULL,
    available_date VARCHAR (50) NOT NULL
    );

create table if not exists fitness_services(
                                               id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                               service_id VARCHAR(36) NOT NULL,
    promo_id VARCHAR(36) NOT NULL,
    title VARCHAR(50) NOT NULL,
    duration VARCHAR(50) NOT NULL,
    description VARCHAR(120) NOT NULL,
    price DECIMAL NOT NULL
    );

create table if not exists promo_offers(
                                           id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                           promotion_id VARCHAR(36) NOT NULL,
    title VARCHAR(50) NOT NULL,
    description VARCHAR(120) NOT NULL,
    price DECIMAL NOT NULL
    );

