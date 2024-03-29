import client from "./client"

const userService = {
    async getUserById(id){
        return await client.get(`/users/${id}`, id)
    }
}

export default userService;