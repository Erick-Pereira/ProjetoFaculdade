<template>
  <div>
    <div class="title">
      <h4>Reserva</h4>
      <h6>Condomínio >> Reservas >> Consulta</h6>
    </div>

    <div class="container">
      <div class="info">
        <div class="info-month">
          <i class="bx bx-calendar icon"><span>{{ monthNames[currentMonth] }} {{ currentYear }}</span></i>
        </div>

        <div class="info-section">
          <strong>Suas Reservas:</strong>
          <div class="info-item">
            <i class="bx bx-check-circle icon-verde"></i>
            <h5>Reservado</h5>
          </div>
        </div>

        <div class="info-section">
          <strong>Outras Reservas:</strong>
          <div class="info-item">
            <i class="bx bx-info-circle icon-azul"></i>
            <h5>Reservado</h5>
          </div>
        </div>

        <button id="new" @click="goToReserve">Reservas</button>
      </div>

      <div class="Calendario">
        <div class="calendar">
          <div class="header-calendar">
            <button @click="prevMonth(); getContent()">‹</button>
            <span>{{ monthNames[currentMonth] }} {{ currentYear }}</span>
            <button @click="nextMonth(); getContent()">›</button>
          </div>
          <div class="weekdays">
            <div v-for="day in daysOfWeek" :key="day">{{ day }}</div>
          </div>
          <div class="days">
            <div v-for="day in blankDays" :key="day" class="day blank"></div>
            <div v-for="day in daysInMonth" :key="day" class="day">
              <span>{{ day }}</span>
              <div v-if="events[day]" class="event" v-for="event in events[day]" :key="event.id" :style="{ backgroundColor: event.color }">
                {{ event.name }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import router from '@/router';

const goToReserve = () => router.push({ name: 'Reserve' });
const currentMonth = ref(new Date().getMonth());
const currentYear = ref(new Date().getFullYear());
const currentUserId = ref(1);

const events = ref({});

const daysOfWeek = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

const daysInMonth = computed(() => {
  const date = new Date(currentYear.value, currentMonth.value + 1, 0);
  return Array.from({ length: date.getDate() }, (_, i) => i + 1);
});

const blankDays = computed(() => {
  const firstDayOfMonth = new Date(currentYear.value, currentMonth.value, 1).getDay();
  return Array.from({ length: firstDayOfMonth });
});

const monthNames = [
  "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
  "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
];

const prevMonth = () => {
  if (currentMonth.value === 0) {
    currentMonth.value = 11;
    currentYear.value--;
  } else {
    currentMonth.value--;
  }
};

const nextMonth = () => {
  if (currentMonth.value === 11) {
    currentMonth.value = 0;
    currentYear.value++;
  } else {
    currentMonth.value++;
  }
};

async function getContent() {
  try {
    const response = await fetch('http://localhost:8080/reserva');
    const data = await response.json();
    populateEvents(data);
  } catch (error) {
    console.log("ERROUUUUU");
  }
};

function populateEvents(reservations) {
  events.value = {}; // Limpa os eventos do mês atual

  reservations.forEach(reservation => {
    const date = new Date(reservation.data);
    const localDate = new Date(date.getTime() + date.getTimezoneOffset() * 60000); // Ajuste para o fuso horário local

    const day = localDate.getDate();
    const month = localDate.getMonth();
    const year = localDate.getFullYear();

    if (currentMonth.value === month && currentYear.value === year) {
      if (!events.value[day]) {
        events.value[day] = [];
      }

      events.value[day].push({
        ...reservation,
        color: reservation.usuario.id === 1 ? 'green' : 'blue'
      });
    }
  });
}

onMounted(() => {
  getContent();
});
</script>

<style scoped>
.title {
  text-align: left;
  margin-top: 13px;
  margin-left: 120px;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%; 
}

.title h4 {
  font-size: 24px;
  font-weight: bold;
}

body, html {
  height: 100%;
  margin: 0;
  display: flex;
  justify-content: center; /* Centraliza horizontalmente */
  align-items: center; /* Centraliza verticalmente */
}

.container {
  width: 80%;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.header {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.header i {
  margin-bottom: 65px;
  margin-right: 50px;
}

.header-calendar {
  display: flex;
  align-items: center;
}

.icon {
  font-size: 2rem;
  margin-right: 10px;
}

.info {
  width: 300px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-start;
  margin-left: 40px;
  margin-top: 20px;
}

.info-month {
  margin-bottom: 40px;
}

.info-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 10px;
}

.info-item {
  display: flex;
  align-items: center;
  margin-top: 5px;
  margin-bottom: 25px;
}

.info-item h5 {
  margin-left: 5px;
}

.icon-verde {
  color: #0fbd26;
}

.icon-azul {
  color: rgb(15, 119, 189);
}

#new {
  font-size: 16px;
  padding: 8px;
  border-radius: 5px;
  border: 1px solid #c4c4c4;
  color: #4070ec;
  background-color: white;
  width: 280px; 
  text-align: center; 
}

#new:hover {
  color: white;
  background-color: #4070ec;
}


.Calendario {
  flex: 1;
  margin-left: 20px;
}

.calendar {
  width: 700px;
  height: 415px;
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: #fff;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  margin-top: 20px;
}

.header .bx {
  font-size: 1.5rem;
}

.header-calendar .bx {
  font-size: 1.5rem;
}

.header button {
  background: none;
  border: none;
  font-size: 1.5em;
  cursor: pointer;
}

.header-calendar button {
  background: none;
  border: none;
  font-size: 1.5em;
  cursor: pointer;
}

.weekdays {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  margin-bottom: 10px;
  font-weight: bold;
  text-align: center;
}

.days {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 8px;
}

.day {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 40px;
  background: #f9f9f9;
  border-radius: 4px;
  position: relative;
}

.day.blank {
  background: transparent;
}

.event {
  background: #8ab4f8;
  color: white;
  border-radius: 4px;
  padding: 2px 5px;
  font-size: 0.8em;
  margin-top: 2px;
  text-align: center;
}
</style>
