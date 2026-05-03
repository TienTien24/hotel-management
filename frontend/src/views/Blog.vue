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

    <div class="max-w-7xl mx-auto px-6 py-32">
      <div v-if="loading" class="flex flex-col items-center justify-center py-32 space-y-4">
        <div class="w-12 h-12 border-4 border-emerald-100 border-t-emerald-800 rounded-full animate-spin"></div>
        <p class="text-xs font-black text-gray-400 uppercase tracking-widest">Đang tải bài viết...</p>
      </div>

      <div v-else-if="!reviews.length" class="text-center py-32 bg-gray-50 rounded-[3rem] border-2 border-dashed border-gray-200">
        <i class="fas fa-newspaper text-5xl text-gray-200 mb-6"></i>
        <p class="text-xs font-black text-gray-400 uppercase tracking-widest">Chưa có bài viết nào phù hợp.</p>
      </div>

      <div v-else>
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10">
          <div
            v-for="review in reviews"
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

        <!-- Load More -->
        <div class="mt-20 text-center">
          <button class="px-10 py-5 bg-[#004d26] text-white rounded-2xl font-black uppercase tracking-widest text-xs hover:bg-[#003d1e] shadow-xl shadow-green-900/20 transition-all flex items-center gap-3 mx-auto">
            <i class="fas fa-book-open"></i>
            Xem thêm bài viết
          </button>
        </div>
      </div>
    </div>

    <!-- CTA Section -->
    <section class="max-w-7xl mx-auto px-6 pb-32">
      <div class="bg-[#004d26] rounded-[3rem] p-12 text-center shadow-2xl shadow-green-900/40 relative overflow-hidden group">
        <div class="absolute inset-0 bg-white/5 opacity-0 group-hover:opacity-100 transition-opacity duration-700"></div>
        <div class="relative z-10">
          <div class="flex flex-col lg:flex-row lg:items-center lg:justify-between gap-12 text-left">
            <div class="lg:w-2/3">
              <div class="w-12 h-12 bg-white/10 rounded-2xl flex items-center justify-center text-amber-400 text-xl mb-6 border border-white/20">
                <i class="fas fa-hotel"></i>
              </div>
              <h2 class="text-4xl md:text-5xl font-black text-white uppercase tracking-tighter mb-4 leading-none">Sẵn sàng cho kỳ nghỉ tuyệt vời?</h2>
              <p class="text-lg text-white/60 font-medium">Đặt phòng ngay hôm nay để tận hưởng ưu đãi tốt nhất tại Grand Hotel.</p>
            </div>
            <div class="lg:w-1/3 flex flex-col gap-4">
              <router-link to="/rooms" class="px-10 py-5 bg-amber-400 text-emerald-950 rounded-2xl font-black uppercase tracking-widest text-xs hover:bg-white text-center transition-all duration-500">Đặt phòng ngay</router-link>
              <router-link to="/contact" class="px-10 py-5 bg-white/10 text-white border-2 border-white/20 rounded-2xl font-black uppercase tracking-widest text-xs hover:bg-white/20 text-center transition-all duration-500">Liên hệ tư vấn</router-link>
            </div>
          </div>
          
          <div class="grid grid-cols-1 md:grid-cols-3 gap-8 mt-12 pt-12 border-t border-white/10">
            <div class="flex items-center gap-4 text-white/50">
              <i class="fas fa-tags text-amber-400"></i>
              <span class="text-[10px] font-black uppercase tracking-widest">Giá tốt nhất</span>
            </div>
            <div class="flex items-center gap-4 text-white/50">
              <i class="fas fa-headset text-amber-400"></i>
              <span class="text-[10px] font-black uppercase tracking-widest">Hỗ trợ 24/7</span>
            </div>
            <div class="flex items-center gap-4 text-white/50">
              <i class="fas fa-shield-alt text-amber-400"></i>
              <span class="text-[10px] font-black uppercase tracking-widest">Thanh toán an toàn</span>
            </div>
          </div>
        </div>
      </div>
    </section>
  </main>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from '../api/axios'

const reviews = ref([
  {
    id: 1,
    title: 'Trải nghiệm tuyệt vời!',
    comment: 'Dịch vụ chuyên nghiệp, phòng ốc sang trọng. Nhân viên rất thân thiện và chu đáo. Chắc chắn sẽ quay lại.',
    customerName: 'Nguyễn Văn A',
    createdAt: '2026-05-02T11:45:28',
    imageUrl: 'https://i.amazingo.vn/images/vinh_phuc/khach_san_royal_huy_tam_dao/493448218050619500065251608.jpg'
  },
  {
    id: 2,
    title: 'Kỳ nghỉ hoàn hảo',
    comment: 'Khách sạn đẹp, vị trí thuận lợi. Bữa sáng phong phú và ngon miệng. Hồ bơi rất sạch sẽ và rộng rãi.',
    customerName: 'Trần Thị B',
    createdAt: '2026-05-02T11:45:28',
    imageUrl: 'https://danang-shopping.com/wp-content/uploads/2017/05/khach-san-da-nang-co-ho-boi-dep-diamond-sea-hotel.jpg'
  },
  {
    id: 3,
    title: 'Dịch vụ xuất sắc',
    comment: 'Từ lễ tân đến nhà hàng, tất cả mọi thứ đều hoàn hảo. Đặc biệt là massage spa và massage cực kỳ thư giãn.',
    customerName: 'Lê Minh C',
    createdAt: '2026-05-01T11:45:28',
    imageUrl: 'https://tse4.mm.bing.net/th/id/OIP.N0akW_TDbuvAuFd5QP5cVAHaE7?rs=1&pid=ImgDetMain&o=7&rm=3'
  },
  {
    id: 4,
    title: 'Phòng Deluxe siêu đẹp',
    comment: 'Phòng rất rộng rãi, view nhìn ra biển tuyệt đẹp. Nội thất hiện đại và tiện nghi đầy đủ. Rất đáng tiền!',
    customerName: 'Phạm Thị D',
    createdAt: '2026-04-24T11:45:28',
    imageUrl: 'https://media-cdn.tripadvisor.com/media/photo-m/1280/1b/08/9d/9b/getlstd-property-photo.jpg'
  },
  {
    id: 5,
    title: 'Trải nghiệm ẩm thực đỉnh cao',
    comment: 'Nhà hàng phục vụ món ăn rất ngon, đặc biệt là hải sản tươi sống. Đầu bếp rất chuyên nghiệp.',
    customerName: 'Hoàng Văn E',
    createdAt: '2026-04-24T11:45:28',
    imageUrl: 'https://images.unsplash.com/photo-1414235077428-338989a2e8c0?auto=format&fit=crop&w=800&q=80'
  },
  {
    id: 6,
    title: 'Spa & Wellness tuyệt vời',
    comment: 'Spa của khách sạn thật sự đẳng cấp. Các liệu pháp massage rất chuyên nghiệp, nhân viên tận tâm.',
    customerName: 'Vũ Thị F',
    createdAt: '2026-04-24T11:45:28',
    imageUrl: 'https://vinapad.com/wp-content/uploads/2019/07/cac-goi-dich-vu-spa-1.jpg'
  }
])
const loading = ref(false)

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