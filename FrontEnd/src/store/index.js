import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    notificationMessage: '',
    alertClass: ''
  },
  mutations: {
    setNotification(state, { message, type }) {
      state.notificationMessage = message;
      state.alertClass = type === 'success' ? 'alert-success' : 'alert-danger';
    },
    clearNotification(state) {
      state.notificationMessage = '';
      state.alertClass = '';
    }
  },
  actions: {
    setNotification({ commit }, payload) {
      commit('setNotification', payload);
      setTimeout(() => {
        commit('clearNotification');
      }, 4000); // Limpar a mensagem após 4 segundos
    }
  },
  getters: {
    notificationMessage: state => state.notificationMessage,
    alertClass: state => state.alertClass
  }
});
