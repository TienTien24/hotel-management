<template>
  <div class="min-h-screen flex bg-[#f8faff] font-sans text-[#2d3748]">
    <StaffSidebar />

    <main class="flex-1 p-8 overflow-y-auto">
      <!-- Header -->
      <header class="flex items-center justify-between mb-8">
        <div>
          <h2 class="text-3xl font-bold text-[#1a202c]">Check-in / Check-out</h2>
        </div>
        
        <div class="flex items-center gap-4">
          <!-- Notification Bell -->
          <div class="relative group">
            <button @click="toggleNotifications" class="w-10 h-10 flex items-center justify-center rounded-full bg-white shadow-sm border border-gray-100 hover:bg-gray-50 transition-all relative">
              <i class="far fa-bell text-gray-500 text-lg"></i>
              <span v-if="unreadNotificationsCount > 0" class="absolute -top-1 -right-1 min-w-[1.25rem] h-5 bg-rose-500 text-white text-[10px] font-bold rounded-full flex items-center justify-center border-2 border-white px-1 animate-pulse">
                {{ unreadNotificationsCount }}
              </span>
            </button>
            
            <!-- Notification Dropdown -->
            <div v-if="showNotifications" class="absolute right-0 mt-3 w-80 bg-white rounded-2xl shadow-xl border border-gray-100 z-[60] overflow-hidden">
              <div class="p-4 border-b border-gray-50 flex items-center justify-between bg-gray-50 bg-opacity-50">
                <span class="font-bold text-sm text-gray-800">Thông báo</span>
                <button @click="markAllAsRead" class="text-[10px] font-bold text-blue-600 hover:underline">Đánh dấu đã đọc</button>
              </div>
              <div class="max-h-96 overflow-y-auto">
                <div v-for="(noti, index) in notifications" :key="index" 
                  class="p-4 border-b border-gray-50 hover:bg-gray-50 transition-all cursor-pointer flex gap-3"
                  :class="{'bg-blue-50': !noti.read}"
                >
                  <div class="w-8 h-8 rounded-full bg-blue-100 flex items-center justify-center shrink-0">
                    <span class="text-sm">🔔</span>
                  </div>
                  <div>
                    <p class="text-xs font-medium text-gray-800 leading-relaxed">{{ noti.message }}</p>
                    <span class="text-[10px] text-gray-400 mt-1 block">{{ noti.time }}</span>
                  </div>
                </div>
              </div>
              <div class="p-3 text-center border-t border-gray-50">
                <button class="text-[11px] font-bold text-gray-500 hover:text-gray-700">Xem tất cả thông báo</button>
              </div>
            </div>
          </div>
        </div>
      </header>

      <!-- Tabs -->
      <div class="flex items-center gap-12 mb-8 border-b border-gray-200 px-2">
        <button @click="activeTab = 'checkin'" 
          class="pb-4 text-base font-bold transition-all relative flex items-center gap-2"
          :class="activeTab === 'checkin' ? 'text-[#3182ce]' : 'text-gray-400 hover:text-gray-600'">
          <i class="far fa-calendar-check"></i>
          Check-in
          <div v-if="activeTab === 'checkin'" class="absolute bottom-0 left-0 w-full h-1 bg-[#3182ce] rounded-full"></div>
        </button>
        <button @click="activeTab = 'checkout'" 
          class="pb-4 text-base font-bold transition-all relative flex items-center gap-2"
          :class="activeTab === 'checkout' ? 'text-[#3182ce]' : 'text-gray-400 hover:text-gray-600'">
          <i class="fas fa-briefcase"></i>
          Check-out
          <div v-if="activeTab === 'checkout'" class="absolute bottom-0 left-0 w-full h-1 bg-[#3182ce] rounded-full"></div>
        </button>
      </div>

      <!-- Search and Filters -->
      <div class="flex flex-wrap gap-4 mb-8 items-center">
        <div class="relative flex-1 min-w-[300px]">
          <i class="fas fa-search absolute left-4 top-1/2 -translate-y-1/2 text-gray-400 text-sm"></i>
          <input type="text" v-model="filters.search" placeholder="Tìm mã đặt phòng, tên khách, SĐT..." 
            class="w-full bg-white border border-gray-200 rounded-xl pl-11 pr-4 py-3 outline-none focus:border-blue-400 transition-all text-sm font-medium shadow-sm">
        </div>
        
        <select v-model="filters.status" class="bg-white border border-gray-200 rounded-xl px-4 py-3 outline-none focus:border-blue-400 transition-all text-sm font-bold shadow-sm min-w-[150px]">
          <option value="">Tất cả trạng thái</option>
          <template v-if="activeTab === 'checkin'">
            <option value="CONFIRMED">Đã đặt (Chờ đến)</option>
            <option value="CHECKED_IN">Đã nhận phòng</option>
          </template>
          <template v-else>
            <option value="CHECKED_IN">Đang lưu trú</option>
            <option value="COMPLETED">Đã check-out</option>
          </template>
        </select>

        <select v-model="filters.roomType" class="bg-white border border-gray-200 rounded-xl px-4 py-3 outline-none focus:border-blue-400 transition-all text-sm font-bold shadow-sm min-w-[150px]">
          <option value="">Tất cả phòng</option>
          <option v-for="type in roomTypes" :key="type" :value="type">{{ type }}</option>
        </select>

        <div class="relative">
          <input type="date" v-model="filters.date" class="bg-white border border-gray-200 rounded-xl px-4 py-3 outline-none focus:border-blue-400 transition-all text-sm font-bold shadow-sm">
        </div>

        <button @click="fetchBookings" class="bg-[#3182ce] hover:bg-[#2b6cb0] text-white px-8 py-3 rounded-xl font-bold text-sm transition-all shadow-lg shadow-blue-100">
          Tìm kiếm
        </button>
      </div>

      <!-- Stats Cards -->
      <div v-if="activeTab === 'checkin'" class="grid grid-cols-1 md:grid-cols-5 gap-6 mb-8">
        <div class="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm flex items-center gap-4">
          <div class="w-12 h-12 rounded-xl bg-blue-50 flex items-center justify-center text-blue-500">
            <i class="fas fa-user-clock text-xl"></i>
          </div>
          <div>
            <p class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Đã đặt</p>
            <p class="text-xs font-bold text-blue-600 mt-0.5">Chờ đến</p>
            <p class="text-2xl font-bold text-gray-800 mt-1">{{ stats.pendingCheckin }} <span class="text-xs font-medium text-gray-400">khách</span></p>
          </div>
        </div>
        <div class="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm flex items-center gap-4">
          <div class="w-12 h-12 rounded-xl bg-emerald-50 flex items-center justify-center text-emerald-500">
            <i class="fas fa-user-check text-xl"></i>
          </div>
          <div>
            <p class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Đã nhận phòng</p>
            <p class="text-xs font-bold text-emerald-600 mt-0.5">(Trong ngày)</p>
            <p class="text-2xl font-bold text-gray-800 mt-1">{{ stats.checkedInToday }} <span class="text-xs font-medium text-gray-400">khách</span></p>
          </div>
        </div>
        <div class="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm flex items-center gap-4">
          <div class="w-12 h-12 rounded-xl bg-amber-50 flex items-center justify-center text-amber-500">
            <i class="fas fa-sign-out-alt text-xl"></i>
          </div>
          <div>
            <p class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Sắp check-out</p>
            <p class="text-xs font-bold text-amber-600 mt-0.5">(Hôm nay)</p>
            <p class="text-2xl font-bold text-gray-800 mt-1">{{ stats.dueCheckout }} <span class="text-xs font-medium text-gray-400">khách</span></p>
          </div>
        </div>
        <div class="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm flex items-center gap-4">
          <div class="w-12 h-12 rounded-xl bg-rose-50 flex items-center justify-center text-rose-500">
            <i class="fas fa-clock text-xl"></i>
          </div>
          <div>
            <p class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Quá hạn check-out</p>
            <p class="text-xs font-bold text-rose-600 mt-0.5">out</p>
            <p class="text-2xl font-bold text-gray-800 mt-1">{{ stats.overdueCheckout }} <span class="text-xs font-medium text-gray-400">khách</span></p>
          </div>
        </div>
        <div class="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm flex items-center gap-4">
          <div class="w-12 h-12 rounded-xl bg-purple-50 flex items-center justify-center text-purple-500">
            <i class="fas fa-exclamation-triangle text-xl"></i>
          </div>
          <div>
            <p class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Đã check-out</p>
            <p class="text-xs font-bold text-purple-600 mt-0.5">(Hôm nay)</p>
            <p class="text-2xl font-bold text-gray-800 mt-1">{{ stats.checkedOutToday }} <span class="text-xs font-medium text-gray-400">khách</span></p>
          </div>
        </div>
      </div>

      <!-- Stats Cards Checkout Tab -->
      <div v-else class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
        <div class="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm flex items-center gap-4">
          <div class="w-12 h-12 rounded-xl bg-blue-50 flex items-center justify-center text-blue-500">
            <i class="fas fa-user-clock text-xl"></i>
          </div>
          <div>
            <p class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Sắp check-out</p>
            <p class="text-xs font-bold text-blue-600 mt-0.5">(Hôm nay)</p>
            <p class="text-2xl font-bold text-gray-800 mt-1">{{ stats.dueCheckout }} <span class="text-xs font-medium text-gray-400">khách</span></p>
          </div>
        </div>
        <div class="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm flex items-center gap-4">
          <div class="w-12 h-12 rounded-xl bg-emerald-50 flex items-center justify-center text-emerald-500">
            <i class="fas fa-user-check text-xl"></i>
          </div>
          <div>
            <p class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Đã check-out</p>
            <p class="text-xs font-bold text-emerald-600 mt-0.5">(Hôm nay)</p>
            <p class="text-2xl font-bold text-gray-800 mt-1">{{ stats.checkedOutToday }} <span class="text-xs font-medium text-gray-400">khách</span></p>
          </div>
        </div>
        <div class="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm flex items-center gap-4">
          <div class="w-12 h-12 rounded-xl bg-amber-50 flex items-center justify-center text-amber-500">
            <i class="fas fa-clock text-xl"></i>
          </div>
          <div>
            <p class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Quá hạn check-out</p>
            <p class="text-xs font-bold text-amber-600 mt-0.5">(Quá giờ)</p>
            <p class="text-2xl font-bold text-gray-800 mt-1">{{ stats.overdueCheckout }} <span class="text-xs font-medium text-gray-400">khách</span></p>
          </div>
        </div>
        <div class="bg-white p-6 rounded-2xl border border-gray-100 shadow-sm flex items-center gap-4">
          <div class="w-12 h-12 rounded-xl bg-purple-50 flex items-center justify-center text-purple-500">
            <i class="fas fa-calendar-alt text-xl"></i>
          </div>
          <div>
            <p class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Đặt check-out</p>
            <p class="text-xs font-bold text-purple-600 mt-0.5">(Hôm nay)</p>
            <p class="text-2xl font-bold text-gray-800 mt-1">1 <span class="text-xs font-medium text-gray-400">khách</span></p>
          </div>
        </div>
      </div>

      <!-- Bookings Table -->
      <div class="bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden">
        <div class="overflow-x-auto">
          <table class="w-full text-left">
            <thead>
              <tr class="bg-gray-50 bg-opacity-50 border-b border-gray-100">
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider">Mã đặt phòng</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider">Khách hàng</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider">Phòng</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider">Check-in</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider">Check-out</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider text-center">Trạng thái</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider text-center">Thao tác</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-gray-50">
              <tr v-for="booking in filteredBookings" :key="booking.id" class="hover:bg-gray-50 hover:bg-opacity-30 transition-colors">
                <td class="px-6 py-5">
                  <span @click="openDetail(booking)" class="text-blue-600 font-bold cursor-pointer hover:underline">BK{{ String(booking.id).padStart(8, '0') }}</span>
                </td>
                <td class="px-6 py-5">
                  <div class="flex flex-col">
                    <span class="text-sm font-bold text-gray-800">{{ booking.guestFullName || booking.customer?.fullName }}</span>
                    <span class="text-[11px] text-gray-400 font-medium mt-0.5">{{ booking.guestPhone || booking.customer?.phone }}</span>
                  </div>
                </td>
                <td class="px-6 py-5">
                  <div class="flex flex-col">
                    <span class="text-sm font-bold text-gray-800">{{ booking.room?.roomNumber }}</span>
                    <span class="text-[10px] text-gray-400 font-medium">{{ booking.room?.category }}</span>
                  </div>
                </td>
                <td class="px-6 py-5">
                  <div class="flex flex-col">
                    <span class="text-sm font-medium text-gray-800">{{ formatDate(booking.checkInDate) }}</span>
                    <span class="text-[10px] text-gray-400 font-medium">14:00</span>
                  </div>
                </td>
                <td class="px-6 py-5">
                  <div class="flex flex-col">
                    <span class="text-sm font-medium text-gray-800">{{ formatDate(booking.checkOutDate) }}</span>
                    <span class="text-[10px] text-gray-400 font-medium">12:00</span>
                  </div>
                </td>
                <td class="px-6 py-5 text-center">
                  <span :class="getStatusStyles(booking.status)" class="px-3 py-1.5 rounded-lg text-[10px] font-bold inline-block min-w-[100px]">
                    {{ formatStatus(booking.status) }}
                  </span>
                </td>
                <td class="px-6 py-5">
                  <div class="flex items-center justify-center gap-2">
                    <button @click="openDetail(booking)" 
                      class="w-8 h-8 rounded-lg bg-slate-50 text-slate-400 hover:bg-indigo-50 hover:text-indigo-600 transition-all flex items-center justify-center border border-slate-100"
                      title="Xem chi tiết">
                      <i class="fas fa-eye text-[10px]"></i>
                    </button>
                    <button v-if="booking.status === 'CONFIRMED'" @click="handleCheckIn(booking)" 
                      class="px-4 py-1.5 rounded-lg bg-indigo-600 text-white hover:bg-indigo-700 transition-all text-[10px] font-black uppercase tracking-widest shadow-md shadow-indigo-100">
                      Check-in
                    </button>
                    <button v-if="booking.status === 'CHECKED_IN'" @click="handleCheckOut(booking)" 
                      class="px-4 py-1.5 rounded-lg bg-emerald-50 text-emerald-600 hover:bg-emerald-600 hover:text-white transition-all text-[10px] font-bold border border-emerald-100">
                      Check-out
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- Pagination -->
        <div class="p-6 border-t border-gray-50 flex items-center justify-between">
          <p class="text-xs text-gray-400 font-medium">Hiển thị 1 - {{ filteredBookings.length }} trong {{ filteredBookings.length }} kết quả</p>
          <div class="flex items-center gap-2">
            <button class="w-8 h-8 rounded-lg border border-gray-100 flex items-center justify-center text-gray-400 hover:bg-gray-50 transition-all">
              <i class="fas fa-chevron-left text-[10px]"></i>
            </button>
            <button v-for="p in [1, 2, 3, 4, 5]" :key="p" class="w-8 h-8 rounded-lg font-bold text-xs transition-all"
              :class="p === 1 ? 'bg-[#3182ce] text-white' : 'text-gray-400 hover:bg-gray-50'">
              {{ p }}
            </button>
            <button class="w-8 h-8 rounded-lg border border-gray-100 flex items-center justify-center text-gray-400 hover:bg-gray-50 transition-all">
              <i class="fas fa-chevron-right text-[10px]"></i>
            </button>
          </div>
        </div>
      </div>
    </main>

    <!-- Modal Check-in UI (Matching User's Image) -->
    <div v-if="showCheckInModal" class="fixed inset-0 z-[100] flex items-center justify-center p-6 bg-slate-900 bg-opacity-40 backdrop-blur-sm overflow-y-auto font-sans">
      <div class="bg-white rounded-[2.5rem] max-w-lg w-full shadow-2xl animate-in fade-in zoom-in duration-300 my-auto overflow-hidden border border-gray-100">
        <!-- Header (Blue/Indigo like the image) -->
        <div class="bg-indigo-600 p-8 text-white relative">
          <button @click="showCheckInModal = false" class="absolute top-6 right-6 w-10 h-10 flex items-center justify-center rounded-xl bg-white bg-opacity-10 hover:bg-white hover:bg-opacity-20 transition-all">
            <i class="fas fa-times"></i>
          </button>
          <h3 class="text-3xl font-black uppercase tracking-tight">Thủ tục Check-in</h3>
          <p class="text-white text-opacity-70 text-sm font-medium mt-1">Lưu trữ hồ sơ khách hàng (CCCD/Passport)</p>
        </div>

        <div class="p-8 space-y-8">
          <!-- Xác nhận thông tin khách -->
          <div class="bg-gray-50 rounded-3xl p-6 space-y-4">
            <h5 class="text-[10px] font-black text-gray-400 uppercase tracking-widest">Xác nhận thông tin khách</h5>
            <div class="space-y-3">
              <div class="flex justify-between items-center">
                <span class="text-sm font-medium text-gray-500">Tên khách</span>
                <span class="text-sm font-black text-gray-800">{{ selectedBooking?.guestFullName || selectedBooking?.customer?.fullName }}</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-sm font-medium text-gray-500">Số người</span>
                <span class="text-sm font-black text-gray-800">2 người</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-sm font-medium text-gray-500">Phòng</span>
                <span class="text-sm font-black text-gray-800">P.{{ selectedBooking?.room?.roomNumber }} - {{ selectedBooking?.room?.category }}</span>
              </div>
            </div>
          </div>

          <!-- Số CCCD / Passport -->
          <div class="space-y-3">
            <label class="text-[10px] font-black text-gray-400 uppercase tracking-widest ml-1">Số CCCD / Passport *</label>
            <input v-model="checkInForm.guestIdNumber" type="text" placeholder="Nhập số định danh..." 
              class="w-full bg-gray-50 border border-gray-100 rounded-2xl px-6 py-4 outline-none focus:border-indigo-400 transition-all text-sm font-bold shadow-sm">
          </div>

          <!-- Footer Action -->
          <button @click="submitCheckIn" class="w-full bg-indigo-600 hover:bg-indigo-700 text-white py-5 rounded-[1.5rem] font-black uppercase tracking-[0.2em] text-xs transition-all shadow-xl shadow-indigo-100 mt-4">
            Hoàn tất nhận phòng
          </button>
        </div>
      </div>
    </div>

    <!-- Modal Chi tiết đặt phòng -->
    <div v-if="showDetailModal" class="fixed inset-0 z-[100] flex items-center justify-center p-6 bg-slate-900 bg-opacity-40 backdrop-blur-sm overflow-y-auto font-sans">
      <div class="bg-white rounded-[2.5rem] max-w-2xl w-full shadow-2xl animate-in fade-in zoom-in duration-300 my-auto overflow-hidden border border-gray-100">
        <!-- Modal Header -->
        <div class="bg-slate-50 p-8 border-b border-gray-100 flex items-center justify-between sticky top-0 z-10">
          <div class="flex items-center gap-4">
            <div class="w-12 h-12 bg-white rounded-2xl flex items-center justify-center shadow-sm border border-gray-100">
              <i class="fas fa-info-circle text-blue-500 text-xl"></i>
            </div>
            <div>
              <h3 class="text-xl font-black text-gray-800 uppercase tracking-tight">Chi tiết đặt phòng</h3>
              <p class="text-xs font-bold text-gray-400 mt-0.5">Mã số: BK{{ String(selectedBooking?.id).padStart(8, '0') }}</p>
            </div>
          </div>
          <button @click="showDetailModal = false" class="w-10 h-10 flex items-center justify-center rounded-xl hover:bg-gray-200 transition-all text-gray-400">
            <i class="fas fa-times"></i>
          </button>
        </div>

        <!-- Modal Body -->
        <div class="p-8 space-y-8 max-h-[70vh] overflow-y-auto custom-scrollbar">
          <!-- Thông tin khách -->
          <div class="space-y-4">
            <h5 class="text-[10px] font-black text-gray-400 uppercase tracking-widest ml-1">Thông tin khách hàng</h5>
            <div class="grid grid-cols-2 gap-6 bg-gray-50 rounded-3xl p-6 border border-gray-100">
              <div class="space-y-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Họ tên</span>
                <p class="text-sm font-black text-gray-800">{{ selectedBooking?.guestFullName || selectedBooking?.customer?.fullName }}</p>
              </div>
              <div class="space-y-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Số điện thoại</span>
                <p class="text-sm font-black text-gray-800">{{ selectedBooking?.guestPhone || selectedBooking?.customer?.phone }}</p>
              </div>
              <div class="space-y-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">CCCD / Passport</span>
                <p class="text-sm font-black text-gray-800">{{ selectedBooking?.guestIdNumber || '--' }}</p>
              </div>
              <div class="space-y-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Trạng thái</span>
                <span :class="getStatusStyles(selectedBooking?.status)" class="px-2 py-0.5 rounded text-[9px] font-black uppercase tracking-widest">
                  {{ formatStatus(selectedBooking?.status) }}
                </span>
              </div>
            </div>
          </div>

          <!-- Dịch vụ đã sử dụng -->
          <div v-if="selectedBooking?.status === 'CHECKED_IN' || selectedBooking?.status === 'COMPLETED'" class="space-y-4">
            <div class="flex items-center justify-between">
              <h5 class="text-[10px] font-black text-gray-400 uppercase tracking-widest ml-1">Dịch vụ đã sử dụng</h5>
              <button v-if="selectedBooking?.status === 'CHECKED_IN'" @click="showAddService = true" class="text-[10px] font-black text-blue-600 uppercase tracking-widest hover:underline flex items-center gap-1.5">
                <i class="fas fa-plus-circle"></i>
                Thêm dịch vụ
              </button>
            </div>
            
            <div v-if="currentServices.length === 0" class="text-center py-8 bg-gray-50 rounded-3xl border border-dashed border-gray-200">
              <p class="text-xs font-bold text-gray-400 uppercase tracking-widest">Chưa sử dụng dịch vụ nào</p>
            </div>
            <div v-else class="space-y-3">
              <div v-for="service in currentServices" :key="service.id" class="flex items-center justify-between p-4 bg-gray-50 rounded-2xl border border-gray-100">
                <div class="flex items-center gap-3">
                  <div class="w-8 h-8 rounded-lg bg-white flex items-center justify-center text-blue-500 shadow-sm">
                    <i class="fas fa-concierge-bell text-xs"></i>
                  </div>
                  <div>
                    <p class="text-xs font-black text-gray-800">{{ service.service?.name }}</p>
                    <div class="flex items-center gap-2">
                      <p class="text-[9px] font-bold text-gray-400 uppercase tracking-widest">{{ formatDateTime(service.usedDate) }}</p>
                      <span :class="getStatusClass(service.status)" class="px-1.5 py-0.5 rounded text-[8px] font-black uppercase">
                        {{ formatStatus(service.status) }}
                      </span>
                    </div>
                  </div>
                </div>
                <div class="text-right">
                  <p class="text-xs font-black text-gray-800">{{ formatCurrency(service.service?.price * service.quantity) }}</p>
                  <p class="text-[9px] font-bold text-gray-400 uppercase tracking-widest">SL: {{ service.quantity }}</p>
                </div>
              </div>
            </div>
          </div>

          <!-- Thanh toán & Hóa đơn -->
          <div class="space-y-4">
            <h5 class="text-[10px] font-black text-gray-400 uppercase tracking-widest ml-1 flex items-center gap-2">
              Thanh toán & Hóa đơn
              <span v-if="selectedBooking?.paymentStatus === 'PAID'" class="bg-emerald-100 text-emerald-700 px-2 py-0.5 rounded text-[9px] font-black uppercase">Đã thanh toán</span>
              <span v-else class="bg-amber-100 text-amber-700 px-2 py-0.5 rounded text-[9px] font-black uppercase">Chưa thanh toán</span>
            </h5>
            <div v-if="invoiceLoading" class="text-center text-xs text-gray-400 py-4">Đang tải hóa đơn...</div>
            <div v-else class="bg-gray-50 rounded-3xl p-6 border border-gray-100 space-y-3">
              <div class="flex justify-between items-center">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Phương thức</span>
                <span class="text-sm font-black text-gray-800">{{ selectedBooking?.paymentMethod || 'Tiền mặt' }}</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Tiền phòng</span>
                <span class="text-sm font-black text-gray-800">{{ formatCurrency(invoice?.roomCharges) }}</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Dịch vụ (đã hoàn thành)</span>
                <span class="text-sm font-black text-gray-800">{{ formatCurrency(invoice?.serviceCharges) }}</span>
              </div>
              <div class="flex justify-between items-center pt-3 border-t border-gray-200">
                <span class="text-[11px] font-black text-gray-800 uppercase tracking-widest">Tổng thanh toán</span>
                <span class="text-lg font-black text-emerald-600">{{ formatCurrency(invoice?.totalAmount ?? selectedBooking?.totalPrice) }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- Modal Footer -->
        <div class="p-8 border-t border-gray-100 bg-slate-50 flex gap-4">
          <button v-if="selectedBooking?.status === 'CONFIRMED'" @click="handleCheckIn(selectedBooking)" class="flex-1 bg-indigo-600 hover:bg-indigo-700 text-white py-4 rounded-2xl font-black uppercase tracking-widest text-xs transition-all shadow-lg shadow-indigo-100">
            Tiến hành Check-in
          </button>
          <button v-if="selectedBooking?.status === 'CHECKED_IN'" @click="handleCheckOut(selectedBooking)" class="flex-1 bg-emerald-600 hover:bg-emerald-700 text-white py-4 rounded-2xl font-black uppercase tracking-widest text-xs transition-all shadow-lg shadow-emerald-100">
            Tiến hành Check-out
          </button>
          <button @click="showDetailModal = false" class="px-8 bg-white border border-gray-200 text-gray-500 py-4 rounded-2xl font-black uppercase tracking-widest text-xs hover:bg-gray-50 transition-all">
            Đóng
          </button>
        </div>
      </div>
    </div>

    <!-- Modal Thêm dịch vụ (New Modal) -->
    <div v-if="showAddService" class="fixed inset-0 z-[110] flex items-center justify-center p-6 bg-slate-900/60 backdrop-blur-md font-sans">
      <div class="bg-white rounded-[2.5rem] max-w-md w-full overflow-hidden shadow-2xl animate-in fade-in zoom-in duration-300 my-auto border border-gray-100">
        <div class="bg-indigo-600 p-8 text-white flex justify-between items-center">
          <div>
            <h3 class="text-2xl font-black uppercase tracking-tight">Thêm dịch vụ</h3>
            <p class="text-white/60 text-[10px] font-bold uppercase tracking-widest mt-1">Phòng {{ selectedBooking?.room?.roomNumber }}</p>
          </div>
          <button @click="showAddService = false" class="w-10 h-10 flex items-center justify-center rounded-xl bg-white/10 text-white hover:bg-rose-500 transition-all">
            <i class="fas fa-times"></i>
          </button>
        </div>
        
        <form @submit.prevent="submitAddService" class="p-8 space-y-6">
          <div class="space-y-2">
            <label class="text-[10px] font-black text-gray-400 uppercase tracking-widest ml-1">Chọn dịch vụ</label>
            <select v-model="serviceForm.serviceId" required class="w-full bg-gray-50 border border-gray-100 rounded-2xl px-6 py-4 outline-none focus:border-indigo-400 transition-all text-sm font-bold shadow-sm">
              <option value="">-- Chọn dịch vụ --</option>
              <option v-for="s in services" :key="s.id" :value="s.id">{{ s.name }} ({{ formatCurrency(s.price) }})</option>
            </select>
          </div>

          <div class="space-y-2">
            <label class="text-[10px] font-black text-gray-400 uppercase tracking-widest ml-1">Số lượng</label>
            <div class="flex items-center gap-4 bg-gray-50 rounded-2xl p-2 border border-gray-100">
              <button type="button" @click="serviceForm.quantity > 1 && serviceForm.quantity--" class="w-10 h-10 rounded-xl bg-white shadow-sm flex items-center justify-center text-gray-400 hover:text-indigo-600 transition-all">
                <i class="fas fa-minus text-[10px]"></i>
              </button>
              <input type="number" v-model.number="serviceForm.quantity" min="1" class="flex-1 bg-transparent border-0 text-center text-sm font-black text-gray-800 outline-none">
              <button type="button" @click="serviceForm.quantity++" class="w-10 h-10 rounded-xl bg-white shadow-sm flex items-center justify-center text-gray-400 hover:text-indigo-600 transition-all">
                <i class="fas fa-plus text-[10px]"></i>
              </button>
            </div>
          </div>

          <div class="space-y-2">
            <label class="text-[10px] font-black text-gray-400 uppercase tracking-widest ml-1">Ghi chú</label>
            <textarea v-model="serviceForm.note" rows="3" placeholder="Yêu cầu đặc biệt..." class="w-full bg-gray-50 border border-gray-100 rounded-2xl px-6 py-4 outline-none focus:border-indigo-400 transition-all text-sm font-bold shadow-sm resize-none"></textarea>
          </div>

          <button type="submit" class="w-full bg-indigo-600 hover:bg-indigo-700 text-white py-5 rounded-[1.5rem] font-black uppercase tracking-[0.2em] text-xs transition-all shadow-xl shadow-indigo-100 mt-4">
            Xác nhận thêm
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, nextTick } from 'vue'
import axios from '../api/axios'
import StaffSidebar from '../components/StaffSidebar.vue'
import jsPDF from 'jspdf'
import 'jspdf-autotable'

const activeTab = ref('checkin')
const bookings = ref([])
const loading = ref(true)
const roomTypes = ref(['Deluxe Room', 'Superior Room', 'Suite Room', 'Standard Room'])
const showCheckInModal = ref(false)
const showDetailModal = ref(false)
const showVoucherModal = ref(false)
const showAddService = ref(false)
const selectedBooking = ref(null)
const invoice = ref(null)
const invoiceLoading = ref(false)
const services = ref([])
const currentServices = ref([])

const serviceForm = ref({
  serviceId: '',
  quantity: 1,
  note: ''
})

const fetchInvoice = async (bookingId) => {
  invoiceLoading.value = true
  invoice.value = null
  try {
    const response = await axios.get(`/invoices/booking/${bookingId}`)
    invoice.value = response.data
  } catch (error) {
    console.error('Error fetching invoice:', error)
  } finally {
    invoiceLoading.value = false
  }
}

const fetchServices = async () => {
  try {
    const response = await axios.get('/hotel-services')
    services.value = response.data
  } catch (error) {
    console.error('Lỗi tải dịch vụ:', error)
  }
}

const fetchCurrentServices = async (bookingId) => {
  try {
    const response = await axios.get(`/hotel-services/booking/${bookingId}`)
    currentServices.value = response.data
  } catch (error) {
    console.error('Lỗi tải dịch vụ của khách:', error)
  }
}

const submitAddService = async () => {
  try {
    await axios.post('/hotel-services/add-to-booking', null, {
      params: {
        bookingId: selectedBooking.value.id,
        serviceId: serviceForm.value.serviceId,
        quantity: serviceForm.value.quantity,
        note: serviceForm.value.note
      }
    })
    showAddService.value = false
    serviceForm.value = { serviceId: '', quantity: 1, note: '' }
    fetchCurrentServices(selectedBooking.value.id)
    alert('Thêm dịch vụ thành công!')
  } catch (error) {
    alert('Lỗi: ' + (error.response?.data?.message || 'Không thể thêm dịch vụ'))
  }
}

const formatDateTime = (dateStr) => {
  if (!dateStr) return '--'
  const d = new Date(dateStr)
  return d.toLocaleString('vi-VN', { 
    day: '2-digit', month: '2-digit', year: 'numeric',
    hour: '2-digit', minute: '2-digit'
  })
}

// Image & Camera Logic
const fileInput = ref(null)
const idCardPreview = ref(null)
const showCamera = ref(false)
const video = ref(null)
const stream = ref(null)

const triggerFileInput = () => {
  fileInput.value.click()
}

const onFileChange = (e) => {
  const file = e.target.files[0]
  if (file) {
    const reader = new FileReader()
    reader.onload = (event) => {
      idCardPreview.value = event.target.result
    }
    reader.readAsDataURL(file)
  }
}

const startCamera = async () => {
  try {
    showCamera.value = true
    await nextTick()
    stream.value = await navigator.mediaDevices.getUserMedia({ video: { facingMode: 'environment' } })
    if (video.value) {
      video.value.srcObject = stream.value
    }
  } catch (err) {
    console.error('Không thể mở camera:', err)
    alert('Không thể truy cập camera. Vui lòng kiểm tra quyền truy cập.')
    showCamera.value = false
  }
}

const capturePhoto = () => {
  const canvas = document.createElement('canvas')
  canvas.width = video.value.videoWidth
  canvas.height = video.value.videoHeight
  const ctx = canvas.getContext('2d')
  ctx.drawImage(video.value, 0, 0)
  idCardPreview.value = canvas.toDataURL('image/jpeg')
  stopCamera()
}

const stopCamera = () => {
  if (stream.value) {
    stream.value.getTracks().forEach(track => track.stop())
  }
  showCamera.value = false
  stream.value = null
}

const staffName = computed(() => {
  const user = JSON.parse(localStorage.getItem('user') || '{}')
  return user.fullName || 'Nhân viên'
})

// Notifications Logic
const showNotifications = ref(false)
const notifications = ref([
  { message: '🔔 Có khách vừa đặt phòng mới', time: '5 phút trước', read: false },
  { message: '🔔 Có booking chờ xác nhận', time: '10 phút trước', read: false },
  { message: '🔔 Khách vừa hủy đặt phòng', time: '1 giờ trước', read: true },
  { message: '🔔 Đặt phòng sắp đến ngày check-in', time: '2 giờ trước', read: false },
  { message: '🔔 Khách sắp check-out hôm nay', time: '3 giờ trước', read: false },
  { message: '🔔 Phòng khách đặt hiện không khả dụng', time: '1 ngày trước', read: true },
  { message: '🔔 Khách chưa thanh toán tiền cọc', time: '1 ngày trước', read: false },
  { message: '🔔 Có yêu cầu đổi phòng / đổi ngày', time: '2 ngày trước', read: false },
  { message: '🔔 Booking quá hạn chưa check-in', time: '2 ngày trước', read: true },
  { message: '🔔 Có ghi chú đặc biệt từ khách VIP', time: '3 ngày trước', read: false }
])

const unreadNotificationsCount = computed(() => notifications.value.filter(n => !n.read).length)

const toggleNotifications = () => {
  showNotifications.value = !showNotifications.value
}

const markAllAsRead = () => {
  notifications.value.forEach(n => n.read = true)
}

const checkInForm = ref({
  checkInDate: '',
  checkInTime: '14:00',
  guestIdNumber: '',
  nationality: 'Việt Nam',
  address: '',
  note: '',
  paymentMethod: 'Tiền mặt',
  depositAmount: 500000,
  internalNote: ''
})

const filters = ref({
  search: '',
  status: '',
  roomType: '',
  date: new Date().toISOString().substr(0, 10)
})

const stats = ref({
  pendingCheckin: 0,
  checkedInToday: 0,
  dueCheckout: 0,
  overdueCheckout: 0,
  checkedOutToday: 0
})

const fetchBookings = async () => {
  loading.value = true
  try {
    const response = await axios.get('/bookings')
    bookings.value = response.data
    calculateStats()
  } catch (error) {
    console.error('Error fetching bookings:', error)
  } finally {
    loading.value = false
  }
}

const calculateStats = () => {
  const today = new Date().toISOString().substr(0, 10)
  stats.value.pendingCheckin = bookings.value.filter(b => b.status === 'CONFIRMED').length
  stats.value.checkedInToday = bookings.value.filter(b => b.status === 'CHECKED_IN' && b.checkInDate === today).length
  stats.value.dueCheckout = bookings.value.filter(b => b.status === 'CHECKED_IN' && b.checkOutDate === today).length
  stats.value.checkedOutToday = bookings.value.filter(b => b.status === 'COMPLETED' && b.checkOutDate === today).length
  
  const now = new Date()
  stats.value.overdueCheckout = bookings.value.filter(b => 
    b.status === 'CHECKED_IN' && 
    new Date(b.checkOutDate) < now && 
    b.checkOutDate !== today
  ).length
}

const filteredBookings = computed(() => {
  return bookings.value.filter(b => {
    if (activeTab.value === 'checkin') {
      if (b.status === 'COMPLETED' || b.status === 'CANCELLED') return false
    } else {
      if (b.status === 'CONFIRMED' || b.status === 'PENDING' || b.status === 'CANCELLED') return false
    }

    const searchStr = filters.value.search.toLowerCase()
    const matchSearch = !filters.value.search || 
      (b.guestFullName || b.customer?.fullName || '').toLowerCase().includes(searchStr) ||
      (b.guestPhone || b.customer?.phone || '').includes(searchStr) ||
      String(b.id).includes(searchStr)
    
    const matchStatus = !filters.value.status || b.status === filters.value.status
    const matchRoomType = !filters.value.roomType || b.room?.category === filters.value.roomType
    
    return matchSearch && matchStatus && matchRoomType
  })
})

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const d = new Date(dateStr)
  return d.toLocaleDateString('vi-VN')
}

