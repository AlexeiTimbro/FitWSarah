USE `fitwsarah-db`;

create table if not exists accounts(
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(100) NOT NULL,
    account_id VARCHAR(150) NOT NULL,
    username VARCHAR(50) ,
    email VARCHAR(50) ,
    city VARCHAR(50)
);

create table if not exists invoices(
                                       id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       invoice_id VARCHAR(36) NOT NULL,
    account_id VARCHAR(36) NOT NULL,
    user_id VARCHAR(100) NOT NULL,
    username VARCHAR(50),
    status VARCHAR(50) NOT NULL,
    date DATETIME NOT NULL,
    due_date DATETIME NOT NULL,
    payment_type VARCHAR(50),
    price DECIMAL(10, 2) NOT NULL
    );

create table if not exists feedback_threads(
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    feedback_id VARCHAR(36) NOT NULL,
    account_id VARCHAR(36) NOT NULL,
    stars INTEGER NOT NULL,
    content VARCHAR(120) NOT NULL,
    status VARCHAR(50) NOT NULL
);

create table if not exists appointments(
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    appointment_id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36),
    availability_id VARCHAR(36),
    service_id VARCHAR(36) NOT NULL,
    status VARCHAR(50) NOT NULL,
    location VARCHAR(50),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone_num VARCHAR(50),
    date VARCHAR(50),
    time VARCHAR(50)
);



create table if not exists fitness_services(
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    service_id VARCHAR(36) NOT NULL,
    promo_id VARCHAR(36),
    title_en VARCHAR(50) NOT NULL,
    title_fr VARCHAR(50) NOT NULL,
    duration VARCHAR(50) NOT NULL,
    description_en VARCHAR(120) NOT NULL,
    description_fr VARCHAR(120) NOT NULL,
    other_information_en VARCHAR(300) NOT NULL,
    other_information_fr VARCHAR(300) NOT NULL,
    price DECIMAL NOT NULL
);

create table if not exists promo_offers(
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    promotion_id VARCHAR(36) NOT NULL,
    title VARCHAR(50) NOT NULL,
    availability TINYINT(1) NOT NULL,
    description VARCHAR(120) NOT NULL,
    price DECIMAL NOT NULL
);

create table if not exists availabilities(
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    availability_id VARCHAR(36) NOT NULL,
    available TINYINT(1) NOT NULL,
    account_id VARCHAR(36) NOT NULL,
    datetime DATETIME NOT NULL
);

create table if not exists coach_notes(
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    coach_note_id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    content_en VARCHAR(120) NOT NULL,
    content_fr VARCHAR(120) NOT NULL
);