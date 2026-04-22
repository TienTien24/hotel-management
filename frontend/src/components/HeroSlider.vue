<template>
  <div class="relative w-full h-[600px] overflow-hidden group">
    <!-- Slides -->
    <div 
      class="flex transition-transform duration-700 ease-in-out h-full"
      :style="{ transform: `translateX(-${currentSlide * 100}%)` }"
    >
      <div v-for="(slide, index) in slides" :key="index" class="w-full h-full flex-shrink-0 relative">
        <img :src="slide.image" :alt="slide.title" class="w-full h-full object-cover">
        <!-- Overlay -->
        <div class="absolute inset-0 bg-black bg-opacity-30 flex flex-col justify-center items-center text-white p-10">
          <h2 class="text-5xl font-black uppercase tracking-tighter mb-4 transform transition-all duration-700" :class="currentSlide === index ? 'translate-y-0 opacity-100' : 'translate-y-10 opacity-0'">
            {{ slide.title }}
          </h2>
          <p class="text-xl font-light tracking-widest uppercase mb-8 transform transition-all duration-700 delay-100" :class="currentSlide === index ? 'translate-y-0 opacity-100' : 'translate-y-10 opacity-0'">
            {{ slide.subtitle }}
          </p>
          <router-link to="/rooms" class="bg-white text-emerald-900 px-8 py-3 rounded-full font-bold uppercase tracking-widest hover:bg-emerald-800 hover:text-white transition-all duration-300 transform transition-all duration-700 delay-200" :class="currentSlide === index ? 'translate-y-0 opacity-100' : 'translate-y-10 opacity-0'">
            Khám phá ngay
          </router-link>
        </div>
      </div>
    </div>

    <!-- Navigation Arrows -->
    <button 
      @click="prevSlide" 
      class="absolute left-4 top-1/2 -translate-y-1/2 w-12 h-12 rounded-full bg-white bg-opacity-50 flex items-center justify-center text-gray-800 hover:bg-opacity-100 transition-all duration-200 opacity-0 group-hover:opacity-100"
    >
      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" class="w-6 h-6">
        <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5L8.25 12l7.5-7.5" />
      </svg>
    </button>
    <button 
      @click="nextSlide" 
      class="absolute right-4 top-1/2 -translate-y-1/2 w-12 h-12 rounded-full bg-white bg-opacity-50 flex items-center justify-center text-gray-800 hover:bg-opacity-100 transition-all duration-200 opacity-0 group-hover:opacity-100"
    >
      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" class="w-6 h-6">
        <path stroke-linecap="round" stroke-linejoin="round" d="M8.25 4.5l7.5 7.5-7.5 7.5" />
      </svg>
    </button>

    <!-- Pagination Dots -->
    <div class="absolute bottom-6 left-1/2 -translate-x-1/2 flex space-x-3">
      <button 
        v-for="(_, index) in slides" 
        :key="index"
        @click="currentSlide = index"
        class="w-3 h-3 rounded-full transition-all duration-300"
        :class="currentSlide === index ? 'bg-white w-8' : 'bg-white bg-opacity-50'"
      ></button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'

const currentSlide = ref(0)
const slides = [
  {
    image: 'https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80',
    title: 'Trải nghiệm Nghỉ dưỡng Xa hoa',
    subtitle: 'Nơi sự sang trọng hội tụ cùng sự tiện nghi'
  },
  {
    image: 'https://images.unsplash.com/photo-1571896349842-33c89424de2d?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80',
    title: 'Phòng Suite Đẳng cấp',
    subtitle: 'Tầm nhìn tuyệt đẹp và không gian riêng tư'
  },
  {
    image: 'https://images.unsplash.com/photo-1445019980597-93fa8acb246c?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80',
    title: 'Dịch vụ Spa & Wellness',
    subtitle: 'Thư giãn tâm hồn và tái tạo năng lượng'
  }
]

let timer = null

const nextSlide = () => {
  currentSlide.value = (currentSlide.value + 1) % slides.length
}

const prevSlide = () => {
  currentSlide.value = (currentSlide.value - 1 + slides.length) % slides.length
}

const startTimer = () => {
  timer = setInterval(nextSlide, 5000)
}

const stopTimer = () => {
  if (timer) clearInterval(timer)
}

onMounted(() => {
  startTimer()
})

onUnmounted(() => {
  stopTimer()
})
</script>