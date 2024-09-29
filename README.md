# Fortnox-Car-Rental
Assuming you have Maven, Java 17, Docker, Node 16 LTS, NPM installed and have started the database. You will need to do the following things to Set up the system to run as designed.

Create the following SQL tabels in the database:

CREATE TABLE car (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    daily_rate DECIMAL(10, 2) NOT NULL
);

CREATE TABLE rental (
    id BIGSERIAL PRIMARY KEY,
    driver_name VARCHAR(255) NOT NULL,
    driver_age INT NOT NULL,
    car_id BIGINT NOT NULL,
    from_date DATE NOT NULL,
    to_date DATE NOT NULL,
    revenue DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (car_id) REFERENCES car(id)
);

Then you need to insert the following data into the table : 
INSERT INTO car (id, name, daily_rate)
VALUES
    (1, 'Volvo S60', 1500.00),
    (2, 'Volkswagen Golf', 1333.00),
    (3, 'Ford Mustang', 3000.00),
    (4, 'Ford Transit', 2400.00);

Assuming you have all the relevant components up and running, you should now be able to rent a car and see the overview.
