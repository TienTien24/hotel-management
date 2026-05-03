<template>
  <div class="min-h-screen bg-white font-sans overflow-x-hidden">
    <!-- Hero Section -->
    <section class="relative h-[600px] flex items-center justify-center">
      <HeroSlider />
      <!-- Hero Content Overlay -->
      <div class="absolute inset-0 z-10 flex flex-col justify-center items-start max-w-7xl mx-auto px-6 pointer-events-none">
      </div>
    </section>

    <!-- Search Section -->
    <div class="relative z-30 max-w-7xl mx-auto px-6 -mt-24">
      <RoomSearchSection />
    </div>

    <!-- Quick Features -->
    <section class="max-w-7xl mx-auto px-6 py-20 grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-8">
      <div v-for="(feat, idx) in quickFeatures" :key="idx" class="flex flex-col items-center text-center group">
        <div class="w-14 h-14 bg-gray-50 rounded-2xl flex items-center justify-center text-emerald-800 text-xl mb-4 group-hover:bg-[#004d26] group-hover:text-white transition-all duration-500 shadow-sm border border-gray-100">
          <i :class="feat.icon"></i>
        </div>
        <h4 class="text-[10px] font-black text-emerald-950 uppercase tracking-widest mb-1">{{ feat.title }}</h4>
        <p class="text-[9px] text-gray-400 font-bold uppercase tracking-widest">{{ feat.desc }}</p>
      </div>
    </section>

    <!-- Room Highlights -->
    <section id="rooms" class="max-w-7xl mx-auto px-6 py-32 border-t border-gray-50">
      <div class="flex flex-col md:flex-row justify-between items-end mb-16 gap-8">
        <div>
          <p class="text-amber-400 font-bold uppercase tracking-[0.3em] text-[10px] mb-4">Phòng nghỉ</p>
          <h2 class="text-3xl md:text-5xl font-black text-emerald-950 uppercase tracking-tighter leading-none">Không gian nghỉ dưỡng tuyệt vời</h2>
        </div>
        <router-link to="/rooms" class="group flex items-center gap-3 text-emerald-900 font-black uppercase tracking-widest text-[11px] hover:text-emerald-700 transition-colors">
          Xem tất cả phòng
          <i class="fas fa-arrow-right group-hover:translate-x-2 transition-transform"></i>
        </router-link>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
        <!-- Deluxe Ocean View -->
        <div class="group bg-white rounded-3xl overflow-hidden shadow-md hover:shadow-xl transition-all duration-500 border border-gray-100 flex flex-col h-full">
          <div class="relative h-48 overflow-hidden">
            <img src="https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=800&q=80" class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-1000">
          </div>
          <div class="p-6 flex-1 flex flex-col">
            <h3 class="text-lg font-black text-emerald-950 uppercase tracking-tight mb-2">Deluxe Ocean View</h3>
            <div class="flex items-center gap-4 mb-4 text-gray-400">
              <span class="text-[9px] font-bold uppercase tracking-widest flex items-center gap-1.5"><i class="fas fa-expand text-[8px]"></i> 45m²</span>
              <span class="text-[9px] font-bold uppercase tracking-widest flex items-center gap-1.5"><i class="fas fa-users text-[8px]"></i> 2 người</span>
              <span class="text-[9px] font-bold uppercase tracking-widest flex items-center gap-1.5"><i class="fas fa-bed text-[8px]"></i> 1 giường</span>
            </div>
            <p class="text-[11px] text-gray-500 font-medium leading-relaxed mb-6 line-clamp-2">Hướng biển tuyệt đẹp, không gian thoáng đãng và đầy đủ tiện nghi hiện đại.</p>
            <div class="mt-auto pt-6 border-t border-gray-50 flex items-center justify-between">
              <div><p class="text-lg font-black text-emerald-800 leading-none">1.850.000đ <span class="text-[10px] text-gray-400 font-bold">/đêm</span></p></div>
              <button @click="$router.push('/rooms')" class="px-5 py-2 bg-white text-emerald-950 border border-emerald-100 rounded-xl text-[9px] font-black uppercase tracking-widest hover:bg-[#004d26] hover:text-white transition-all">Xem chi tiết</button>
            </div>
          </div>
        </div>

        <!-- Premier Suite -->
        <div class="group bg-white rounded-3xl overflow-hidden shadow-md hover:shadow-xl transition-all duration-500 border border-gray-100 flex flex-col h-full">
          <div class="relative h-48 overflow-hidden">
            <img src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80" class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-1000">
          </div>
          <div class="p-6 flex-1 flex flex-col">
            <h3 class="text-lg font-black text-emerald-950 uppercase tracking-tight mb-2">Premier Suite</h3>
            <div class="flex items-center gap-4 mb-4 text-gray-400">
              <span class="text-[9px] font-bold uppercase tracking-widest flex items-center gap-1.5"><i class="fas fa-expand text-[8px]"></i> 65m²</span>
              <span class="text-[9px] font-bold uppercase tracking-widest flex items-center gap-1.5"><i class="fas fa-users text-[8px]"></i> 2 người</span>
              <span class="text-[9px] font-bold uppercase tracking-widest flex items-center gap-1.5"><i class="fas fa-bed text-[8px]"></i> 1 giường</span>
            </div>
            <p class="text-[11px] text-gray-500 font-medium leading-relaxed mb-6 line-clamp-2">Phòng suite sang trọng với ban công riêng và tầm nhìn toàn cảnh thành phố.</p>
            <div class="mt-auto pt-6 border-t border-gray-50 flex items-center justify-between">
              <div><p class="text-lg font-black text-emerald-800 leading-none">2.850.000đ <span class="text-[10px] text-gray-400 font-bold">/đêm</span></p></div>
              <button @click="$router.push('/rooms')" class="px-5 py-2 bg-white text-emerald-950 border border-emerald-100 rounded-xl text-[9px] font-black uppercase tracking-widest hover:bg-[#004d26] hover:text-white transition-all">Xem chi tiết</button>
            </div>
          </div>
        </div>

        <!-- Family Suite -->
        <div class="group bg-white rounded-3xl overflow-hidden shadow-md hover:shadow-xl transition-all duration-500 border border-gray-100 flex flex-col h-full">
          <div class="relative h-48 overflow-hidden">
            <img src="https://images.unsplash.com/photo-1512918728675-ed5a9ecdebfd?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80" class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-1000">
          </div>
          <div class="p-6 flex-1 flex flex-col">
            <h3 class="text-lg font-black text-emerald-950 uppercase tracking-tight mb-2">Family Suite</h3>
            <div class="flex items-center gap-4 mb-4 text-gray-400">
              <span class="text-[9px] font-bold uppercase tracking-widest flex items-center gap-1.5"><i class="fas fa-expand text-[8px]"></i> 55m²</span>
              <span class="text-[9px] font-bold uppercase tracking-widest flex items-center gap-1.5"><i class="fas fa-users text-[8px]"></i> 4 người</span>
              <span class="text-[9px] font-bold uppercase tracking-widest flex items-center gap-1.5"><i class="fas fa-bed text-[8px]"></i> 2 giường</span>
            </div>
            <p class="text-[11px] text-gray-500 font-medium leading-relaxed mb-6 line-clamp-2">Không gian rộng rãi cho cả gia đình, đầy đủ tiện ích và ấm cúng.</p>
            <div class="mt-auto pt-6 border-t border-gray-50 flex items-center justify-between">
              <div><p class="text-lg font-black text-emerald-800 leading-none">3.450.000đ <span class="text-[10px] text-gray-400 font-bold">/đêm</span></p></div>
              <button @click="$router.push('/rooms')" class="px-5 py-2 bg-white text-emerald-950 border border-emerald-100 rounded-xl text-[9px] font-black uppercase tracking-widest hover:bg-[#004d26] hover:text-white transition-all">Xem chi tiết</button>
            </div>
          </div>
        </div>

        <!-- Presidential Suite -->
        <div class="group bg-white rounded-3xl overflow-hidden shadow-md hover:shadow-xl transition-all duration-500 border border-gray-100 flex flex-col h-full">
          <div class="relative h-48 overflow-hidden">
            <img src="https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=800&q=80" class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-1000">
          </div>
          <div class="p-6 flex-1 flex flex-col">
            <h3 class="text-lg font-black text-emerald-950 uppercase tracking-tight mb-2">Presidential Suite</h3>
            <div class="flex items-center gap-4 mb-4 text-gray-400">
              <span class="text-[9px] font-bold uppercase tracking-widest flex items-center gap-1.5"><i class="fas fa-expand text-[8px]"></i> 125m²</span>
              <span class="text-[9px] font-bold uppercase tracking-widest flex items-center gap-1.5"><i class="fas fa-users text-[8px]"></i> 2 người</span>
              <span class="text-[9px] font-bold uppercase tracking-widest flex items-center gap-1.5"><i class="fas fa-bed text-[8px]"></i> 1 giường</span>
            </div>
            <p class="text-[11px] text-gray-500 font-medium leading-relaxed mb-6 line-clamp-2">Đẳng cấp thượng lưu với phòng khách riêng, bếp và dịch vụ quản gia.</p>
            <div class="mt-auto pt-6 border-t border-gray-50 flex items-center justify-between">
              <div><p class="text-lg font-black text-emerald-800 leading-none">6.850.000đ <span class="text-[10px] text-gray-400 font-bold">/đêm</span></p></div>
              <button @click="$router.push('/rooms')" class="px-5 py-2 bg-white text-emerald-950 border border-emerald-100 rounded-xl text-[9px] font-black uppercase tracking-widest hover:bg-[#004d26] hover:text-white transition-all">Xem chi tiết</button>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Summer Promo -->
    <section class="max-w-7xl mx-auto px-6 pb-32">
      <div class="relative rounded-[3rem] overflow-hidden h-[400px] flex items-center group">
        <img src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80" class="absolute inset-0 w-full h-full object-cover transition-transform duration-1000 group-hover:scale-105">
        <div class="absolute inset-0 bg-emerald-950/40 backdrop-blur-[2px]"></div>
        <div class="relative z-10 p-12 md:p-20 text-white max-w-2xl">
          <p class="text-amber-400 font-black uppercase tracking-[0.3em] text-[10px] mb-4">Ưu đãi đặc biệt</p>
          <h2 class="text-4xl md:text-6xl font-black uppercase tracking-tighter mb-6">Ưu đãi mùa hè 2026</h2>
          <p class="text-lg text-white/80 font-medium mb-10 italic">Giảm đến 20% khi đặt phòng sớm và lưu trú từ 01/05 - 30/09/2026</p>
          
          <div class="flex gap-4 mb-10">
            <div v-for="t in ['23 NGÀY', '14 GIỜ', '36 PHÚT', '45 GIÂY']" :key="t" class="bg-black/20 backdrop-blur-md rounded-2xl px-4 py-3 border border-white/10">
              <p class="text-lg font-black leading-none">{{ t.split(' ')[0] }}</p>
              <p class="text-[8px] font-bold uppercase mt-1 opacity-60">{{ t.split(' ')[1] }}</p>
            </div>
          </div>
          
          <button class="px-10 py-4 bg-white text-emerald-950 rounded-2xl font-black uppercase tracking-widest text-[10px] hover:bg-amber-400 transition-all shadow-xl">Xem ưu đãi</button>
        </div>
      </div>
    </section>

    <!-- Service Highlights -->
    <section id="services" class="bg-gray-50 py-32 border-y border-gray-100">
      <div class="max-w-7xl mx-auto px-6">
        <div class="text-center mb-24">
          <p class="text-amber-400 font-bold uppercase tracking-[0.3em] text-[10px] mb-4 italic">Dịch vụ & Tiện ích</p>
          <h2 class="text-4xl md:text-6xl font-black text-emerald-950 uppercase tracking-tighter leading-none">Trải nghiệm dịch vụ đẳng cấp</h2>
          <div class="w-24 h-1 bg-[#004d26] mx-auto mt-8"></div>
        </div>

        <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-8">
          <div v-for="s in services" :key="s.title" class="group bg-white p-8 rounded-[2.5rem] shadow-xl shadow-emerald-900/5 hover:shadow-2xl hover:-translate-y-2 transition-all duration-500 border border-gray-100 flex flex-col items-center text-center">
            <div class="w-14 h-14 bg-emerald-50 rounded-2xl flex items-center justify-center text-emerald-800 text-xl mb-6 group-hover:bg-[#004d26] group-hover:text-white transition-all shadow-inner">
              <i :class="s.icon"></i>
            </div>
            <h4 class="text-[11px] font-black text-emerald-950 uppercase tracking-widest mb-3">{{ s.title }}</h4>
            <p class="text-[9px] text-gray-400 font-bold uppercase tracking-widest leading-relaxed">{{ s.desc }}</p>
          </div>
        </div>
        
        <div class="mt-20 text-center">
          <router-link to="/services" class="px-10 py-4 border-2 border-emerald-900 text-emerald-900 rounded-2xl font-black uppercase tracking-widest text-[10px] hover:bg-emerald-900 hover:text-white transition-all">Xem tất cả dịch vụ</router-link>
        </div>
      </div>
    </section>

    <!-- Photo Gallery -->
    <section class="max-w-7xl mx-auto px-6 py-32">
      <div class="text-center mb-24">
        <p class="text-amber-400 font-bold uppercase tracking-[0.3em] text-[10px] mb-4 italic">Khám phá Grand Hotel</p>
        <h2 class="text-4xl md:text-6xl font-black text-emerald-950 uppercase tracking-tighter leading-none">Khoảnh khắc đáng nhớ</h2>
      </div>

      <div class="grid grid-cols-2 md:grid-cols-4 gap-6">
        <div v-for="i in 4" :key="i" class="relative h-64 md:h-80 rounded-[2rem] overflow-hidden group cursor-pointer shadow-xl">
          <img :src="`https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80&sig=${i}`" class="w-full h-full object-cover transition-transform duration-1000 group-hover:scale-110">
          <div class="absolute inset-0 bg-black/20 opacity-0 group-hover:opacity-100 transition-opacity flex items-center justify-center">
            <i class="fas fa-search-plus text-white text-3xl"></i>
          </div>
        </div>
      </div>
      
      <div class="mt-16 text-center">
        <button class="px-10 py-4 bg-gray-50 text-gray-400 rounded-2xl font-black uppercase tracking-widest text-[10px] hover:bg-emerald-50 hover:text-emerald-800 transition-all border border-gray-100">Xem thêm hình ảnh</button>
      </div>
    </section>

    <!-- Testimonials -->
    <section class="bg-gray-50 py-32 overflow-hidden">
      <div class="max-w-7xl mx-auto px-6">
        <div class="mb-20">
          <p class="text-amber-400 font-bold uppercase tracking-[0.3em] text-[10px] mb-4">Khách hàng nói gì</p>
          <h2 class="text-4xl md:text-5xl font-black text-emerald-950 uppercase tracking-tighter">Cảm nhận từ khách hàng</h2>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-10">
          <div v-for="(test, idx) in testimonials" :key="idx" class="bg-white p-10 rounded-[3rem] shadow-xl shadow-emerald-900/5 border border-gray-100 relative group hover:shadow-2xl transition-all duration-500">
            <div class="flex text-amber-400 gap-1 mb-6">
              <i v-for="n in 5" :key="n" class="fas fa-star text-[10px]"></i>
            </div>
            <p class="text-sm text-slate-700 font-medium leading-relaxed mb-8 italic">"{{ test.content }}"</p>
            <div class="flex items-center gap-4">
              <div class="w-12 h-12 rounded-full overflow-hidden border-2 border-emerald-50">
                <img :src="test.avatar" class="w-full h-full object-cover">
              </div>
              <div>
                <p class="text-xs font-black text-emerald-950 uppercase tracking-widest">{{ test.name }}</p>
                <p class="text-[9px] text-gray-400 font-bold uppercase tracking-widest mt-1">{{ test.location }}</p>
              </div>
            </div>
            <i class="fas fa-quote-right absolute top-10 right-10 opacity-5 text-4xl text-emerald-900"></i>
          </div>
        </div>
      </div>
    </section>

    <!-- Newsletter -->
    <section class="max-w-7xl mx-auto px-6 pb-32">
      <div class="bg-[#004d26] rounded-[3rem] p-12 md:p-20 text-white relative overflow-hidden group shadow-2xl shadow-green-900/40">
        <div class="absolute inset-0 bg-white/5 opacity-0 group-hover:opacity-100 transition-opacity duration-700"></div>
        <div class="relative z-10 flex flex-col lg:flex-row items-center justify-between gap-12">
          <div class="flex items-center gap-8 lg:w-1/2">
            <div class="w-20 h-20 bg-white/10 rounded-[2rem] flex items-center justify-center text-amber-400 text-3xl border border-white/20 shadow-inner shrink-0">
              <i class="fas fa-envelope-open-text"></i>
            </div>
            <div>
              <h3 class="text-3xl font-black uppercase tracking-tighter mb-2">Đăng ký nhận ưu đãi & tin tức mới nhất</h3>
              <p class="text-sm text-white/60 font-medium">Nhận ngay ưu đãi đặc quyền và thông tin hữu ích từ Grand Hotel.</p>
            </div>
          </div>
          <div class="lg:w-1/2 w-full">
            <form class="flex flex-col sm:flex-row gap-4">
              <input type="email" placeholder="Nhập email của bạn" class="flex-1 bg-white/10 border-2 border-white/10 rounded-2xl px-8 py-5 outline-none focus:bg-white focus:text-emerald-950 transition-all font-bold text-sm">
              <button class="px-10 py-5 bg-amber-400 text-emerald-950 rounded-2xl font-black uppercase tracking-widest text-[10px] hover:bg-white transition-all shadow-xl">Đăng ký ngay</button>
            </form>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import HeroSlider from '../components/HeroSlider.vue'
