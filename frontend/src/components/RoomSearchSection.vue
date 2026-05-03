<template>
  <section class="relative z-20 -mt-24 pb-16">
    <div class="max-w-7xl mx-auto px-4">
      <div class="bg-white rounded-[2rem] shadow-2xl shadow-emerald-900/10 p-8 border border-emerald-50">
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-4">
          <!-- Check-in -->
          <div class="relative group">
            <label class="absolute left-12 top-3 text-[9px] font-black text-gray-400 uppercase tracking-widest z-10">Ngày check-in</label>
            <i class="fas fa-calendar-alt absolute left-5 top-1/2 -translate-y-1/2 text-emerald-800 text-sm"></i>
            <input 
              type="date" 
              v-model="searchData.checkIn"
              class="w-full bg-gray-50 border-2 border-gray-50 rounded-xl pl-12 pr-4 pt-7 pb-3 outline-none focus:bg-white focus:border-emerald-800 transition-all font-bold text-xs text-slate-700"
            >
          </div>

          <!-- Check-out -->
          <div class="relative group">
            <label class="absolute left-12 top-3 text-[9px] font-black text-gray-400 uppercase tracking-widest z-10">Ngày check-out</label>
            <i class="fas fa-calendar-check absolute left-5 top-1/2 -translate-y-1/2 text-emerald-800 text-sm"></i>
            <input 
              type="date" 
              v-model="searchData.checkOut"
              class="w-full bg-gray-50 border-2 border-gray-50 rounded-xl pl-12 pr-4 pt-7 pb-3 outline-none focus:bg-white focus:border-emerald-800 transition-all font-bold text-xs text-slate-700"
            >
          </div>

          <!-- Guests -->
          <div class="relative group">
            <label class="absolute left-12 top-3 text-[9px] font-black text-gray-400 uppercase tracking-widest z-10">Số người</label>
            <i class="fas fa-user-friends absolute left-5 top-1/2 -translate-y-1/2 text-emerald-800 text-sm"></i>
            <select 
              v-model="searchData.capacity"
              class="w-full bg-gray-50 border-2 border-gray-50 rounded-xl pl-12 pr-4 pt-7 pb-3 outline-none focus:bg-white focus:border-emerald-800 transition-all font-bold text-xs text-slate-700 appearance-none cursor-pointer"
            >
              <option :value="1">1 người lớn</option>
              <option :value="2">2 người lớn</option>
              <option :value="3">3 người lớn</option>
              <option :value="4">4 người lớn</option>
            </select>
            <i class="fas fa-chevron-down absolute right-5 top-1/2 -translate-y-1/2 text-gray-300 text-[10px] pointer-events-none"></i>
          </div>

          <!-- Room Type -->
          <div class="relative group">
            <label class="absolute left-12 top-3 text-[9px] font-black text-gray-400 uppercase tracking-widest z-10">Loại phòng</label>
            <i class="fas fa-hotel absolute left-5 top-1/2 -translate-y-1/2 text-emerald-800 text-sm"></i>
            <select 
              v-model="searchData.category"
              class="w-full bg-gray-50 border-2 border-gray-50 rounded-xl pl-12 pr-4 pt-7 pb-3 outline-none focus:bg-white focus:border-emerald-800 transition-all font-bold text-xs text-slate-700 appearance-none cursor-pointer"
            >
              <option value="All">Tất cả</option>
              <option value="Standard">Standard</option>
              <option value="Deluxe">Deluxe</option>
              <option value="Suite">Suite</option>
            </select>
            <i class="fas fa-chevron-down absolute right-5 top-1/2 -translate-y-1/2 text-gray-300 text-[10px] pointer-events-none"></i>
          </div>

          <!-- Search Button -->
          <button 
            @click="handleSearch"
            class="w-full bg-[#004d26] text-white rounded-xl font-black uppercase tracking-[0.2em] text-[11px] hover:bg-emerald-700 transition-all shadow-xl shadow-green-900/20"
          >
            Tìm phòng
          </button>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const categories = ['All', 'Standard', 'Deluxe', 'Suite']

const searchData = reactive({
  checkIn: '',
  checkOut: '',
  capacity: 1,
  category: 'All'
})

const handleSearch = () => {
  const query = {}
  if (searchData.category !== 'All') {
    query.category = searchData.category
  }
  if (searchData.capacity > 1) {
    query.capacity = searchData.capacity
  }
  if (searchData.checkIn) {
    query.checkIn = searchData.checkIn
  }
  if (searchData.checkOut) {
    query.checkOut = searchData.checkOut
  }

  router.push({
    path: '/rooms',
    query
  })
}

const handleClear = () => {
  searchData.checkIn = ''
  searchData.checkOut = ''
  searchData.capacity = 1
  searchData.category = 'All'
}
</script>

<style scoped>
/* Add any component specific styles here if needed */
</style>