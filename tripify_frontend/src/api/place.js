//각각의 컴포넌트(.vue)에서 요청하는 비동기 작업을 수행하는 함수들이 있는 파일
//axios-common.js 에서 export한 axios 객체를 import 해서 작업

import myaxios from "@/util/axios-common.js";

function sigun(success, fail) {
  myaxios.get("/place/sido").then(success).catch(fail);
}

function gugun(param, success, fail) {
  myaxios.get(`/place/gugun`, { params: param.sido }).then(success).catch(fail);
}

function type(success, fail) {
  myaxios.get("/place/type").then(success).catch(fail);
}

function place(location, success, fail) {
  myaxios.post("/place/search", JSON.stringify(location)).then(success).catch(fail);
}
function addLike(itemAndUserId, success, fail) {
  myaxios.post("/place/like", itemAndUserId).then(success).catch(fail);
}

function removeLike(itemAndUserId, success, fail) {
  myaxios.delete("/place/like", { data: itemAndUserId }).then(success).catch(fail);
}

function getLikeCount(placeId, success, fail) {
  myaxios.get(`/place/likecount/${placeId}`).then(success).catch(fail);
}

function getLikedPlaces(userId) {
  return myaxios
    .get("/place/likedplaces", { params: { userId: userId } })
    .then((response) => {
      console.log("res data:", response.data);
      return response.data;
    })
    .catch((error) => {
      console.error("Failed to fetch liked places:", error);
      throw error; // 오류를 던져서 밖에서 처리할 수 있게 합니다.
    });
}

function getPlaceByPlaceId(placeId, success, fail) {
  myaxios.get(`/place/${placeId}`).then(success).catch(fail);
}

export {
  sigun,
  gugun,
  type,
  place,
  addLike,
  removeLike,
  getLikeCount,
  getLikedPlaces,
  getPlaceByPlaceId,
};
