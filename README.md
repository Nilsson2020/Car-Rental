As long as you have the bootstap set up ofMaven, Java 17, Docker ( for easy setup of database ), Node 16 LTS and NPM installed.

You can start the data base with the same set up as the bootstrap:
docker run --name postgresql -p 5432:5432 -e POSTGRESQL_USERNAME=my_user 
-e POSTGRESQL_PASSWORD=password123 -e POSTGRESQL_DATABASE=rental bitnami/postgresql:latest

Then you have to create the DB tabels using this code in either commandline or a UI like pgAdmin : 

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

Then you need to insert the following data into the car table: 
INSERT INTO car (id, name, daily_rate) VALUES
(1, 'Volvo S60', 1500.00),
(2, 'Volkswagen Golf', 1333.00),
(3, 'Ford Mustang', 3000.00),
(4, 'Ford Transit', 2400.00);


**Then you run the RentalApplication class
**

Then you write:

cd frontend then npm install then npm start

Following this should allow you to get the application running on your localhost  

