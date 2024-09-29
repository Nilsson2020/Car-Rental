import React, { useState, useEffect } from 'react';

function HomeView() {
  const [carId, setCarId] = useState('');
  const [pickupDate, setPickupDate] = useState('');
  const [returnDate, setReturnDate] = useState('');
  const [driverName, setDriverName] = useState('');
  const [driverAge, setDriverAge] = useState('');
  const [errors, setErrors] = useState({});
  const [cost, setCost] = useState('');
  const [cars, setCars] = useState([]);
  const [loading, setLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState('');

  // Hämtar data från backend
  useEffect(() => {
    const fetchCars = async () => {
      try {
        const response = await fetch('/api/cars/available');
        if (!response.ok) {
          throw new Error('Failed to fetch cars');
        }
        const carData = await response.json();
        setCars(carData);
      } catch (error) {
        setErrorMessage(error.message);
        console.error('Error fetching car data:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchCars();
  }, []);

  useEffect(() => {
    if (!carId || !pickupDate || !returnDate) {
      setCost('');
      return;
    }

    const selectedCar = cars.find((car) => car.id === parseInt(carId));
    if (!selectedCar) {
      setCost('');
      return;
    }

    const days = calculateDaysDifference(pickupDate, returnDate);
    if (days > 0) {
      const totalCost = calculateCost(days, selectedCar.dailyRate);
      setCost(`${totalCost} SEK`);
    } else {
      setCost('');
    }
  }, [carId, pickupDate, returnDate, cars]);

  const calculateCost = (days, dailyRate) => {
    return days > 0 ? days * dailyRate : 0;
  };

  const calculateDaysDifference = (startDate, endDate) => {
    const start = new Date(startDate);
    const end = new Date(endDate);

    if (isNaN(start.getTime()) || isNaN(end.getTime())) return -1;

    const differenceInMs = end - start;
    const differenceInDays = differenceInMs / (1000 * 60 * 60 * 24);
    return differenceInDays >= 0 ? Math.ceil(differenceInDays) : -1; // gör så att man kan hyra samma dag utan att få fel pris
  };

  const resetTime = (date) => {
    const newDate = new Date(date);
    newDate.setHours(0, 0, 0, 0); // Så att det inte påverkar när på en dag man kan hyra
    return newDate;
  };

  const validateForm = () => {
    const newErrors = {};
    const today = resetTime(new Date());

        // Alla error medelanden
    if (!carId) newErrors.car = 'Please select a car.';
    if (!pickupDate) {
      newErrors.pickupDate = 'Please select a pickup date.';
    } else if (resetTime(new Date(pickupDate)) < today) {
      newErrors.pickupDate = 'Pickup date cannot be in the past.';
    }

    if (!returnDate) {
      newErrors.returnDate = 'Please select a return date.';
    } else if (new Date(returnDate) <= new Date(pickupDate)) {
      newErrors.returnDate = 'Return date must be after pickup date.';
    }

    if (!driverName) {
      newErrors.driverName = 'Please enter your name.';
    } else if (/\d/.test(driverName)) {
      newErrors.driverName = 'Name cannot contain numbers.';
    }

    if (!driverAge) {
      newErrors.driverAge = 'Please enter your age.';
    } else if (driverAge < 18) {
      newErrors.driverAge = 'Driver must be at least 18 years old.';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

// Skickar datan till databasen
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (validateForm()) {
      const formData = { carId, pickupDate, returnDate, driverName, driverAge };
      try {
        const response = await fetch('/api/rentals/rent', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(formData),
        });
        const result = await response.text();
        if (response.ok) {
          alert(`Success: ${result}`);
          resetForm();
        } else {
          alert(result);
        }
      } catch (error) {
        console.error('Error submitting rental form:', error);
      }
    }
  };

  const resetForm = () => {
    setCarId('');
    setPickupDate('');
    setReturnDate('');
    setDriverName('');
    setDriverAge('');
    setCost('');
    setErrors({});
  };

  return (
    <div>
      <h2>Nox Car Rental</h2>
      {loading ? (
        <p>Loading available cars...</p>
      ) : errorMessage ? (
        <p style={{ color: 'red' }}>{errorMessage}</p>
      ) : (
        <form onSubmit={handleSubmit}>
          {/* Car selection */}
          <div>
            <label>Car: </label>
            <select value={carId} onChange={(e) => setCarId(e.target.value)}>
              <option value="">Select a car</option>
              {cars.map((carOption) => (
                <option key={carOption.id} value={carOption.id}>
                  {carOption.name} - {carOption.dailyRate} SEK/day
                </option>
              ))}
            </select>
            {errors.car && <span className="error">{errors.car}</span>}
          </div>
          {/* Pickup date */}
          <div>
            <label>Pickup Date: </label>
            <input
              type="date"
              value={pickupDate}
              onChange={(e) => setPickupDate(e.target.value)}
            />
            {errors.pickupDate && <span className="error">{errors.pickupDate}</span>}
          </div>
          {/* Return date */}
          <div>
            <label>Return Date: </label>
            <input
              type="date"
              value={returnDate}
              onChange={(e) => setReturnDate(e.target.value)}
            />
            {errors.returnDate && <span className="error">{errors.returnDate}</span>}
          </div>
          {/* Driver name */}
          <div>
            <label>Driver Name: </label>
            <input
              type="text"
              value={driverName}
              onChange={(e) => setDriverName(e.target.value)}
            />
            {errors.driverName && <span className="error">{errors.driverName}</span>}
          </div>
          {/* Driver age */}
          <div>
            <label>Driver Age: </label>
            <input
              type="number"
              value={driverAge}
              onChange={(e) => setDriverAge(e.target.value)}
            />
            {errors.driverAge && <span className="error">{errors.driverAge}</span>}
          </div>
          {/* Cost summary */}
          <div>
            <strong>Estimated Total Cost: </strong>
            <span>{cost}</span>
          </div>
          {/* Submit button */}
          <div>
            <button type="submit">Rent Car</button>
          </div>
        </form>
      )}
    </div>
  );
}

export default HomeView;
