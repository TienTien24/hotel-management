<template>
  <main class="services-page min-h-screen bg-white font-sans">
    <!-- Hero Section -->
    <header class="relative h-[500px] flex items-center justify-center overflow-hidden">
      <div class="absolute inset-0 z-0">
        <img src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80" 
             alt="Hero Background" 
             class="w-full h-full object-cover">
        <div class="absolute inset-0 bg-black/60"></div>
      </div>
      <div class="max-w-7xl mx-auto px-6 relative z-10">
        <h1 class="text-7xl md:text-9xl font-black text-white uppercase tracking-tighter mb-4 leading-none">
          DỊCH VỤ
        </h1>
        <p class="text-2xl text-white/80 font-medium tracking-tight">
          Trải nghiệm tiện nghi đẳng cấp
        </p>
      </div>
    </header>

    <!-- Info Bar -->
    <div class="max-w-7xl mx-auto px-6 -mt-16 relative z-20">
      <div class="bg-white rounded-[2.5rem] shadow-2xl shadow-emerald-900/10 p-10 border border-emerald-50 grid grid-cols-1 md:grid-cols-4 gap-8">
        <div class="flex items-center gap-5">
          <div class="w-14 h-14 bg-emerald-50 rounded-2xl flex items-center justify-center text-emerald-800 text-xl shadow-inner shrink-0">
            <i class="fas fa-star"></i>
          </div>
          <div>
            <h4 class="text-sm font-black text-slate-800 uppercase tracking-tight">Chất lượng 5 sao</h4>
            <p class="text-[10px] text-gray-400 font-bold mt-1 uppercase">Dịch vụ tiêu chuẩn quốc tế</p>
          </div>
        </div>
        <div class="flex items-center gap-5">
          <div class="w-14 h-14 bg-emerald-50 rounded-2xl flex items-center justify-center text-emerald-800 text-xl shadow-inner shrink-0">
            <i class="fas fa-th-large"></i>
          </div>
          <div>
            <h4 class="text-sm font-black text-slate-800 uppercase tracking-tight">Đa dạng tiện ích</h4>
            <p class="text-[10px] text-gray-400 font-bold mt-1 uppercase">Đáp ứng mọi nhu cầu</p>
          </div>
        </div>
        <div class="flex items-center gap-5">
          <div class="w-14 h-14 bg-emerald-50 rounded-2xl flex items-center justify-center text-emerald-800 text-xl shadow-inner shrink-0">
            <i class="fas fa-file-alt"></i>
          </div>
          <div>
            <h4 class="text-sm font-black text-slate-800 uppercase tracking-tight">Đặt dịch vụ dễ dàng</h4>
            <p class="text-[10px] text-gray-400 font-bold mt-1 uppercase">Nhanh chóng, tiện lợi</p>
          </div>
        </div>
        <div class="flex items-center gap-5">
          <div class="w-14 h-14 bg-emerald-50 rounded-2xl flex items-center justify-center text-emerald-800 text-xl shadow-inner shrink-0">
            <i class="fas fa-headset"></i>
          </div>
          <div>
            <h4 class="text-sm font-black text-slate-800 uppercase tracking-tight">Hỗ trợ 24/7</h4>
            <p class="text-[10px] text-gray-400 font-bold mt-1 uppercase">Luôn sẵn sàng phục vụ</p>
          </div>
        </div>
      </div>
    </div>

    <!-- Services Section -->
    <section class="max-w-7xl mx-auto px-6 py-16">
      <div class="text-center mb-12">
        <p class="text-amber-400 font-bold uppercase tracking-[0.3em] text-[10px] mb-4 italic">Dịch vụ & Tiện ích</p>
        <h2 class="text-4xl md:text-6xl font-black text-emerald-950 uppercase tracking-tighter mb-4">Danh sách dịch vụ</h2>
        <div class="w-24 h-1 bg-[#004d26] mx-auto mb-8"></div>
        <p class="text-gray-400 font-bold uppercase tracking-widest text-sm">Khám phá các tiện ích và dịch vụ đẳng cấp tại Grand Hotel</p>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-12">
        <!-- Service Cards -->
        <div v-for="service in services" :key="service.id" 
             @click="openServiceDetail(service)"
             class="group bg-white rounded-[3rem] shadow-xl hover:shadow-2xl transition-all duration-500 overflow-hidden border border-gray-100 flex flex-col sm:flex-row cursor-pointer hover:-translate-y-2">
          <div class="sm:w-2/5 h-64 sm:h-auto overflow-hidden">
            <img :src="service.image" class="w-full h-full object-cover group-hover:scale-110 transition-transform duration-1000">
          </div>
          <div class="sm:w-3/5 p-10 flex flex-col justify-between">
            <div>
              <div class="flex items-center gap-4 mb-4">
                <div class="w-12 h-12 bg-emerald-50 rounded-2xl flex items-center justify-center text-emerald-800 text-lg shadow-inner">
                  <i :class="service.icon"></i>
                </div>
                <h3 class="text-2xl font-black text-slate-800 uppercase tracking-tight">{{ service.title }}</h3>
              </div>
              <p class="text-sm text-gray-500 leading-relaxed font-medium mb-6">{{ service.shortDesc }}</p>
              <ul class="space-y-3 mb-8">
                <li v-for="feature in service.features.slice(0, 3)" :key="feature" class="flex items-center gap-3 text-xs font-bold text-gray-400 uppercase tracking-widest">
                  <i class="fas fa-check-circle text-emerald-500"></i> {{ feature }}
                </li>
              </ul>
            </div>
            <div class="flex items-center gap-2 text-emerald-800 text-xs font-black uppercase tracking-widest">
              <span>Xem chi tiết</span>
              <i class="fas fa-arrow-right group-hover:translate-x-1 transition-transform"></i>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Service Detail Modal -->
    <div v-if="selectedService" class="fixed inset-0 bg-emerald-950/80 backdrop-blur-sm flex items-center justify-center p-4 z-50">
      <div class="bg-white rounded-[3rem] max-w-4xl w-full shadow-2xl transform transition-all max-h-[90vh] overflow-y-auto">
        <div class="relative">
          <div class="aspect-video overflow-hidden rounded-t-[3rem]">
            <img :src="selectedService.image" class="w-full h-full object-cover">
          </div>
          <button @click="closeServiceDetail" class="absolute top-4 right-4 w-12 h-12 bg-white/90 hover:bg-white rounded-full flex items-center justify-center text-emerald-900 shadow-lg transition-all">
            <i class="fas fa-times text-xl"></i>
          </button>
        </div>
        <div class="p-10">
          <div class="flex items-center gap-4 mb-6">
            <div class="w-16 h-16 bg-emerald-50 rounded-2xl flex items-center justify-center text-emerald-800 text-2xl shadow-inner">
              <i :class="selectedService.icon"></i>
            </div>
            <div>
              <h2 class="text-3xl font-black text-emerald-950 uppercase tracking-tighter">{{ selectedService.title }}</h2>
              <p v-if="selectedService.price" class="text-emerald-700 font-black text-xl mt-1">{{ selectedService.price }}</p>
            </div>
          </div>
          
          <p class="text-gray-600 leading-relaxed mb-8">{{ selectedService.fullDesc }}</p>

          <div class="mb-8">
            <h4 class="text-sm font-black text-emerald-900 uppercase tracking-widest mb-4">Tính năng nổi bật</h4>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div v-for="feature in selectedService.features" :key="feature" class="flex items-center gap-3 text-gray-600">
                <i class="fas fa-check-circle text-emerald-600 text-lg"></i>
                <span class="font-medium">{{ feature }}</span>
              </div>
            </div>
          </div>

          <div v-if="selectedService.hours" class="bg-emerald-50 rounded-2xl p-6 border border-emerald-100 mb-8">
            <h4 class="text-sm font-black text-emerald-900 uppercase tracking-widest mb-3">Giờ hoạt động</h4>
            <p class="text-emerald-800 font-medium">{{ selectedService.hours }}</p>
          </div>

        </div>
      </div>
    </div>
  </main>
