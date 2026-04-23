<template>
  <div class="min-h-screen bg-white">
    <!-- Hero Section -->
    <HeroSlider />
    <RoomSearchSection />

    <!-- About Section -->
    <section id="about" class="max-w-7xl mx-auto px-4 py-12 border-b border-gray-100">
      <div class="flex flex-col md:flex-row items-start gap-12">
        <div class="md:w-5/12 relative group">
          <div class="absolute -top-10 -left-10 w-40 h-40 bg-emerald-50 rounded-full -z-10 group-hover:scale-110 transition-transform duration-700"></div>
          <img src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=1000&q=80" class="rounded-3xl shadow-2xl relative z-10 w-full h-[480px] object-cover transition-transform duration-700 hover:scale-[1.02]">
          <div class="absolute -bottom-6 -right-6 bg-white p-8 rounded-2xl shadow-2xl z-20 border border-emerald-50 transform hover:-translate-y-2 transition-all duration-300 flex flex-col gap-4">
            <div>
              <p class="text-4xl font-black text-emerald-800">25+</p>
              <p class="text-gray-500 uppercase tracking-widest text-[10px] font-bold mt-1">Năm Kinh Nghiệm</p>
            </div>
            <div class="pt-4 border-t border-gray-100">
              <p class="text-3xl font-black text-emerald-800">60+</p>
              <p class="text-gray-500 uppercase tracking-widest text-[10px] font-bold mt-1">Phòng & 6 Tầng</p>
            </div>
          </div>
        </div>
        <div class="md:w-7/12">
          <h2 class="text-4xl md:text-6xl font-black uppercase text-emerald-950 tracking-tighter mb-10 leading-[1.1]">Chào mừng bạn đến với <span class="text-emerald-800">Grand Hotel</span></h2>
          <p class="text-gray-600 font-light leading-relaxed mb-12 text-lg">
            Được thành lập từ năm 2000, Grand Hotel không chỉ là một khách sạn nghỉ dưỡng, mà là nơi mỗi khoảnh khắc của bạn đều được trân trọng. Chúng tôi mang đến sự kết hợp hoàn hảo giữa kiến trúc sang trọng và dịch vụ tận tâm theo tiêu chuẩn quốc tế.
          </p>
          
          <div class="grid grid-cols-1 sm:grid-cols-2 gap-10 mb-12">
            <div class="flex items-start space-x-5 group">
              <div class="w-14 h-14 bg-emerald-50 rounded-2xl flex items-center justify-center flex-shrink-0 group-hover:bg-emerald-800 transition-colors duration-300">
                <i class="fas fa-history text-2xl text-emerald-800 group-hover:text-white transition-colors"></i>
              </div>
              <div>
                <h4 class="font-bold text-emerald-900 uppercase text-sm tracking-widest mb-2">Lịch sử lâu đời</h4>
                <p class="text-gray-500 text-sm font-light leading-relaxed">Hơn 2 thập kỷ đồng hành cùng hàng triệu khách hàng toàn cầu.</p>
              </div>
            </div>
            
            <div class="flex items-start space-x-5 group">
              <div class="w-14 h-14 bg-emerald-50 rounded-2xl flex items-center justify-center flex-shrink-0 group-hover:bg-emerald-800 transition-colors duration-300">
                <i class="fas fa-concierge-bell text-2xl text-emerald-800 group-hover:text-white transition-colors"></i>
              </div>
              <div>
                <h4 class="font-bold text-emerald-900 uppercase text-sm tracking-widest mb-2">Dịch vụ 24/7</h4>
                <p class="text-gray-500 text-sm font-light leading-relaxed">Đội ngũ nhân viên chuyên nghiệp sẵn sàng phục vụ mọi yêu cầu của bạn.</p>
              </div>
            </div>

            <div class="flex items-start space-x-5 group">
              <div class="w-14 h-14 bg-emerald-50 rounded-2xl flex items-center justify-center flex-shrink-0 group-hover:bg-emerald-800 transition-colors duration-300">
                <i class="fas fa-map-marked-alt text-2xl text-emerald-800 group-hover:text-white transition-colors"></i>
              </div>
              <div>
                <h4 class="font-bold text-emerald-900 uppercase text-sm tracking-widest mb-2">Vị trí đắc địa</h4>
                <p class="text-gray-500 text-sm font-light leading-relaxed">Nằm tại trung tâm thành phố, thuận tiện cho việc tham quan và di chuyển.</p>
              </div>
            </div>

            <div class="flex items-start space-x-5 group">
              <div class="w-14 h-14 bg-emerald-50 rounded-2xl flex items-center justify-center flex-shrink-0 group-hover:bg-emerald-800 transition-colors duration-300">
                <i class="fas fa-award text-2xl text-emerald-800 group-hover:text-white transition-colors"></i>
              </div>
              <div>
                <h4 class="font-bold text-emerald-900 uppercase text-sm tracking-widest mb-2">Giải thưởng</h4>
                <p class="text-gray-500 text-sm font-light leading-relaxed">Được bình chọn là khách sạn có dịch vụ tốt nhất trong nhiều năm liền.</p>
              </div>
            </div>
          </div>
          
          <button @click="$router.push('/about')" class="bg-emerald-800 text-white px-12 py-4 rounded-full font-bold uppercase tracking-[0.2em] hover:bg-emerald-900 transition-all shadow-xl hover:shadow-emerald-200 transform hover:-translate-y-1">Khám phá câu chuyện của chúng tôi</button>
        </div>
      </div>
    </section>

    <!-- Introduction Section (Room Types) -->
    <section id="rooms" class="max-w-7xl mx-auto px-4 py-16">
      <div class="text-center mb-16">
        <h3 class="text-emerald-800 font-bold uppercase tracking-[0.3em] mb-4 text-xs">LOẠI PHÒNG</h3>
        <h2 class="text-4xl md:text-5xl font-black uppercase text-emerald-950 tracking-tight">NGHỈ DƯỠNG 5 SAO</h2>
        <div class="w-24 h-1 bg-emerald-800 mx-auto mt-6"></div>
      </div>
      
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-12">
        <div v-for="category in displayCategories" :key="category.name" class="bg-white group cursor-pointer" @click="$router.push({ path: '/rooms', query: { category: category.name } })">
          <!-- Image Section -->
          <div class="relative h-72 mb-8 overflow-hidden rounded-[2rem]">
            <img :src="category.imageUrl" 
                 class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-700">
          </div>

          <!-- Content Section -->
          <div>
            <h4 class="text-xl font-bold uppercase text-emerald-950 mb-3 tracking-widest">{{ category.displayTitle }}</h4>
            <p class="text-gray-500 font-light text-base leading-relaxed">
              {{ category.description }}
            </p>
          </div>
        </div>
      </div>

      <div class="text-center mt-16">
        <router-link to="/rooms" class="inline-block border-2 border-emerald-900 text-emerald-900 px-10 py-3 rounded-full font-black uppercase tracking-widest text-sm hover:bg-emerald-900 hover:text-white transition-all transform hover:-translate-y-1">
          XEM TẤT CẢ PHÒNG
        </router-link>
      </div>
    </section>

    <!-- Services Section -->
    <section id="services" class="bg-gray-50 py-16 border-y border-gray-100">
      <div class="max-w-7xl mx-auto px-4">
        <div class="flex flex-col md:flex-row justify-between items-end mb-12 gap-6">
          <div class="max-w-2xl">
            <h3 class="text-emerald-800 font-bold uppercase tracking-[0.3em] mb-4 text-xs">TRẢI NGHIỆM DỊCH VỤ</h3>
            <h2 class="text-4xl md:text-5xl font-black uppercase text-emerald-950 tracking-tight">TIỆN NGHI ĐẲNG CẤP QUỐC TẾ</h2>
          </div>
          <p class="text-gray-400 font-light max-w-[300px] mb-2 italic text-sm text-right">"Chúng tôi không chỉ cung cấp chỗ nghỉ, chúng tôi mang đến trải nghiệm."</p>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-4 gap-8">
          <div v-for="(service, index) in services" :key="index" class="bg-white p-10 rounded-3xl shadow-sm border border-gray-100 hover:border-emerald-200 hover:shadow-2xl transition-all duration-300 group">
            <div class="w-16 h-16 bg-emerald-50 rounded-2xl flex items-center justify-center mb-8 group-hover:bg-emerald-800 transition-colors duration-300">
              <i :class="[service.icon, 'text-2xl text-emerald-800 group-hover:text-white transition-colors duration-300']"></i>
            </div>
            <h4 class="text-lg font-bold uppercase tracking-widest text-emerald-900 mb-4">{{ service.title }}</h4>
            <p class="text-gray-500 font-light text-sm leading-relaxed">{{ service.desc }}</p>
          </div>
        </div>
      </div>
    </section>

    <!-- Blog/Experience Section -->
    <section id="blog" class="py-24 bg-gray-50 overflow-hidden">
      <div class="max-w-7xl mx-auto px-4">
        <div class="flex flex-col md:flex-row justify-between items-end mb-16 gap-8">
          <div class="max-w-2xl">
            <h3 class="text-emerald-800 font-bold uppercase tracking-[0.3em] mb-4 text-xs">BLOG & TRẢI NGHIỆM</h3>
            <h2 class="text-4xl md:text-6xl font-black uppercase text-emerald-950 tracking-tighter leading-none">
              CHIA SẺ TỪ <span class="text-emerald-800 italic">KHÁCH HÀNG</span>
            </h2>
          </div>
          <router-link to="/blog" class="group flex items-center gap-3 text-emerald-900 font-black uppercase tracking-widest text-sm hover:text-emerald-700 transition-colors">
            Xem tất cả bài viết
            <div class="w-10 h-10 rounded-full border-2 border-emerald-900 flex items-center justify-center group-hover:bg-emerald-900 group-hover:text-white transition-all">
              <i class="fas fa-arrow-right"></i>
            </div>
          </router-link>
        </div>

        <div v-if="loadingReviews" class="text-center py-20 text-gray-400 font-bold uppercase tracking-widest text-xs">
          Đang tải bài viết...
        </div>

        <div v-else-if="!blogPosts.length" class="text-center py-20 bg-white rounded-[3rem] shadow-sm border border-dashed border-gray-200">
          <p class="text-gray-400 font-bold uppercase tracking-widest text-xs">Chưa có bài viết trải nghiệm nào.</p>
        </div>

        <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-10">
          <div v-for="post in blogPosts" :key="post.id" class="group cursor-pointer" @click="$router.push('/blog')">
            <div class="relative h-80 mb-8 overflow-hidden rounded-[2.5rem] shadow-2xl">
              <img :src="post.room?.imageUrl || 'https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?auto=format&fit=crop&w=800&q=80'" 
                   class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-700">
              <div class="absolute top-6 left-6 bg-red-600 text-white w-14 h-14 rounded-2xl flex flex-col items-center justify-center shadow-xl">
                <span class="text-xl font-black leading-none">{{ getDay(post.reviewCreatedAt) }}</span>
                <span class="text-[10px] font-bold uppercase mt-1">{{ getMonth(post.reviewCreatedAt) }}</span>
              </div>
            </div>
            
            <div class="px-2">
              <h4 class="text-2xl font-black text-emerald-950 mb-4 group-hover:text-emerald-800 transition-colors line-clamp-2 leading-tight uppercase tracking-tighter">
                {{ post.reviewTitle || `Kỳ nghỉ tuyệt vời tại phòng ${post.room?.roomNumber}` }}
              </h4>
              <p class="text-gray-500 font-light text-base leading-relaxed line-clamp-3 mb-6">
                {{ post.reviewComment }}
              </p>
              <div class="flex items-center gap-3 pt-6 border-t border-gray-100">
                <div class="w-10 h-10 bg-emerald-100 rounded-full flex items-center justify-center font-black text-emerald-800 text-sm">
                  {{ post.guestFullName?.charAt(0) || 'K' }}
                </div>
                <div>
                  <p class="text-sm font-black text-emerald-950 uppercase tracking-widest">{{ post.guestFullName || 'Khách hàng' }}</p>
                  <p class="text-[10px] text-gray-400 font-bold uppercase tracking-widest mt-0.5">Người trải nghiệm</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Call to Action -->
    <section class="bg-emerald-950 py-20 text-white relative overflow-hidden">
      <div class="absolute inset-0 opacity-10">
        <img src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80" class="w-full h-full object-cover">
      </div>
      <div class="max-w-4xl mx-auto text-center px-4 relative z-10">
        <h2 class="text-5xl font-black uppercase mb-8 tracking-tight">Kiến tạo những kỷ niệm vô giá</h2>
        <p class="text-emerald-100 mb-12 text-lg font-light tracking-[0.3em] uppercase">Đặt chỗ ngay để nhận ưu đãi tốt nhất trong mùa này</p>
        <router-link to="/rooms" class="inline-block bg-white text-emerald-950 px-12 py-5 rounded-full font-bold uppercase tracking-[0.2em] hover:bg-emerald-100 transition-all duration-300 transform hover:scale-105 shadow-2xl">
          Bắt đầu hành trình của bạn
        </router-link>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import HeroSlider from '../components/HeroSlider.vue'
