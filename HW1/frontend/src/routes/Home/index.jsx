import DatePicker from 'react-datepicker';
import { useState, useEffect } from 'react';
import 'react-datepicker/dist/react-datepicker.css';
import cityService from '../../services/cityService';
import {Link} from 'react-router-dom';


function Home() {
    const [startDate, setStartDate] = useState(null);
    const today = new Date();
    const [cities, setCities] = useState({});
    const [dataload, setDataload] = useState(false);
    const [origin, setOrigin] = useState(null);
    const [destination, setDestination] = useState(null);

    useEffect(() => {
      cityService.getCities().then((data) => {
        setCities(data.data);
        setDataload(true);
      })
    }, []);

    return (
        <div>
            <div className="text-2xl font-semibold w-full text-center mt-[10%]">TQS BUS WEBPAGE</div>
            <div className="items-center flex flex-col mt-[5%]">
            <select id="origin" className="mb-[3%] w-[220px] text-center h-12 rounded-md" onChange={(event) => setOrigin(event.target.value)}>
                <option value="" disabled selected hidden>Select Origin</option>
                {dataload && cities.slice(0,2).map((city, index) => (
                    <option key={index} value={city.id}>{city.name}</option>
                ))}
            </select>
            <select id="destination" className="mb-[3%] border-1 w-[220px] text-center h-12 rounded-md" onChange={(event) => setDestination(event.target.value)}>
                <option value="" disabled selected hidden>Select Destination</option>
                {dataload && cities.slice(2,4).map((city, index) => (
                    <option key={"destination" + index}value={city.id}>{city.name}</option>
                ))}
            </select>
            <DatePicker
                selected={startDate}
                onChange={date => setStartDate(date)}
                className="border-2"
                dateFormat="yyyy-MM-dd"
                placeholderText="Select Date"
                minDate={today}
            />
            {startDate && origin && destination && (<Link
                to={{
                    pathname: "/trips",
                    search: `?date=${startDate.toISOString().split('T')[0]}&origin=${origin}&destination=${destination}`
                }}
                >
                <button id="submit" className="rounded-md bg-blue-500 mt-10 w-20 h-10">Find trips</button>
            </Link>
            )}
            </div>
        </div>
    )
}


export default Home