</template>

<script setup>
import { ref } from 'vue'

const selectedService = ref(null)

const services = ref([
  {
    id: 1,
    title: 'Nhà hàng & Bar',
    icon: 'fas fa-utensils',
    image: 'https://viphouse.vn/viphouse/images/2017/t7/thiet%20ke%20bar%20khach%20s%E1%BA%A1n/201408-w-best-rooftop-bars-in-nyc-hotel-hugo.jpg',
    shortDesc: 'Thưởng thức ẩm thực đa dạng từ Á đến Âu được chế biến bởi đội ngũ đầu bếp chuyên nghiệp.',
    fullDesc: ' Nhà hàng & Bar của Grand Hotel mang đến trải nghiệm ẩm thực tuyệt vời với thực đơn đa dạng từ các món ăn Á Đông đến Âu Mỹ. Đội ngũ đầu bếp chuyên nghiệp với nhiều năm kinh nghiệm sẽ chế biến những món ăn ngon mắt, ngon miệng từ nguyên liệu tươi ngon nhất. Không gian nhà hàng sang trọng, ấm cúng với view thành phố tuyệt đẹp là nơi hoàn hảo cho những bữa ăn gia đình, hẹn hò lãng mạn hoặc các buổi tiệc công sự.',
    features: ['Thực đơn đa dạng', 'Nguyên liệu tươi ngon', 'Không gian sang trọng', 'Đầu bếp quốc tế', 'Dịch vụ tận tâm', 'View thành phố tuyệt đẹp'],
    hours: '06:00 - 23:00 hàng ngày',
    price: 'Từ 200.000đ/người'
  },
  {
    id: 2,
    title: 'Spa & Wellness',
    icon: 'fas fa-spa',
    image: 'https://vinapad.com/wp-content/uploads/2019/07/cac-goi-dich-vu-spa-1.jpg',
    shortDesc: 'Thư giãn và tái tạo năng lượng với các liệu trình spa chuyên nghiệp.',
    fullDesc: 'Spa & Wellness của Grand Hotel là thiên đường thư giãn hoàn hảo để bạn giải tỏa căng thẳng và tái tạo năng lượng. Chúng tôi cung cấp các liệu trình massage chuyên nghiệp, chăm sóc da chuyên sâu, phòng xông hơi và sauna hiện đại. Đội ngũ kỹ thuật viên được đào tạo bài bản sẽ mang đến trải nghiệm thư giãn tuyệt vời giúp bạn cảm thấy trẻ hóa và tràn đầy năng lượng.',
    features: ['Massage thư giãn', 'Chăm sóc da chuyên sâu', 'Phòng xông hơi & sauna', 'Liệu trình dầu thơm', 'Kỹ thuật viên chuyên nghiệp', 'Không gian yên bình'],
    hours: '09:00 - 22:00 hàng ngày',
    price: 'Từ 500.000đ/liệu trình'
  },
  {
    id: 3,
    title: 'Hồ bơi vô cực',
    icon: 'fas fa-swimming-pool',
    image: 'https://danang-shopping.com/wp-content/uploads/2017/05/khach-san-da-nang-co-ho-boi-dep-diamond-sea-hotel.jpg',
    shortDesc: 'Hồ bơi vô cực view thành phố tuyệt đẹp, mang đến trải nghiệm thư giãn tuyệt vời.',
    fullDesc: 'Hồ bơi vô cực của Grand Hotel nằm trên tầng thượng với view panorama 360° toàn cảnh thành phố. Đây là nơi hoàn hảo để bạn thư giãn, tận hưởng ánh nắng và không khí trong lành. Hồ bơi được bảo dưỡng và làm sạch hàng ngày đảm bảo nước luôn trong và an toàn. Bên cạnh hồ bơi là quầy bar phục vụ các đồ uống mát lạnh và các món ăn nhẹ.',
    features: ['View panorama 360°', 'Nước sạch 4 mùa', 'Quầy bar bên hồ bơi', 'Ghế thư giãn', 'Giường nằm', 'Nhân viên cứu hộ 24/7'],
    hours: '06:00 - 21:00 hàng ngày',
    price: 'Miễn phí cho khách lưu trú'
  },
  {
    id: 4,
    title: 'Phòng họp & Sự kiện',
    icon: 'fas fa-users',
    image: 'https://acihome.vn/uploads/15/kha-nang-tuy-bien-cua-phong-hop-da-nang-khach-san.jpg',
    shortDesc: 'Không gian tổ chức sự kiện chuyên nghiệp, hiện đại với đầy đủ trang thiết bị.',
    fullDesc: 'Phòng họp & Sự kiện của Grand Hotel là lựa chọn hoàn hảo cho các hội nghị, hội thảo, tiệc cưới, tiệc sinh nhật và các sự kiện khác. Chúng tôi có nhiều phòng họp với sức chứa linh hoạt từ 10 đến 500 người, được trang bị đầy đủ trang thiết bị hiện đại như máy chiếu, âm thanh, ánh sáng chuyên nghiệp. Đội ngũ nhân viên chuyên nghiệp sẽ hỗ trợ bạn từ khâu lên kế hoạch đến khi sự kiện hoàn thành.',
    features: ['Sức chứa linh hoạt', 'Thiết bị âm thanh ánh sáng', 'Hỗ trợ kỹ thuật 24/7', 'Đăng ký chỗ', 'Dịch vụ ăn uống', 'Bảo vệ an ninh'],
    hours: 'Liên hệ để đặt lịch',
    price: 'Liên hệ để báo giá'
  },
  {
    id: 5,
    title: 'Đưa đón sân bay',
    icon: 'fas fa-shuttle-van',
    image: 'https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80',
    shortDesc: 'Dịch vụ đưa đón sân bay an toàn, tiện lợi với đội ngũ tài xế chuyên nghiệp.',
    fullDesc: 'Dịch vụ đưa đón sân bay của Grand Hotel giúp bạn có một chuyến đi an toàn và tiện lợi từ sân bay đến khách sạn và ngược lại. Chúng tôi có đội xe sang trọng, hiện đại với đầy đủ trang thiết bị an toàn. Đội ngũ tài xế chuyên nghiệp, có nhiều năm kinh nghiệm sẽ đưa đón bạn đúng giờ và an toàn. Bạn có thể đặt lịch trước 24/7 qua điện thoại hoặc email.',
    features: ['Xe sang trọng, hiện đại', 'Tài xế chuyên nghiệp', 'Đặt lịch trước 24/7', 'Đưa đón đúng giờ', 'Trang bị an toàn đầy đủ', 'Giá cả hợp lý'],
    hours: '24/7',
    price: 'Từ 400.000đ/chuyến'
  },
  {
    id: 6,
    title: 'Giặt ủi',
    icon: 'fas fa-tshirt',
    image: 'https://images.unsplash.com/photo-1582735689369-4fe89db7114c?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80',
    shortDesc: 'Dịch vụ giặt ủi nhanh chóng, sạch sẽ với công nghệ hiện đại.',
    fullDesc: 'Dịch vụ giặt ủi của Grand Hotel giúp bạn giữ quần áo sạch sẽ và thơm mát trong suốt kỳ nghỉ. Chúng tôi sử dụng công nghệ giặt ủi hiện đại, chất tẩy rửa an toàn và thân thiện với môi trường. Dịch vụ bao gồm giặt, sấy khô, là ủi và giặt khô. Chúng tôi nhận và trả quần áo tại phòng của bạn, tiết kiệm thời gian cho bạn.',
    features: ['Giặt ủi nhanh chóng', 'Sấy khô, là ủi chuyên nghiệp', 'Nhận & trả tại phòng', 'Giá cả hợp lý', 'Chất tẩy rửa an toàn', 'Công nghệ hiện đại'],
    hours: '08:00 - 20:00 hàng ngày',
    price: 'Từ 20.000đ/chiếc'
  }
])

const openServiceDetail = (service) => {
  selectedService.value = service
}

const closeServiceDetail = () => {
  selectedService.value = null
}
</script>

<style scoped>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css');
</style>