const formatStatus = (status) => {
  switch (status) {
    case 'CONFIRMED': return 'Đã đặt (Chờ đến)'
    case 'CHECKED_IN': return 'Đã nhận phòng'
    case 'COMPLETED': return 'Đã trả phòng'
    case 'CANCELLED': return 'Đã hủy'
    default: return status
  }
}

const getStatusStyles = (status) => {
  switch (status) {
    case 'CONFIRMED': return 'bg-blue-50 text-blue-600 border border-blue-100'
    case 'CHECKED_IN': return 'bg-emerald-50 text-emerald-600 border border-emerald-100'
    case 'COMPLETED': return 'bg-gray-50 text-gray-500 border border-gray-100'
    case 'CANCELLED': return 'bg-rose-50 text-rose-500 border border-rose-100'
    default: return 'bg-amber-50 text-amber-600 border border-amber-100'
  }
}

const handleCheckIn = (booking) => {
  selectedBooking.value = booking
  idCardPreview.value = null
  // Ưu tiên lấy citizenId từ tài khoản khách hàng, nếu không có thì lấy guestIdNumber từ booking
  const citizenId = booking.customer?.citizenId || booking.guestIdNumber || ''
  
  checkInForm.value = {
    checkInDate: booking.checkInDate,
    checkInTime: '14:00',
    guestIdNumber: citizenId,
    nationality: 'Việt Nam',
    address: booking.guestAddress || '',
    note: booking.note || '',
    paymentMethod: 'Tiền mặt',
    depositAmount: 500000,
    internalNote: ''
  }
  showCheckInModal.value = true
}

