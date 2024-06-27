import { createRouter, createWebHistory } from 'vue-router';

import Home from '../views/Home.vue';
import LogInSignUp from '../views/LogInSignUp.vue';
import Occurrence from '../views/Occurrence.vue';
import LostFound from '../views/LostFound.vue';
import Meeting from '../views/Meeting.vue';
import Reserve from '../views/Reserve.vue';
import CalendarReserve from '../views/CalendarReserve.vue';
import AuthService from '../services/AuthService'; 

const routes = [
  {
    path: '/',
    name: 'LogInSignUp',
    component: LogInSignUp
  },
  {
    path: '/home',
    name: 'Home',
    component: Home,
    meta: { requiresAuth: true } 
  },
  {
    path: '/occurrence',
    name: 'Occurrence',
    component: Occurrence,
    meta: { requiresAuth: true } 
  },
  {
    path: '/lostfound',
    name: 'LostFound',
    component: LostFound,
    meta: { requiresAuth: true } 
  },
  {
    path: '/meeting',
    name: 'Meeting',
    component: Meeting,
    meta: { requiresAuth: true } 
  },
  {
    path: '/reserve',
    name: 'Reserve',
    component: Reserve,
    meta: { requiresAuth: true } 
  },
  {
    path: '/calendar',
    name: 'CalendarReserve',
    component: CalendarReserve,
    meta: { requiresAuth: true } 
  }
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
});

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!AuthService.isAuthenticated()) {
      next({
        path: '/',
        query: { redirect: to.fullPath }
      });
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router;
