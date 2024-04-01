import client from "./client"

const reservationService = {
    async createReservation(data){
        return await client.post(`/reservations`, data)
    },

    async getReservationById(id){
        return await client.get(`/reservations/${id}`, id)
    },

    async confirmReservation(id){
        return await client.post(`/reservations/${id}/pay`, id)
    },

    async getReservationsByUser(){
        return await client.get(`/reservations/user/1`)
    }
}

export default reservationService;