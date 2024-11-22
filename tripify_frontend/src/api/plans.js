import myaxios from "@/util/axios-common.js";

function todaySchedule(user_id, success, fail) {
  myaxios.post("/today", { user_id: user_id }).then(success).catch(fail);
}

function plans(user_id, success, fail) {
  myaxios.post("/plans", { user_id: user_id }).then(success).catch(fail);
}

function addPlan(plan, success, fail) {
  myaxios.post("/plans/add", plan).then(success).catch(fail);
}

function deletePlan(plan, success, fail) {
  //console.log("delete plan", plan);
  myaxios.delete("/plans/delete", { data: plan }).then(success).catch(fail);
}

function getPlanDetail(plan, success, fail) {
  //console.log("getPlanDetail: ", plan);
  myaxios.post("/plans/detail", plan).then(success).catch(fail);
}

function addPlanDetail(plan, success, fail) {
  myaxios.post("/plans/detail/add", plan).then(success).catch(fail);
}

function addNewActivity(plan, success, fail) {
  myaxios.post("/plans/detail/addNewActivity", plan).then(success).catch(fail);
}

function updateActivity(plan, success, fail) {
  myaxios.put("/plans/detail/updateActivity", plan).then(success).catch(fail);
}

function deletePlanDetail(plan, success, fail) {
  myaxios.delete("/plans/detail/delete", { data: plan }).then(success).catch(fail);
}

export {
  todaySchedule,
  plans,
  addPlan,
  deletePlan,
  getPlanDetail,
  addPlanDetail,
  addNewActivity,
  updateActivity,
  deletePlanDetail,
};
