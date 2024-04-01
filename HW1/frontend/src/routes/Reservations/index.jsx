import { useEffect, useState } from "react"
import reservationService from "../../services/reservationService";


function Reservations() {

    const [userReservations, setUserReservations] = useState([]);
    const [dataLoaded, setDataLoaded] = useState(false);

    useEffect(() => {
        reservationService.getReservationsByUser().then((data) => {
            setUserReservations(data.data);
            setDataLoaded(true);
        })
    }, []);

    return(
        <div className="h-full flex justify-center items-center">
            <div className="container pb-[15%]">
            <thead>
                <tr>
                <th className="border px-4 py-2 w-1/4">Status</th>
                <th className="border px-4 py-2 w-1/4">Price</th>
                <th className="border px-4 py-2 w-1/4">Currency</th>
                <th className="border px-4 py-2 w-1/4">Origin</th>
                <th className="border px-4 py-2 w-1/4">Destination</th>
                </tr>
            </thead>
            <tbody className="text-center">
            {dataLoaded && userReservations.map((reservation, index) => (
                 <tr key={reservation.id} className="border-t">
                <td id={"reservation-" + reservation.id}className="border px-4 py-2 font-bold bg-slate-300">{reservation.status}</td>
                <td className="border px-4 py-2">{reservation.price}</td>
                <td className="border px-4 py-2">{reservation.currency}</td>
                <td className="border px-4 py-2">{reservation.trip?.origin?.name}</td>
                <td className="border px-4 py-2">{reservation.trip?.destination?.name}</td>
             </tr>
            ))}
            </tbody>
            </div>
        </div>
    )
}

export default Reservations