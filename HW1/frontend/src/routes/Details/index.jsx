import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom"
import tripService from "../../services/tripService";

function Details(){
    const location = useLocation();
    const [dataload, setDataload] = useState(false);
    const [tripInfo, setTripInfo] = useState({});
    const [currency, setCurrency] = useState("EUR");

    useEffect(() => {
        tripService.getTripById(location.state).then((data) => {
            setTripInfo(data.data);
            setDataload(true);
        })
    }, []);

    console.log(tripInfo);
    return (
        <div className="h-full flex justify-center items-center">
            {dataload && <div className="container pb-[15%] w-[40%] mr-auto ml-auto">
                <h3 className="text-2xl font-semibold text-center mb-3">Provide us with your information to complete it</h3>
                <hr className="my-4" />
                <p className="mb-4">PRICE: <span className="text-2xl ml-3 font-semibold">914.76 {currency}</span></p>

                <form method="POST">
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
                    onChange={(event) => setCurrency(event.target.value)}>
                        <option value="EUR">EUR</option>
                        <option value="USD">USD</option>
                        <option value="GBP">GBP</option>
                    </select>
                    </div>
                    <div className="mb-4">
                        <input type="submit" value="Purchase Flight" className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" />
                    </div>
                </form>
            </div>
        }
        </div>
    )
}

export default Details