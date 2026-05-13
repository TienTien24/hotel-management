<template>
  <div class="min-h-screen flex bg-[#f8faff] font-sans text-[#2d3748]">
    <!-- Sidebar -->
    <StaffSidebar />

    <!-- Main Content -->
    <main class="flex-1 p-8 overflow-y-auto">
      <!-- Header Section -->
      <header class="flex items-center justify-between mb-8">
        <h2 class="text-2xl font-bold text-[#1a202c]">Quản lý đặt phòng</h2>
        
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
                <span class="font-bold text-sm">Thông báo</span>
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

          <!-- Create Booking Button -->
          <button @click="openCreateModal" class="bg-[#3182ce] hover:bg-[#2b6cb0] text-white px-5 py-2.5 rounded-xl font-bold text-sm transition-all shadow-lg shadow-blue-100 flex items-center gap-2">
            <i class="fas fa-plus text-xs"></i>
            Tạo đặt phòng
          </button>
        </div>
      </header>

      <!-- Tabs Navigation -->
      <div class="flex items-center gap-8 mb-6 border-b border-gray-200 px-2">
        <button v-for="tab in tabItems" :key="tab.value"
          @click="filters.status = tab.value"
          class="pb-4 text-sm font-bold transition-all relative"
          :class="filters.status === tab.value ? 'text-[#3182ce]' : 'text-gray-400 hover:text-gray-600'"
        >
          {{ tab.label }}
          <div v-if="filters.status === tab.value" class="absolute bottom-0 left-0 w-full h-0.5 bg-[#3182ce] rounded-full"></div>
        </button>
      </div>

      <!-- Filters Section -->
      <div class="flex flex-col md:flex-row gap-4 mb-6">
        <div class="relative flex-1 max-w-md">
          <i class="fas fa-search absolute left-4 top-1/2 -translate-y-1/2 text-gray-400 text-sm"></i>
          <input type="text" v-model="filters.search" placeholder="Tìm tên khách, SĐT, mã đặt phòng..." 
            class="w-full bg-white border border-gray-200 rounded-xl pl-11 pr-4 py-2.5 outline-none focus:border-blue-400 transition-all text-sm font-medium shadow-sm">
        </div>
        
        <div class="relative w-full md:w-56">
          <i class="far fa-calendar-alt absolute left-4 top-1/2 -translate-y-1/2 text-gray-400 text-sm"></i>
          <select v-model="filters.timeRange" class="w-full bg-white border border-gray-200 rounded-xl pl-11 pr-4 py-2.5 outline-none focus:border-blue-400 transition-all text-sm font-bold shadow-sm appearance-none">
            <option value="all">Tất cả thời gian</option>
            <option value="today">Hôm nay</option>
            <option value="this-week">Tuần này</option>
            <option value="this-month">Tháng này</option>
          </select>
          <i class="fas fa-chevron-down absolute right-4 top-1/2 -translate-y-1/2 text-gray-300 text-[10px] pointer-events-none"></i>
        </div>
      </div>

      <!-- Bookings Table -->
      <div class="bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden">
        <div v-if="loading" class="p-16 text-center text-gray-400 font-bold">Đang tải danh sách đặt phòng...</div>
        <div v-else-if="filteredBookings.length === 0" class="p-16 text-center text-gray-400 font-bold">Không có đặt phòng phù hợp.</div>
        <div v-else class="overflow-x-auto">
          <table class="w-full text-left">
            <thead>
              <tr class="bg-gray-50 bg-opacity-50 border-b border-gray-100">
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider">Mã đặt phòng</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider">Khách hàng</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider">Phòng</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider">Check-in</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider">Check-out</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider text-center">Trạng thái</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider text-center">Thanh toán</th>
                <th class="px-6 py-4 text-[11px] font-bold text-gray-500 uppercase tracking-wider text-center">Thao tác</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-gray-50">
              <tr v-for="booking in filteredBookings" :key="booking.id" class="hover:bg-gray-50 hover:bg-opacity-30 transition-colors">
                <td class="px-6 py-5">
                  <span @click="openDetailModal(booking)" class="text-blue-600 font-bold cursor-pointer hover:underline">BK{{ String(booking.id).padStart(8, '0') }}</span>
                </td>
                <td class="px-6 py-5">
                  <div class="flex flex-col">
                    <span class="text-sm font-bold text-gray-800">{{ booking.guestFullName || booking.customer?.fullName }}</span>
                    <span class="text-[11px] text-gray-400 font-medium mt-0.5">{{ booking.guestPhone || booking.customer?.phone || 'N/A' }}</span>
                  </div>
                </td>
                <td class="px-6 py-5 text-sm font-bold text-gray-600">{{ booking.room?.roomNumber }}</td>
                <td class="px-6 py-5 text-sm font-medium text-gray-600">{{ formatDate(booking.checkInDate) }}</td>
                <td class="px-6 py-5 text-sm font-medium text-gray-600">{{ formatDate(booking.checkOutDate) }}</td>
                <td class="px-6 py-5 text-center">
                  <span :class="getStatusStyles(booking.status)" class="px-3 py-1 rounded-full text-[10px] font-bold flex items-center justify-center gap-1.5 w-fit mx-auto">
                    <div class="w-1.5 h-1.5 rounded-full" :class="getStatusDotClass(booking.status)"></div>
                    {{ formatStatus(booking.status) }}
                  </span>
                </td>
                <td class="px-6 py-5 text-center">
                  <span :class="getPaymentStatusStyles(booking.paymentStatus)" class="px-3 py-1 rounded-lg text-[10px] font-bold">
                    {{ formatPaymentStatus(booking.paymentStatus) }}
                  </span>
                </td>
                <td class="px-6 py-5">
                  <div class="flex items-center justify-center gap-2">
                    <button @click="openDetailModal(booking)" class="w-8 h-8 rounded-lg bg-blue-50 text-blue-500 hover:bg-blue-500 hover:text-white transition-all flex items-center justify-center border border-blue-100" title="Xem chi tiết">
                      <i class="far fa-eye text-xs"></i>
                    </button>
                    <button @click="openUpdateModal(booking)" class="w-8 h-8 rounded-lg bg-gray-50 text-gray-500 hover:bg-gray-800 hover:text-white transition-all flex items-center justify-center border border-gray-100" title="Chỉnh sửa">
                      <i class="far fa-edit text-xs"></i>
                    </button>
                    <button @click="handleCancel(booking.id)" class="w-8 h-8 rounded-lg bg-rose-50 text-rose-500 hover:bg-rose-500 hover:text-white transition-all flex items-center justify-center border border-rose-100" title="Hủy">
                      <i class="far fa-trash-alt text-xs"></i>
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        
        <!-- Pagination -->
        <div class="p-6 border-t border-gray-50 flex items-center justify-between">
          <p class="text-xs text-gray-400 font-medium">Hiển thị 1 - {{ filteredBookings.length }} trong {{ bookings.length }} đặt phòng</p>
          <div class="flex items-center gap-2">
            <button class="w-8 h-8 rounded-lg border border-gray-100 flex items-center justify-center text-gray-400 hover:bg-gray-50 transition-all">
              <i class="fas fa-chevron-left text-[10px]"></i>
            </button>
            <button v-for="p in 5" :key="p" class="w-8 h-8 rounded-lg font-bold text-xs transition-all"
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

    <!-- Modal Chi tiết đặt phòng (Image 3) -->
    <div v-if="showDetailModal" class="fixed inset-0 z-[100] flex items-center justify-center p-6 bg-slate-900 bg-opacity-40 backdrop-blur-sm overflow-y-auto">
      <div class="bg-white rounded-2xl max-w-2xl w-full shadow-2xl animate-in fade-in zoom-in duration-300 my-auto">
        <!-- Modal Header -->
        <div class="p-6 border-b border-gray-100 flex items-center justify-between sticky top-0 bg-white rounded-t-2xl z-10">
          <div class="flex items-center gap-3">
            <h3 class="text-lg font-bold text-gray-800">Chi tiết đặt phòng</h3>
            <span :class="getStatusStyles(selectedBooking?.status)" class="px-2 py-0.5 rounded-md text-[10px] font-bold">
              {{ formatStatus(selectedBooking?.status) }}
            </span>
          </div>
          <button @click="showDetailModal = false" class="w-8 h-8 flex items-center justify-center rounded-lg hover:bg-gray-100 transition-all text-gray-400">
            <i class="fas fa-times"></i>
          </button>
        </div>

        <!-- Modal Body -->
        <div class="p-8 space-y-8 max-h-[70vh] overflow-y-auto custom-scrollbar">
          <!-- Booking Basic Info -->
          <div class="flex items-center gap-5">
            <div class="w-14 h-14 bg-blue-50 rounded-xl flex items-center justify-center border border-blue-100">
              <i class="far fa-calendar-alt text-blue-500 text-2xl"></i>
            </div>
            <div>
              <h4 class="text-xl font-bold text-blue-600">BK{{ String(selectedBooking?.id).padStart(8, '0') }}</h4>
              <p class="text-[11px] text-gray-400 font-bold mt-1 uppercase tracking-wider">Ngày đặt: {{ formatDateTime(selectedBooking?.createdAt) }}</p>
              <p class="text-[11px] text-gray-400 font-bold mt-0.5 uppercase tracking-wider">Lễ tân: {{ staffName }}</p>
            </div>
          </div>

          <!-- Section: Thông tin khách hàng -->
          <div class="space-y-4">
            <div class="flex items-center justify-between">
              <h5 class="text-xs font-bold text-gray-400 uppercase tracking-widest">Thông tin khách hàng</h5>
              <button @click="openUpdateModal(selectedBooking)" class="text-blue-500 font-bold text-[10px] hover:underline px-3 py-1 bg-blue-50 rounded-lg">Sửa</button>
            </div>
            <div class="grid grid-cols-2 gap-y-4 bg-gray-50 bg-opacity-50 p-6 rounded-2xl border border-gray-100">
              <div class="space-y-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Họ tên</span>
                <p class="text-sm font-bold text-gray-800">{{ selectedBooking?.guestFullName || selectedBooking?.customer?.fullName }}</p>
              </div>
              <div class="space-y-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">SĐT</span>
                <p class="text-sm font-bold text-gray-800">{{ selectedBooking?.guestPhone || selectedBooking?.customer?.phone }}</p>
              </div>
              <div class="space-y-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Email</span>
                <p class="text-sm font-bold text-gray-800">{{ selectedBooking?.guestEmail || selectedBooking?.customer?.email || '--' }}</p>
              </div>
              <div class="space-y-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Quốc tịch</span>
                <p class="text-sm font-bold text-gray-800">Việt Nam</p>
              </div>
              <div class="space-y-1 col-span-2">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Địa chỉ</span>
                <p class="text-sm font-bold text-gray-800">{{ selectedBooking?.guestAddress || '123 Đường ABC, Quận 1, TP. HCM' }}</p>
              </div>
              <div class="space-y-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Số giấy tờ</span>
                <p class="text-sm font-bold text-gray-800">{{ selectedBooking?.guestIdNumber || '--' }}</p>
              </div>
              <div class="space-y-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Ghi chú</span>
                <p class="text-sm font-bold text-gray-800">Khách VIP - Yêu cầu phòng yên tĩnh</p>
              </div>
            </div>
          </div>

          <!-- Section: Thông tin đặt phòng -->
          <div class="space-y-4">
            <h5 class="text-xs font-bold text-gray-400 uppercase tracking-widest">Thông tin đặt phòng</h5>
            <div class="grid grid-cols-2 gap-x-8 gap-y-4 bg-gray-50 bg-opacity-50 p-6 rounded-2xl border border-gray-100">
              <div class="flex justify-between items-center border-b border-gray-100 pb-2 col-span-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Phòng</span>
                <span class="text-sm font-bold text-gray-800">{{ selectedBooking?.room?.roomNumber }} - {{ selectedBooking?.room?.category }}</span>
              </div>
              <div class="flex justify-between items-center border-b border-gray-100 pb-2 col-span-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Loại giường</span>
                <span class="text-sm font-bold text-gray-800">1 giường đôi</span>
              </div>
              <div class="flex justify-between items-center border-b border-gray-100 pb-2 col-span-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Check-in</span>
                <span class="text-sm font-bold text-gray-800">{{ formatDate(selectedBooking?.checkInDate) }} (14:00)</span>
              </div>
              <div class="flex justify-between items-center border-b border-gray-100 pb-2 col-span-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Check-out</span>
                <span class="text-sm font-bold text-gray-800">{{ formatDate(selectedBooking?.checkOutDate) }} (12:00)</span>
              </div>
              <div class="flex justify-between items-center border-b border-gray-100 pb-2 col-span-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Số đêm</span>
                <span class="text-sm font-bold text-gray-800">{{ calculateNights(selectedBooking?.checkInDate, selectedBooking?.checkOutDate) }} đêm</span>
              </div>
              <div class="flex justify-between items-center border-b border-gray-100 pb-2 col-span-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Số khách</span>
                <span class="text-sm font-bold text-gray-800">{{ selectedBooking?.numberOfGuests }} người lớn, 0 trẻ em</span>
              </div>
              <div class="flex justify-between items-center pt-2 col-span-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Giá phòng / đêm</span>
                <span class="text-sm font-bold text-gray-800">{{ formatCurrency(selectedBooking?.room?.price) }}</span>
              </div>
              <div class="flex justify-between items-center pt-2 col-span-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Tổng tiền phòng</span>
                <span class="text-sm font-bold text-gray-800">{{ formatCurrency(selectedBooking?.totalPrice) }}</span>
              </div>
            </div>
          </div>

          <!-- Section: Thanh toán -->
          <div class="space-y-4">
            <h5 class="text-xs font-bold text-gray-400 uppercase tracking-widest flex items-center gap-2">
              Thanh toán
              <span :class="getPaymentStatusStyles(selectedBooking?.paymentStatus)" class="px-2 py-0.5 rounded text-[9px] font-bold">
                {{ formatPaymentStatus(selectedBooking?.paymentStatus) }}
              </span>
            </h5>
            <div class="bg-gray-50 bg-opacity-50 p-6 rounded-2xl border border-gray-100 space-y-3">
              <div class="flex justify-between items-center">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Phương thức</span>
                <span class="text-sm font-bold text-gray-800">{{ selectedBooking?.paymentMethod || 'Tiền mặt' }}</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Tiền cọc</span>
                <span class="text-sm font-bold text-gray-800">{{ formatCurrency((selectedBooking?.totalPrice || 0) * 0.4) }}</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Thanh toán ngày</span>
                <span class="text-sm font-bold text-gray-800">{{ formatDateTime(selectedBooking?.createdAt) }}</span>
              </div>
              <div class="flex justify-between items-center pt-3 border-t border-gray-100">
                <span class="text-[11px] font-bold text-gray-800 uppercase tracking-widest">Tổng thanh toán</span>
                <span class="text-lg font-bold text-emerald-600">{{ formatCurrency(selectedBooking?.totalPrice) }}</span>
              </div>
            </div>
          </div>

          <!-- Section: Ghi chú -->
          <div class="space-y-4">
            <h5 class="text-xs font-bold text-gray-400 uppercase tracking-widest">Ghi chú & Yêu cầu đặc biệt</h5>
            <div class="bg-gray-50 bg-opacity-50 p-6 rounded-2xl border border-gray-100 space-y-4">
              <div class="space-y-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Yêu cầu</span>
                <p class="text-sm font-bold text-gray-800">Phòng yên tĩnh, tầng cao</p>
              </div>
              <div class="space-y-1">
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Ghi chú</span>
                <p class="text-sm font-bold text-gray-800">Khách đi công tác</p>
              </div>
            </div>
          </div>

          <!-- Section: Lịch sử đặt phòng -->
          <div class="space-y-4">
            <h5 class="text-xs font-bold text-gray-400 uppercase tracking-widest">Lịch sử đặt phòng</h5>
            <div class="relative pl-8 space-y-6 before:absolute before:left-[11px] before:top-2 before:bottom-2 before:w-0.5 before:bg-gray-100">
              <div class="relative">
                <div class="absolute -left-8 top-1.5 w-6 h-6 rounded-full bg-blue-500 border-4 border-white shadow-sm flex items-center justify-center"></div>
                <div class="flex justify-between items-center">
                  <span class="text-[11px] font-bold text-gray-800">{{ formatDateTime(selectedBooking?.createdAt) }}</span>
                  <span class="text-[11px] font-medium text-gray-600">Tạo đặt phòng bởi {{ staffName }}</span>
                </div>
              </div>
              <div v-if="selectedBooking?.status === 'CHECKED_IN' || selectedBooking?.status === 'COMPLETED'" class="relative">
                <div class="absolute -left-8 top-1.5 w-6 h-6 rounded-full bg-blue-500 border-4 border-white shadow-sm flex items-center justify-center"></div>
                <div class="flex justify-between items-center">
                  <span class="text-[11px] font-bold text-gray-800">{{ formatDateTime(selectedBooking?.checkedInAt) }}</span>
                  <span class="text-[11px] font-medium text-gray-600">Khách check-in</span>
                </div>
              </div>
              <div v-if="selectedBooking?.paymentStatus === 'PAID'" class="relative">
                <div class="absolute -left-8 top-1.5 w-6 h-6 rounded-full bg-emerald-500 border-4 border-white shadow-sm flex items-center justify-center"></div>
                <div class="flex justify-between items-center">
                  <span class="text-[11px] font-bold text-gray-800">{{ formatDateTime(selectedBooking?.createdAt) }}</span>
                  <span class="text-[11px] font-medium text-gray-600">Thanh toán {{ formatCurrency((selectedBooking?.totalPrice || 0) * 0.4) }} (Tiền cọc)</span>
                </div>
              </div>
              <div v-if="selectedBooking?.status === 'COMPLETED'" class="relative">
                <div class="absolute -left-8 top-1.5 w-6 h-6 rounded-full bg-emerald-500 border-4 border-white shadow-sm flex items-center justify-center"></div>
                <div class="flex justify-between items-center">
                  <span class="text-[11px] font-bold text-gray-800">{{ formatDateTime(selectedBooking?.checkedOutAt) }}</span>
                  <span class="text-[11px] font-medium text-gray-600">Thanh toán {{ formatCurrency((selectedBooking?.totalPrice || 0) * 0.6) }} (Còn lại)</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Modal Footer -->
        <div class="p-6 border-t border-gray-100 flex gap-4 sticky bottom-0 bg-white rounded-b-2xl">
          <button @click="printInvoice" class="flex-1 border border-gray-200 hover:bg-gray-50 text-gray-600 py-3 rounded-xl font-bold text-sm transition-all flex items-center justify-center gap-2">
            <i class="fas fa-print"></i>
            In phiếu đặt phòng
          </button>
          <button @click="handleCancel(selectedBooking.id)" class="flex-1 border border-rose-200 hover:bg-rose-50 text-rose-500 py-3 rounded-xl font-bold text-sm transition-all flex items-center justify-center gap-2">
            <i class="far fa-trash-alt"></i>
            Hủy đặt phòng
          </button>
        </div>
      </div>
    </div>

    <!-- Modal Tạo/Cập nhật Booking -->
    <div v-if="showModal" class="fixed inset-0 z-[110] flex items-center justify-center p-6 bg-slate-900 bg-opacity-40 backdrop-blur-sm overflow-y-auto">
      <div class="bg-white rounded-[2.5rem] max-w-xl w-full shadow-2xl animate-in fade-in zoom-in duration-300 my-auto overflow-hidden">
        <div class="bg-[#3182ce] p-8 text-white relative">
          <button @click="closeModal" class="absolute top-6 right-6 w-10 h-10 flex items-center justify-center rounded-xl bg-white bg-opacity-10 hover:bg-white hover:bg-opacity-20 transition-all">
            <i class="fas fa-times"></i>
          </button>
          <h3 class="text-2xl font-bold uppercase tracking-tight">{{ isEditing ? 'Cập nhật đặt phòng' : 'Tạo đặt phòng mới' }}</h3>
          <p class="text-white text-opacity-70 text-sm font-medium mt-1">Vui lòng điền đầy đủ thông tin bên dưới</p>
        </div>

        <form @submit.prevent="handleSubmit" class="p-8 space-y-6">
          <div class="grid grid-cols-2 gap-6">
            <div class="space-y-2">
              <label class="block text-[10px] font-bold uppercase tracking-widest text-gray-400 ml-1">Tên khách hàng</label>
              <input v-model="form.guestFullName" type="text" required placeholder="Họ và tên..." class="w-full bg-gray-50 border border-gray-100 rounded-xl px-4 py-3 outline-none focus:border-blue-400 transition-all font-bold text-sm">
            </div>
            <div class="space-y-2">
              <label class="block text-[10px] font-bold uppercase tracking-widest text-gray-400 ml-1">SĐT / Email</label>
              <input v-model="form.guestContact" type="text" required placeholder="Liên hệ..." class="w-full bg-gray-50 border border-gray-100 rounded-xl px-4 py-3 outline-none focus:border-blue-400 transition-all font-bold text-sm">
            </div>
          </div>

          <div class="grid grid-cols-2 gap-6">
            <div class="space-y-2">
              <label class="block text-[10px] font-bold uppercase tracking-widest text-gray-400 ml-1">Ngày nhận phòng</label>
              <input v-model="form.checkInDate" type="date" required class="w-full bg-gray-50 border border-gray-100 rounded-xl px-4 py-3 outline-none focus:border-blue-400 transition-all font-bold text-sm">
            </div>
            <div class="space-y-2">
              <label class="block text-[10px] font-bold uppercase tracking-widest text-gray-400 ml-1">Ngày trả phòng</label>
              <input v-model="form.checkOutDate" type="date" required class="w-full bg-gray-50 border border-gray-100 rounded-xl px-4 py-3 outline-none focus:border-blue-400 transition-all font-bold text-sm">
            </div>
          </div>

          <div class="grid grid-cols-2 gap-6">
            <div class="space-y-2">
              <label class="block text-[10px] font-bold uppercase tracking-widest text-gray-400 ml-1">Số người</label>
              <input v-model.number="form.numberOfGuests" type="number" min="1" required class="w-full bg-gray-50 border border-gray-100 rounded-xl px-4 py-3 outline-none focus:border-blue-400 transition-all font-bold text-sm">
            </div>
            <div class="space-y-2">
              <label class="block text-[10px] font-bold uppercase tracking-widest text-gray-400 ml-1">Chọn phòng</label>
              <select v-model="form.roomId" required class="w-full bg-gray-50 border border-gray-100 rounded-xl px-4 py-3 outline-none focus:border-blue-400 transition-all font-bold text-sm">
                <option value="">-- Chọn phòng trống --</option>
                <option v-for="room in availableRooms" :key="room.id" :value="room.id">
                  P.{{ room.roomNumber }} - {{ room.category }}
                </option>
              </select>
            </div>
          </div>

          <button type="submit" class="w-full bg-[#3182ce] hover:bg-[#2b6cb0] text-white py-4 rounded-xl font-bold uppercase tracking-widest text-sm transition-all shadow-lg shadow-blue-100 flex items-center justify-center gap-3">
            <i class="fas fa-check-circle"></i>
            {{ isEditing ? 'Lưu thay đổi' : 'Xác nhận đặt phòng' }}
          </button>
        </form>
      </div>
    </div>

    <!-- Modal Check-in -->
    <div v-if="showCheckInModal" class="fixed inset-0 z-[120] flex items-center justify-center p-6 bg-slate-900 bg-opacity-40 backdrop-blur-sm overflow-y-auto">
      <div class="bg-white rounded-[2.5rem] max-w-lg w-full shadow-2xl animate-in fade-in zoom-in duration-300 my-auto overflow-hidden">
        <div class="bg-indigo-600 p-8 text-white relative">
          <button @click="closeCheckInModal" class="absolute top-6 right-6 w-10 h-10 flex items-center justify-center rounded-xl bg-white bg-opacity-10 hover:bg-white hover:bg-opacity-20 transition-all">
            <i class="fas fa-times"></i>
          </button>
          <h3 class="text-2xl font-bold uppercase tracking-tight">Thủ tục Check-in</h3>
          <p class="text-white text-opacity-70 text-sm font-medium mt-1">BK{{ String(selectedBooking?.id).padStart(8, '0') }} - P.{{ selectedBooking?.room?.roomNumber }}</p>
        </div>

        <form @submit.prevent="handleCheckIn" class="p-8 space-y-6">
          <div class="space-y-2">
            <label class="block text-[10px] font-bold uppercase tracking-widest text-gray-400 ml-1">Số CCCD / Passport</label>
            <input v-model="checkInForm.guestIdNumber" type="text" required placeholder="Nhập số định danh..." class="w-full bg-gray-50 border border-gray-100 rounded-xl px-4 py-3 outline-none focus:border-indigo-400 transition-all font-bold text-sm">
          </div>

          <div class="space-y-2">
            <label class="block text-[10px] font-bold uppercase tracking-widest text-gray-400 ml-1">Hình ảnh giấy tờ</label>
            <div class="w-full h-48 bg-gray-50 border-2 border-dashed border-gray-200 rounded-2xl flex flex-col items-center justify-center overflow-hidden relative group">
              <video v-if="isCameraActive" ref="cameraRef" autoplay playsinline class="w-full h-full object-cover"></video>
              <template v-else-if="checkInForm.guestIdImageUrl">
                <img :src="checkInForm.guestIdImageUrl" class="w-full h-full object-cover">
                <button @click="clearImage" type="button" class="absolute top-3 right-3 w-8 h-8 bg-rose-500 text-white rounded-lg flex items-center justify-center shadow-lg">
                  <i class="fas fa-times text-xs"></i>
                </button>
              </template>
              <template v-else>
                <i class="fas fa-id-card text-3xl text-gray-200 mb-2"></i>
                <span class="text-[10px] font-bold text-gray-400 uppercase tracking-widest">Chưa có ảnh</span>
              </template>
            </div>
            
            <div class="flex gap-2">
              <input type="file" ref="fileInputRef" accept="image/*" @change="handleFileSelect" class="hidden">
              <button @click="openFilePicker" type="button" class="flex-1 bg-gray-100 hover:bg-gray-200 text-gray-600 py-3 rounded-xl font-bold text-[10px] uppercase tracking-widest transition-all">Upload</button>
              <button v-if="!isCameraActive" @click="startCamera" type="button" class="flex-1 bg-indigo-50 hover:bg-indigo-100 text-indigo-600 py-3 rounded-xl font-bold text-[10px] uppercase tracking-widest transition-all">Camera</button>
              <button v-else @click="capturePhoto" type="button" class="flex-1 bg-emerald-500 hover:bg-emerald-600 text-white py-3 rounded-xl font-bold text-[10px] uppercase tracking-widest transition-all">Chụp ảnh</button>
            </div>
          </div>

          <button type="submit" class="w-full bg-indigo-600 hover:bg-indigo-700 text-white py-4 rounded-xl font-bold uppercase tracking-widest text-sm transition-all shadow-lg shadow-indigo-100 flex items-center justify-center gap-3">
            <i class="fas fa-user-check"></i>
            Hoàn tất nhận phòng
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../api/axios'
import AdminSidebar from '../components/AdminSidebar.vue'
import StaffSidebar from '../components/StaffSidebar.vue'

