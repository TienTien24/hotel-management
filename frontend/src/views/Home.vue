<template>
  <div class="min-h-screen bg-white">
    <!-- Hero Section -->
    <HeroSlider />

    <!-- Role-based Welcome Section -->
    <section v-if="user" class="bg-emerald-50 py-12 border-y border-emerald-100">
      <div class="max-w-7xl mx-auto px-4 flex flex-col md:flex-row items-center justify-between">
        <div class="mb-6 md:mb-0">
          <h2 class="text-2xl font-black uppercase text-emerald-950">Chào mừng trở lại, {{ user.username }}!</h2>
          <p class="text-emerald-700 font-medium tracking-widest uppercase text-sm mt-1">Vai trò: {{ user.role }}</p>
        </div>
        <div class="flex space-x-4">
          <router-link v-if="user.role === 'ADMIN' || user.role === 'STAFF'" to="/dashboard" class="bg-emerald-800 text-white px-8 py-3 rounded-full font-bold uppercase tracking-widest hover:bg-emerald-900 transition-all shadow-md">
            Trang Quản lý
          </router-link>
          <router-link to="/rooms" class="bg-white text-emerald-800 border-2 border-emerald-800 px-8 py-3 rounded-full font-bold uppercase tracking-widest hover:bg-emerald-50 transition-all shadow-sm">
            {{ user.role === 'CUSTOMER' ? 'Đặt phòng ngay' : 'Xem danh sách phòng' }}
          </router-link>
        </div>
      </div>
    </section>

    <!-- About Section -->
    <section id="about" class="max-w-7xl mx-auto px-4 py-12 border-b border-gray-100">
      <div class="flex flex-col md:flex-row items-start gap-12">
        <div class="md:w-5/12 relative group">
          <div class="absolute -top-10 -left-10 w-40 h-40 bg-emerald-50 rounded-full -z-10 group-hover:scale-110 transition-transform duration-700"></div>
          <img src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=1000&q=80" class="rounded-3xl shadow-2xl relative z-10 w-full h-[480px] object-cover transition-transform duration-700 hover:scale-[1.02]">
          <div class="absolute -bottom-6 -right-6 bg-white p-8 rounded-2xl shadow-2xl z-20 border border-emerald-50 transform hover:-translate-y-2 transition-all duration-300">
            <p class="text-4xl font-black text-emerald-800">25+</p>
            <p class="text-gray-500 uppercase tracking-widest text-[10px] font-bold mt-1">Năm Kinh Nghiệm</p>
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
      <div class="text-center mb-12">
        <h3 class="text-emerald-800 font-bold uppercase tracking-[0.3em] mb-4">Loại Phòng</h3>
        <h2 class="text-4xl md:text-5xl font-black uppercase text-emerald-950 tracking-tighter">Nghỉ dưỡng 5 sao</h2>
        <div class="w-20 h-1 bg-emerald-800 mx-auto mt-6"></div>
      </div>
      
      <div class="grid grid-cols-1 md:grid-cols-3 gap-10">
        <div class="group cursor-pointer">
          <div class="overflow-hidden rounded-2xl mb-6 shadow-xl relative">
            <img src="https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80" alt="Phòng Suite" class="w-full h-80 object-cover group-hover:scale-110 transition-transform duration-700">
            <div class="absolute inset-0 bg-emerald-900/40 opacity-0 group-hover:opacity-100 transition-opacity duration-300 flex items-center justify-center">
              <router-link to="/rooms" class="bg-white text-emerald-900 px-6 py-2 rounded-full font-bold uppercase text-xs tracking-widest shadow-xl">Xem chi tiết</router-link>
            </div>
          </div>
          <h4 class="text-xl font-bold uppercase tracking-widest text-emerald-900 mb-2">Presidential Suite</h4>
          <p class="text-gray-600 leading-relaxed font-light">Sự kết hợp hoàn hảo giữa nét cổ điển và hơi thở hiện đại trong từng chi tiết thiết kế.</p>
        </div>
        
        <div class="group cursor-pointer">
          <div class="overflow-hidden rounded-2xl mb-6 shadow-xl relative">
            <img src="https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80" alt="Phòng Deluxe" class="w-full h-80 object-cover group-hover:scale-110 transition-transform duration-700">
            <div class="absolute inset-0 bg-emerald-900/40 opacity-0 group-hover:opacity-100 transition-opacity duration-300 flex items-center justify-center">
              <router-link to="/rooms" class="bg-white text-emerald-900 px-6 py-2 rounded-full font-bold uppercase text-xs tracking-widest shadow-xl">Xem chi tiết</router-link>
            </div>
          </div>
          <h4 class="text-xl font-bold uppercase tracking-widest text-emerald-900 mb-2">Deluxe Ocean View</h4>
          <p class="text-gray-600 leading-relaxed font-light">Mỗi phòng nghỉ là một ốc đảo riêng tư với đầy đủ tiện nghi cao cấp nhất dành cho bạn.</p>
        </div>
        
        <div class="group cursor-pointer">
          <div class="overflow-hidden rounded-2xl mb-6 shadow-xl relative">
            <img src="https://images.unsplash.com/photo-1540555700478-4be289fbecef?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80" alt="Phòng Standard" class="w-full h-80 object-cover group-hover:scale-110 transition-transform duration-700">
            <div class="absolute inset-0 bg-emerald-900/40 opacity-0 group-hover:opacity-100 transition-opacity duration-300 flex items-center justify-center">
              <router-link to="/rooms" class="bg-white text-emerald-900 px-6 py-2 rounded-full font-bold uppercase text-xs tracking-widest shadow-xl">Xem chi tiết</router-link>
            </div>
          </div>
          <h4 class="text-xl font-bold uppercase tracking-widest text-emerald-900 mb-2">Standard Garden Room</h4>
          <p class="text-gray-600 leading-relaxed font-light">Thưởng thức những món ăn đặc sắc được chế biến bởi các đầu bếp hàng đầu thế giới.</p>
        </div>
      </div>
    </section>

    <!-- Services Section -->
    <section id="services" class="bg-gray-50 py-16 border-y border-gray-100">
      <div class="max-w-7xl mx-auto px-4">
        <div class="flex flex-col md:flex-row justify-between items-end mb-12 gap-6">
          <div class="max-w-2xl">
            <h3 class="text-emerald-800 font-bold uppercase tracking-[0.3em] mb-4 text-sm">Trải nghiệm dịch vụ</h3>
            <h2 class="text-4xl md:text-5xl font-black uppercase text-emerald-950 tracking-tighter">Tiện nghi đẳng cấp quốc tế</h2>
          </div>
          <p class="text-gray-500 font-light max-w-sm mb-2 italic">"Chúng tôi không chỉ cung cấp chỗ nghỉ, chúng tôi mang đến trải nghiệm."</p>
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

    <!-- Testimonials Section -->
    <section id="testimonials" class="py-16 bg-white">
      <div class="max-w-7xl mx-auto px-4">
        <div class="text-center mb-12">
          <h3 class="text-emerald-800 font-bold uppercase tracking-[0.3em] mb-4 text-sm">Đánh giá của khách</h3>
          <h2 class="text-4xl md:text-5xl font-black uppercase text-emerald-950 tracking-tighter">Khách hàng nói gì về chúng tôi</h2>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-12">
          <div v-for="(testimonial, index) in testimonials" :key="index" class="relative">
            <div class="absolute -top-6 -left-4 text-6xl text-emerald-100 font-serif">"</div>
            <div class="relative z-10">
              <p class="text-gray-600 italic leading-relaxed mb-8 font-light text-lg">
                {{ testimonial.content }}
              </p>
              <div class="flex items-center space-x-4 border-t border-gray-100 pt-6">
                <img :src="testimonial.avatar" class="w-14 h-14 rounded-full object-cover shadow-md">
                <div>
                  <h5 class="font-bold text-emerald-900 uppercase text-sm tracking-widest">{{ testimonial.name }}</h5>
                  <div class="flex text-yellow-400 text-xs mt-1">
                    <i v-for="i in 5" :key="i" class="fas fa-star"></i>
                  </div>
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
import { ref, onMounted } from 'vue'
import HeroSlider from '../components/HeroSlider.vue'

const user = ref(null)

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

onMounted(() => {
  const userData = localStorage.getItem('user')
  if (userData) {
    user.value = JSON.parse(userData)
  }
})
</script>