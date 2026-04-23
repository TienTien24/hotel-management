<template>
  <div class="min-h-screen bg-gray-100 pt-24 pb-10">
    <div class="max-w-6xl mx-auto px-4">
      <header class="mb-10">
        <h2 class="text-3xl font-black text-emerald-950">Blog & Đánh giá</h2>
        <p class="text-sm text-gray-500 mt-2">Những gì khách hàng của chúng tôi nói về chúng tôi</p>
      </header>

      <div v-if="reviews.length === 0" class="bg-white rounded-[2rem] shadow-lg p-10 text-center">
        <p class="text-gray-500 text-lg">Chưa có đánh giá nào.</p>
      </div>

      <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <div v-for="review in reviews" :key="review.id" class="bg-white rounded-[2rem] shadow-lg p-6">
          <div class="flex items-center gap-2 mb-3">
            <div class="flex text-yellow-400">
              <i v-for="n in 5" :key="n" :class="n <= review.rating ? 'fas' : 'far'" class="fa-star text-sm"></i>
            </div>
            <p class="text-sm text-gray-500">({{ review.rating }}/5)</p>
          </div>
          <h3 class="text-xl font-bold text-emerald-950 mb-2">{{ review.title }}</h3>
          <p class="text-gray-600 mb-4">{{ review.comment }}</p>
          <div class="text-sm text-gray-400">
            <p>Bởi: {{ review.customerName || 'Khách hàng ẩn danh' }}</p>
            <p>{{ formatDateTime(review.createdAt) }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from '../api/axios'

const reviews = ref([])

const formatDateTime = (date) => {
  if (!date) return '--'
  return new Date(date).toLocaleString('vi-VN')
}

const fetchReviews = async () => {
  try {
    // Assuming there's an API endpoint to fetch all reviews
    const response = await axios.get('/reviews')
    reviews.value = response.data
  } catch (error) {
    console.error('Lỗi khi lấy danh sách đánh giá:', error)
  }
}

onMounted(() => {
  fetchReviews()
})
</script>

<style scoped>
/* Add any specific styles for the blog page here */
</style>
