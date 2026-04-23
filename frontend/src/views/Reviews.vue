<template>
  <div class="min-h-screen bg-gray-100">
    <section class="bg-emerald-950 text-white relative overflow-hidden h-[600px] flex items-center justify-center">
      <div class="absolute inset-0 opacity-10">
        <img src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80" class="w-full h-full object-cover">
      </div>
      <div class="max-w-7xl mx-auto px-4 relative z-10 text-center">
        <h1 class="text-5xl md:text-7xl font-black uppercase tracking-tighter mb-6">Blog & <span class="text-emerald-400">Trải nghiệm</span></h1>
        <p class="text-emerald-100 font-light tracking-[0.3em] uppercase text-sm">Những câu chuyện và đánh giá từ khách hàng của chúng tôi</p>
      </div>
    </section>

    <div class="max-w-7xl mx-auto px-4">
      <div v-if="loading" class="text-center py-20 text-gray-400 font-bold uppercase tracking-widest text-xs">
        Đang tải bài viết...
      </div>

      <div v-else-if="!reviews.length" class="bg-white rounded-[3rem] shadow-lg p-16 text-center border-2 border-dashed border-gray-100">
        <p class="text-gray-500 text-lg font-medium">Chưa có bài viết trải nghiệm nào.</p>
        <router-link to="/rooms" class="inline-block mt-6 bg-emerald-800 text-white px-10 py-4 rounded-2xl font-bold uppercase tracking-widest text-sm hover:bg-emerald-900 transition-all shadow-xl">
          Trở thành người đầu tiên chia sẻ
        </router-link>
      </div>

      <div v-else>
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10">
          <div
            v-for="review in reviews"
            :key="review.id"
            class="bg-white rounded-[2.5rem] shadow-lg overflow-hidden transition-all duration-500 hover:shadow-2xl group flex flex-col h-full"
          >
            <div class="h-[600px] w-full overflow-hidden relative">
              <img :src="review.room?.imageUrl || 'https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=800&q=80'" alt="Blog Image" class="w-full h-full object-cover transition-transform duration-700 group-hover:scale-110">
              <div class="absolute top-6 left-6 bg-red-600 text-white w-14 h-14 rounded-2xl flex flex-col items-center justify-center shadow-xl">
                <span class="text-xl font-black leading-none">{{ getDay(review.reviewCreatedAt) }}</span>
                <span class="text-[10px] font-bold uppercase mt-1">{{ getMonth(review.reviewCreatedAt) }}</span>
              </div>
            </div>
            <div class="p-8 flex-1 flex flex-col">
              <h3 class="text-2xl font-black text-emerald-950 mb-4 group-hover:text-emerald-800 transition-colors uppercase tracking-tighter leading-tight line-clamp-2">
                {{ review.reviewTitle || `Trải nghiệm tại Phòng ${review.room?.roomNumber}` }}
              </h3>
              
              <p v-if="review.reviewComment" class="text-gray-500 font-light leading-relaxed mb-6 line-clamp-4 flex-1">
                {{ review.reviewComment }}
              </p>

              <div class="flex items-center gap-4 pt-6 border-t border-gray-100">
                <div class="w-12 h-12 bg-emerald-100 rounded-full flex items-center justify-center shrink-0">
                  <span class="text-emerald-800 font-black text-lg">{{ getInitial(review.guestFullName) }}</span>
                </div>
                <div>
                  <p class="font-black text-emerald-950 uppercase tracking-widest text-sm">{{ review.guestFullName || 'Khách hàng' }}</p>
                  <div class="flex items-center gap-2 mt-1">
                    <div class="flex text-yellow-400">
                      <i v-for="n in 5" :key="n" :class="n <= review.reviewRating ? 'fas' : 'far'" class="fa-star text-[10px]"></i>
                    </div>
                    <span class="text-[10px] text-gray-400 font-bold uppercase tracking-widest">Đã lưu trú tại {{ review.room?.category }}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="mt-8 text-center">
        <router-link to="/rooms" class="bg-emerald-800 hover:bg-emerald-900 text-white px-8 py-4 rounded-2xl font-bold uppercase tracking-widest text-sm transition-all inline-block">
          Đặt phòng ngay
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import axios from '../api/axios'

