import axios from 'axios'

const instance = axios.create({
    baseURL: 'http://localhost:8080/api',
    timeout: 100000,
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
        // Do something with response data
        return response;
    },
    (error) => {
        if (error.response.status === 401) {
            localStorage.removeItem('accessToken')
            localStorage.removeItem('user')
            document.location.href = '/login'
    }
        return Promise.reject(error)
    }
)

export default instance