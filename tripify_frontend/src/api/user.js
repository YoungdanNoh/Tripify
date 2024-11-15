import axios from "axios";

const API_URL = "/member";

export const login = (email, password) => {
  return axios.post(`${API_URL}/login`, { email, password });
};

export const logout = () => {
  return axios.post(`${API_URL}/logout`);
};

export const signUp = (userId, userName, email, password) => {
  return axios.post(API_URL, { userId, userName, email, password });
};

export const fetchUser = () => {
  return axios.get(API_URL);
};

export const updateUser = (userId, userName, email, password) => {
  return axios.put(API_URL, { userId, userName, email, password });
};

export const deleteUser = (userId) => {
  return axios.delete(API_URL, { data: { id: userId } });
};

export const checkIdAvailability = (id) => {
  return axios.get(`${API_URL}/check-id`, { params: { id } });
};

export const findPassword = (email) => {
  return axios.post(`${API_URL}/find-password`, { email });
};
