import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom"
import tripService from "../../services/tripService";
import reservationService from "../../services/reservationService";
import userService from "../../services/userService";
import currencyService from "../../services/currencyService";

function Details(){
    const location = useLocation();
    const [dataload, setDataload] = useState(false);
    const [currency, setCurrency] = useState("USD");
    const [price, setPrice] = useState();
    const [reservationData, setReservationData] = useState({
        status: 'awaiting-payment',
        currency: 'USD',
        trip: '',
        user: '',
    });

    const navigate = useNavigate();


    useEffect(() => {
        tripService.getTripById(location.state).then((data) => {
            setDataload(true);
            setPrice(data.data.price);
            setReservationData(prevData => ({
                ...prevData,
                trip: data.data,
                price: data.data.price
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

    const submit = () => {
        reservationService.createReservation(reservationData).then((data) => {
            navigate('/confirm', { state: data.data });
        })
        .catch((error) => {
            console.error('Error creating reservation:', error);
        });
    };

    console.log(reservationData);

    const handleCurrencyChange = async (event) => {
        const selectedCurrency = event.target.value;
        setCurrency(selectedCurrency);
        setReservationData(prevState => ({ ...prevState, currency: selectedCurrency }));

        currencyService.getPrice(selectedCurrency, reservationData.trip.price).then((data) => {
            setPrice(data.data);
            setReservationData(prevState => ({ ...prevState, price: data.data }));
        });
    };

    return (
        <div className="h-full flex justify-center items-center">
            {dataload && <div className="container pb-[15%] w-[40%] mr-auto ml-auto">
                <h3 className="text-2xl font-semibold text-center mb-3">Provide us with your information to complete it</h3>
                <hr className="my-4" />
                <p className="mb-4">PRICE: <span className="text-2xl ml-3 font-semibold">{price} {currency}</span></p>

                <div className="mb-4">
                    <label className="block text-gray-700 text-sm font-bold mb-2">
                        Name{' '}
                        <input id="inputName" name="inputName" type="text" placeholder="Enter your name here" className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" />
                    </label>
                </div>
                <div className="mb-4">
                    <label className="block text-gray-700 text-sm font-bold mb-2">
                        Phone{' '}
                        <input id="address" name="address" type="text" placeholder="Enter your phone number here" className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" />
                    </label>
                </div>
                <div className="mb-4 w-[15%]">
                <label className="block text-gray-700 text-sm font-bold mb-2">
                    Select Currency{' '}
                    <select id="currency" className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" onChange={handleCurrencyChange} value={currency}>
                        <option value="USD">USD</option>
                        <option value="EUR">EUR</option>
                        <option value="GBP">GBP</option>
                    </select>
                </label>
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