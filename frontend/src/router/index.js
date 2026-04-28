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
import MyBookings from '../views/MyBookings.vue'
import Reviews from '../views/Reviews.vue'
import Blog from '../views/Blog.vue'
import Services from '../views/Services.vue'
import StaffManagement from '../views/StaffManagement.vue'
import ManageServices from '../views/ManageServices.vue'
import CancellationPolicy from '../views/CancellationPolicy.vue'

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
    path: '/my-bookings', 
    component: MyBookings,
    meta: { requiresAuth: true }
  },
  { 
    path: '/blog', 
    component: Blog
  },
  {
    path: '/staff',
    component: StaffManagement,
    meta: { requiresAuth: true }
  },
  {
    path: '/manage-services',
    component: ManageServices,
    meta: { requiresAuth: true }
  },
  {
    path: '/cancellation-policy',
    component: CancellationPolicy
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
  const user = JSON.parse(localStorage.getItem('user'))

  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!user) {
      next('/login')
    } else {
      // Check for specific route permissions
      if ((to.path === '/dashboard' || to.path === '/staff') && user.role !== 'ADMIN') {
        next('/') // Redirect non-admins away from admin routes
      } else {
        next()
      }
    }
  } else {
    next()
  }
})

export default router
