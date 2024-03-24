import DatePicker from 'react-datepicker';
import { useState } from 'react';
import 'react-datepicker/dist/react-datepicker.css';

function Root() {
  const [startDate, setStartDate] = useState(null);
  const today = new Date();

    return (
      <div className="h-[100vh] w-full">
        <div className="text-2xl font-semibold w-full text-center mt-[10%]">TQS BUS WEBPAGE</div>
        <div className="items-center flex flex-col mt-[5%]">
          <select id="origin" className="mb-[3%] w-[220px] text-center h-12 rounded-md">
            <option value="" disabled selected hidden>Select Origin</option>
            <option value="origin1">Origin 1</option>
            <option value="origin2">Origin 2</option>
            <option value="origin3">Origin 3</option>
          </select>
          <select id="destination" className="mb-[3%] border-1 w-[220px] text-center h-12 rounded-md">
            <option value="" disabled selected hidden>Select Destination</option>
            <option value="destination1">Destination 1</option>
            <option value="destination2">Destination 2</option>
            <option value="destination3">Destination 3</option>
          </select>
          <DatePicker
            selected={startDate}
            onChange={date => setStartDate(date)}
            className="border-2"
            dateFormat="MM/dd/yyyy"
            placeholderText="Select Date"
            minDate={today}
          />
          <button id="submit" className="rounded-md bg-blue-500 mt-10 w-20 h-10">Submit</button>
        </div>
      </div>
    );
}

export default Root;