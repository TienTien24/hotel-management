import axios from 'axios'

const instance = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json'
  }
})

instance.interceptors.request.use(
  (config) => {
    const userStr = localStorage.getItem('user')
    console.log('Axios Interceptor - User in localStorage:', userStr ? 'Found' : 'Not Found')
    const user = userStr ? JSON.parse(userStr) : null
    if (user && user.token) {
      config.headers['Authorization'] = 'Bearer ' + user.token
      console.log('Axios Interceptor - Added Authorization header for:', config.url)
    } else {
      console.warn('Axios Interceptor - No token found for:', config.url)
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
      // Thay vì chuyển hướng ngay lập tức, hãy để component xử lý và hiển thị thông báo lỗi
      // localStorage.removeItem('user')
      // window.location.href = '/login'
      console.warn('Lỗi 401: Chưa xác thực hoặc hết hạn');
    }
    return Promise.reject(error)
  }
)

export default instance
