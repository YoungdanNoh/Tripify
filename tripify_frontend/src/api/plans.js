import myaxios from "@/util/axios-common.js";

function plans(user_id, success, fail) {
  myaxios.post("/plans", { user_id: user_id }).then(success).catch(fail);
}

function addPlan(plan, success, fail) {
  myaxios.post("/plans/add", plan).then(success).catch(fail);
}

function getPlanDetail(plan, success, fail) {
  console.log("getPlanDetail: ", plan);
  myaxios.post("/plans/detail", plan).then(success).catch(fail);
}

export { plans, addPlan, getPlanDetail };