import RoomSearchSection from '../components/RoomSearchSection.vue'
import axios from '../api/axios'

const user = ref(null)
const rooms = ref([])
const blogPosts = ref([])
const loadingReviews = ref(true)
const submittingReview = ref(false)
const quickReview = ref({
  guestFullName: '',
  title: '',
  rating: 5,
  comment: ''
})

const submitQuickReview = async () => {
  if (submittingReview.value) return
  submittingReview.value = true
  try {
    await axios.post('/bookings/quick-review', quickReview.value)
    alert('Cảm ơn bạn đã gửi đánh giá! Đánh giá của bạn sẽ xuất hiện trong phần Blog.')
    quickReview.value = {
      guestFullName: '',
      title: '',
      rating: 5,
      comment: ''
    }
    await fetchBlogPosts() // Tải lại danh sách blog để hiện bài mới nhất
  } catch (error) {
    console.error('Lỗi khi gửi đánh giá:', error)
    alert('Có lỗi xảy ra khi gửi đánh giá. Vui lòng thử lại sau.')
  } finally {
    submittingReview.value = false
  }
}

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

const fetchBlogPosts = async () => {
  try {
    const response = await axios.get('/bookings/reviews')
    blogPosts.value = response.data.slice(0, 3) // Chỉ lấy 3 bài mới nhất cho trang chủ
  } catch (error) {
    console.error('Lỗi khi tải bài viết:', error)
  } finally {
    loadingReviews.value = false
  }
}

