import axios from 'axios';

const API_BASE_URL = 'http://localhost:3000/api/v1';

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add token to every request
apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Handle response errors
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export const authAPI = {
  login: (username, password) => 
    apiClient.post('/auth/login', { username, password }),
  logout: () => 
    apiClient.post('/auth/logout'),
  getCurrentUser: () => 
    apiClient.get('/auth/me'),
};

export const employeeAPI = {
  getAll: () => 
    apiClient.get('/employees'),
  getById: (id) => 
    apiClient.get(`/employees/${id}`),
  create: (employee) => 
    apiClient.post('/employees', employee),
  update: (id, employee) => 
    apiClient.put(`/employees/${id}`, employee),
  delete: (id) => 
    apiClient.delete(`/employees/${id}`),
  getTeamMembers: (teamLeadId) => 
    apiClient.get(`/employees/team-lead/${teamLeadId}`),
};

export const dashboardAPI = {
  getStats: () => 
    apiClient.get('/dashboard/stats'),
  getAttendanceStats: () => 
    apiClient.get('/dashboard/attendance-stats'),
  getProjectStats: () => 
    apiClient.get('/dashboard/project-stats'),
};

export default apiClient;
