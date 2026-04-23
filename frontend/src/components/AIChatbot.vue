<template>
  <div class="fixed bottom-6 right-6 z-[100]">
    <!-- Chat Toggle Button -->
    <button 
      @click="toggleChat"
      class="w-16 h-16 bg-emerald-800 text-white rounded-full shadow-2xl flex items-center justify-center hover:bg-emerald-900 transition-all transform hover:scale-110 active:scale-95"
    >
      <i v-if="!isOpen" class="fas fa-comment-dots text-2xl"></i>
      <i v-else class="fas fa-times text-2xl"></i>
      <!-- Notification Badge -->
      <span v-if="!isOpen && unreadCount > 0" class="absolute -top-1 -right-1 bg-red-500 text-white text-[10px] font-bold w-5 h-5 rounded-full flex items-center justify-center border-2 border-white">
        {{ unreadCount }}
      </span>
    </button>

    <!-- Chat Window -->
    <div 
      v-if="isOpen"
      class="absolute bottom-20 right-0 w-[350px] md:w-[400px] bg-white rounded-[2rem] shadow-2xl border border-gray-100 overflow-hidden flex flex-col transition-all duration-300 transform origin-bottom-right"
    >
      <!-- Header -->
      <div class="bg-emerald-800 p-6 text-white">
        <div class="flex items-center gap-4">
          <div class="w-12 h-12 bg-white/20 rounded-2xl flex items-center justify-center">
            <i class="fas fa-robot text-xl"></i>
          </div>
          <div>
            <h3 class="font-black uppercase tracking-tighter text-lg leading-none">Grand AI Assistant</h3>
            <p class="text-[10px] text-emerald-200 uppercase tracking-widest mt-1 font-bold">Luôn sẵn sàng hỗ trợ bạn</p>
          </div>
        </div>
      </div>

      <!-- Messages Area -->
      <div class="flex-grow p-6 h-[400px] overflow-y-auto bg-gray-50 space-y-4" ref="messageContainer">
        <div 
          v-for="(msg, index) in messages" 
          :key="index"
          :class="['flex', msg.role === 'user' ? 'justify-end' : 'justify-start']"
        >
          <div 
            :class="[
              'max-w-[80%] p-4 rounded-2xl text-sm leading-relaxed shadow-sm',
              msg.role === 'user' 
                ? 'bg-emerald-800 text-white rounded-tr-none' 
                : 'bg-white text-gray-700 rounded-tl-none border border-gray-100'
            ]"
          >
            {{ msg.content }}
            <div v-if="msg.action" class="mt-3 pt-3 border-t border-white/20 flex flex-wrap gap-2">
              <button 
                v-for="btn in msg.actionButtons" 
                :key="btn.label"
                @click="handleAction(btn.action)"
                class="bg-white/20 hover:bg-white/30 text-white px-3 py-1.5 rounded-lg text-[10px] font-bold uppercase tracking-widest transition-all"
              >
                {{ btn.label }}
              </button>
            </div>
          </div>
        </div>
        <div v-if="isTyping" class="flex justify-start">
          <div class="bg-white p-4 rounded-2xl rounded-tl-none border border-gray-100 shadow-sm flex gap-1">
            <span class="w-1.5 h-1.5 bg-emerald-300 rounded-full animate-bounce"></span>
            <span class="w-1.5 h-1.5 bg-emerald-400 rounded-full animate-bounce [animation-delay:0.2s]"></span>
            <span class="w-1.5 h-1.5 bg-emerald-500 rounded-full animate-bounce [animation-delay:0.4s]"></span>
          </div>
        </div>
      </div>

      <!-- Input Area -->
      <div class="p-4 bg-white border-t border-gray-100">
        <form @submit.prevent="sendMessage" class="flex gap-2">
          <input 
            v-model="userInput"
            placeholder="Hỏi tôi bất cứ điều gì..."
            class="flex-grow bg-gray-50 border-0 rounded-xl px-4 py-3 text-sm focus:ring-2 focus:ring-emerald-800 outline-none transition-all"
          />
          <button 
            type="submit"
            :disabled="!userInput.trim()"
            class="w-12 h-12 bg-emerald-800 text-white rounded-xl flex items-center justify-center hover:bg-emerald-900 disabled:bg-gray-200 transition-all shadow-lg"
          >
            <i class="fas fa-paper-plane"></i>
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, nextTick, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../api/axios'

