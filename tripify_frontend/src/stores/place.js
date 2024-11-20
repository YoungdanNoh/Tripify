import { ref, computed } from "vue";
import { defineStore } from "pinia";
import { sigun, gugun, type, place } from "@/api/place";

export const usePlaceStore = defineStore("place", () => {
  //1. data
  // const sigun = ref([]); //시, 군 정보
  const typeList = ref([]); //관광지 타입 정보
  const sidoList = ref([]); //시도 목록
  const gugunList = ref([{ text: "구군선택", value: "" }]); //구군 목록
  const places = ref([]); //관광지 정보
  const navigation = ref([]);
  const pgno = ref(0); //새로운 지역을 탐색하는지의 여부

  //2. getters
  const getSigun = computed(() => sigun.value);
  const getTypeList = computed(() => typeList.value);
  const getSidoList = computed(() => sidoList.value);
  const getGugunList = computed(() => gugunList.value);
  const getPlaces = computed(() => places.value);

  //3. actions
  const listSigun = async () => {
    sigun(
      ({ data }) => {
        let options = [];
        options.push({ text: "시도선택", value: "" });
        data.forEach((sido) => {
          options.push({ text: sido.sido_name, value: sido.sido_code });
        });
        sidoList.value = options;
        //console.log(sidoList.value);
      },
      (err) => {
        console.log(err);
      }
    );
  };

  const listGugun = async (val) => {
    gugun(
      { sido: val },
      ({ data }) => {
        let options = [];
        options.push({ text: "구군선택", value: "" });
        //console.log(data);
        data.forEach((gugun) => {
          options.push({ text: gugun.gugun_name, value: gugun.gugun_code });
        });
        gugunList.value = options;
      },
      (err) => {
        //console.log(err);
      }
    );
  };

  const listType = async () => {
    type(
      ({ data }) => {
        let options = [];
        options.push({ text: "관광지타입선택", value: "" });
        data.forEach((t) => {
          options.push({ text: t.content_type_name, value: t.content_type_id });
        });
        typeList.value = options;
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const listPlaces = async (location) => {
    place(
      location,
      ({ data }) => {
        //console.log(location);
        //reset.value = false;
        pgno.value = location.pgno;

        let options = [];
        data.attrList.forEach((place) => {
          console.log(place);
          options.push({
            place_id: place.placeId,
            title: place.title,
            latitude: place.latitude,
            longitude: place.longitude,
            addr1: place.addr1,
            addr2: place.addr2,
            first_image1: place.first_image1,
            count: place.count,
          });
        });
        places.value = [...places.value, ...options];
        //console.log('검색된 장소', places.value);

        navigation.value = {
          currentPage: data.navigation.currentPage,
          totalPageCount: data.navigation.totalPageCount,
        };
        //console.log('검색된 네비게이션', navigation.value);
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const resetPlaces = () => {
    //reset.value = true;
    places.value = [];
  };

  return {
    sidoList,
    typeList,
    gugunList,
    places,
    pgno,
    getSigun,
    getTypeList,
    getSidoList,
    getGugunList,
    getPlaces,
    listSigun,
    listGugun,
    listType,
    listPlaces,
    resetPlaces,
  };
});