import RoomSearchSection from '../components/RoomSearchSection.vue'
import axios from '../api/axios'

const quickFeatures = [
  { icon: 'fas fa-map-marker-alt', title: 'Vị trí đắc địa', desc: 'Ngay trung tâm thành phố' },
  { icon: 'fas fa-award', title: 'Dịch vụ 5 sao', desc: 'Chuẩn quốc tế, tận tâm 24/7' },
  { icon: 'fas fa-utensils', title: 'Ẩm thực tinh tế', desc: 'Nhà hàng sang trọng, đầu bếp danh tiếng' },
  { icon: 'fas fa-swimming-pool', title: 'Tiện ích đa dạng', desc: 'Hồ bơi, spa, gym, kid club...' },
  { icon: 'fas fa-tags', title: 'Ưu đãi hấp dẫn', desc: 'Giá tốt nhất khi đặt trực tuyến' },
  { icon: 'fas fa-credit-card', title: 'Thanh toán linh hoạt', desc: 'Nhiều hình thức tiện lợi, an toàn' }
]

const roomHighlights = [
  { displayTitle: 'Deluxe Ocean View', size: '45m²', description: 'Hướng biển tuyệt đẹp, không gian thoáng đãng với tiện nghi hiện đại.', price: '1.850.000đ', imageUrl: 'https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=800&q=80' },
  { displayTitle: 'Premier Suite', size: '65m²', description: 'Phòng suite sang trọng với ban công riêng và tầm nhìn toàn cảnh.', price: '2.850.000đ', imageUrl: 'https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80' },
  { displayTitle: 'Family Suite', size: '55m²', description: 'Không gian rộng cho gia đình, đầy đủ tiện ích và ấm cúng.', price: '3.450.000đ', imageUrl: 'https://images.unsplash.com/photo-1512918728675-ed5a9ecdebfd?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80' },
  { displayTitle: 'Presidential Suite', size: '125m²', description: 'Đẳng cấp thượng lưu với phòng khách riêng, bếp và dịch vụ quản gia.', price: '6.850.000đ', imageUrl: 'https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=800&q=80' }
]

