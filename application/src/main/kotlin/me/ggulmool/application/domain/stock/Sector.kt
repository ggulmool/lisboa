package me.ggulmool.application.domain.stock

enum class Sector(val no: Int, val industryName: String, val multiple: Int) {

    DEFAULT(99999, "섹터기본", Multiple.GENERAL),
    STARTUP_INVESTMENT(277, "창업투자", Multiple.GENERAL),
    STATIONERY(332, "문구류", Multiple.GENERAL),
    NON_LIFE_INSURANCE(315, "손해보험", Multiple.FINANCE_STOCK),
    LIFE_INSURANCE(330, "생명보험", Multiple.FINANCE_STOCK),
    NON_FERROUS_METAL(322, "비철금속", Multiple.GENERAL),
    BANK(301, "은행", Multiple.FINANCE_STOCK),
    STOCK(321, "증권", Multiple.FINANCE_STOCK),
    TOBACCO(275, "담배", Multiple.GENERAL),
    OTHER_FINANCE(319, "기타금융", Multiple.FINANCE_STOCK),
    STEEL(304, "철강", Multiple.GENERAL),
    REAL_ESTATE(280, "부동산", Multiple.GENERAL),
    HOME_APPLIANCE(297, "가정용품", Multiple.GENERAL),
    CHEMISTRY(272, "화학", Multiple.GENERAL),
    WIRELESS_COMMUNICATION_SERVICE(333, "무선통신서비스", Multiple.GENERAL),
    LEISURE_EQUIPMENT(271, "레저용장비와제품", Multiple.GENERAL),
    AEROSPACE_AND_DEFENSE(284, "우주항공과국방", Multiple.GENERAL),
    COMMERCIAL_SERVICES(324, "상업서비스와공급품", Multiple.GENERAL),
    CONSTRUCTION(279, "건설", Multiple.CONSTRUCTION_SHIPPING),
    COMPUTERS_AND_PERIPHERALS(293, "컴퓨터와주변기기", Multiple.GENERAL),
    COMPLEX_UTILITY(331, "복합유틸리티", Multiple.GENERAL),
    TEXTILES_CLOTHING_SHOES_LUXURY_GOODS(274, "섬유의류신발호화품", Multiple.GENERAL),
    SHIPPING_COMPANY(323, "해운사", Multiple.CONSTRUCTION_SHIPPING),
    CONGLOMERATE(276, "복합기업", Multiple.GENERAL),
    TRANSPORT_INFRASTRUCTURE(296, "운송인프라", Multiple.GENERAL),
    FOOD(268, "식품", Multiple.GENERAL),
    ADVERTISING(310, "광고", Multiple.GENERAL),
    EDUCATION_SERVICE(290, "교육서비스", Multiple.GENERAL),
    RETAIL_FOOD_BASIC_FOOD_PRODUCTS(302, "식품과기본식료품소매", Multiple.GENERAL),
    DEPARTMENT_STORES(264, "백화점과일반상점", Multiple.GENERAL),
    HOUSEHOD_APPLIANCES(298, "가정용기기와용품", Multiple.GENERAL),
    DISPLAY_PANEL(327, "디스플레이패널", Multiple.GENERAL),
    INTERNET_AND_CATALOG_RETAIL(308, "인터넷과카탈로그소매", Multiple.GENERAL),
    AIRLINE(305, "항공사", Multiple.GENERAL),
    COSMETICS(266, "화장품", Multiple.GENERAL),
    TRADING_COMPANIES_AND_VENDORS(334, "무역회사와판매업체", Multiple.GENERAL),
    ETC(25, "기타", Multiple.GENERAL),
    AUTOMOTIVE_PARTS(270, "자동차부품", Multiple.GENERAL),
    MACHINE(299, "기계", Multiple.GENERAL),
    HANDSET(292, "핸드셋", Multiple.GENERAL),
    OIL_AND_GAS(313, "석유와가스", Multiple.GENERAL),
    DIVERSIFIED_COMMUNICATION_SERVICE(336, "다각화된통신서비스", Multiple.IT_INDUSTRY),
    OFFICE_ELECTRONICS(338, "사무용전자제품", Multiple.GENERAL),
    GAS_UTILITY(312, "가스유틸리티", Multiple.GENERAL),
    ROAD_AND_RAIL_TRANSPORTATION(329, "도로와철도운송", Multiple.GENERAL),
    PUBLISHING(314, "출판", Multiple.GENERAL),
    ELECTRIC_UTILITY(325, "전기유틸리티", Multiple.GENERAL),
    CONSTRUCTION_MATERIALS(289, "건축자재", Multiple.GENERAL),
    SOFTWARE(287, "소프트웨어", Multiple.SOFTWARE),
    SEMICONDUCTOR_AND_EQUIPMENT(278, "반도체와반도체장비", Multiple.SEMICONDUCTOR),
    CARD(337, "카드", Multiple.GENERAL),
    FURNITURE(303, "가구", Multiple.GENERAL),
    PACKING_MATERIAL(311, "포장재", Multiple.GENERAL),
    ELECTRICAL_APPLIANCE(283, "전기제품", Multiple.SECONDARY_BATTERY),
    HOTEL_RESTAURANT_LEISURE(317, "호텔,레스토랑,레저", Multiple.GENERAL),
    HEALTH_CARE_EQUIPMENT(281, "건강관리장비와용품", Multiple.GENERAL),
    CAR(273, "자동차", Multiple.CAR),
    SELLERS(265, "판매업체", Multiple.GENERAL),
    PAPER_AND_WOOD(318, "종이와목재", Multiple.GENERAL),
    BROADCASTING_AND_ENTERTAINMENT(285, "방송과엔터테인먼트", Multiple.GENERAL),
    INTERACTIVE_MEDIA_AND_SERVICES(300, "양방향미디어와서비스", Multiple.SOFTWARE),
    GAME_ENTERTAINMENT(263, "게임엔터테인먼트", Multiple.GAME),
    IT_SERVICE(267, "IT서비스", Multiple.GENERAL),
    SHIPBUILDING(291, "조선", Multiple.CONSTRUCTION_SHIPPING),
    ELECTRICAL_EQUIPMENT(306, "전기장비", Multiple.GENERAL),
    BEVERAGE(309, "음료", Multiple.GENERAL),
    CONSTRUCTION_PRODUCTS(320, "건축제품", Multiple.GENERAL),
    ELECTRONIC_EQUIPMENT_AND_DEVICES(282, "전자장비와기기", Multiple.GENERAL),
    ELECTRONIC_PRODCUTS(307, "전자제품", Multiple.GENERAL),
    RESTRICTIONS(261, "제약", Multiple.BIO_RESRICTION),
    BIOTECHNOLOGY(286, "생물공학", Multiple.BIO_RESRICTION),
    DISPLAY_EQUIPMENT_AND_PARTS(269, "디스플레이장비및부품", Multiple.GENERAL),
    LIFE_SCIENCE_TOOLS_AND_SERVICES(262, "생명과학도구및서비스", Multiple.GENERAL),
    ENERGY_EQUIPMENT_AND_SERVICES(295, "에너지장비및서비스", Multiple.GENERAL),
    HEALTH_MANAGEMENT_TECHNOLOGY(288, "건강관리기술", Multiple.GENERAL),
    PROFESSIONAL_RETAIL(328, "전문소매", Multiple.GENERAL),
    AIR_FREIGHT_TRANSPORT_AND_LOGISTICS(326, "항공화물운송과물류", Multiple.GENERAL),
    COMMUNICATION_EQUIPMENT(294, "통신장비", Multiple.FIVEG),
    HEALTH_CARE_COMPANIES(316, "건강관리업체및서비스", Multiple.GENERAL),
    POWER_GENERATION_AND_ENERGY_TRANSACTION(335, "독립전력생산및에너지거래", Multiple.GENERAL);

    companion object {
        operator fun get(no: Int): Sector {
            return values().find { it.no == no } ?: throw IllegalArgumentException("잘못된 sectorNo 값입니다. : $no")
        }
    }
}

object Multiple {
    const val GENERAL = 10
    const val BIO_RESRICTION = 20 // 바이오/제약 20~40
    const val GAME = 15 // 게임
    const val FIVEG = 20 // 5G
    const val IT_INDUSTRY = 12 // IT산업 10~15
    const val SMARTPHONE = 10
    const val CAR = 9 // 자동차 8~10
    const val CONSTRUCTION_SHIPPING = 8 // 건설/해운업 8
    const val SOFTWARE = 15 // 소프트웨어 30~40
    const val SECONDARY_BATTERY = 20 // 2차전지 30~40
    const val MEDICAL_EQUIPMENT = 20 // 의료장비 20
    const val SEMICONDUCTOR = 10 // 반도체/장비 10
    const val WASTE = 10 // 폐기물 10
    const val ETC_MANUFACTURE = 9 // 기타제조업 9
    const val FINANCE_STOCK = 6 // 금융/증권 3~8
}