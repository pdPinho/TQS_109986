import client from "./client"

const cityService = {
    async getCities(){
        return await client.get(`/cities`)
    }
}

export default cityService;