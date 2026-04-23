const defaultAmenities = ['Dieu hoa', 'Wi-Fi', 'TV', 'Phong tam rieng']

const roomSpecMap = {
  Standard: {
    Single: {
      shortDescription: 'Phòng Standard gọn gàng, phù hợp cho khách đi một mình cần sự riêng tư và tiết kiệm.',
      overview: 'Lua chon gon gang, tiet kiem va van day du tien nghi cho khach di mot minh.',
      bedType: '1 giuong don',
      capacityText: '1 nguoi',
      capacityValue: 1,
      nightlyRate: 45,
      amenities: defaultAmenities,
      design: 'Nho gon, am cung, toi uu cho nghi ngan ngay',
      suitableFor: 'Khach di cong tac hoac luu tru ngan ngay'
    },
    Double: {
      shortDescription: 'Phòng Standard đôi cân bằng giữa tiện nghi, thoải mái và chi phí hợp lý cho 2 khách.',
      overview: 'Lua chon ly tuong cho khach tim kiem khong gian tien nghi voi chi phi hop ly.',
      bedType: '1 giuong doi',
      capacityText: '1 - 2 nguoi',
      capacityValue: 2,
      nightlyRate: 50,
      amenities: defaultAmenities,
      design: 'Gon gang, am cung, tao cam giac thoai mai',
      suitableFor: 'Cap doi hoac khach luu tru ngan ngay'
    },
    Twin: {
      shortDescription: 'Phòng Standard Twin tiện lợi với 2 giường riêng, phù hợp bạn bè hoặc đồng nghiệp.',
      overview: 'Phong tieu chuan linh hoat, phu hop cho 2 khach can ngu rieng.',
      bedType: '2 giuong don',
      capacityText: '2 nguoi',
      capacityValue: 2,
      nightlyRate: 55,
      amenities: defaultAmenities,
      design: 'Bo tri can doi, de su dung, de nghi ngoi',
      suitableFor: 'Ban be, dong nghiep hoac khach di cung nguoi than'
    }
  },
  Deluxe: {
    Double: {
      shortDescription: 'Phòng Deluxe Double rộng rãi, hiện đại và thích hợp cho kỳ nghỉ thoải mái hơn.',
      overview: 'Khong gian cao cap hon, rong rai hon va phu hop cho ky nghi thoai mai.',
      bedType: '1 giuong doi lon',
      capacityText: '1 - 2 nguoi',
      capacityValue: 2,
      nightlyRate: 90,
      amenities: [...defaultAmenities, 'Ban tra', 'Mini bar'],
      design: 'Hien dai, sang trong va thong thoang',
      suitableFor: 'Cap doi, khach nghi duong hoac cong tac cao cap'
    },
    Twin: {
      shortDescription: 'Phòng Deluxe Twin mang lại không gian thoải mái với 2 giường riêng sang trọng.',
      overview: 'Phong Deluxe bo tri 2 giuong rieng, dam bao su tien nghi cho 2 khach.',
      bedType: '2 giuong don lon',
      capacityText: '2 nguoi',
      capacityValue: 2,
      nightlyRate: 95,
      amenities: [...defaultAmenities, 'Ban tra', 'Mini bar'],
      design: 'Thanh lich, can doi, de nghi ngo dai ngay',
      suitableFor: 'Dong nghiep, ban be hoac doi khach can su rieng tu'
    },
    'Ocean View': {
      shortDescription: 'Phòng Deluxe Ocean View nổi bật với tầm nhìn biển thoáng đãng và cảm giác nghỉ dưỡng.',
      overview: 'Phong Deluxe co tam nhin bien rong mo, tao cam giac thu gian va cao cap.',
      bedType: '1 giuong doi lon',
      capacityText: '1 - 2 nguoi',
      capacityValue: 2,
      nightlyRate: 110,
      amenities: [...defaultAmenities, 'Ban tra', 'Mini bar', 'Ban cong view bien'],
      design: 'Sang trong, mo rong tam nhin, nhieu anh sang',
      suitableFor: 'Cap doi va khach nghi duong yeu thich view bien'
    },
    'City View': {
      shortDescription: 'Phòng Deluxe City View có không gian trẻ trung với góc nhìn thành phố nổi bật.',
      overview: 'Phong Deluxe huong pho voi khong gian hien dai va tam nhin thanh pho dep.',
      bedType: '1 giuong doi lon',
      capacityText: '1 - 2 nguoi',
      capacityValue: 2,
      nightlyRate: 100,
      amenities: [...defaultAmenities, 'Ban tra', 'Mini bar', 'Cua so lon'],
      design: 'Hien dai, tre trung, noi bat tam nhin do thi',
      suitableFor: 'Khach cong tac hoac du lich thich ngam thanh pho'
    }
  },
  Suite: {
    Junior: {
      shortDescription: 'Suite Junior mang lại trải nghiệm cao cấp với không gian rộng và riêng tư hơn.',
      overview: 'Phong Suite co khong gian mo rong, cao cap va rieng tu hon Deluxe.',
      bedType: '1 giuong doi lon',
      capacityText: '2 nguoi',
      capacityValue: 2,
      nightlyRate: 180,
      amenities: [...defaultAmenities, 'Phong khach nho', 'Bon tam', 'Mini bar'],
      design: 'Sang trong, mem mai, tao trai nghiem nghi duong',
      suitableFor: 'Cap doi hoac khach muon nang cap trai nghiem luu tru'
    },
    Executive: {
      shortDescription: 'Suite Executive phù hợp khách doanh nhân cần không gian tiếp khách và nghỉ ngơi đẳng cấp.',
      overview: 'Suite danh cho khach can khong gian tiep khach va nghi ngo thoai mai.',
      bedType: '1 giuong king',
      capacityText: '2 nguoi',
      capacityValue: 2,
      nightlyRate: 220,
      amenities: [...defaultAmenities, 'Phong khach rieng', 'Ban lam viec', 'Bon tam'],
      design: 'Dang cap, chuyen nghiep, toi uu cho nghi duong va cong tac',
      suitableFor: 'Doanh nhan hoac khach luu tru dai ngay'
    },
    Family: {
      shortDescription: 'Suite Family rộng rãi, thân thiện cho gia đình hoặc nhóm khách nhỏ lưu trú cùng nhau.',
      overview: 'Suite rong rai cho gia dinh voi bo tri tien nghi va de su dung.',
      bedType: '1 giuong doi lon + 1 giuong phu',
      capacityText: '3 - 4 nguoi',
      capacityValue: 4,
      nightlyRate: 260,
      amenities: [...defaultAmenities, 'Khu vuc sinh hoat chung', 'Bon tam', 'Mini bar'],
      design: 'Am cung, rong rai, than thien voi gia dinh',
      suitableFor: 'Gia dinh co tre em hoac nhom khach nho'
    },
    Presidential: {
      shortDescription: 'Suite Presidential là lựa chọn cao cấp nhất với không gian lớn và tiện nghi hạng sang.',
      overview: 'Hang phong cao cap nhat voi khong gian lon, noi that sang trong va dich vu uu tien.',
      bedType: '1 giuong king cao cap',
      capacityText: '2 - 4 nguoi',
      capacityValue: 4,
      nightlyRate: 320,
      amenities: [...defaultAmenities, 'Phong khach lon', 'Bon tam cao cap', 'Mini bar', 'Khu tiep khach'],
      design: 'Dang cap thuong luu, tinh te va rat rieng tu',
      suitableFor: 'Khach VIP, nghi duong cao cap hoac dip dac biet'
    }
  }
}

const categoryDefaults = {
  Standard: roomSpecMap.Standard.Double,
  Deluxe: roomSpecMap.Deluxe.Double,
  Suite: roomSpecMap.Suite.Junior
}

export const getRoomSpec = (room) => {
  const category = room?.category || 'Standard'
  const type = room?.type
  const spec = roomSpecMap[category]?.[type] || categoryDefaults[category] || categoryDefaults.Standard

  return {
    ...spec,
    category,
    type: type || 'Standard',
    amenities: spec.amenities || defaultAmenities,
    capacityValue: room?.capacity || spec.capacityValue || 2,
    shortDescription: spec.shortDescription || spec.overview,
    nightlyRate: spec.nightlyRate || room?.price || 0
  }
}

export const getRoomPrice = (room) => {
  return getRoomSpec(room).nightlyRate || room?.price || 0
}