onMounted(() => {
  fetchBlogPosts()
})

const displayCategories = computed(() => {
  const categories = [
    { 
      name: 'Suite', 
      displayTitle: 'PRESIDENTIAL SUITE',
      description: 'Phòng Suite là biểu tượng của sự sang trọng và đẳng cấp, nơi bạn được tận hưởng không gian rộng lớn với phòng khách và phòng ngủ riêng biệt. Thiết kế tinh xảo, tiện nghi cao cấp cùng dịch vụ ưu tiên mang đến trải nghiệm như một “ngôi nhà thứ hai” đầy riêng tư và đẳng cấp. Đây là lựa chọn hoàn hảo cho những ai muốn tận hưởng kỳ nghỉ theo phong cách thượng lưu.',
      imageUrl: 'https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80'
    },
    { 
      name: 'Deluxe', 
      displayTitle: 'DELUXE OCEAN VIEW',
      description: 'Phòng Deluxe nâng tầm trải nghiệm lưu trú với không gian rộng rãi, nội thất tinh tế và tầm nhìn ấn tượng. Mỗi chi tiết đều được chăm chút nhằm mang lại cảm giác thư giãn tối đa, giúp bạn tận hưởng kỳ nghỉ trọn vẹn hơn. Đây là lựa chọn lý tưởng cho những ai mong muốn sự thoải mái và một chút “đẳng cấp” trong từng khoảnh khắc.',
      imageUrl: 'https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80'
    },
    { 
      name: 'Standard', 
      displayTitle: 'STANDARD GARDEN ROOM',
      description: 'Phòng Standard mang đến không gian nghỉ ngơi ấm cúng và tiện nghi với mức giá hợp lý, là lựa chọn hoàn hảo cho những ai tìm kiếm sự thoải mái đơn giản nhưng đầy đủ. Với thiết kế gọn gàng, trang bị hiện đại và không gian dễ chịu, đây chính là điểm dừng chân lý tưởng sau một ngày dài khám phá hoặc làm việc.',
      imageUrl: 'https://images.unsplash.com/photo-1512918728675-ed5a9ecdebfd?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80'
    }
  ]
  return categories
})

