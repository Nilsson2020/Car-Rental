import React, { Component } from 'react';

class AboutView extends Component {
  state = {
    rentedCars: [],
    totalRevenue: 0
  };

  async componentDidMount() {
    await this.fetchRentedCars();
  }

  async fetchRentedCars() {
    try {
      const response = await fetch('/api/rentals/overview');
      const data = await response.json();

      const totalRevenue = data.reduce((sum, rental) => sum + rental.revenue, 0);

      this.setState({ rentedCars: data, totalRevenue: totalRevenue });
    } catch (error) {
      console.error("Error fetching rented cars data", error);
    }
  }

  render() {
    const { rentedCars, totalRevenue } = this.state;

    return (
      <div className="AboutView">
        <header className="App-header">
          <h1>Overview of Rented Cars</h1>
        </header>

        {/* Table for displaying rented cars */}
        <table>
          <thead>
            <tr>
              <th>Driver Name</th>
              <th>Car</th>
              <th>From Date</th>
              <th>To Date</th>
              <th>Revenue</th>
            </tr>
          </thead>
          <tbody>
            {rentedCars.length > 0 ? (
              rentedCars.map((rental, index) => (
                <tr key={index}>
                  <td>{rental.driverName}</td>
                  <td>{rental.carName}</td> {/* Display car name */}
                  <td>{rental.fromDate}</td>
                  <td>{rental.toDate}</td>
                  <td>{rental.revenue} SEK</td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="5">No rented cars available.</td>
              </tr>
            )}
          </tbody>
        </table>
        {/* Total Revenue displayed below the table */}
        <footer className="App-footer">
          <h3>Total Revenue Generated: {totalRevenue} SEK</h3>
        </footer>
      </div>
    );
  }
}

export default AboutView;
