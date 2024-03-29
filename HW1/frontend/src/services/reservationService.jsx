import client from "./client"

const reservationService = {
    async createReservation(data){
        return await client.post(`/reservations`, data)
    },

    async getReservationById(id){
        return await client.get(`/reservations/${id}`, id)
    }
}

export default reservationService;