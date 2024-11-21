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

export { sigun, gugun, type, place };