const router = useRouter()
const isAdmin = computed(() => {
  const user = JSON.parse(localStorage.getItem('user') || '{}')
  return user.role === 'ADMIN'
})

const staffName = computed(() => {
  const user = JSON.parse(localStorage.getItem('user') || '{}')
  return user.fullName || 'Nhân viên'
})

const bookings = ref([])
const availableRooms = ref([])
const loading = ref(true)
const showModal = ref(false)
const showDetailModal = ref(false)
const showCheckInModal = ref(false)
const isEditing = ref(false)
const selectedBooking = ref(null)

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

// Tabs Logic
const tabItems = [
  { label: 'Tất cả', value: '' },
  { label: 'Đã đặt', value: 'CONFIRMED' },
  { label: 'Đã nhận phòng', value: 'CHECKED_IN' },
  { label: 'Đã trả phòng', value: 'COMPLETED' },
  { label: 'Đã hủy', value: 'CANCELLED' }
]

const filters = ref({
  search: '',
  timeRange: 'all',
  status: ''
})

const fetchBookings = async () => {
  loading.value = true
  try {
    const response = await axios.get('/bookings')
    bookings.value = response.data
  } catch (error) {
    console.error('Error fetching bookings:', error)
  } finally {
    loading.value = false
  }
}

const filteredBookings = computed(() => {
  return bookings.value.filter(b => {
    const searchStr = filters.value.search.toLowerCase()
    const matchSearch = !filters.value.search || 
      (b.guestFullName || b.customer?.fullName || '').toLowerCase().includes(searchStr) ||
      (b.guestPhone || b.customer?.phone || '').includes(searchStr) ||
      String(b.id).includes(searchStr)
    
    const matchStatus = !filters.value.status || b.status === filters.value.status
    
    // Time range filter (simplified)
    const matchTime = true 
    
    return matchSearch && matchStatus && matchTime
  })
})

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const d = new Date(dateStr)
  return d.toLocaleDateString('vi-VN')
}