const openDetail = (booking) => {
  selectedBooking.value = booking
  showDetailModal.value = true
  fetchCurrentServices(booking.id)
  fetchInvoice(booking.id)
}

const openVoucher = (booking) => {
  selectedBooking.value = booking
  showVoucherModal.value = true
}

const submitCheckIn = async () => {
  if (!checkInForm.value.guestIdNumber) {
    alert('Vui lòng nhập số CCCD/Passport')
    return
  }
  
  if (checkInForm.value.guestIdNumber.length !== 12) {
    alert('Số CCCD phải bao gồm đúng 12 chữ số')
    return
  }

  try {
    const payload = {
      guestIdNumber: checkInForm.value.guestIdNumber,
      guestIdImageUrl: idCardPreview.value // Truyền thêm ảnh CCCD nếu có
    }
    await axios.put(`/bookings/${selectedBooking.value.id}/check-in`, payload)
    showCheckInModal.value = false
    fetchBookings()
    alert('Khách đã nhận phòng thành công!')
  } catch (error) {
    const errorMsg = error.response?.data?.message || error.message || 'Check-in thất bại'
    alert('Lỗi: ' + errorMsg)
  }
}

const formatCurrency = (amount) => {
  if (!amount) return '0 đ'
  return new Intl.NumberFormat('vi-VN').format(amount * 25000) + ' đ'
}

