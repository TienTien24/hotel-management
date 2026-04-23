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
  return ['/login', '/register'].includes(route.path)
})
</script>

<style>
/* Global styles can go here */
</style>
