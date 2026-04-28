import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Dashboard from '../views/Dashboard.vue'
import RoomList from '../views/RoomList.vue'
import BookingList from '../views/BookingList.vue'
import Register from '../views/Register.vue'
import Home from '../views/Home.vue'
import About from '../views/About.vue'
import Contact from '../views/Contact.vue'
import RoomDetail from '../views/RoomDetail.vue'
import Checkin from '../views/Checkin.vue'
import MyBookings from '../views/MyBookings.vue'
import Reviews from '../views/Reviews.vue'
import Services from '../views/Services.vue'
import ManageServices from '../views/ManageServices.vue'

const routes = [
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  {
    path: '/',
    component: Home
  },
  {
    path: '/about',
    component: About
  },
  {
    path: '/contact',
    component: Contact
  },
  {
    path: '/services',
    component: Services
  },
  { 
    path: '/rooms/:id', 
    component: RoomDetail
  },
  { 
    path: '/dashboard', 
    component: Dashboard,
    meta: { requiresAuth: true }
  },
  { 
    path: '/rooms', 
    component: RoomList
  },
  { 
    path: '/bookings', 
    component: BookingList,
    meta: { requiresAuth: true }
  },
  { 
    path: '/checkin', 
    component: Checkin,
    meta: { requiresAuth: true }
  },
  { 
    path: '/my-bookings', 
    component: MyBookings,
    meta: { requiresAuth: true }
  },
  { 
    path: '/blog', 
    component: Reviews
  },
  { 
    path: '/manage-services', 
    component: ManageServices,
    meta: { requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (to.hash) {
      return {
        el: to.hash,
        behavior: 'smooth',
      }
    }
    return { top: 0 }
  }
})

router.beforeEach((to, from, next) => {
  const loggedIn = localStorage.getItem('user')

  if (to.matched.some(record => record.meta.requiresAuth) && !loggedIn) {
    next('/login')
  } else {
    next()
  }
})

export default router
