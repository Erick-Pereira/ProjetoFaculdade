export default {
  isAuthenticated() {
    return !!localStorage.getItem('authToken');
  },

  login(token) {
    localStorage.setItem('authToken', token);
  },

  logout() {
    localStorage.removeItem('authToken');
  }
};