const services = [
  { icon: 'fas fa-swimming-pool', title: 'Hồ bơi vô cực', desc: 'Thư giãn với tầm nhìn hướng biển tuyệt đẹp' },
  { icon: 'fas fa-spa', title: 'Spa & Wellness', desc: 'Liệu trình chuyên sâu chăm sóc sức khỏe' },
  { icon: 'fas fa-utensils', title: 'Nhà hàng cao cấp', desc: 'Ẩm thực đa dạng hương vị quốc tế' },
  { icon: 'fas fa-dumbbell', title: 'Phòng gym hiện đại', desc: 'Trang thiết bị tiên tiến, đầy đủ tiện nghi' },
  { icon: 'fas fa-child', title: 'Kid Club', desc: 'Khu vui chơi an toàn, thú vị cho trẻ nhỏ' },
  { icon: 'fas fa-shuttle-van', title: 'Dịch vụ đưa đón', desc: 'Xe đưa đón sân bay tham quan tiện lợi' }
]

const testimonials = [
  { name: 'Nguyễn Minh Anh', location: 'Hà Nội', avatar: 'https://i.pravatar.cc/150?u=1', content: 'Kỳ nghỉ tuyệt vời! Dịch vụ chuyên nghiệp, phòng ốc sạch đẹp, đồ ăn rất ngon. Chắc chắn sẽ quay lại.' },
  { name: 'Trần Quốc Bảo', location: 'TP. Hồ Chí Minh', avatar: 'https://i.pravatar.cc/150?u=2', content: 'Không gian yên bình, view đẹp, nhân viên nhiệt tình. Gia đình mình rất hài lòng về kỳ nghỉ tại đây.' },
  { name: 'Lê Thùy Dương', location: 'Đà Nẵng', avatar: 'https://i.pravatar.cc/150?u=3', content: 'Spa rất thư giãn, hồ bơi vô cực đẹp xuất sắc. Một trải nghiệm 5 sao đúng nghĩa!' }
]

onMounted(() => {
  window.scrollTo(0, 0)
})
</script>

<style scoped>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css');

.animate-fade-in { animation: fadeIn 1s ease-out forwards; }
.animate-slide-up { animation: slideUp 1s ease-out forwards; }
.delay-200 { animation-delay: 0.2s; }

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

@keyframes slideUp {
  from { opacity: 0; transform: translateY(30px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>