const formatDateTime = (dateStr) => {
  if (!dateStr) return '--'
  const d = new Date(dateStr)
  return d.toLocaleString('vi-VN', { 
    day: '2-digit', month: '2-digit', year: 'numeric',
    hour: '2-digit', minute: '2-digit'
  })
}

const formatCurrency = (amount) => {
  if (!amount) return '0 đ'
  return new Intl.NumberFormat('vi-VN').format(amount * 25000) + ' đ'
}

const calculateNights = (checkIn, checkOut) => {
  if (!checkIn || !checkOut) return 0
  const start = new Date(checkIn)
  const end = new Date(checkOut)
  const diff = end - start
  return Math.max(1, Math.ceil(diff / (1000 * 60 * 60 * 24)))
}

const formatStatus = (status) => {
  switch (status) {
    case 'PENDING': return 'Chờ xác nhận'
    case 'CONFIRMED': return 'Đã đặt'
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

const getStatusDotClass = (status) => {
  switch (status) {
    case 'CONFIRMED': return 'bg-blue-500'
    case 'CHECKED_IN': return 'bg-emerald-500'
    case 'COMPLETED': return 'bg-gray-400'
    case 'CANCELLED': return 'bg-rose-500'
    default: return 'bg-amber-500'
  }
}

const formatPaymentStatus = (status) => {
  return status === 'PAID' ? 'Đã thanh toán' : 'Chưa thanh toán'
}

const getPaymentStatusStyles = (status) => {
  return status === 'PAID' 
    ? 'bg-emerald-50 text-emerald-600' 
    : 'bg-amber-50 text-amber-600'
}

const openDetailModal = (booking) => {
  selectedBooking.value = booking
  showDetailModal.value = true
}

const fetchRooms = async () => {
  try {
    const response = await axios.get('/rooms')
    availableRooms.value = response.data.filter(r => r.status === 'AVAILABLE')
  } catch (error) {
    console.error('Error fetching rooms:', error)
  }
}

const form = ref({
  guestFullName: '',
  guestContact: '',
  checkInDate: '',
  checkOutDate: '',
  roomId: '',
  numberOfGuests: 1
})

const checkInForm = ref({
  guestIdNumber: '',
  guestIdImageUrl: ''
})
const isCameraActive = ref(false)
const cameraRef = ref(null)
const fileInputRef = ref(null)
let cameraStream = null
const showInvoiceModal = ref(false)
const currentInvoice = ref(null)

const openCreateModal = () => {
  isEditing.value = false
  form.value = {
    guestFullName: '',
    guestContact: '',
    checkInDate: '',
    checkOutDate: '',
    roomId: '',
    numberOfGuests: 1
  }
  showModal.value = true
  fetchRooms()
}

const openUpdateModal = (booking) => {
  isEditing.value = true
  selectedBooking.value = booking
  form.value = {
    guestFullName: booking.guestFullName || booking.customer?.fullName,
    guestContact: booking.guestPhone || booking.customer?.phone || booking.guestEmail || booking.customer?.email,
    checkInDate: booking.checkInDate,
    checkOutDate: booking.checkOutDate,
    roomId: booking.room?.id,
    numberOfGuests: booking.numberOfGuests || 1
  }
  showModal.value = true
  fetchRooms()
  if (booking.room && !availableRooms.value.find(r => r.id === booking.room.id)) {
    availableRooms.value.push(booking.room)
  }
}

const closeModal = () => {
  showModal.value = false
  selectedBooking.value = null
}

const handleSubmit = async () => {
  try {
    const user = JSON.parse(localStorage.getItem('user'))
    const payload = {
      ...form.value,
      userId: user.id,
      guestPhone: form.value.guestContact.includes('@') ? '' : form.value.guestContact,
      guestEmail: form.value.guestContact.includes('@') ? form.value.guestContact : ''
    }

    if (isEditing.value) {
      await axios.put(`/bookings/${selectedBooking.value.id}/update`, payload)
      if (form.value.roomId !== selectedBooking.value.room?.id) {
        await axios.put(`/bookings/${selectedBooking.value.id}/change-room?newRoomId=${form.value.roomId}`)
      }
    } else {
      await axios.post('/bookings', payload)
    }
    
    showModal.value = false
    fetchBookings()
    alert(isEditing.value ? 'Cập nhật thành công!' : 'Đã tạo booking thành công!')
  } catch (error) {
    alert('Lỗi: ' + (error.response?.data?.message || 'Không thể thực hiện'))
  }
}

const openCheckInModal = (booking) => {
  selectedBooking.value = booking
  checkInForm.value = {
    guestIdNumber: booking.guestIdNumber || '',
    guestIdImageUrl: booking.guestIdImageUrl || ''
  }
  showCheckInModal.value = true
}

const closeCheckInModal = () => {
  showCheckInModal.value = false
  stopCamera()
}

const startCamera = async () => {
  try {
    cameraStream = await navigator.mediaDevices.getUserMedia({ video: true })
    isCameraActive.value = true
    await nextTick()
    if (cameraRef.value) {
      cameraRef.value.srcObject = cameraStream
    }
  } catch (error) {
    console.error('Lỗi khi mở camera:', error)
    alert('Không thể mở camera. Vui lòng kiểm tra quyền truy cập.')
  }
}

const stopCamera = () => {
  if (cameraStream) {
    cameraStream.getTracks().forEach(track => track.stop())
    cameraStream = null
  }
  isCameraActive.value = false
}

const capturePhoto = () => {
  if (!cameraRef.value) return
  
  const canvas = document.createElement('canvas')
  canvas.width = cameraRef.value.videoWidth
  canvas.height = cameraRef.value.videoHeight
  const ctx = canvas.getContext('2d')
  ctx.drawImage(cameraRef.value, 0, 0)
  checkInForm.value.guestIdImageUrl = canvas.toDataURL('image/jpeg')
  stopCamera()
}

const openFilePicker = () => {
  fileInputRef.value?.click()
}

const handleFileSelect = (event) => {
  const file = event.target.files[0]
  if (file) {
    const reader = new FileReader()
    reader.onload = (e) => {
      checkInForm.value.guestIdImageUrl = e.target.result
    }
    reader.readAsDataURL(file)
  }
}

const clearImage = () => {
  checkInForm.value.guestIdImageUrl = ''
}

const handleCheckIn = async () => {
  try {
    await axios.put(`/bookings/${selectedBooking.value.id}/check-in`, checkInForm.value)
    showCheckInModal.value = false
    stopCamera()
    fetchBookings()
    alert('Khách đã nhận phòng thành công!')
  } catch (error) {
    alert('Lỗi: ' + (error.response?.data?.message || 'Check-in thất bại'))
  }
}

const handleCheckOut = async (id) => {
  if (!confirm('Xác nhận trả phòng (Check-out) cho khách?')) return
  try {
    const response = await axios.put(`/invoices/booking/${id}/check-out`)
    currentInvoice.value = response.data
    fetchBookings()
    showInvoiceModal.value = true
  } catch (error) {
    alert('Lỗi: ' + (error.response?.data?.message || 'Check-out thất bại'))
  }
}

const openInvoiceModal = async (booking) => {
  try {
    const response = await axios.get(`/invoices/booking/${booking.id}`)
    currentInvoice.value = response.data
    showInvoiceModal.value = true
  } catch (error) {
    alert('Lỗi: ' + (error.response?.data?.message || 'Không tìm thấy hóa đơn'))
  }
}

const handleCancel = async (id) => {
  if (!confirm('Xác nhận hủy đặt phòng?')) return
  try {
    const user = JSON.parse(localStorage.getItem('user'))
    await axios.put(`/bookings/${id}/cancel?userId=${user.id}&isAdmin=true`)
    fetchBookings()
  } catch (error) {
    console.error('Cancel failed:', error)
  }
}

const printInvoice = () => {
  window.print()
}

onMounted(() => {
  fetchBookings()
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

/* Hide some elements during print */
@media print {
  aside, header, .flex-row.gap-4, .border-b.px-2, .pagination {
    display: none !important;
  }
  main {
    padding: 0 !important;
  }
  .bg-slate-900 {
    background: white !important;
    position: static !important;
  }
  .max-w-2xl {
    max-width: 100% !important;
    box-shadow: none !important;
  }
}
</style>
