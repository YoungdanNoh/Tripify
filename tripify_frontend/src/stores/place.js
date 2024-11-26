import { ref, computed } from "vue";
import { defineStore } from "pinia";
import {
  sigun,
  gugun,
  type,
  place,
  addLike,
  removeLike,
  getLikeCount,
  getLikedPlaces,
  getPlaceByPlaceId,
} from "@/api/place";

export const usePlaceStore = defineStore("place", () => {
  //1. data
  // const sigun = ref([]); //시, 군 정보
  const typeList = ref([]); //관광지 타입 정보
  const sidoList = ref([]); //시도 목록
  const gugunList = ref([{ text: "구군선택", value: "" }]); //구군 목록
  const places = ref([]); //관광지 정보
  const navigation = ref([]);
  const pgno = ref(0); //새로운 지역을 탐색하는지의 여부
  const highlightedPlaceId = ref(null); // 강조된 placeId
  const selectedPlace = ref(null); // 현재 선택된 장소 정보

  //2. getters
  const getSigun = computed(() => sigun.value);
  const getTypeList = computed(() => typeList.value);
  const getSidoList = computed(() => sidoList.value);
  const getGugunList = computed(() => gugunList.value);
  const getPlaces = computed(() => places.value);

  const setPlaces = (places) => {
    getPlaces.value = places;
  };

  const setHighlightedPlaceId = (placeId) => {
    highlightedPlaceId.value = placeId;
  };

  const setSelectedPlace = (placeId) => {
    console.log("here!:", placeId);
    try {
      getPlaceByPlaceId(
        placeId,
        (response) => {
          console.log("loadig...");

          console.log("successfully fetched place details");
          console.log(response.data);

          selectedPlace.value = response.data;
          console.log(selectedPlace.value);
        },
        () => {
          console.log("err");
        }
      ); // `place_id`로 장소 정보를 가져옴
    } catch (error) {
      console.error("Failed to fetch place details:", error);
    }
  };

  const clearSelectedPlace = () => {
    selectedPlace.value = null;
  };

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
        pgno.value = location.pgno;

        let options = [];
        data.attrList.forEach((place) => {
          options.push({
            place_id: place.placeId,
            title: place.title,
            latitude: place.latitude,
            longitude: place.longitude,
            addr1: place.addr1,
            addr2: place.addr2,
            first_image1: place.firstImage1,
            count: place.count,
            likeStatus: place.likeStatus,
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

  const addLikePlace = async (placeId, userId) => {
    try {
      const requestData = { placeId, userId };
      await addLike(
        requestData,
        () => console.log(`Successfully added like for place ${placeId}`),
        (error) => console.error("Failed to add like", error)
      );
    } catch (error) {
      console.error("Error in addLikePlace:", error);
    }
  };

  const removeLikePlace = async (placeId, userId) => {
    try {
      const requestData = { placeId, userId };
      await removeLike(
        requestData,
        () => console.log(`Successfully removed like for place ${placeId}`),
        (error) => console.error("Failed to remove like", error)
      );
    } catch (error) {
      console.error("Error in removeLikePlace:", error);
    }
  };

  const getLikeCountPlace = async (placeId) => {
    try {
      let likeCount = 0;
      await getLikeCount(
        placeId,
        (response) => {
          likeCount = response.data;
          console.log(`Like count for place ${placeId}: ${likeCount}`);
        },
        (error) => console.error("Failed to get like count", error)
      );
      return likeCount;
    } catch (error) {
      console.error("Error in getLikeCountPlace:", error);
      return 0;
    }
  };

  const getLikedPlacesOfUserId = async (userId) => {
    try {
      const userData = { userId };
      let likedPlaces = [];
      await getLikedPlaces(
        userData,
        (response) => {
          likedPlaces = response.data;
          console.log(`Liked places for user ${userId}:`, likedPlaces);
        },
        (error) => console.error("Failed to get liked places", error)
      );
      return likedPlaces;
    } catch (error) {
      console.error("Error in getLikedPlacesOfUserId:", error);
      return [];
    }
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
    highlightedPlaceId,
    selectedPlace,
    listSigun,
    listGugun,
    listType,
    listPlaces,
    resetPlaces,
    addLikePlace,
    removeLikePlace,
    getLikeCountPlace,
    getLikedPlacesOfUserId,
    setPlaces,
    setHighlightedPlaceId,
    setSelectedPlace,
    clearSelectedPlace,
  };
});
