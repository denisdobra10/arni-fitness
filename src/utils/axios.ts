import axios from 'axios'

const instance = axios.create({
    baseURL: 'http://localhost:8080/api',
    timeout: 10000,
    headers: {
        'Content-Type': 'application/json'
    }
})

instance.interceptors.request.use(
    (config) => {
        const accessToken = localStorage.getItem('accessToken')
        if (accessToken) {
            config.headers['Authorization'] = `Bearer ${accessToken}`
        }
        return config
    },
    (error) => {
        // Do something with request error
        return Promise.reject(error)
    }
)

instance.interceptors.response.use(
    (response) => {
        if (response.status === 401) {
            localStorage.removeItem('accessToken')
            localStorage.removeItem('user')
            // logout
            // redirectionez pe login
            // sters din local storage
        }
        return response
    },
    (error) => {
        // Do something with response error
        return Promise.reject(error)
    }
)

export default instance