<template>
  <div class="bg-white">
    <!-- Hero Section -->
    <section class="relative h-[50vh] md:h-[70vh] min-h-[400px] overflow-hidden">
      <div class="absolute inset-0">
        <img src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80" 
             class="w-full h-full object-cover scale-105 animate-slow-zoom" alt="Luxury Hotel">
        <div class="absolute inset-0 bg-gradient-to-b from-emerald-950/70 via-emerald-950/50 to-white/10"></div>
      </div>
      <div class="absolute inset-0 flex items-center justify-center text-center px-4">
        <div class="max-w-5xl">
          <span class="inline-block text-emerald-400 font-bold tracking-[0.5em] uppercase mb-6 animate-fade-in-down">Chào mừng đến với Grand Hotel</span>
          <h1 class="text-5xl md:text-8xl font-black text-white uppercase tracking-tighter mb-8 leading-[0.9] animate-fade-in-up">
            Nơi Khởi Đầu <br/>
            <span class="text-transparent bg-clip-text bg-gradient-to-r from-emerald-400 to-emerald-200">Những Tuyệt Tác</span>
          </h1>
          <p class="text-emerald-50 text-lg md:text-xl font-light tracking-[0.2em] uppercase max-w-2xl mx-auto animate-fade-in">
            Câu chuyện về sự đẳng cấp, tinh tế và lòng hiếu khách tận tâm từ năm 2000
          </p>
        </div>
      </div>
      <div class="absolute bottom-10 left-1/2 -translate-x-1/2 animate-bounce">
        <div class="w-1 h-12 rounded-full bg-gradient-to-b from-white to-transparent"></div>
      </div>
    </section>

    <!-- Our Story Section -->
    <section class="max-w-7xl mx-auto px-4 py-16 overflow-hidden">
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-24 items-start">
        <div class="relative group" data-aos="fade-right">
          <div class="relative z-10 rounded-[2rem] overflow-hidden shadow-2xl transform group-hover:-translate-y-2 transition-transform duration-700">
            <img src="https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80" 
                 class="w-full h-[600px] object-cover" alt="Our Story">
          </div>
          <div class="absolute -top-10 -left-10 w-64 h-64 bg-emerald-50 rounded-full -z-0 opacity-50"></div>
          <div class="absolute -bottom-10 -right-10 w-80 h-80 bg-red-50 rounded-full -z-0 opacity-50"></div>
          <div class="absolute bottom-12 -left-12 bg-white p-8 rounded-2xl shadow-xl z-20 max-w-[240px] hidden md:block">
            <div class="text-4xl font-black text-emerald-800 mb-2">24/7</div>
            <p class="text-gray-500 text-sm font-medium uppercase tracking-wider">Phục vụ tận tâm trong suốt 25 năm qua</p>
          </div>
        </div>

        <div data-aos="fade-left">
          <span class="text-red-600 font-bold uppercase tracking-[0.4em] mb-6 block text-sm">Hành trình 25 năm</span>
          <h2 class="text-4xl md:text-6xl font-black text-emerald-950 uppercase tracking-tighter mb-10 leading-[1.1]">
            Khởi nguồn từ niềm <br/>
            <span class="text-emerald-700">đam mê phục vụ</span>
          </h2>
          <div class="space-y-8 text-gray-600 font-light leading-relaxed text-lg">
            <p class="first-letter:text-5xl first-letter:font-black first-letter:text-emerald-800 first-letter:mr-3 first-letter:float-left">
              Được thành lập vào năm 2000, Grand Hotel bắt đầu từ một ý tưởng đơn giản: tạo ra một không gian nơi mỗi vị khách đều cảm thấy như đang ở trong chính ngôi nhà sang trọng của mình.
            </p>
            <p>
              Qua hơn hai thập kỷ, chúng tôi đã không ngừng phát triển và nâng cấp cơ sở hạ tầng cũng như chất lượng dịch vụ để đáp ứng những tiêu chuẩn khắt khe nhất của ngành khách sạn nghỉ dưỡng quốc tế.
            </p>
            <div class="pt-8 grid grid-cols-2 gap-8">
              <div>
                <h4 class="font-black text-emerald-900 uppercase tracking-wider mb-2">Chất lượng</h4>
                <p class="text-sm">Tiêu chuẩn 5 sao quốc tế trong mọi dịch vụ.</p>
              </div>
              <div>
                <h4 class="font-black text-emerald-900 uppercase tracking-wider mb-2">Con người</h4>
                <p class="text-sm">Đội ngũ chuyên nghiệp và tận tâm nhất.</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Luxury Facilities Gallery -->
    <section class="bg-emerald-950 py-20 text-white overflow-hidden">
      <div class="max-w-7xl mx-auto px-4">
        <div class="text-center mb-16">
          <span class="text-emerald-400 font-bold uppercase tracking-[0.4em] mb-6 block text-sm">Không gian của chúng tôi</span>
          <h2 class="text-4xl md:text-6xl font-black uppercase tracking-tighter">Đẳng cấp trong từng chi tiết</h2>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-12 gap-6 h-[800px]">
          <div class="md:col-span-8 group relative overflow-hidden rounded-3xl">
            <img src="https://images.unsplash.com/photo-1582719478250-c89cae4df85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=1200&q=80" 
                 class="w-full h-full object-cover transition-transform duration-1000 group-hover:scale-110" alt="Lobby">
            <div class="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent flex items-end p-12">
              <div>
                <h3 class="text-2xl font-bold uppercase tracking-widest mb-2">Sảnh chờ sang trọng</h3>
                <p class="text-emerald-200 uppercase tracking-widest text-xs">Sự chào đón nồng hậu ngay từ bước chân đầu tiên</p>
              </div>
            </div>
          </div>
          <div class="md:col-span-4 grid grid-rows-2 gap-6">
            <div class="group relative overflow-hidden rounded-3xl">
              <img src="https://images.unsplash.com/photo-1571896349842-33c89424de2d?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80" 
                   class="w-full h-full object-cover transition-transform duration-1000 group-hover:scale-110" alt="Pool">
              <div class="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent flex items-end p-8">
                <h3 class="text-xl font-bold uppercase tracking-widest">Hồ bơi vô cực</h3>
              </div>
            </div>
            <div class="group relative overflow-hidden rounded-3xl">
              <img src="https://images.unsplash.com/photo-1544161515-4ab6ce6db874?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80" 
                   class="w-full h-full object-cover transition-transform duration-1000 group-hover:scale-110" alt="Spa">
              <div class="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent flex items-end p-8">
                <h3 class="text-xl font-bold uppercase tracking-widest">Dịch vụ Spa & Wellness</h3>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Stats Grid -->
    <section class="max-w-7xl mx-auto px-4 -mt-16 relative z-20">
      <div class="bg-white rounded-[3rem] shadow-2xl p-12 md:p-20 grid grid-cols-2 md:grid-cols-4 gap-12 border border-gray-100">
        <div class="text-center group">
          <div class="text-5xl md:text-6xl font-black text-emerald-800 mb-3 group-hover:scale-110 transition-transform duration-300">25+</div>
          <div class="text-gray-400 uppercase tracking-[0.2em] text-[10px] font-bold">Năm kinh nghiệm</div>
        </div>
        <div class="text-center group">
          <div class="text-5xl md:text-6xl font-black text-emerald-800 mb-3 group-hover:scale-110 transition-transform duration-300">500+</div>
          <div class="text-gray-400 uppercase tracking-[0.2em] text-[10px] font-bold">Phòng cao cấp</div>
        </div>
        <div class="text-center group">
          <div class="text-5xl md:text-6xl font-black text-emerald-800 mb-3 group-hover:scale-110 transition-transform duration-300">120k</div>
          <div class="text-gray-400 uppercase tracking-[0.2em] text-[10px] font-bold">Khách hàng tin tưởng</div>
        </div>
        <div class="text-center group">
          <div class="text-5xl md:text-6xl font-black text-emerald-800 mb-3 group-hover:scale-110 transition-transform duration-300">15</div>
          <div class="text-gray-400 uppercase tracking-[0.2em] text-[10px] font-bold">Giải thưởng lớn</div>
        </div>
      </div>
    </section>

    <!-- Vision & Mission with Image -->
    <section class="max-w-7xl mx-auto px-4 py-20">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
        <div class="relative group h-[500px] overflow-hidden rounded-[40px]">
          <img src="https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?ixlib=rb-4.0.3&auto=format&fit=crop&w=1000&q=80" 
               class="w-full h-full object-cover transition-transform duration-[2s] group-hover:scale-110" alt="Vision">
          <div class="absolute inset-0 bg-emerald-950/60 p-16 flex flex-col justify-end">
            <div class="absolute top-10 right-10 text-white/10 text-[12rem] font-black leading-none select-none">V</div>
            <h4 class="text-3xl font-black text-white uppercase tracking-tight mb-6">Tầm nhìn</h4>
            <p class="text-emerald-50/80 font-light leading-relaxed text-lg">
              Trở thành biểu tượng hàng đầu của sự sang trọng và lòng hiếu khách tại Việt Nam, mang đến những trải nghiệm nghỉ dưỡng đẳng cấp thế giới kết hợp tinh hoa văn hóa địa phương.
            </p>
          </div>
        </div>
        <div class="relative group h-[500px] overflow-hidden rounded-[40px]">
          <img src="https://images.unsplash.com/photo-1551882547-ff43c63efe81?ixlib=rb-4.0.3&auto=format&fit=crop&w=1000&q=80" 
               class="w-full h-full object-cover transition-transform duration-[2s] group-hover:scale-110" alt="Mission">
          <div class="absolute inset-0 bg-gray-900/60 p-16 flex flex-col justify-end">
            <div class="absolute top-10 right-10 text-white/10 text-[12rem] font-black leading-none select-none">M</div>
            <h4 class="text-3xl font-black text-white uppercase tracking-tight mb-6">Sứ mệnh</h4>
            <p class="text-gray-300 font-light leading-relaxed text-lg">
              Chúng tôi cam kết kiến tạo những kỷ niệm vô giá cho khách hàng thông qua sự tận tâm, tinh tế trong từng chi tiết và không ngừng đổi mới để vượt xa mọi mong đợi.
            </p>
          </div>
        </div>
      </div>
    </section>

    <!-- Team Section -->
    <section class="bg-gray-50 py-20 overflow-hidden">
      <div class="max-w-7xl mx-auto px-4">
        <div class="flex flex-col md:flex-row justify-between items-end mb-20 gap-8">
          <div class="max-w-2xl">
            <span class="text-red-600 font-bold uppercase tracking-[0.4em] mb-6 block text-sm">Đội ngũ của chúng tôi</span>
            <h2 class="text-4xl md:text-6xl font-black text-emerald-950 uppercase tracking-tighter">Những người kiến tạo trải nghiệm</h2>
          </div>
          <button class="bg-emerald-800 text-white px-10 py-4 rounded-full font-bold uppercase tracking-widest hover:bg-emerald-900 transition-all shadow-lg">Gia nhập đội ngũ</button>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-12">
          <div class="group">
            <div class="relative overflow-hidden rounded-[2.5rem] mb-8 shadow-xl">
              <img src="https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?ixlib=rb-4.0.3&auto=format&fit=crop&w=600&q=80" 
                   class="w-full h-[450px] object-cover transition-transform duration-700 group-hover:scale-105" alt="CEO">
              <div class="absolute inset-0 bg-emerald-950/20 group-hover:bg-transparent transition-colors duration-500"></div>
            </div>
            <h3 class="text-2xl font-black text-emerald-950 uppercase tracking-tight mb-1">Trần Anh Tuấn</h3>
            <p class="text-emerald-700 font-bold uppercase tracking-widest text-xs">Tổng Giám Đốc Điều Hành</p>
          </div>
          <div class="group mt-12 md:mt-24">
            <div class="relative overflow-hidden rounded-[2.5rem] mb-8 shadow-xl">
              <img src="https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-4.0.3&auto=format&fit=crop&w=600&q=80" 
                   class="w-full h-[450px] object-cover transition-transform duration-700 group-hover:scale-105" alt="Manager">
              <div class="absolute inset-0 bg-emerald-950/20 group-hover:bg-transparent transition-colors duration-500"></div>
            </div>
            <h3 class="text-2xl font-black text-emerald-950 uppercase tracking-tight mb-1">Lê Thu Thủy</h3>
            <p class="text-emerald-700 font-bold uppercase tracking-widest text-xs">Giám Đốc Dịch Vụ Khách Hàng</p>
          </div>
          <div class="group">
            <div class="relative overflow-hidden rounded-[2.5rem] mb-8 shadow-xl">
              <img src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-4.0.3&auto=format&fit=crop&w=600&q=80" 
                   class="w-full h-[450px] object-cover transition-transform duration-700 group-hover:scale-105" alt="Chef">
              <div class="absolute inset-0 bg-emerald-950/20 group-hover:bg-transparent transition-colors duration-500"></div>
            </div>
            <h3 class="text-2xl font-black text-emerald-950 uppercase tracking-tight mb-1">Nguyễn Minh Đức</h3>
            <p class="text-emerald-700 font-bold uppercase tracking-widest text-xs">Bếp Trưởng Điều Hành</p>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'

onMounted(() => {
  window.scrollTo(0, 0)
})
</script>

<style scoped>
@keyframes slow-zoom {
  0% { transform: scale(1); }
  100% { transform: scale(1.1); }
}

@keyframes fade-in-up {
  from { opacity: 0; transform: translateY(40px); }
  to { opacity: 1; transform: translateY(0); }
}

@keyframes fade-in-down {
  from { opacity: 0; transform: translateY(-20px); }
  to { opacity: 1; transform: translateY(0); }
}

@keyframes fade-in {
  from { opacity: 0; }
  to { opacity: 1; }
}

.animate-slow-zoom {
  animation: slow-zoom 20s ease-in-out infinite alternate;
}

.animate-fade-in-up {
  animation: fade-in-up 1.2s cubic-bezier(0.16, 1, 0.3, 1) forwards;
}

.animate-fade-in-down {
  animation: fade-in-down 1s cubic-bezier(0.16, 1, 0.3, 1) forwards;
}

.animate-fade-in {
  animation: fade-in 1.5s ease-out forwards;
}
</style>
