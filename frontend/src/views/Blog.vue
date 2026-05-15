<template>
  <main class="blog-page min-h-screen bg-white font-sans">
    <!-- Hero Section -->
    <header class="relative h-[500px] flex items-center justify-center overflow-hidden">
      <div class="absolute inset-0 z-0">
        <img src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80" 
             alt="Blog Hero" 
             class="w-full h-full object-cover">
        <div class="absolute inset-0 bg-black/60"></div>
      </div>
      <div class="max-w-7xl mx-auto px-6 relative z-10 text-center">
        <div class="w-24 h-1 bg-amber-400 mx-auto mb-8"></div>
        <h1 class="text-7xl md:text-9xl font-black text-white uppercase tracking-tighter mb-4 leading-none">
          BLOG & <span class="text-emerald-400">CÂU CHUYỆN</span>
        </h1>
        <p class="text-2xl text-white/80 font-medium tracking-tight mb-8">
          Những trải nghiệm và câu chuyện từ khách hàng của chúng tôi
        </p>
        <div class="w-10 h-10 border-2 border-white/30 rounded-full flex items-center justify-center mx-auto text-white/50 animate-bounce cursor-pointer">
          <i class="fas fa-chevron-down text-xs"></i>
        </div>
      </div>
    </header>

    <div class="max-w-7xl mx-auto px-6 py-16">
      <div v-if="loading" class="flex flex-col items-center justify-center py-32 space-y-4">
        <div class="w-12 h-12 border-4 border-emerald-100 border-t-emerald-800 rounded-full animate-spin"></div>
        <p class="text-xs font-black text-gray-400 uppercase tracking-widest">Đang tải bài viết...</p>
      </div>

      <div v-else-if="!paginatedReviews.length" class="text-center py-32 bg-gray-50 rounded-[3rem] border-2 border-dashed border-gray-200">
        <i class="fas fa-newspaper text-5xl text-gray-200 mb-6"></i>
        <p class="text-xs font-black text-gray-400 uppercase tracking-widest">Chưa có bài viết nào phù hợp.</p>
      </div>

      <div v-else>
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10">
          <div
            v-for="review in paginatedReviews"
            :key="review.id"
            class="group bg-white rounded-[2.5rem] overflow-hidden shadow-xl hover:shadow-2xl transition-all duration-500 border border-gray-100 flex flex-col h-full"
          >
            <!-- Blog Image -->
            <div class="relative h-72 overflow-hidden">
              <img :src="review.imageUrl || 'https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=800&q=80'" 
                   class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-1000">
              <div class="absolute top-6 left-6 bg-emerald-800 text-white px-4 py-1.5 rounded-full text-[9px] font-black uppercase tracking-widest shadow-xl">
                Blog
              </div>
            </div>

            <!-- Blog Content -->
            <div class="p-8 flex-1 flex flex-col">
              <h3 class="text-2xl font-black text-slate-800 uppercase tracking-tighter mb-4 group-hover:text-[#004d26] transition-colors leading-tight">
                {{ review.title || 'Trải nghiệm tại Grand Hotel' }}
              </h3>
              
              <p class="text-sm text-gray-500 font-medium leading-relaxed mb-8 line-clamp-3 flex-1">
                {{ review.comment || 'Khám phá những trải nghiệm tuyệt vời tại Grand Hotel.' }}
              </p>

              <div class="flex items-center gap-4 pt-8 border-t border-gray-50">
                <div class="w-12 h-12 bg-emerald-50 rounded-2xl flex items-center justify-center shrink-0 border border-emerald-100">
                  <span class="text-emerald-800 font-black text-sm">{{ getInitials(review.customerName) }}</span>
                </div>
                <div>
                  <p class="text-xs font-black text-slate-800 uppercase tracking-widest">{{ review.customerName || 'Khách hàng' }}</p>
                  <div class="flex items-center gap-2 text-[10px] text-gray-400 font-bold mt-1 uppercase tracking-widest">
                    <i class="far fa-calendar-alt"></i>
                    {{ formatDateTime(review.createdAt) }}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Pagination -->
        <div class="mt-20 flex justify-center items-center gap-3">
          <button 
            @click="currentPage--" 
            :disabled="currentPage === 1"
            class="w-12 h-12 rounded-2xl bg-white border-2 border-gray-50 text-gray-300 hover:border-emerald-800 hover:text-emerald-800 disabled:opacity-30 transition-all flex items-center justify-center"
          >
            <i class="fas fa-chevron-left text-sm"></i>
          </button>
          
          <div class="flex gap-2">
            <button 
              v-for="page in totalPages" 
              :key="page"
              @click="currentPage = page"
              :class="[
                'w-12 h-12 rounded-2xl border-2 font-black text-sm transition-all',
                currentPage === page 
                  ? 'bg-[#004d26] border-[#004d26] text-white shadow-xl shadow-green-900/20' 
                  : 'bg-white border-gray-50 text-gray-400 hover:border-emerald-800 hover:text-emerald-800'
              ]"
            >
              {{ page }}
            </button>
          </div>

          <button 
            @click="currentPage++" 
            :disabled="currentPage === totalPages"
            class="w-12 h-12 rounded-2xl bg-white border-2 border-gray-50 text-gray-300 hover:border-emerald-800 hover:text-emerald-800 disabled:opacity-30 transition-all flex items-center justify-center"
          >
            <i class="fas fa-chevron-right text-sm"></i>
          </button>
        </div>
      </div>
    </div>
  </main>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import axios from '../api/axios'

