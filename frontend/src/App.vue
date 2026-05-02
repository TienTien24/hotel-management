<template>
  <div class="min-h-screen bg-gray-50 flex flex-col">
    <Navbar v-if="!isAuthPage" />
    <main class="flex-grow">
      <router-view></router-view>
    </main>
    <Footer v-if="!isAuthPage" />
    <AIChatbot v-if="!isAuthPage" />
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import Navbar from './components/Navbar.vue'
import Footer from './components/Footer.vue'
import AIChatbot from './components/AIChatbot.vue'

const route = useRoute()
const isAuthPage = computed(() => {
  const staffRoutes = [
    '/login', 
    '/register', 
    '/staff-dashboard', 
    '/staff-rooms', 
    '/bookings', 
    '/staff', 
    '/manage-services'
  ]
  return staffRoutes.includes(route.path)
})
</script>

<style>
/* Global styles can go here */
</style>
