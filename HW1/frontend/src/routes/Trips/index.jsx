import { useEffect, useState } from "react";
import tripService from '../../services/tripService'
import { useLocation, useNavigate } from "react-router-dom";

function Trips(){
    const [trips, setTrips] = useState({});
    const [dataload, setDataload] = useState(false);
    const params = new URLSearchParams(useLocation().search);
    const navigate = useNavigate();

    useEffect(() => {
        tripService.getTripByDateAndOriginAndDestination(params.get('date'), params.get('origin'), params.get('destination')).then((data) => {
            setTrips(data.data);
            setDataload(true);
        })
    }, []);

    return (
        <div className="h-full flex justify-center items-center">
            <div className="container pb-[15%]">
                <h3 className="text-2xl font-semibold text-center mb-3">AVAILABLE TRIPS</h3>
                <h5 className="flex flex-row mb-[5%] justify-center">
                    <span className="mr-[5%]">FROM: {dataload && trips[0].origin.name} </span>
                    <span>TO: {dataload && trips[0].destination.name}</span> 
                </h5>
                
                <div className="overflow-x-auto">
                    <table className="table-auto border-collapse w-full">
                        <thead>
                            <tr>
                                <th className="border px-4 py-2 w-1/4">Choose</th>
                                <th className="border px-4 py-2 w-1/4">Price</th>
                                <th className="border px-4 py-2 w-1/4">Occupancy</th>
                                <th className="border px-4 py-2 w-1/4">Capacity</th>
                            </tr>
                        </thead>
                        <tbody className="text-center">
                            {dataload && trips.map((trip, index) => (
                                <tr key={trip.id} className="border-t">
                                    <td className="border px-4 py-2">
                                        <button className="rounded-md bg-gray-300 p-2"
                                                onClick={() => {navigate('/details', {state: trip.id})}}>
                                            Choose this one
                                        </button>
                                    </td>
                                    <td className="border px-4 py-2">{trip.price} €</td>
                                    <td className="border px-4 py-2">{trip.occupancy}</td>
                                    <td className="border px-4 py-2">{trip.bus.capacity}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    )
}

export default Trips;