const getStatusClass = (status) => {
  switch (status) {
    case 'PENDING': return 'bg-amber-100 text-amber-700'
    case 'IN_PROGRESS': return 'bg-blue-100 text-blue-700'
    case 'COMPLETED': return 'bg-emerald-100 text-emerald-700'
    default: return 'bg-gray-100 text-gray-700'
  }
}

const getServiceIcon = (name) => {
  const n = name?.toLowerCase() || ''
  if (n.includes('breakfast') || n.includes('ăn')) return 'fas fa-utensils'
  if (n.includes('laundry') || n.includes('giặt')) return 'fas fa-tshirt'
  if (n.includes('drink') || n.includes('nước')) return 'fas fa-glass-martini-alt'
  return 'fas fa-concierge-bell'
}

const fetchInvoiceForBooking = async (bookingId) => {
  const response = await axios.get(`/invoices/booking/${bookingId}`)
  return response.data
}

const handleCheckOut = async (booking) => {
  try {
    const invoice = await fetchInvoiceForBooking(booking.id)
    const room = invoice.roomCharges || 0
    const services = invoice.serviceCharges || 0
    const total = invoice.totalAmount || 0

    if (booking.paymentStatus !== 'PAID') {
      const settleMsg = `Hóa đơn quyết toán:\n- Tiền phòng: ${formatCurrency(room)}\n- Dịch vụ: ${formatCurrency(services)}\n- Tổng: ${formatCurrency(total)}\n\nThu tiền và check-out?`
      if (!confirm(settleMsg)) return
      await axios.put(`/bookings/${booking.id}/mark-paid`)
    } else if (!confirm(`Xác nhận check-out phòng ${booking.room?.roomNumber}?\nTổng đã thanh toán: ${formatCurrency(total)}`)) {
      return
    }

    await axios.put(`/bookings/${booking.id}/check-out`)
    showDetailModal.value = false
    await fetchBookings()
    alert('Check-out thành công!')
  } catch (error) {
    console.error('Check-out failed:', error)
    const errorMsg = error.response?.data?.message || error.message || 'Không thể check-out'
    alert('Lỗi check-out: ' + errorMsg)
  }
}

