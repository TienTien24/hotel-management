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
import StaffDashboard from '../views/StaffDashboard.vue'
import StaffRoomManagement from '../views/StaffRoomManagement.vue'
import ManageServices from '../views/ManageServices.vue'
import CancellationPolicy from '../views/CancellationPolicy.vue'
import StaffMessages from '../views/StaffMessages.vue'
import AdminDashboard from '../views/AdminDashboard.vue'
import AdminStaff from '../views/AdminStaff.vue'
import AdminCustomers from '../views/AdminCustomers.vue'
import AdminReports from '../views/AdminReports.vue'
import AdminServices from '../views/AdminServices.vue'
import AdminRooms from '../views/AdminRooms.vue'
import AdminSettings from '../views/AdminSettings.vue'

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
    path: '/staff-dashboard', 
    component: StaffDashboard,
    meta: { requiresAuth: true }
  },
  { 
    path: '/admin-dashboard', 
    component: AdminDashboard,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  { 
    path: '/admin/staff', 
    component: AdminStaff,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  {
    path: '/admin/rooms',
    component: AdminRooms,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  {
    path: '/admin/services',
    component: AdminServices,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  {
    path: '/admin/customers',
    component: AdminCustomers,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  {
    path: '/admin/reports',
    component: AdminReports,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  {
    path: '/admin/settings',
    component: AdminSettings,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  { 
    path: '/staff-rooms', 
    component: StaffRoomManagement,
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
    component: AdminStaff,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  {
    path: '/manage-services',
    component: ManageServices,
    meta: { requiresAuth: true }
  },
  {
    path: '/cancellation-policy',
    component: CancellationPolicy
  },
  {
    path: '/staff-messages',
    component: StaffMessages,
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
  const userStr = localStorage.getItem('user')
  const user = userStr ? JSON.parse(userStr) : null

  // Trang khách (guest paths)
  const guestPaths = ['/', '/rooms', '/about', '/contact', '/services', '/blog', '/cancellation-policy']
  
  // Nếu là STAFF, cưỡng bức về staff-dashboard nếu truy cập trang khách hoặc các trang không thuộc staff
  if (user && user.role === 'STAFF') {
    const isStaffRoute = to.path.startsWith('/staff') || to.path === '/bookings' || to.path === '/manage-services' || to.path === '/login'
    if (guestPaths.includes(to.path) || !isStaffRoute) {
      if (to.path !== '/staff-dashboard') {
        next('/staff-dashboard')
        return
      }
    }
  }

  // Nếu là ADMIN, chuyển hướng từ trang khách sang admin-dashboard
  if (user && user.role === 'ADMIN' && (guestPaths.includes(to.path) || to.path === '/staff-dashboard')) {
    next('/admin-dashboard')
    return
  }

  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!user) {
      next('/login')
    } else {
      // Check for specific route permissions
      if (to.matched.some(record => record.meta.requiresAdmin) && user.role !== 'ADMIN') {
        next('/staff-dashboard') // Redirect non-admins to staff dashboard
      } else if ((to.path === '/dashboard' || to.path === '/staff') && user.role !== 'ADMIN') {
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
