import { createRouter, createWebHistory } from 'vue-router';

import Home from '../views/Home.vue';
import LogInSignUp from '../views/LogInSignUp.vue';
import Occurrence from '../views/Occurrence.vue';
import LostFound from '../views/LostFound.vue';
import Meeting from '../views/Meeting.vue';
import Reserve from '../views/Reserve.vue';
import CalendarReserve from '../views/CalendarReserve.vue';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/login',
    name: 'LogInSignUp',
    component: LogInSignUp
  },
  {
    path: '/occurrence',
    name: 'Occurrence',
    component: Occurrence
  },
  {
    path: '/lostfound',
    name: 'LostFound',
    component: LostFound
  },
  {
    path: '/meeting',
    name: 'Meeting',
    component: Meeting
  },
  {
    path: '/reserve',
    name: 'Reserve',
    component: Reserve
  },
  {
    path: '/calendar',
    name: 'CalendarReserve',
    component: CalendarReserve
  }
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
});

export default router;
