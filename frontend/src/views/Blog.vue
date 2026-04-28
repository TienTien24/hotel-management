<template>
  <div class="min-h-screen bg-gray-100">
    <section class="bg-emerald-950 text-white relative overflow-hidden h-[500px] flex items-center justify-center">
      <div class="absolute inset-0 opacity-10">
        <img src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80" class="w-full h-full object-cover">
      </div>
      <div class="max-w-7xl mx-auto px-4 relative z-10 text-center">
        <h1 class="text-5xl md:text-7xl font-black uppercase tracking-tighter mb-6">Blog & <span class="text-emerald-400">Câu chuyện</span></h1>
        <p class="text-emerald-100 font-light tracking-[0.3em] uppercase text-sm">Những trải nghiệm và câu chuyện từ khách hàng của chúng tôi</p>
      </div>
    </section>

    <div class="max-w-7xl mx-auto px-4 py-16">
      <div v-if="loading" class="text-center py-20 text-gray-400 font-bold uppercase tracking-widest text-xs">
        Đang tải bài viết...
      </div>

      <div v-else-if="!reviews.length" class="bg-white rounded-[3rem] shadow-lg p-16 text-center border-2 border-dashed border-gray-100">
        <p class="text-gray-500 text-lg font-medium">Chưa có bài viết nào.</p>
        <router-link to="/rooms" class="inline-block mt-6 bg-emerald-800 text-white px-10 py-4 rounded-2xl font-bold uppercase tracking-widest text-sm hover:bg-emerald-900 transition-all shadow-xl">
          Khám phá phòng của chúng tôi
        </router-link>
      </div>

      <div v-else>
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10">
          <div
            v-for="review in reviews"
            :key="review.id"
            class="bg-white rounded-[2.5rem] shadow-lg overflow-hidden transition-all duration-500 hover:shadow-2xl group flex flex-col h-full"
          >
            <div class="h-[400px] w-full overflow-hidden relative">
              <img :src="review.imageUrl || 'https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=800&q=80'" alt="Blog Image" class="w-full h-full object-cover transition-transform duration-700 group-hover:scale-110">
              <div class="absolute top-6 left-6 bg-emerald-600 text-white px-4 py-2 rounded-full text-xs font-bold uppercase tracking-wider shadow-xl">
                Blog
              </div>
            </div>
            <div class="p-8 flex-1 flex flex-col">
              <h3 class="text-2xl font-black text-emerald-950 mb-4 group-hover:text-emerald-800 transition-colors uppercase tracking-tighter leading-tight">
                {{ review.title || 'Trải nghiệm tại Grand Hotel' }}
              </h3>
              
              <p class="text-gray-500 font-light leading-relaxed mb-6 line-clamp-4 flex-1">
                {{ review.comment || 'Khám phá những trải nghiệm tuyệt vời tại Grand Hotel.' }}
              </p>

              <div class="flex items-center gap-4 pt-6 border-t border-gray-100">
                <div class="w-12 h-12 bg-emerald-100 rounded-full flex items-center justify-center shrink-0">
                  <span class="text-emerald-800 font-black text-lg">{{ getInitials(review.customerName) }}</span>
                </div>
                <div>
                  <p class="font-black text-emerald-950 uppercase tracking-widest text-sm">{{ review.customerName || 'Khách hàng' }}</p>
                  <p class="text-xs text-gray-400 font-bold uppercase tracking-widest mt-1">{{ formatDateTime(review.createdAt) }}</p>
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
import { ref, onMounted } from 'vue'
import axios from '../api/axios'

const reviews = ref([
  {
    id: 1,
    title: 'Trải nghiệm tuyệt vời!',
    comment: 'Dịch vụ chuyên nghiệp, phòng ốc sang trọng. Nhân viên rất thân thiện và chu đáo. Chắc chắn sẽ quay lại.',
    rating: 5,
    customerName: 'Nguyễn Văn A',
    createdAt: new Date().toISOString(),
    imageUrl: 'https://i.amazingo.vn/images/vinh_phuc/khach_san_royal_huy_tam_dao/493448218050619500065251608.jpg'
  },
  {
    id: 2,
    title: 'Kỳ nghỉ hoàn hảo',
    comment: 'Khách sạn đẹp, vị trí thuận lợi. Bữa sáng phong phú và ngon miệng. Hồ bơi rất sạch sẽ và rộng rãi.',
    rating: 4,
    customerName: 'Trần Thị B',
    createdAt: new Date(Date.now() - 86400000).toISOString(),
    imageUrl: 'https://danang-shopping.com/wp-content/uploads/2017/05/khach-san-da-nang-co-ho-boi-dep-diamond-sea-hotel.jpg'
  },
  {
    id: 3,
    title: 'Dịch vụ xuất sắc',
    comment: 'Từ lễ tân đến buổi check-out, mọi thứ đều hoàn hảo. Đặc biệt ấn tượng với spa và massage cực kỳ thư giãn.',
    rating: 5,
    customerName: 'Lê Minh C',
    createdAt: new Date(Date.now() - 172800000).toISOString(),
    imageUrl: 'https://tse4.mm.bing.net/th/id/OIP.N0akW_TDbuvAuFd5QP5cVAHaE7?rs=1&pid=ImgDetMain&o=7&rm=3'
  },
  {
    id: 4,
    title: 'Phòng Deluxe siêu đẹp',
    comment: 'Phòng rất rộng rãi, view nhìn ra biển tuyệt đẹp. Nội thất hiện đại và tiện nghi đầy đủ. Rất đáng tiền!',
    rating: 5,
    customerName: 'Phạm Thị D',
    createdAt: new Date(Date.now() - 259200000).toISOString(),
    imageUrl: 'https://media-cdn.tripadvisor.com/media/photo-m/1280/1b/08/9d/9b/getlstd-property-photo.jpg'
  },
  {
    id: 5,
    title: 'Trải nghiệm ẩm thực đỉnh cao',
    comment: 'Nhà hàng phục vụ món ăn rất ngon, đặc biệt là hải sản tươi sống. Đầu bếp rất chuyên nghiệp.',
    rating: 4,
    customerName: 'Hoàng Văn E',
    createdAt: new Date(Date.now() - 345600000).toISOString(),
    imageUrl: 'https://images.unsplash.com/photo-1414235077428-338989a2e8c0?auto=format&fit=crop&w=800&q=80'
  },
  {
    id: 6,
    title: 'Spa & Wellness tuyệt vời',
    comment: 'Spa của khách sạn thật sự đẳng cấp. Các liệu pháp massage rất chuyên nghiệp, nhân viên tận tâm.',
    rating: 5,
    customerName: 'Vũ Thị F',
    createdAt: new Date(Date.now() - 432000000).toISOString(),
    imageUrl: 'https://vinapad.com/wp-content/uploads/2019/07/cac-goi-dich-vu-spa-1.jpg'
  }
])
const loading = ref(false)

const formatDateTime = (date) => {
  if (!date) return '--'
  return new Date(date).toLocaleString('vi-VN')
}

const getInitials = (name) => {
  if (!name) return 'K'
  return name.split(' ').map(word => word[0]).join('').toUpperCase().slice(0, 2)
}

const fetchReviews = async () => {
  try {
    loading.value = true
    const response = await axios.get('/reviews')
    reviews.value = response.data
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

<style scoped>
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.line-clamp-4 {
  display: -webkit-box;
  -webkit-line-clamp: 4;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>