const router = useRouter()
const isOpen = ref(false)
const unreadCount = ref(1)
const isTyping = ref(false)
const userInput = ref('')
const messageContainer = ref(null)

const messages = ref([
  {
    role: 'bot',
    content: 'Xin chào! Tôi là trợ lý ảo của Grand Hotel. Tôi có thể giúp bạn tìm phòng, xem giá hoặc hỗ trợ đặt phòng. Bạn cần giúp gì không?',
    actionButtons: [
      { label: 'Xem giá phòng', action: 'check_price' },
      { label: 'Còn phòng không?', action: 'check_availability' },
      { label: 'Đặt phòng ngay', action: 'go_to_booking' }
    ]
  }
])

const toggleChat = () => {
  isOpen.value = !isOpen.value
  if (isOpen.value) {
    unreadCount.value = 0
    scrollToBottom()
  }
}

const scrollToBottom = async () => {
  await nextTick()
  if (messageContainer.value) {
    messageContainer.value.scrollTop = messageContainer.value.scrollHeight
  }
}

const handleAction = (action) => {
  switch (action) {
    case 'check_price':
      userInput.value = 'Giá phòng bao nhiêu?'
      sendMessage()
      break
    case 'check_availability':
      userInput.value = 'Còn phòng Deluxe không?'
      sendMessage()
      break
    case 'go_to_booking':
      router.push('/rooms')
      isOpen.value = false
      break
  }
}

const sendMessage = async () => {
  if (!userInput.value.trim()) return

  const userMsg = userInput.value
  messages.value.push({ role: 'user', content: userMsg })
  userInput.value = ''
  scrollToBottom()

  isTyping.value = true
  
  // Simulate AI delay
  setTimeout(async () => {
    let response = ''
    const lowerMsg = userMsg.toLowerCase()

    if (lowerMsg.includes('giá') || lowerMsg.includes('bao nhiêu')) {
      response = 'Tại Grand Hotel, chúng tôi có các hạng phòng: Standard từ 1.200.000đ, Deluxe từ 2.500.000đ và Suite từ 5.000.000đ mỗi đêm. Bạn muốn xem chi tiết hạng phòng nào không?'
    } else if (lowerMsg.includes('deluxe') && (lowerMsg.includes('còn') || lowerMsg.includes('trống'))) {
      try {
        const res = await axios.get('/rooms/search', { params: { category: 'Deluxe' } })
        const availableRooms = res.data.filter(r => r.status === 'AVAILABLE')
        if (availableRooms.length > 0) {
          response = `Hiện tại chúng tôi còn ${availableRooms.length} phòng Deluxe trống. Bạn có muốn đặt ngay không?`
        } else {
          response = 'Rất tiếc, hiện tại hạng phòng Deluxe đã hết. Bạn có muốn tham khảo sang hạng phòng Suite sang trọng hơn không?'
        }
      } catch (e) {
        response = 'Hiện tại tôi không thể kiểm tra trạng thái phòng trực tiếp, nhưng thông thường phòng Deluxe luôn có sẵn. Bạn có thể vào mục Loại Phòng để xem trực tiếp nhé!'
      }
    } else if (lowerMsg.includes('đặt phòng')) {
      response = 'Để đặt phòng, bạn có thể vào mục "Loại Phòng", chọn phòng ưng ý và nhấn "Đặt phòng ngay". Tôi có thể dẫn bạn đến đó!'
    } else {
      response = 'Cảm ơn bạn đã quan tâm! Tôi có thể giúp bạn kiểm tra giá phòng, tình trạng phòng trống hoặc hỗ trợ quy trình đặt phòng. Bạn có muốn hỏi thêm gì không?'
    }

    messages.value.push({ role: 'bot', content: response })
    isTyping.value = false
    scrollToBottom()
  }, 1000)
}
</script>

<style scoped>
@keyframes bounce {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-4px); }
}
.animate-bounce {
  animation: bounce 0.6s infinite;
}
</style>
