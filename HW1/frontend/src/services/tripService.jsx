import client from "./client"

const tripService = {
    async getTripByDateAndOriginAndDestination(date, origin, destination){
        return await client.get(`/trips/chosen?date=${date}&origin=${origin}&destination=${destination}&all=false`)
    },

    async getTripById(id){
        return await client.get(`/trips/${id}`)
    }
}

export default tripService;