import client from "./client"

const currencyService = {
    async getPrice(currency, price){
        return await client.get(`/currency/${currency}?price=${price}`)
    }
}

export default currencyService;