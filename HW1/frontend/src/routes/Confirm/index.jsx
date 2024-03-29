import { useEffect, useState } from "react"
import reservationService from "../../services/reservationService";
import { useLocation } from "react-router-dom";

function Confirm(){
    const location = useLocation();
    const [reservationData, setReservationData] = useState({});
    const [dataload, setDataload] = useState(false);

    console.log(reservationData);

    useEffect(() => {
        reservationService.getReservationById(location.state).then((data) => {
            setReservationData(data.data);
            setDataload(true);
        });
    }, []);


    
    return(
        <div className="h-[50%] flex justify-center items-center flex-col">
            <div className="text-3xl">Details about your reservation below</div>
            {dataload && 
            <div className="flex flex-col mt-5">
                <div>Status: <span className="text-xl font-medium">{reservationData.status}</span></div>
                <div>Currency: <span>{reservationData.currency}</span></div>
                <div>Price: <span>{reservationData.trip.price}</span></div>
                <div>From: <span>{reservationData.trip.origin.name}</span></div>
                <div>To: <span>{reservationData.trip.destination.name}</span></div>
                <div>Occupancy: <span>{reservationData.trip.occupancy}</span></div>
                <div className="mt-4"> 
                    <button 
                            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" >
                                Confirm payment
                    </button>
                </div>
            </div>
            }
        </div>
    )
}

export default Confirm