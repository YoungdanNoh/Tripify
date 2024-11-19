import axios from '@/util/axios-common.js';

const API_URL = "/member";

export const login = (email, password) => {
  return axios.post(`${API_URL}/login`, { email, password });
};

export const logout = () => {
  return axios.post(`${API_URL}/logout`);
};

export const signUp = ( userName, email, password) => {
  console.log(userName, email, password);
  
  return axios.post(API_URL, { userName, email, password });
};

export const fetchUserByEmail = (email) => {
  return axios.get(API_URL, {email});
};

export const updateUser = (userName, email, password, profileImage) => {
  return axios.put(API_URL, {userName, email, password, profileImage });
};

export const deleteUser = (email) => {
  return axios.delete(`${API_URL}?email=${email}`);
};

export const checkIdAvailability = (id) => {
  return axios.get(`${API_URL}/check-id`, { params: { id } });
};

export const findPassword = (email) => {
  return axios.post(`${API_URL}/find-password`, { email });
};
