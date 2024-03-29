import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom"
import tripService from "../../services/tripService";
import reservationService from "../../services/reservationService";
import userService from "../../services/userService";

function Details(){
    const location = useLocation();
    const [dataload, setDataload] = useState(false);
    const [tripInfo, setTripInfo] = useState({});
    const [currency, setCurrency] = useState("EUR");
    const [reservationId, setReservationId] = useState();
    const [reservationData, setReservationData] = useState({
        status: 'awaiting-payment',
        currency: 'EUR',
        trip: '',
        user: '',
    });

    const navigate = useNavigate();


    useEffect(() => {
        tripService.getTripById(location.state).then((data) => {
            setTripInfo(data.data);
            setDataload(true);
            setReservationData(prevData => ({
                ...prevData,
                trip: data.data
            }));

            userService.getUserById(1).then((data) => {
                const user = data.data;
                setReservationData(prevData => ({
                    ...prevData,
                    user: user
                }));
            });
    });
    
    }, []);

    console.log(reservationData);

    const submit = () => {
        reservationService.createReservation(reservationData).then((response) => {
            setReservationId(response.data.id);
            console.log(response);
            //navigate('/confirm', { state: response.data.id });
        })
        .catch((error) => {
            console.error('Error creating reservation:', error);
        });
    };

    return (
        <div className="h-full flex justify-center items-center">
            {dataload && <div className="container pb-[15%] w-[40%] mr-auto ml-auto">
                <h3 className="text-2xl font-semibold text-center mb-3">Provide us with your information to complete it</h3>
                <hr className="my-4" />
                <p className="mb-4">PRICE: <span className="text-2xl ml-3 font-semibold">914.76 {currency}</span></p>

                <div className="mb-4">
                    <label className="block text-gray-700 text-sm font-bold mb-2">Name</label>
                    <input id="inputName" name="inputName" type="text" placeholder="Enter your name here" 
                    className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" />
                </div>
                <div className="mb-4">
                    <label className="block text-gray-700 text-sm font-bold mb-2">Phone</label>
                    <input id="address" name="address" type="text" placeholder="Enter your phone number here" 
                    className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" />
                </div>
                <div className="mb-4 w-[15%]">
                <label className="block text-gray-700 text-sm font-bold mb-2">Select Currency</label>
                <select id="currency"
                className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                onChange={(event) => { 
                    setCurrency(event.target.value);
                    setReservationData(prevState => ({ 
                                                    ...reservationData, 
                                                    currency: event.target.value 
                                                }));
                }}>
                    <option value="EUR">EUR</option>
                    <option value="USD">USD</option>
                    <option value="GBP">GBP</option>
                </select>
                </div>
                <div className="mb-4"> 
                    <button onClick={() => {submit()}} 
                            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" >
                                Reserve Trip
                    </button>
                </div>
            </div>
        }
        </div>
    )
}

export default Details