const reviews = ref([
  {
    id: 1,
    title: 'Trải nghiệm tuyệt vời!',
    comment: 'Dịch vụ chuyên nghiệp, phòng ốc sang trọng. Nhân viên rất thân thiện và chu đáo. Chắc chắn sẽ quay lại.',
    customerName: 'Nguyễn Văn A',
    createdAt: '2026-05-02T11:45:28',
    imageUrl: 'https://loremflickr.com/800/600/hotel,room?lock=10'
  },
  {
    id: 2,
    title: 'Kỳ nghỉ hoàn hảo',
    comment: 'Khách sạn đẹp, vị trí thuận lợi. Bữa sáng phong phú và ngon miệng. Hồ bơi rất sạch sẽ và rộng rãi.',
    customerName: 'Trần Thị B',
    createdAt: '2026-05-02T11:45:28',
    imageUrl: 'https://loremflickr.com/800/600/hotel,pool?lock=20'
  },
  {
    id: 3,
    title: 'Dịch vụ xuất sắc',
    comment: 'Từ lễ tân đến nhà hàng, tất cả mọi thứ đều hoàn hảo. Đặc biệt là massage spa và massage cực kỳ thư giãn.',
    customerName: 'Lê Minh C',
    createdAt: '2026-05-01T11:45:28',
    imageUrl: 'https://loremflickr.com/800/600/hotel,spa?lock=30'
  },
  {
    id: 4,
    title: 'Phòng Deluxe siêu đẹp',
    comment: 'Phòng rất rộng rãi, view nhìn ra biển tuyệt đẹp. Nội thất hiện đại và tiện nghi đầy đủ. Rất đáng tiền!',
    customerName: 'Phạm Thị D',
    createdAt: '2026-04-24T11:45:28',
    imageUrl: 'https://loremflickr.com/800/600/hotel,deluxe?lock=40'
  },
  {
    id: 5,
    title: 'Trải nghiệm ẩm thực đỉnh cao',
    comment: 'Nhà hàng phục vụ món ăn rất ngon, đặc biệt là hải sản tươi sống. Đầu bếp rất chuyên nghiệp.',
    customerName: 'Hoàng Văn E',
    createdAt: '2026-04-24T11:45:28',
    imageUrl: 'https://loremflickr.com/800/600/hotel,restaurant?lock=50'
  },
  {
    id: 6,
    title: 'Spa & Wellness tuyệt vời',
    comment: 'Spa của khách sạn thật sự đẳng cấp. Các liệu pháp massage rất chuyên nghiệp, nhân viên tận tâm.',
    customerName: 'Vũ Thị F',
    createdAt: '2026-04-24T11:45:28',
    imageUrl: 'https://loremflickr.com/800/600/hotel,wellness?lock=60'
  },
  {
    id: 7,
    title: 'View biển triệu đô',
    comment: 'Ngắm bình minh ngay tại phòng là trải nghiệm không thể quên. Grand Hotel có vị trí đắc địa nhất.',
    customerName: 'Lý Tiểu Long',
    createdAt: '2026-04-20T09:30:00',
    imageUrl: 'https://loremflickr.com/800/600/hotel,ocean?lock=70'
  },
  {
    id: 8,
    title: 'Sự kiện công ty thành công',
    comment: 'Phòng hội nghị hiện đại, hỗ trợ kỹ thuật nhiệt tình. Buổi tiệc tối ngoài trời rất lãng mạn.',
    customerName: 'Đặng Lê Nguyên Vũ',
    createdAt: '2026-04-18T15:20:00',
    imageUrl: 'https://loremflickr.com/800/600/hotel,event?lock=80'
  },
  {
    id: 9,
    title: 'Nơi lý tưởng cho gia đình',
    comment: 'Khu vui chơi trẻ em an toàn, các con tôi rất thích. Hồ bơi có khu vực riêng cho bé rất tiện.',
    customerName: 'Phạm Nhật Vượng',
    createdAt: '2026-04-15T10:00:00',
    imageUrl: 'https://loremflickr.com/800/600/hotel,family?lock=90'
  },
  {
    id: 10,
    title: 'Cảm giác như ở nhà',
    comment: 'Sự đón tiếp nồng hậu làm tôi thấy ấm lòng. Từng chi tiết nhỏ trong phòng đều được chăm chút.',
    customerName: 'Trần Đình Long',
    createdAt: '2026-04-12T08:45:00',
    imageUrl: 'https://loremflickr.com/800/600/hotel,home?lock=100'
  },
  {
    id: 11,
    title: 'Tiện nghi 5 sao thực thụ',
    comment: 'Từ hệ thống điều khiển thông minh đến giường ngủ êm ái, mọi thứ đều vượt xa mong đợi.',
    customerName: 'Nguyễn Đăng Quang',
    createdAt: '2026-04-10T22:15:00',
    imageUrl: 'https://loremflickr.com/800/600/hotel,tech?lock=110'
  },
  {
    id: 12,
    title: 'Bữa sáng buffet đa dạng',
    comment: 'Rất nhiều lựa chọn từ món Á đến món Âu. Nước trái cây tươi và bánh ngọt tuyệt vời.',
    customerName: 'Trần Bá Dương',
    createdAt: '2026-04-08T07:30:00',
    imageUrl: 'https://loremflickr.com/800/600/hotel,breakfast?lock=120'
  }
])
const loading = ref(false)
const currentPage = ref(1)
const itemsPerPage = 6

const totalPages = computed(() => Math.ceil(reviews.value.length / itemsPerPage))
const paginatedReviews = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage
  return reviews.value.slice(start, start + itemsPerPage)
})

const formatDateTime = (date) => {
  if (!date) return '--'
  const d = new Date(date)
  const time = d.toLocaleTimeString('vi-VN', { hour: '2-digit', minute: '2-digit', second: '2-digit' })
  const day = d.toLocaleDateString('vi-VN', { day: '2-digit', month: '2-digit', year: 'numeric' })
  return `${time} - ${day}`
}

const getInitials = (name) => {
  if (!name) return 'K'
  return name.split(' ').map(word => word[0]).join('').toUpperCase().slice(0, 2)
}

const fetchReviews = async () => {
  try {
    loading.value = true
    const response = await axios.get('/reviews')
    if (response.data && response.data.length > 0) {
      reviews.value = response.data
    }
  } catch (error) {
    console.error('Lỗi khi lấy danh sách đánh giá:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchReviews()
  window.scrollTo(0, 0)
})
</script>

<style scoped>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css');
</style>