const services = [
  {
    title: 'Nhà hàng & Bar',
    desc: 'Trải nghiệm ẩm thực tinh hoa từ các đầu bếp hàng đầu trong không gian sang trọng.',
    icon: 'fas fa-utensils'
  },
  {
    title: 'Spa & Wellness',
    desc: 'Thư giãn tuyệt đối với các liệu trình chăm sóc sức khỏe và sắc đẹp chuyên sâu.',
    icon: 'fas fa-spa'
  },
  {
    title: 'Hồ bơi vô cực',
    desc: 'Đắm mình trong làn nước mát lạnh và ngắm nhìn toàn cảnh thành phố từ trên cao.',
    icon: 'fas fa-swimmer'
  },
  {
    title: 'Đưa đón sân bay',
    desc: 'Dịch vụ xe đưa đón sang trọng, đảm bảo sự tiện lợi và an toàn cho hành trình của bạn.',
    icon: 'fas fa-car'
  }
]

const testimonials = [
  {
    name: 'Nguyễn Văn A',
    content: 'Dịch vụ tuyệt vời, phòng ốc sạch sẽ và sang trọng. Tôi chắc chắn sẽ quay lại đây vào lần tới.',
    avatar: 'https://i.pravatar.cc/150?u=1',
  },
  {
    name: 'Trần Thị B',
    content: 'Nhân viên rất nhiệt tình và chu đáo. Đồ ăn tại nhà hàng rất ngon và đa dạng.',
    avatar: 'https://i.pravatar.cc/150?u=2',
  },
  {
    name: 'Lê Văn C',
    content: 'Không gian yên tĩnh, view đẹp. Một nơi lý tưởng để nghỉ dưỡng cùng gia đình.',
    avatar: 'https://i.pravatar.cc/150?u=3',
  }
]

const formatPrice = (price) => {
  if (!price) return '0 đ'
  const formatted = new Intl.NumberFormat('vi-VN').format(price * 25000)
  return `${formatted} đ`
}

const fetchRooms = async () => {
  try {
    const response = await axios.get('/rooms')
    rooms.value = response.data
  } catch (error) {
    console.error('Error fetching rooms:', error)
  }
}

onMounted(() => {
  fetchRooms()
  const userData = localStorage.getItem('user')
  if (userData) {
    user.value = JSON.parse(userData)
  }
})
</script>
