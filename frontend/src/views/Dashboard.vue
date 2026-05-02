<template>
  <div class="min-h-screen flex">
    <!-- Sidebar -->
    <aside class="w-64 bg-gray-800 text-white min-h-screen p-6 hidden md:block">
      <h1 class="text-2xl font-bold mb-10 text-center">Hotel Admin</h1>
      <nav>
        <router-link to="/dashboard" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/dashboard'}">
          {{ user?.role === 'ADMIN' ? 'Báo cáo doanh thu' : 'Tổng quan' }}
        </router-link>
        <router-link to="/rooms" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/rooms'}">
          {{ (user?.role === 'ADMIN' || user?.role === 'STAFF') ? 'Quản lý Phòng' : 'Xem phòng' }}
        </router-link>
        <router-link to="/bookings" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/bookings'}">
          {{ (user?.role === 'ADMIN' || user?.role === 'STAFF') ? 'Quản lý Đặt phòng' : 'Lịch sử đặt phòng' }}
        </router-link>
        <router-link v-if="user?.role === 'ADMIN' || user?.role === 'STAFF'" to="/manage-services" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/manage-services'}">Quản lý Dịch vụ</router-link>
        <router-link v-if="user?.role === 'ADMIN'" to="/staff" class="block py-2.5 px-4 rounded transition duration-200 hover:bg-gray-700" :class="{'bg-gray-700': $route.path === '/staff'}">Quản lý Nhân viên</router-link>
        <button @click="logout" class="w-full text-left py-2.5 px-4 rounded transition duration-200 hover:bg-red-700 mt-10 flex items-center text-red-400 hover:text-white">
          <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"></path>
          </svg>
          Đăng xuất
        </button>
      </nav>
    </aside>

    <!-- Main content -->
    <main class="flex-1 p-8 bg-gray-100">
      <header class="mb-10">
        <div class="flex justify-between items-center mb-6">
          <div>
            <h1 class="text-4xl font-bold text-gray-900 mb-2">
              {{ user?.role === 'ADMIN' ? 'Báo cáo Doanh thu' : 'Tổng quan Hệ thống' }}
            </h1>
            <p class="text-gray-600 text-lg">Chào mừng trở lại, <span class="font-semibold text-blue-600">{{ user?.username }}</span></p>
          </div>
        </div>
        
        <!-- Date and time display -->
        <div class="flex items-center justify-between bg-white rounded-xl shadow-sm p-4 border border-gray-100">
          <div class="flex items-center space-x-2 text-gray-600">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
            </svg>
            <span class="font-medium">{{ new Date().toLocaleDateString('vi-VN', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' }) }}</span>
          </div>
          <div class="flex items-center space-x-2 text-gray-600">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
            </svg>
            <span class="font-medium">{{ new Date().toLocaleTimeString('vi-VN') }}</span>
          </div>
        </div>
      </header>

      <!-- Stats Grid -->
      <div v-if="user?.role === 'ADMIN' || user?.role === 'STAFF'" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-10">
        <div class="bg-white rounded-xl shadow-lg hover:shadow-xl transition-all duration-300 transform hover:-translate-y-1 overflow-hidden">
          <div class="bg-gradient-to-r from-blue-500 to-blue-600 p-4 flex justify-between items-center">
            <svg class="w-8 h-8 text-white opacity-80" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
            </svg>
            <span class="text-white text-xs font-black uppercase tracking-widest opacity-80">Doanh thu</span>
          </div>
          <div class="p-6">
            <p class="text-3xl font-black text-gray-900 leading-none mb-2">{{ formatPrice(stats.totalRevenue || 0) }}</p>
            <p class="text-[10px] text-gray-400 font-bold uppercase tracking-widest">Tổng doanh thu thực tế</p>
          </div>
        </div>

        <div class="bg-white rounded-xl shadow-lg hover:shadow-xl transition-all duration-300 transform hover:-translate-y-1 overflow-hidden">
          <div class="bg-gradient-to-r from-green-500 to-green-600 p-4 flex justify-between items-center">
            <svg class="w-8 h-8 text-white opacity-80" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path>
            </svg>
            <span class="text-white text-xs font-black uppercase tracking-widest opacity-80">Lấp đầy</span>
          </div>
          <div class="p-6">
            <p class="text-3xl font-black text-gray-900 leading-none mb-2">{{ stats.occupancyRate?.toFixed(1) || 0 }}%</p>
            <p class="text-[10px] text-gray-400 font-bold uppercase tracking-widest">Tỷ lệ lấp đầy phòng</p>
          </div>
        </div>

        <div class="bg-white rounded-xl shadow-lg hover:shadow-xl transition-all duration-300 transform hover:-translate-y-1 overflow-hidden">
          <div class="bg-gradient-to-r from-amber-500 to-amber-600 p-4 flex justify-between items-center">
            <svg class="w-8 h-8 text-white opacity-80" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
            </svg>
            <span class="text-white text-xs font-black uppercase tracking-widest opacity-80">Booking</span>
          </div>
          <div class="p-6">
            <p class="text-3xl font-black text-gray-900 leading-none mb-2">{{ stats.newBookings || 0 }}</p>
            <p class="text-[10px] text-gray-400 font-bold uppercase tracking-widest">Đặt phòng mới trong ngày</p>
          </div>
        </div>

        <div class="bg-white rounded-xl shadow-lg hover:shadow-xl transition-all duration-300 transform hover:-translate-y-1 overflow-hidden">
          <div class="bg-gradient-to-r from-purple-500 to-purple-600 p-4 flex justify-between items-center">
            <svg class="w-8 h-8 text-white opacity-80" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"></path>
            </svg>
            <span class="text-white text-xs font-black uppercase tracking-widest opacity-80">Phòng</span>
          </div>
          <div class="p-6">
            <p class="text-3xl font-black text-gray-900 leading-none mb-2">{{ stats.occupiedRooms || 0 }} / {{ stats.totalRooms || 0 }}</p>
            <p class="text-[10px] text-gray-400 font-bold uppercase tracking-widest">Phòng đang được sử dụng</p>
          </div>
        </div>
      </div>

      <!-- Room Map (Staff & Admin) -->
      <div v-if="user?.role === 'ADMIN' || user?.role === 'STAFF'" class="mb-10">
        <RoomMap @refresh-stats="fetchStats" />
      </div>

      <!-- Revenue Report Section (Admin only) -->
    <div v-if="user?.role === 'ADMIN'" class="bg-white rounded-3xl shadow-xl overflow-hidden mb-10 border border-gray-100">
        <div class="bg-gradient-to-r from-blue-600 to-blue-700 text-white p-6">
          <h3 class="text-2xl font-bold flex items-center">
            <svg class="w-6 h-6 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path>
            </svg>
            Báo cáo Doanh thu
          </h3>
          <p class="text-blue-100 mt-1">Phân tích doanh thu theo khoảng thời gian</p>
        </div>
        
        <div class="p-6">
          <div class="bg-gray-50 rounded-xl p-6 mb-6">
            <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
              <div>
                <label class="block text-sm font-semibold text-gray-700 mb-2">Từ ngày</label>
                <input v-model="reportParams.fromDate" type="date" class="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
              </div>
              <div>
                <label class="block text-sm font-semibold text-gray-700 mb-2">Đến ngày</label>
                <input v-model="reportParams.toDate" type="date" class="w-full border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent" />
              </div>
              <div class="md:col-span-2 flex items-end">
                <button @click="fetchRevenueReport" class="bg-gradient-to-r from-blue-600 to-blue-700 hover:from-blue-700 hover:to-blue-800 text-white px-6 py-3 rounded-lg font-semibold transition-all duration-200 shadow-lg hover:shadow-xl flex items-center">
                  <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z"></path>
                  </svg>
                  Xem báo cáo
                </button>
              </div>
            </div>
          </div>

        <div v-if="revenueReport" class="mt-8">
          <div class="grid grid-cols-1 md:grid-cols-3 gap-6 bg-blue-50 p-6 rounded-xl border border-blue-100 mb-8">
            <div>
              <p class="text-sm text-blue-600 font-bold uppercase mb-1">Doanh thu trong kỳ</p>
              <p class="text-3xl font-black text-blue-900">{{ formatPrice(revenueReport.totalRevenue || 0) }}</p>
            </div>
            <div>
              <p class="text-sm text-blue-600 font-bold uppercase mb-1">Tổng số booking</p>
              <p class="text-3xl font-black text-blue-900">{{ revenueReport.totalBookings || 0 }}</p>
            </div>
            <div>
              <p class="text-sm text-blue-600 font-bold uppercase mb-1">Số hóa đơn đã xuất</p>
              <p class="text-3xl font-black text-blue-900">{{ revenueReport.invoiceCount || 0 }}</p>
            </div>
          </div>

          <div class="bg-white border border-gray-100 rounded-xl overflow-hidden">
            <h4 class="px-6 py-4 bg-gray-50 text-sm font-black text-gray-600 uppercase tracking-widest border-b border-gray-100">Chi tiết theo ngày</h4>
            <div class="overflow-x-auto">
              <table class="w-full text-left">
                <thead>
                  <tr class="border-b border-gray-100">
                    <th class="py-3 px-6 text-xs font-black text-gray-400 uppercase tracking-widest">Ngày</th>
                    <th class="py-3 px-6 text-xs font-black text-gray-400 uppercase tracking-widest">Số booking</th>
                    <th class="py-3 px-6 text-xs font-black text-gray-400 uppercase tracking-widest text-right">Doanh thu</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="day in revenueReport.details" :key="day.date" class="border-b border-gray-50 hover:bg-gray-50 transition-colors">
                    <td class="py-4 px-6 text-sm font-bold text-gray-700">{{ formatDate(day.date) }}</td>
                    <td class="py-4 px-6 text-sm text-gray-600">{{ day.bookings }}</td>
                    <td class="py-4 px-6 text-sm font-black text-emerald-700 text-right">{{ formatPrice(day.revenue) }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        </div>
      </div>

      <!-- Feedback Section (Staff & Admin) -->
      <div v-if="user?.role === 'ADMIN' || user?.role === 'STAFF'" class="bg-white rounded-3xl shadow-xl overflow-hidden border border-gray-100">
        <div class="bg-gradient-to-r from-emerald-800 to-emerald-900 text-white p-6">
          <h3 class="text-2xl font-black uppercase tracking-tight flex items-center">
            <svg class="w-7 h-7 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 10h.01M12 10h.01M16 10h.01M9 16H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-5l-5 5v-5z"></path>
            </svg>
            Phản hồi khách hàng
          </h3>
        </div>
        <div class="p-8">
          <div class="overflow-x-auto">
            <table class="w-full">
              <thead>
                <tr class="text-left border-b-2 border-gray-100">
                  <th class="pb-4 text-xs font-black uppercase tracking-widest text-gray-400">Khách hàng</th>
                  <th class="pb-4 text-xs font-black uppercase tracking-widest text-gray-400">Email</th>
                  <th class="pb-4 text-xs font-black uppercase tracking-widest text-gray-400">Nội dung</th>
                  <th class="pb-4 text-xs font-black uppercase tracking-widest text-gray-400 text-center">Trạng thái</th>
                  <th class="pb-4 text-xs font-black uppercase tracking-widest text-gray-400 text-right">Hành động</th>
                </tr>
              </thead>
              <tbody class="divide-y divide-gray-50">
                <tr v-for="msg in messages" :key="msg.id" class="hover:bg-gray-50/50 transition-colors">
                  <td class="py-5">
                    <p class="font-black text-gray-900">{{ msg.fullName }}</p>
                    <p class="text-[10px] text-gray-400 font-bold uppercase tracking-widest">{{ formatDate(msg.createdAt) }}</p>
                  </td>
                  <td class="py-5 text-sm text-gray-600">{{ msg.email }}</td>
                  <td class="py-5">
                    <p class="text-sm text-gray-800 line-clamp-1 max-w-xs">{{ msg.note }}</p>
                  </td>
                  <td class="py-5 text-center">
                    <span 
                      :class="msg.status === 'REPLIED' ? 'bg-green-100 text-green-700' : 'bg-amber-100 text-amber-700'"
                      class="px-3 py-1 rounded-full text-[10px] font-black uppercase tracking-widest"
                    >
                      {{ msg.status === 'REPLIED' ? 'Đã phản hồi' : 'Chưa trả lời' }}
                    </span>
                  </td>
                  <td class="py-5 text-right">
                    <button 
                      @click="openReplyModal(msg)"
                      class="p-2 bg-emerald-50 text-emerald-600 rounded-xl hover:bg-emerald-100 transition-all transform hover:scale-110"
                      title="Phản hồi"
                    >
                      <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 10h10a8 8 0 018 8v2M3 10l6 6m-6-6l6-6" />
                      </svg>
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>

      <!-- Reply Modal -->
      <div v-if="showReplyModal && selectedMessage" class="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/60 backdrop-blur-sm">
        <div class="bg-white rounded-[2.5rem] max-w-lg w-full p-8 shadow-2xl transform transition-all animate-in fade-in zoom-in duration-300">
          <div class="flex justify-between items-start mb-6">
            <div>
              <h3 class="text-3xl font-black text-emerald-950 uppercase tracking-tighter">Phản hồi khách hàng</h3>
              <p class="text-sm text-gray-500 font-bold mt-1">{{ selectedMessage.fullName }} - {{ selectedMessage.email }}</p>
            </div>
            <button @click="closeReplyModal" class="text-gray-400 hover:text-gray-600 transition-colors">
              <i class="fas fa-times text-xl"></i>
            </button>
          </div>

          <div class="mb-6 bg-gray-50 rounded-2xl p-6">
            <p class="text-xs font-black uppercase tracking-widest text-gray-400 mb-2">Nội dung tin nhắn:</p>
            <p class="text-gray-800 leading-relaxed">{{ selectedMessage.note }}</p>
          </div>

          <div class="mb-8">
            <label class="block text-xs font-black uppercase tracking-widest text-emerald-900 mb-3">Nội dung phản hồi</label>
            <textarea 
              v-model="replyContent" 
              rows="4" 
              class="w-full bg-gray-50 border-0 rounded-2xl px-5 py-4 outline-none focus:ring-2 focus:ring-emerald-800 transition-all"
              placeholder="Nhập nội dung phản hồi cho khách hàng..."
            ></textarea>
          </div>

          <button 
            @click="submitReply"
            :disabled="!replyContent"
            class="w-full bg-emerald-800 hover:bg-emerald-900 disabled:bg-gray-300 text-white py-4 rounded-2xl font-black uppercase tracking-widest text-xs transition-all shadow-lg shadow-emerald-200"
          >
            Gửi phản hồi
          </button>
        </div>
      </div>

      <div v-if="user?.role !== 'ADMIN' && user?.role !== 'STAFF'" class="bg-white rounded-xl shadow-lg overflow-hidden">
        <div class="bg-gradient-to-r from-indigo-600 to-indigo-700 text-white p-8 text-center">
          <svg class="w-16 h-16 mx-auto mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"></path>
          </svg>
          <h3 class="text-3xl font-bold mb-2">Chào mừng đến với Khách sạn!</h3>
          <p class="text-indigo-100 text-lg">Hệ thống Quản lý Khách sạn hiện đại</p>
        </div>
        <div class="p-8">
          <p class="text-gray-600 text-center mb-8 text-lg">Khám phá các tiện ích và đặt phòng dễ dàng</p>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <router-link to="/rooms" class="group bg-gradient-to-r from-blue-50 to-blue-100 p-6 rounded-xl hover:shadow-lg transition-all duration-300 border border-blue-200">
              <div class="flex items-center mb-4">
                <svg class="w-8 h-8 text-blue-600 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4"></path>
                </svg>
                <h4 class="text-lg font-semibold text-gray-800">Xem phòng</h4>
              </div>
              <p class="text-gray-600">Khám phá danh sách phòng và tiện ích</p>
            </router-link>
            <router-link to="/bookings" class="group bg-gradient-to-r from-purple-50 to-purple-100 p-6 rounded-xl hover:shadow-lg transition-all duration-300 border border-purple-200">
              <div class="flex items-center mb-4">
                <svg class="w-8 h-8 text-purple-600 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"></path>
                </svg>
                <h4 class="text-lg font-semibold text-gray-800">Lịch sử đặt phòng</h4>
              </div>
              <p class="text-gray-600">Xem các đặt phòng của bạn</p>
            </router-link>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../api/axios'
import RoomMap from '../components/RoomMap.vue'

const messages = ref([])
const router = useRouter()

const user = ref(JSON.parse(localStorage.getItem('user') || 'null'))
const stats = ref({})

const showReplyModal = ref(false)
const selectedMessage = ref(null)
const replyContent = ref('')

const openReplyModal = (msg) => {
  selectedMessage.value = msg
  replyContent.value = msg.replyContent || ''
  showReplyModal.value = true
}

const closeReplyModal = () => {
  showReplyModal.value = false
  selectedMessage.value = null
  replyContent.value = ''
}

const submitReply = async () => {
  try {
    await axios.post(`/contact/messages/${selectedMessage.value.id}/reply`, {
      replyContent: replyContent.value
    })
    closeReplyModal()
    await fetchMessages()
    alert('Đã gửi phản hồi thành công!')
  } catch (error) {
    console.error('Lỗi khi gửi phản hồi:', error)
    alert('Không thể gửi phản hồi.')
  }
}

const reportParams = ref({
  fromDate: new Date(new Date().setDate(new Date().getDate() - 30)).toISOString().split('T')[0],
  toDate: new Date().toISOString().split('T')[0]
})
const revenueReport = ref(null)

const fetchRevenueReport = async () => {
  if (user.value?.role === 'ADMIN') {
    try {
      const response = await axios.get(`/dashboard/reports/revenue?fromDate=${reportParams.value.fromDate}&toDate=${reportParams.value.toDate}`)
      revenueReport.value = response.data
    } catch (error) {
      console.error('Lỗi khi lấy báo cáo doanh thu:', error)
      alert('Không thể lấy báo cáo doanh thu.')
    }
  }
}

const formatPrice = (price) => {
  if (!price) return '0 đ'
  return `${new Intl.NumberFormat('vi-VN').format(price * 25000)} đ`
}

const formatNumber = (num) => {
  if (!num) return '0'
  return new Intl.NumberFormat('vi-VN').format(num)
}

const formatDate = (date) => {
  if (!date) return '--'
  return new Date(date).toLocaleDateString('vi-VN')
}

const logout = () => {
  localStorage.removeItem('user')
  router.push('/login')
}

const fetchMessages = async () => {
  try {
    const response = await axios.get('/contact/messages')
    messages.value = response.data
  } catch (error) {
    console.error('Lỗi khi lấy tin nhắn liên hệ:', error)
  }
}

const updateMessageStatus = async (id, status) => {
  try {
    await axios.put(`/contact/messages/${id}/status?status=${status}`)
    // Cập nhật lại danh sách sau khi đổi trạng thái
    await fetchMessages()
  } catch (error) {
    console.error('Lỗi khi cập nhật trạng thái:', error)
    alert('Không thể cập nhật trạng thái tin nhắn.')
  }
}

const fetchStats = async () => {
  if (user.value?.role === 'ADMIN') {
    try {
      const response = await axios.get('/dashboard/stats')
      stats.value = response.data
    } catch (error) {
      console.error('Lỗi khi lấy thông tin thống kê:', error)
    }
  }
}

onMounted(() => {
  fetchStats()
  fetchMessages()
  if (user.value?.role === 'ADMIN') {
    fetchRevenueReport()
  }
})
</script>
