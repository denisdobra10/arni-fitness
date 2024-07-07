import axios from 'axios'

const instance = axios.create({
    baseURL: 'http://localhost:8000',
    timeout: 10000,
})

instance.interceptors.request.use(
    (config) => {
        // Do something before request is sent
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
        return response
    },
    (error) => {
        // Do something with response error
        return Promise.reject(error)
    }
)

export default instance