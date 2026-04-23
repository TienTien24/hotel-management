<template>
  <section class="relative z-20 -mt-24 pb-16">
    <div class="max-w-7xl mx-auto px-4">

      <!-- Search Bar Container -->
      <div class="bg-white p-10 rounded-[2.5rem] shadow-2xl border border-gray-50">
        <div class="flex flex-wrap lg:flex-nowrap items-end gap-4">
          <!-- Check-in -->
          <div class="flex-1 min-w-[200px]">
            <label class="block text-[10px] font-black uppercase text-emerald-900 mb-3 tracking-[0.15em]">NGÀY CHECK-IN</label>
            <div class="relative">
              <input v-model="searchData.checkIn" type="date" class="w-full bg-gray-50 px-6 py-4 rounded-2xl border-none focus:ring-2 focus:ring-emerald-500 text-sm font-medium text-gray-700 placeholder-gray-400">
            </div>
          </div>

          <!-- Check-out -->
          <div class="flex-1 min-w-[200px]">
            <label class="block text-[10px] font-black uppercase text-emerald-900 mb-3 tracking-[0.15em]">NGÀY CHECK-OUT</label>
            <div class="relative">
              <input v-model="searchData.checkOut" type="date" class="w-full bg-gray-50 px-6 py-4 rounded-2xl border-none focus:ring-2 focus:ring-emerald-500 text-sm font-medium text-gray-700 placeholder-gray-400">
            </div>
          </div>

          <!-- Guests -->
          <div class="w-full lg:w-40">
            <label class="block text-[10px] font-black uppercase text-emerald-900 mb-3 tracking-[0.15em]">SỐ NGƯỜI</label>
            <input v-model.number="searchData.guests" type="number" min="1" class="w-full bg-gray-50 px-6 py-4 rounded-2xl border-none focus:ring-2 focus:ring-emerald-500 text-sm font-medium text-gray-700">
          </div>

          <!-- Room Type -->
          <div class="w-full lg:w-48">
            <label class="block text-[10px] font-black uppercase text-emerald-900 mb-3 tracking-[0.15em]">LOẠI PHÒNG</label>
            <select v-model="searchData.category" class="w-full bg-gray-50 px-6 py-4 rounded-2xl border-none focus:ring-2 focus:ring-emerald-500 text-sm font-medium text-gray-700 appearance-none cursor-pointer">
              <option value="All">Tất cả</option>
              <option value="Standard">Standard</option>
              <option value="Deluxe">Deluxe</option>
              <option value="Suite">Suite</option>
            </select>
          </div>

          <!-- Action Buttons -->
          <div class="flex gap-3 w-full lg:w-auto">
            <button @click="handleSearch" class="flex-1 lg:flex-none bg-emerald-800 text-white px-8 py-4 rounded-2xl font-bold text-xs uppercase tracking-widest hover:bg-emerald-900 transition-all shadow-lg shadow-emerald-100 flex flex-col items-center justify-center leading-tight">
              <span>TÌM</span>
              <span>PHÒNG</span>
            </button>
            <button @click="handleClear" class="flex-1 lg:flex-none bg-gray-50 text-gray-500 px-8 py-4 rounded-2xl font-bold text-xs uppercase tracking-widest hover:bg-gray-100 transition-all flex flex-col items-center justify-center leading-tight">
              <span>XÓA</span>
              <span>LỌC</span>
            </button>
          </div>
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
  guests: 1,
  category: 'All'
})

const handleSearch = () => {
  const query = {}
  if (searchData.category !== 'All') {
    query.category = searchData.category
  }
  if (searchData.guests > 1) {
    query.capacity = searchData.guests
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
  searchData.guests = 1
  searchData.category = 'All'
}
</script>

<style scoped>
/* Add any component specific styles here if needed */
</style>