const reviews = ref([
  {
    id: 'mock-1',
    reviewTitle: 'Kỳ nghỉ tuyệt vời nhất!',
    reviewComment: 'Tôi đã có một trải nghiệm không thể nào quên tại Grand Hotel. Dịch vụ spa thực sự đẳng cấp và nhân viên rất tận tâm. Phòng Deluxe hướng biển có tầm nhìn cực đẹp, chắc chắn tôi sẽ quay lại.',
    guestFullName: 'Nguyễn Văn Nam',
    reviewRating: 5,
    reviewCreatedAt: new Date('2026-03-15'),
    room: { category: 'Deluxe Ocean View', roomNumber: '601', imageUrl: 'https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=800&q=80' }
  },
  {
    id: 'mock-2',
    reviewTitle: 'Dịch vụ hoàn hảo',
    reviewComment: 'Từ lúc bước vào sảnh cho đến khi trả phòng, mọi thứ đều hoàn hảo. Nhà hàng phục vụ món ăn rất ngon, đặc biệt là buffet sáng phong phú. Hồ bơi vô cực là điểm cộng lớn nhất.',
    guestFullName: 'Trần Thị Mai',
    reviewRating: 5,
    reviewCreatedAt: new Date('2026-04-10'),
    room: { category: 'Suite Presidential', roomNumber: '1001', imageUrl: 'https://images.unsplash.com/photo-1582719478250-c89cae4df85b?auto=format&fit=crop&w=800&q=80' }
  },
  {
    id: 'mock-3',
    reviewTitle: 'Phòng sạch sẽ và tiện nghi',
    reviewComment: 'Phòng rất sạch sẽ, nội thất hiện đại và sang trọng. Tôi rất thích cách trang trí ở đây. Vị trí khách sạn ngay trung tâm nên rất thuận tiện đi lại tham quan.',
    guestFullName: 'Lê Hoàng Long',
    reviewRating: 4,
    reviewCreatedAt: new Date('2026-04-20'),
    room: { category: 'Standard Garden', roomNumber: '205', imageUrl: 'https://images.unsplash.com/photo-1512918728675-ed5a9ecdebfd?auto=format&fit=crop&w=800&q=80' }
  },
  {
    id: 'mock-4',
    reviewTitle: 'Địa điểm lý tưởng cho gia đình',
    reviewComment: 'Gia đình tôi đã có một kỳ nghỉ cuối tuần rất vui vẻ. Khách sạn có khu vui chơi cho trẻ em rất tốt. Nhân viên thân thiện và nhiệt tình giúp đỡ gia đình tôi.',
    guestFullName: 'Phạm Minh Đức',
    reviewRating: 5,
    reviewCreatedAt: new Date('2026-04-22'),
    room: { category: 'Deluxe Family', roomNumber: '412', imageUrl: 'https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?auto=format&fit=crop&w=800&q=80' }
  }
])
const loading = ref(false) // Set loading to false since we have mock data
 
 const getDay = (dateStr) => {
   if (!dateStr) return ''
   const date = new Date(dateStr)
   return date.getDate()
 }
 
 const getMonth = (dateStr) => {
   if (!dateStr) return ''
   const date = new Date(dateStr)
   return `TH${date.getMonth() + 1}`
 }
 
 const averageRating = computed(() => {
   if (!reviews.value.length) return 0
   const sum = reviews.value.reduce((acc, r) => acc + (r.reviewRating || 0), 0)
   return (sum / reviews.value.length).toFixed(1)
 })
 
 const formatDateTime = (date) => {
   if (!date) return '--'
   return new Date(date).toLocaleDateString('vi-VN')
 }
 
 const getInitial = (name) => {
   if (!name) return 'K'
   return name.charAt(0).toUpperCase()
 }
 
 const fetchReviews = async () => {
  try {
    const response = await axios.get('/bookings/reviews')
    if (response.data && response.data.length > 0) {
      reviews.value = [...reviews.value, ...response.data]
    }
  } catch (error) {
    console.error('Lỗi khi lấy danh sách đánh giá:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchReviews()
})
</script>