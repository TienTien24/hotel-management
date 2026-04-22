import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Dashboard from '../views/Dashboard.vue'
import RoomList from '../views/RoomList.vue'
import BookingList from '../views/BookingList.vue'
import Register from '../views/Register.vue'
import Home from '../views/Home.vue'
import About from '../views/About.vue'
import Contact from '../views/Contact.vue'

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
    path: '/dashboard', 
    component: Dashboard,
    meta: { requiresAuth: true }
  },
  { 
    path: '/rooms', 
    component: RoomList,
    meta: { requiresAuth: true }
  },
  { 
    path: '/bookings', 
    component: BookingList,
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
