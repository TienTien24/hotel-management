import axios from 'axios'

const instance = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json'
  }
})

instance.interceptors.request.use(
  (config) => {
    const user = JSON.parse(localStorage.getItem('user'))
    if (user && user.token) {
      config.headers['Authorization'] = 'Bearer ' + user.token
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

instance.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      localStorage.removeItem('user')
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

export default instance