const printVoucher = () => {
  window.print()
}

const downloadPDF = () => {
  const doc = new jsPDF()
  const booking = selectedBooking.value
  
  // Set font
  doc.setFontSize(20)
  doc.text('PHIEU DAT PHONG', 105, 20, { align: 'center' })
  
  doc.setFontSize(12)
  doc.text(`Ma dat phong: BK${String(booking?.id).padStart(8, '0')}`, 20, 40)
  doc.text(`Khach hang: ${booking?.guestFullName || booking?.customer?.fullName}`, 20, 50)
  doc.text(`So dien thoai: ${booking?.guestPhone || booking?.customer?.phone}`, 20, 60)
  doc.text(`Phong: ${booking?.room?.roomNumber} - ${booking?.room?.category}`, 20, 70)
  doc.text(`Check-in: ${formatDate(booking?.checkInDate)} (14:00)`, 20, 80)
  doc.text(`Check-out: ${formatDate(booking?.checkOutDate)} (12:00)`, 20, 90)
  doc.text(`Tong tien: ${new Intl.NumberFormat('vi-VN').format(booking?.totalPrice)} d`, 20, 100)
  
  doc.save(`Voucher_BK${String(booking?.id).padStart(8, '0')}.pdf`)
}

onMounted(() => {
  fetchBookings()
  fetchServices()
})
</script>

<style scoped>
.custom-scrollbar::-webkit-scrollbar {
  width: 4px;
}
.custom-scrollbar::-webkit-scrollbar-track {
  background: transparent;
}
.custom-scrollbar::-webkit-scrollbar-thumb {
  background: #e2e8f0;
  border-radius: 10px;
}

.animate-in {
  animation: animate-in 0.2s ease-out;
}

@keyframes animate-in {
  from {
    opacity: 0;
    transform: scale(0.98);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}

@media print {
  body * {
    visibility: hidden;
  }
  #voucher-print-area, #voucher-print-area * {
    visibility: visible;
  }
  #voucher-print-area {
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
  }
}
</style>
